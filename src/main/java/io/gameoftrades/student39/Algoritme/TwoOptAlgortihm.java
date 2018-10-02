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
import io.gameoftrades.model.kaart.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noah Telussa
 */
public class TwoOptAlgortihm implements StedenTourAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();
    private ArrayList<Stad> new_route = new ArrayList<>();

    private Kaart map;

    public List<Stad> bereken(Kaart worldMap, List<Stad> allCities) {
        ArrayList<Stad> cities = new ArrayList<>(allCities);

        this.map = worldMap;

        double best_distance = calculateTotalDistance(cities);

        for (int i = 1; i < cities.size() - 1; i++) {
            for (int k = i + 1; k < cities.size(); k++) {
                //Swap the cities
                new_route = swap(cities, i, k);

                //Calculate the distance with the new tour
                double new_distance = calculateTotalDistance(new_route);

                //Check if the new distance is better

                if (new_distance < best_distance) {
                    best_distance = new_distance;
                    cities = new_route;

                    debug.debugSteden(map, new_route);
                }
            }
        }

        debug.debugSteden(map, new_route);

        for (Stad city : cities) {
            System.out.println(city.getNaam());
        }

        return cities;
    }

    private double calculateTotalDistance(ArrayList<Stad> cities) {
        //Keep track of all the distnace
        double distance = 0;
        AStar algorithm = new AStar();

        //Get the previous City
        Stad previousCity = cities.get(0);

        //Loop through all the cities that we get the distnace
        for (Stad city : cities) {

            //get distance from the previous city
            Pad newPath = algorithm.bereken(map, city.getCoordinaat(), previousCity.getCoordinaat());
            distance += newPath.getTotaleTijd();

            //current city will be the previous city next time
            previousCity = city;
        }

        //Give back the distance between all the cities
        return distance;
    }


    private ArrayList<Stad> swap(ArrayList<Stad> cities, int i, int k) {
        //Initilize the arraylist
        new_route = new ArrayList<>();

        //  1. take route[0] to route[i-1] and add them in order to new_route
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            new_route.add(cities.get(c));
        }

        //  2. take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for (int c = i; c <= k; c++) {
            new_route.add(cities.get(k - dec));
            dec++;
        }

        //3. take route[k+1] to end and add them in order to new_route
        for (int c = k + 1; c < size; c++) {
            new_route.add(cities.get(c));
        }

        // return new_route;
        return new_route;
    }

    @Override
    public String toString() {
        return "2-Opt local search algoritme";
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

}
