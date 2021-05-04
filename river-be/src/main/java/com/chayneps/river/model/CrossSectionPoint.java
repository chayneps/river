package com.chayneps.river.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Class represent cross section point in river cross section.
 *
 * distance = distance from the closest bank in metres
 * depth = the depth at the point in metres
 * velocity = velocity of stream at the point in m/s
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CrossSectionPoint {

    @EqualsAndHashCode.Include
    private final String id;
    private final double distance;
    private final double depth;
    private Double velocity;

    public synchronized Double getVelocity(){
        return velocity;
    }

    public synchronized void setVelocity(Double velocity){
        this.velocity = velocity;
    }



}
