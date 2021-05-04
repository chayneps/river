package com.chayneps.river

import com.chayneps.river.model.CrossSection
import com.chayneps.river.model.CrossSectionPoint
import com.chayneps.river.service.RiverFlowServiceImpl
import spock.lang.Specification

class RiverFlowTest extends Specification{

    RiverFlowServiceImpl riverFlowService = new RiverFlowServiceImpl()

    def setup(){
        riverFlowService = new RiverFlowServiceImpl()
    }


    def "Calculate volume of water in 1 second on cross section 1x3"() {
        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,1)

        ])

        when:
        double result = riverFlowService.calVolume(crossSection,1)
        println "volume=$result"

        then:
        result == 3.0


    }

    def "Calculate volume of water in 1 second on cross section 1x3 but there is no velocity sensor at the banks"() {
        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,null),
                new CrossSectionPoint(UUID.randomUUID().toString(),1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,null)

        ])

        when:
        double result = riverFlowService.calVolume(crossSection,1)
        println "volume=$result"

        then:
        result == 3.0


    }


    def "Calculate volume of water in triangle shape cross section"() {
        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,0,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),1,1,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,2,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,3,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),4,2,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),5,1,2),
                new CrossSectionPoint(UUID.randomUUID().toString(),6,0,2),

        ])

        when:
        double result = riverFlowService.calVolume(crossSection,1)
        println "volume=$result"

        then:
        result == 18


    }

    def "Throw exception throw when the list does not have the closest bank"(){

        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,2,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,3,1),

        ])

        when:
        double result = riverFlowService.calVolume(crossSection,1)

        then:
        thrown IllegalArgumentException

    }


    def "Throw exception throw when the list does not have at least 2 point"(){

        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),

        ])

        when:
        double result = riverFlowService.calVolume(crossSection,1)

        then:
        thrown IllegalArgumentException

    }

    def "Calculate volume of water in 1 second on cross section 1x3 after update"() {
        given:

        String point2Id = UUID.randomUUID().toString();
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),
                new CrossSectionPoint(point2Id,1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,1)

        ])

        when:
        double result1 = riverFlowService.calVolume(crossSection,1)
        println "result1 volume=$result1"
        crossSection.get(point2Id).setVelocity(2)
        double result2 = riverFlowService.calVolume(crossSection,1)
        println "result2 volume=$result2"

        then:
        result1 < result2
        result1 == 3.0
        result2 == 4.0


    }



}
