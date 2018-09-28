/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shane Partoredjo
 */
public class StedenTour {
    
    private List<Stad> steden;
    private Kaart kaart;
    
    public StedenTour(Kaart kaart, List<Stad> steden){
        //Get the map and cities
        this.kaart = kaart;
        this.steden = new ArrayList<>(steden);
    }
    
    //int a is the start city
    public StedenTour route(int a){
        ArrayList<Stad> route = new ArrayList<>();
        
        for(int i = 0; i < a; i++){
            route.add(steden.get(i));
        }
        
        //return a new StedenTour with all the cities
        return new StedenTour(kaart, route);
    }
    
    public List<Stad> getSteden() {
        return steden;
    }
}
