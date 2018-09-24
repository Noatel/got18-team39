package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.student39.PadImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class AStar implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();
    private ArrayList<Spot> openSet = new ArrayList<>();
    private ArrayList<Spot> closedSet = new ArrayList<>();
    private ArrayList<Richting> route = new ArrayList<>();
    private Spot currentSpot = null;

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
        Spot startSpot = new Spot(startTerrain, end);

        //Set the F Score
        startSpot.setF(startSpot.getG() + startSpot.getCoordinate().afstandTot(end));

        openSet.add(startSpot);

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);

        //Clear the route if there was a previous

        int lowestSpot = 0;

        //while openSet is not empty
        while (openSet.size() != 0) {
            //Check if the lowestSpot is heiger than 0

            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate

            //current := the node in openSet having the lowest fScore[] value
            
            for (int i = 0; i <= openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }
            }

            currentSpot = openSet.get(lowestSpot);

            openSet.remove(currentSpot);
            closedSet.add(currentSpot);

            Coordinaat currentCoordinate = currentSpot.getCoordinate();

            // if current = goal

            if (currentCoordinate.equals(endSpot.getCoordinate())) {
                route.clear();

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

                //Got the best path return
                break;
            }


            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();
            for (Richting direction : directions) {

                //Look the current terrain
                Terrein neighbourTerrain = kaart.kijk(currentSpot.getTerrain(), direction);
                Spot neighbourSpot = new Spot(neighbourTerrain, end);

                if (closedSet.contains(neighbourSpot)) {
                    continue;
                }

                double tentative_gScore = neighbourSpot.getG() + currentSpot.getCoordinate().afstandTot(neighbourSpot.getCoordinate());

                if (!openSet.contains(neighbourSpot)) {
                    openSet.add(neighbourSpot);
                } else if (tentative_gScore >= neighbourSpot.getG()) {
                    continue;
                }

                //Assign new F score
                neighbourSpot.setPrevious(currentSpot);
                neighbourSpot.setG(tentative_gScore);
                neighbourSpot.setF(neighbourSpot.getG() + neighbourSpot.getCoordinate().afstandTot(end));
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