/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shane Partoredjo
 */
public class StedenAlgoritme implements StedenTourAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

    public List<Stad> bereken(Kaart kaart, List<Stad> steden) {
        return null;
    }

    //Function for calculating fastest time going between cities
    public StedenTour snelsteTijd(Kaart kaart, List<Stad> steden) {
        //Adding all cities to the list
        List<Stad> stad = new ArrayList<>();
        stad.addAll(steden);

        //Returning null for now, not done yet
        return null;
    }

    @Override
    public String toString() {
        return "Snelste tour ";
    }
}
