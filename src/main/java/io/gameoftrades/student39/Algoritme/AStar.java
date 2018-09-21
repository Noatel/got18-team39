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
        Spot startSpot = new Spot(startTerrain, start);

        openSet.add(startSpot);

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);

        //Clear the route if there was a previous
        route.clear();

        int lowestSpot = 0;

        //while openSet is not empty
        while (openSet.size() != 0) {
            if (lowestSpot > 0) {
                lowestSpot = lowestSpot - 1;
            }

            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Spot currentSpot = openSet.get(lowestSpot);

            //current := the node in openSet having the lowest fScore[] value
            for (int i = 0; i < openSet.size(); i++) {

                Spot tempSpot = openSet.get(i);
                Spot lastSpot = openSet.get(lowestSpot);

                if (currentSpot.getF() >= tempSpot.getF()) {
                    lowestSpot = i;
                }
            }



            openSet.remove(currentSpot);
            closedSet.add(currentSpot);

            Coordinaat currentCoordinate = currentSpot.getCoordinate();

            // if current = goal
            if (currentCoordinate.equals(endSpot.getCoordinate())) {
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array

                //Check if the currentSpot got previous
                while (currentSpot.getPrevious() != null) {

                    Coordinaat lastSpot = currentSpot.getTerrain().getCoordinaat();
                    Coordinaat lastPreviousSpot = currentSpot.getPrevious().getTerrain().getCoordinaat();

                    Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);

                    //Add the direction to the route
                    route.add(direction);

                    //Change the previous spot
                    currentSpot = currentSpot.getPrevious();
                }
            }

            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

            for (Richting direction : directions) {

                //Look the current terrain
                Terrein currentTerrain = kaart.kijk(currentSpot.getTerrain(), direction);
                Spot tempSpot = new Spot(currentTerrain, end);

                if (!closedSet.contains(tempSpot)) {
                    double HScore = tempSpot.getCoordinate().afstandTot(end);
                    double FScore = tempSpot.getG() + HScore;

                    if (!openSet.contains(tempSpot)) {
                        openSet.add(tempSpot);
                    }

                    //Assign new F score
                    tempSpot.setPrevious(currentSpot);
                }

            }

        }//End while

        //Creating a path, convert the arraylist to a array
        Richting[] path = route.toArray(new Richting[route.size()]);
        PadImpl reversePath = new PadImpl(path);

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

}