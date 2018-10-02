/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Stad;
import java.util.ArrayList;
/**
 *
 * @author billy
 */
public class StedenGroep {
    private ArrayList<Stad> steden;
    
    private int totalEnergyCost;
    
    public StedenGroep(ArrayList<Stad> steden, int totalEnergyCost){
        this.steden = steden;
        this.totalEnergyCost = totalEnergyCost;
    }
    
    public int getTotalEnergyCost(){
        return totalEnergyCost;
    }
    
    public ArrayList<Stad> getSteden(){
        return this.steden;
    }
    //test
}
