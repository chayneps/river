package com.chayneps.river.service;

import com.chayneps.river.model.CrossSection;
import com.chayneps.river.model.CrossSectionPoint;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Iterator;

@Service
public class RiverFlowServiceImpl implements RiverFlowService {


    /**
     * Calculate volume of water pass through the cross section in seconds
     *
     * @param crossSection List of cross section points
     * @param seconds number of seconds
     * @return volume in cubic metres
     */
    public double calVolume(CrossSection crossSection, double seconds){

        int crossSectionSize = crossSection.size();

        if(crossSectionSize<2)
            throw new IllegalArgumentException("Need more 2 or more crossSectionPoint");

        if(crossSection.firstEntry().getDistance()!=0)
            throw new IllegalArgumentException("List of cross section point must start with the closest bank when distance=0");


        Iterator<CrossSectionPoint> iter = crossSection.iterator();
        double result=0;
        int count=0;

        CrossSectionPoint formerPoint=null;
        CrossSectionPoint thisPoint=null;
        CrossSectionPoint nextPoint=null;

        while(count<crossSectionSize){

            formerPoint = thisPoint;
            thisPoint = nextPoint==null?iter.next():nextPoint;
            nextPoint = count<crossSectionSize-1?iter.next():null;

            if(count>0){ //If it's not the closest bank calculate the volume before the point

                //In case of Velocity at the closest bank is null get Velocity from the next one
                double thisVelocity = thisPoint.getVelocity()==null?
                                            formerPoint.getVelocity():thisPoint.getVelocity();

                result = result +(
                            calTrapezoidArea(
                                    thisPoint.getDepth(),
                                    middleDepth(formerPoint.getDepth(), thisPoint.getDepth()),
                                    (thisPoint.getDistance()-formerPoint.getDistance())/2)
                            * thisVelocity
                );

            }


            if(count<crossSectionSize-1){ //If it's farthest bank calculate the volume after the point

                //In case of Velocity at the farther bank is null get Velocity from the former one
                double thisVelocity = thisPoint.getVelocity()==null?
                        nextPoint.getVelocity():thisPoint.getVelocity();

                result = result +(
                            calTrapezoidArea(
                                    thisPoint.getDepth(),
                                    middleDepth(thisPoint.getDepth(),nextPoint.getDepth()),
                                    (nextPoint.getDistance()- thisPoint.getDistance())/2)

                            * thisVelocity
                        );

            }

            count++;

        }

        return result;

    }



    /**
     *
     * @param depth1
     * @param depth2
     * @return the depth between 2 depths
     */
    private double middleDepth(double depth1, double depth2){
        return Math.abs(depth1-depth2)/2 + Math.min(depth1, depth2);
    }

    /**
     *
     * @param a parallel side A
     * @param b parallel side B
     * @param height the height between side A and side B
     * @return area of trapezoid
     */
    private double calTrapezoidArea(double a, double b, double height){
        return ((a+b)/2)*height;
    }


}
