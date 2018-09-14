package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.student39.PadImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AStar implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();
    private ArrayList<Spot> openSet = new ArrayList<>();
    private ArrayList<Spot> closedSet = new ArrayList<>();
    private ArrayList<Richting> route = new ArrayList<>();


    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

    public AStar() {
        //Creating something

    }

    public Pad bereken(Kaart kaart, Coordinaat start, Coordinaat end) {
        //Testing
        Testing.testingMap(kaart);
        //Remove testing


        System.out.println("currentXY=" + start.toString() + "-" + end.toString());


        //clear the open and close set
        openSet.clear();
        closedSet.clear();
        route.clear();


        //Get the terrein and  the openSet with the first Spot
//        System.out.print("startcoord=");
//        System.out.println(start.toString());

        Terrein startTerrain = kaart.getTerreinOp(start);
        Spot startSpot = new Spot(startTerrain, start);

        openSet.add(startSpot);

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);


        int k = 0;
        //while openSet is not empty
        int lowestSpot = 0;

        while (openSet.size() > 0) {

            //current := the node in openSet having the lowest fScore[] value
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }
            }

            System.out.println("lowesSpot=" + lowestSpot);

            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Spot currentSpot = openSet.get(lowestSpot);
            Coordinaat currentCoordinate = currentSpot.getCoordinate();

            // if current = goal
//            System.out.println("End spot=" + endSpot.getCoordinate().toString());
//            System.out.println("current spot=" + currentCoordinate.toString());

            if (currentCoordinate.equals(endSpot.getCoordinate())) {
//                System.out.println("endCoord=" + endSpot.getCoordinate().toString() + " currentCoord=" + currentCoordinate.toString());
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array

                //Get the previous spot and override
                Spot previousSpot = currentSpot;

                //Get the first
//                System.out.println("Start while currrentSPot.getPrevious() =" + previousSpot.getCoordinate().toString());
                while (previousSpot.getPrevious() != null) {
//                    System.out.println(previousSpot.getCoordinate().toString());
//                    System.out.println(previousSpot.getPrevious().getCoordinate().toString());

                    Coordinaat lastSpot = previousSpot.getTerrain().getCoordinaat();
                    Coordinaat lastPreviousSpot = previousSpot.getPrevious().getTerrain().getCoordinaat();

                    Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);

//                    System.out.println("from=" + lastSpot.toString() + " to=" + lastPreviousSpot.toString());
//                    System.out.println("direction=" + direction);
//                    System.out.println("from=" + previousSpot.getTerrain().getTerreinType() + " to=" + previousSpot.getPrevious().getTerrain().getTerreinType());

                    route.add(direction);

                    //Change the previous spot
                    System.out.println("coords=" + previousSpot.getCoordinate());

                    previousSpot = previousSpot.getPrevious();
                }

//              System.out.println("routesize(" + route.size() + ")");
//              System.out.println("end while currrentSPot.getPrevious()");

                //Creating a path, convert the arraylist to a array
                Richting[] path = route.toArray(new Richting[route.size()]);
                PadImpl reversePath = new PadImpl(path);

                //Testing
                for (Richting richting : path) {
                    System.out.println("to-" + richting + " ");
                }
                //EndTesting

                //Reverse the array
                Pad finalPath = reversePath.omgekeerd();
                debug.debugPad(kaart, start, finalPath);

                System.out.println("DONE");
                System.out.println("amount" + finalPath.getTotaleTijd());

                //Got the path , return it
                return finalPath;
            }


            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

            for (Richting direction : directions) {
                System.out.println("coords=" + currentSpot.getCoordinate().toString());

                System.out.println(direction);
                Terrein currentTerrein = kaart.kijk(currentSpot.getTerrain(), direction);

                Spot tempSpot = new Spot(currentTerrein, end);

                // if neighbor in closedSet
                if (!this.containClosed(tempSpot)) {

                    // The distance from start to a neighbor
                    int tempGScore = currentSpot.getG() + 1;

                    if (this.containOpen(tempSpot)) {
                        if (tempGScore < tempSpot.getG()) {
                            tempSpot.setG(tempGScore);
                        }
                    } else {
                        tempSpot.setG(tempGScore);
                        openSet.add(tempSpot);
                    }

                    tempSpot.setH(tempSpot.getCoordinate().afstandTot(end));
                    tempSpot.setPrevious(currentSpot);
                    tempSpot.setF(tempSpot.getG() + tempSpot.getH());
                }
            }
            //Remove from open, add to close
            openSet.remove(currentSpot);
            closedSet.add(currentSpot);

        }

        //Probaly never comes here
        return null;
    }


    @Override
    public String toString() {
        return "A* ";
    }

    //https://stackoverflow.com/questions/43451080/java-check-if-arraylistcars-contains-object
    public boolean containClosed(Spot spot) {
        return closedSet.contains(spot);
    }

    public boolean containOpen(Spot spot) {
        return openSet.contains(spot);
    }
}

