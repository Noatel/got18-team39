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
    private Kaart map;

    public List<Stad> bereken(Kaart worldMap, List<Stad> allCities) {
        ArrayList<Stad> cities = new ArrayList<>(allCities);
        ArrayList<Stad> newTour;

        this.map = worldMap;
        double best_distance = calculateTotalDistance(cities);
        double new_distance;
        boolean check = true;
        int size = cities.size();


        //repeat until no best path found
        while (check) {
            check = true;

            for (int i = 1; i < size; i++) {
                for (int j = i; j < size - 1; j++) {
                    //new_route = 2optSwap(existing_route, i, k)
                    newTour = swap(cities, i, j);

                    //new_distance = calculateTotalDistance(new_route)
                    new_distance = calculateTotalDistance(newTour);

                    if (new_distance < best_distance) {
                        cities = newTour;
                        best_distance = calculateTotalDistance(cities);
                        debug.debugSteden(map, cities);

                        check = false;
                    }
                }
            }
        }
        debug.debugSteden(map, cities);
        return cities;
    }

    private double calculateTotalDistance(ArrayList<Stad> cities) {
        //Keep track of all the distnace
        double distance = 0;
        AStar algorithm = new AStar();

        //Get the previous City
        Stad previousCity = cities.get(cities.size() - 1);

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


    private static ArrayList<Stad> swap(ArrayList<Stad> cities, int i, int j) {
        //Initilize the arraylist
        ArrayList<Stad> newTour = new ArrayList<>();

        //  1. take route[0] to route[i-1] and add them in order to new_route
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(cities.get(c));
        }

        //  2. take route[i] to route[k] and add them in reverse order to new_route
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }

        //3. take route[k+1] to end and add them in order to new_route
        for (int c = j + 1; c < size; c++) {
            newTour.add(cities.get(c));
        }

        // return new_route;
        return newTour;
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
