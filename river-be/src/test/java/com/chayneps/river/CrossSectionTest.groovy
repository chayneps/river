package com.chayneps.river

import com.chayneps.river.model.CrossSection
import com.chayneps.river.model.CrossSectionPoint
import com.chayneps.river.service.RiverFlowServiceImpl
import spock.lang.Specification

class CrossSectionTest extends Specification {

    RiverFlowServiceImpl riverFlowService = new RiverFlowServiceImpl()

    def setup(){
        riverFlowService = new RiverFlowServiceImpl()
    }

    def "Add crossSectionPoint"(){

        given:
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,1)

        ])

        when:
        crossSection.put(
                new CrossSectionPoint(UUID.randomUUID().toString(),4,1,null))

        then:
        crossSection.size()==4


    }

    def "Update crossSectionPoint"(){

        given:
        String point2Id = UUID.randomUUID().toString();
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),
                new CrossSectionPoint(point2Id,1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,1)

        ])

        when:
        crossSection.get(point2Id).setVelocity(20)

        then:
        crossSection.get(point2Id).getVelocity()==20


    }

    def "Remove crossSectionPoint"(){

        given:
        String point2Id = UUID.randomUUID().toString();
        CrossSection crossSection=new CrossSection([
                new CrossSectionPoint(UUID.randomUUID().toString(),0,1,1),
                new CrossSectionPoint(point2Id,1,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),2,1,1),
                new CrossSectionPoint(UUID.randomUUID().toString(),3,1,1)

        ])

        when:
        crossSection.remove(point2Id)

        then:
        crossSection.size()==3


    }
}
