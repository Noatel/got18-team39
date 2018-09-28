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
    private ArrayList<Spot> openSet;
    private ArrayList<Spot> closedSet;
    private ArrayList<Richting> route = new ArrayList<>();
    private Kaart kaart = null;
    private Coordinaat startCoordinate = null;
    private Coordinaat endCoordinate = null;

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

    public AStar() {
    }

    public Pad bereken(Kaart kaart, Coordinaat start, Coordinaat end) {
        closedSet = new ArrayList<>();
        openSet = new ArrayList<>();

        this.reset();

        this.kaart = kaart;
        this.startCoordinate = start;
        this.endCoordinate = end;

        //clear the open and close set
        setFirstSpot();

        //while openSet is not empty
        while (openSet.size() != 0) {
            calculateBestPath();
        }//End while


        //Probaly never comes here
        return getPath();
    }

    private void calculateBestPath() {
        //Assign the size of the openSet
        Spot currentSpot = getLowestSpot();

        openSet.remove(currentSpot);
        closedSet.add(currentSpot);

        //If the currentSpot is at the endLocation
        if (checkEndLocation(currentSpot)) {
            return;
        }

        //for each neighbor of current
        Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

        for (Richting direction : directions) {
            checkDirections(direction, currentSpot);
        }

    }

    private Pad getPath() {
        //Creating a path, convert the arraylist to a array
        Richting[] path = route.toArray(new Richting[route.size()]);
        PadImpl reversePath = new PadImpl(path);

        //Reverse the array
        Pad finalPath = reversePath.omgekeerd();
        debug.debugPad(kaart, startCoordinate, finalPath);

        return finalPath;
    }

    private Spot getLowestSpot() {

        //Assign the current spot so we can later override it
        //And the F Score so we can grab the lowest value
        Spot currentSpot = null;
        double currentCost = 0.00;

        for(Spot checkSpot : openSet) {
            //If the current cost is heiger than 0 and the openSetSpot is lower F Score is lower or equal then the previous cost
            if (currentCost <= checkSpot.getF() && currentCost != 0.00)
                continue;


            // Set the lowest and it's cost
            // and override the currentSpot with the lowest f Score Spot
            currentSpot = checkSpot;
            currentCost = currentSpot.getF();
        }


        return currentSpot;
    }

    private boolean checkEndLocation(Spot currentSpot) {
        Coordinaat currentCoordinate = currentSpot.getCoordinate();

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(endCoordinate);
        Spot endSpot = new Spot(endTerrain, endCoordinate);

        if (currentCoordinate.equals(endSpot.getCoordinate())) {
            //Because we cant assign the size, we first going to make a arraylist
            //After that a array because Richting[] expect a array

            //Check if the currentSpot got previous
            while (currentSpot.getPrevious() != null) {
                currentSpot = getPreviousSpot(currentSpot);
            }

            //Got the best path return
            return true;
        }
        return false;
    }

    private Spot getPreviousSpot(Spot currentSpot) {

        Coordinaat lastSpot = currentSpot.getTerrain().getCoordinaat();
        Coordinaat lastPreviousSpot = currentSpot.getPrevious().getTerrain().getCoordinaat();

        Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);

        //Add the direction to the route
        route.add(direction);

        //Change the previous spot
        return currentSpot.getPrevious();
    }

    private void checkDirections(Richting direction, Spot currentSpot) {
        //Look the current terrain
        Terrein neighbourTerrain = kaart.kijk(currentSpot.getTerrain(), direction);
        Spot neighbour = new Spot(neighbourTerrain, endCoordinate, currentSpot);

        //if neighbor in closedSet
        if (closedSet.contains(neighbour))
            return;// Ignore the neighbor which is already evaluated.

        // if neighbor not in openSet
        if (!openSet.contains(neighbour)) {// Discover a new node
            openSet.add(neighbour);
        }
    }


    private void reset() {
        this.openSet.clear();
        this.closedSet.clear();
        this.route.clear();
    }

    private void setFirstSpot() {
        Terrein startTerrain = kaart.getTerreinOp(startCoordinate);
        Spot startSpot = new Spot(startTerrain, endCoordinate);

        openSet.add(startSpot);

        // The cost of going from start to start is zero.
        startSpot.setG(0);
        // For the first node, that value is completely heuristic.
    }

    @Override
    public String toString() {
        return "A* ";
    }

}