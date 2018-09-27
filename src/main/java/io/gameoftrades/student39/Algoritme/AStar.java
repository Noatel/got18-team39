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

        //Testing
        Testing.testingMap(kaart);
        //Remove testing


        setFirstSpot();


        //while openSet is not empty
        while (openSet.size() != 0) {
            //Assign the size of the openSet
            Spot currentSpot = getLowestSpot();

            openSet.remove(currentSpot);
            closedSet.add(currentSpot);

            //If the currentSpot is at the endLocation
            if (checkEndLocation(currentSpot)) {
                break;
            }

            //for each neighbor of current
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();
            for (Richting direction : directions) {
                checkDirections(direction, currentSpot);
            }

        }//End while

        //Probaly never comes here
        return getPath();
    }

    public Pad getPath() {
        //Creating a path, convert the arraylist to a array
        Richting[] path = route.toArray(new Richting[route.size()]);
        PadImpl reversePath = new PadImpl(path);

        //Reverse the array
        Pad finalPath = reversePath.omgekeerd();
        debug.debugPad(kaart, startCoordinate, finalPath);

        return finalPath;
    }

    public Spot getLowestSpot() {
        int openSetSize = openSet.size();

        //Assign the F Cost that we
        double cost = 0.00;

        //Assign the current spot so we can later override it
        Spot currentSpot = null;

        //current := the node in openSet having the lowest fScore[] value
        for (int i = 0; i < openSetSize; i++) {

            //If the current cost is heiger than 0 and the openSetSpot is lower F Score is lower or equal then the previous cost
            if (cost > 0.00 && cost <= openSet.get(i).getF()) {
                continue;
            }

            // Set the lowest and it's cost
            // and override the currentSpot with the lowest f Score Spot
            currentSpot = openSet.get(i);
            cost = currentSpot.getF();
        }


        return currentSpot;
    }

    public boolean checkEndLocation(Spot currentSpot) {
        Coordinaat currentCoordinate = currentSpot.getCoordinate();

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(endCoordinate);
        Spot endSpot = new Spot(endTerrain, endCoordinate);

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

            //Got the best path return
            return true;
        }
        return false;
    }

    private void checkDirections(Richting direction, Spot currentSpot) {
        //Look the current terrain
        Terrein neighbourTerrain = kaart.kijk(currentSpot.getTerrain(), direction);
        Spot neighbour = new Spot(neighbourTerrain, endCoordinate);

        //if neighbor in closedSet
        if (closedSet.contains(neighbour)) {
            return;
        }

        // The distance from start to a neighbor
        // tentative_gScore := gScore[current] + dist_between(current, neighbor)
        double tentative_gScore = neighbour.getG() + currentSpot.getCoordinate().afstandTot(neighbour.getCoordinate());

        // if neighbor not in openSet	// Discover a new node
        if (!openSet.contains(neighbour)) {
            openSet.add(neighbour);
            // else if tentative_gScore >= gScore[neighbor]
        } else if (tentative_gScore >= neighbour.getG()) {
            return;
        }
        setNeighbour(currentSpot, neighbour, tentative_gScore);
    }

    public void setNeighbour(Spot currentSpot, Spot neighbour, double tentative_gScore) {
        // This path is the best until now. Record it!
        //cameFrom[neighbor] := current
        neighbour.setPrevious(currentSpot);

        // gScore[neighbor] := tentative_gScore
        neighbour.setG(tentative_gScore);

        // fScore[neighbor] := gScore[neighbor] + heuristic_cost_estimate(neighbor, goal)
        neighbour.setF(neighbour.getG() + neighbour.getCoordinate().afstandTot(endCoordinate));
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
        startSpot.setF(startSpot.getG() + startSpot.getCoordinate().afstandTot(endCoordinate));

    }

    @Override
    public String toString() {
        return "A* ";
    }

}