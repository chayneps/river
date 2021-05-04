package com.chayneps.river.service;

import com.chayneps.river.model.CrossSection;

public interface RiverFlowService {


    /**
     * Calculate volume of water pass through the cross section in seconds
     *
     * @param crossSection List of cross section points
     * @param seconds number of seconds
     * @return volume in cubic metres
     */
    double calVolume(CrossSection crossSection, double seconds);

}
