package com.chayneps.river.model;

import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import java.util.*;


public class CrossSection {

    private final TreeMultiset<CrossSectionPoint> crossSectionPoints = TreeMultiset.create(
                    Comparator.comparingDouble(CrossSectionPoint::getDistance));


    private final HashMap<String,CrossSectionPoint> idMap = new HashMap<>();


    public synchronized void put(CrossSectionPoint cp){
        if(idMap.containsKey(cp.getId()))
            throw new IllegalStateException("id duplicated");

        crossSectionPoints.add(cp);
        idMap.put(cp.getId(),cp);
    }

    public synchronized CrossSectionPoint get(String id){
        return idMap.get(id);
    }

    public synchronized void remove(String id){

        if(!idMap.containsKey(id))
            return;

        CrossSectionPoint cp = idMap.get(id);

        crossSectionPoints.remove(cp);
        idMap.remove(id);

    }

    public synchronized CrossSectionPoint firstEntry(){

        Multiset.Entry<CrossSectionPoint> firstEntry = crossSectionPoints.firstEntry();

        return firstEntry!=null? firstEntry.getElement():null;
    }

    public synchronized int size(){
        return crossSectionPoints.size();
    }

    public Iterator<CrossSectionPoint> iterator(){
        return crossSectionPoints.iterator();
    }


    public CrossSection(){

    }

    public CrossSection(List<CrossSectionPoint> list){
        list.forEach(this::put);
    }

}
