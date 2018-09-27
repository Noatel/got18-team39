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
    private ArrayList<Spot> openSet = new ArrayList<Spot>();
    private ArrayList<Spot> closedSet = new ArrayList<Spot>();
    private ArrayList<Richting> route = new ArrayList<>();
    private Spot startSpot = null;

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

    public AStar() {
    }

    public Pad bereken(Kaart kaart, Coordinaat start, Coordinaat end) {
        //Testing
        Testing.testingMap(kaart);
        //Remove testing

        //clear the open and close set
        openSet.clear();
        closedSet.clear();


        //Get the terrein and  the openSet with the first Spot
        Terrein startTerrain = kaart.getTerreinOp(start);
        startSpot = new Spot(startTerrain, start);

        openSet.add(startSpot);

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);

        //Clear the route if there was a previous
        route.clear();

        int lowestSpot = 0;
        //while openSet is not empty
        while (openSet.size() != 0) {
            //current := the node in openSet having the lowest fScore[] value
            for (int i = 1; i < openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }

                //If equal
                if (openSet.get(i).getF() == openSet.get(lowestSpot).getF()) {
                    //Prefer to explore options with longer known paths (closer to goal)
                    if (openSet.get(i).getG() > openSet.get(lowestSpot).getG()) {
                        lowestSpot = i;
                    }
                }
            }


            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Spot currentSpot = openSet.get(lowestSpot);


            openSet.remove(currentSpot);
            closedSet.add(currentSpot);


            Coordinaat currentCoordinate = currentSpot.getCoordinate();

            // if current = goal
//            System.out.println("current="+currentCoordinate.toString());
//            System.out.println("end="+endSpot.getCoordinate().toString());
            if (currentCoordinate.equals(endSpot.getCoordinate())) {
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array

                System.out.println(currentSpot.toString());

                //Check if the currentSpot got previous
                while (currentSpot.getPrevious() != null) {
                    Coordinaat lastSpot = currentSpot.getTerrain().getCoordinaat();
                    Coordinaat lastPreviousSpot = currentSpot.getPrevious().getTerrain().getCoordinaat();

                    System.out.println(lastSpot);
                    Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);
                    System.out.println("Directions from to = " + direction.toString());

                    TerreinType terreinType = currentSpot.getPrevious().getTerrain().getTerreinType();
                    double gCost = currentSpot.getG() + terreinType.getBewegingspunten();
                    System.out.println(gCost);

                    currentSpot.setG(gCost);
                    route.add(direction);

                    //Change the previous spot
                    currentSpot = currentSpot.getPrevious();
                }

                //Creating a path, convert the arraylist to a array
                //Got the path , return it
                break;
            }

            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

            for (Richting direction : directions) {
                //https://stackoverflow.com/questions/11260102/declaring-object-as-final-in-java
                final Terrein currentTerrein = kaart.kijk(currentSpot.getTerrain(), direction);
                final Spot tempSpot = new Spot(currentTerrein, end);

                if (closedSet.contains(tempSpot)) {
                    continue;
                }

                double gScore = tempSpot.getG() + currentSpot.getCoordinate().afstandTot(tempSpot.getCoordinate());

                System.out.println("GScore=" + gScore);
                tempSpot.setG(gScore);
                if (!openSet.contains(tempSpot)) {
                    System.out.println(tempSpot.getPrevious());
                    tempSpot.setPrevious(currentSpot);
                    openSet.add(tempSpot);
                } else if (gScore >= tempSpot.getG()) {
                    continue;
                }

            }

        }//End while

        //Creating a path, convert the arraylist to a array
        Richting[] path = route.toArray(new Richting[route.size()]);
        PadImpl reversePath = new PadImpl(path);

        System.out.println("Route size = " + route.size());

        //Reverse the array
        Pad finalPath = reversePath.omgekeerd();
        debug.debugPad(kaart, start, finalPath);


        //Probaly never comes here
        return finalPath;
    }


    @Override
    public String toString() {
        return "A* ";
    }
    //testing github connection
    //hello world!
}