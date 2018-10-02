/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.student39.StedenPad;
import io.gameoftrades.student39.Algoritme.AStar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author billy
 */
public class StedenTourAlgorithm implements StedenTourAlgoritme, Debuggable {

    /**
     * debugger
     */
    private Debugger debug;
    /**
     * map
     */
    private Kaart kaart;
    /**
     * lijst van de steden
     */
    private List<Stad> steden;
    /**
     * lijst van de steden
     */
    private List<Stad> vasteSteden;
    /**
     * lijst van pad van steden
     */
    private ArrayList<StedenPad> stedenPad;
    /**
     * lijst van steden
     */
    private ArrayList<StedenGroep> stedenGroep;
    /**
     * snelste pad algoritme
     */
    private SnelstePadAlgoritme snelstePadAlgoritme;

    /**
     *
     * @param kaart kaart
     * @param stedenLijst stedenlijst
     * @return kaart, stedenLijst
     */
    public List<Stad> bereken(Kaart kaart, List<Stad> stedenLijst) {
        /*
        set kaart
         */
        kaart = kaart;
        /*
        initialiseert stedenPad en algortime
         */
        stedenPad = new ArrayList<>();
        snelstePadAlgoritme = new AStar();
        /*
        initialiseert steden, vasteSteden en stedenGroep
         */
        steden = new ArrayList<>(stedenLijst);
        vasteSteden = new ArrayList<>(steden);

        stedenGroep = new ArrayList<>();

        /*
        run algortime
         */
        run();
        /*
        welke pad het minst energy kost
         */
        int padEnergy = -1;
        int x = 0;

        /*
        loop door de lijst van stedenGroep en kijk welke pad het korste is en minst energy gebruikt
         */
        for (int i = 0; i < stedenGroep.size(); i++) {
            if (padEnergy == -1 || stedenGroep.get(i).getTotalEnergyCost() < padEnergy) {
                padEnergy = stedenGroep.get(i).getTotalEnergyCost();
                x = i;
            }
        }

        /*
        debug message
         */
        System.out.println("Snelste route gebruikt 'Snelste route' " + stedenGroep.get(x).getTotalEnergyCost());
        System.out.println("Pad geleerd" + stedenGroep.size() + ".");

        /*
        toon visueel pad op kaart
         */
        debug.debugSteden(kaart, this.stedenGroep.get(x).getSteden());
        /*
        get stedenGroep en return snelste pad
         */
        return stedenGroep.get(x).getSteden();
    }
    /**
     * 
     * @param snelsteRoute snelsteroute tussen steden
     * @param padLengte afstand tussen steden
     * @param i steden index
     * @return afstand tussen steden
     */
    private int getPadLengte(ArrayList<Stad> snelsteRoute, int padLengte, int i) {
        /*
        status van doel
        */
        boolean doel = false;
        /*
        loop door de lijst van stedenPad
        */
        for (StedenPad stedenpad : stedenPad) {
            if ((!stedenpad.getStart().equals(snelsteRoute.get(snelsteRoute.size() - 1).getCoordinaat())
                    || !stedenpad.getEnd().equals(steden.get(i).getCoordinaat()))
                    && (!stedenpad.getEnd().equals(snelsteRoute.get(snelsteRoute.size() - 1).getCoordinaat())
                    || !stedenpad.getStart().equals(steden.get(i).getCoordinaat()))) {
                continue;
            }

            //padLengte = stedenPad.getLengte();
            doel = true;
        }
        /*
        voeg pad toe als deze nog niet bekend is
        */
        if (!doel) {
            padLengte = berekenSnelstePad(snelsteRoute.get(snelsteRoute.size() - 1), steden.get(i));
        }
        /*
        return de afstand van de pad
        */
        return padLengte;
    }
    /**
     * Bereken snelste pad tussen twee steden
     * @param een eerste stad
     * @param twee tweede stad
     * @return energykosten tussen twee steden
     */
    private int berekenSnelstePad(Stad een, Stad twee) {
        /*
        bereken snelste pad tussen twee steden
        */
        final Pad pad = snelstePadAlgoritme.bereken(this.kaart, een.getCoordinaat(), twee.getCoordinaat());
        /*
        voeg pad toe tot stedenPad
        */
        stedenPad.add(new StedenPad(een, twee, pad));
        /*
        return de totale tijd voor de pad
        */
        return pad.getTotaleTijd();
    }

    /**
     * zoek de optimaal route
     */
    public void run() {
        /*
        loop door de vasteSteden lijst
         */
        for (int start = 0; start < vasteSteden.size(); start++) {
            /*
            maak een ijst en voeg de snelste route toe met de energy verbruikt
            */
            final ArrayList<Stad> snelsteRoute = new ArrayList<>();
            int totalEnergyCost = 0;
            
            /*
            add steden
            */
            snelsteRoute.add(this.steden.get(start));
            this.steden.remove(start);
            int aantalSteden = 0;
            
            /*
            loop tot er geen steden meer zijn
            */
            while (!this.steden.isEmpty()) {
                int snelstePad = -1;
                int padLengte = 0;
                
                /*
                loop door de steden lijst
                */
                for (int i = 0; i < steden.size(); i++) {
                    /*
                    kijk of al langs deze is geweest
                    */
                    if (!stedenPad.isEmpty()) {
                        //padLengte = getPadLengte(snelsteRoute, padLengte ,i);
                    } else {
                        //padLengte = berekenSnelstePad(snelsteRoute.get(snelsteRoute.size() -1), steden.get(i));
                    }
                    /*
                    kijk welke pad is snelste, en gebruik die als snelste route
                    */
                    if (snelstePad == -1 || padLengte <= snelstePad) {
                        snelstePad = padLengte;
                        aantalSteden = i;
                    }
                }
                /*
                energyverbruik
                */
                totalEnergyCost = totalEnergyCost + snelstePad;
                snelsteRoute.add(this.steden.get(aantalSteden));
                this.steden.remove(aantalSteden);
            }
            /*
            voeg snelste route en de energyverbruik
            */
            this.stedenGroep.add(new StedenGroep(snelsteRoute, totalEnergyCost));
            /*
            empty ArrayList
            */
            this.steden = new ArrayList<>(vasteSteden);
            /*
            debug message
            */
            System.out.println("Start " + start + "'Snelste pad' combinatie berekend...");
        }
    }
    /**
     * 
     * @return algoritme naam 
     */
    @Override
    public String toString() {
        return "Snelste pad Algoritme";
    }
    
    @Override
    public void setDebugger(Debugger debug) {
        this.debug = debug;
    }
    //test
}
