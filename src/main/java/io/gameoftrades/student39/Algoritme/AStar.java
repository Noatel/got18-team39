package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.student39.PadImpl;
import sun.security.ssl.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AStar implements SnelstePadAlgoritme, Debuggable {

    private Debugger debug = new DummyDebugger();
    private ArrayList<Spot> openSet = new ArrayList<>();
    private ArrayList<Spot> closedSet = new ArrayList<>();


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

        openSet.add(startSpot);

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);


        //while openSet is not empty
        while (!openSet.isEmpty()) {
            int lowestSpot = 0;


            //current := the node in openSet having the lowest fScore[] value
            for (int i = 0; i < openSet.size(); i++) {
                System.out.println(openSet.get(i).getF() + " < " + openSet.get(lowestSpot).getF());
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }
            }

            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Spot currentSpot = openSet.get(lowestSpot);
            Coordinaat currentCoordinate = currentSpot.getCoordinate();

            // if current = goal
            System.out.println("End spot=" + endSpot.getCoordinate().toString());
            System.out.println("current spot=" + currentCoordinate.toString());

            if (endSpot.getCoordinate().equals(currentCoordinate)) {
                System.out.println("endCoord=" + endSpot.getCoordinate().toString() + " currentCoord=" + currentCoordinate.toString());
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array

                ArrayList<Richting> route = new ArrayList<>();
                Spot previousSpot = currentSpot.getPrevious();

                //Get the first
                while (previousSpot.getPrevious() != null) {
                    Coordinaat lastSpot = previousSpot.getCoordinate();
                    Coordinaat lastPreviousSpot = previousSpot.getPrevious().getCoordinate();

                    Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);

//                    System.out.println("from="+lastSpot.toString()+" to="+lastPreviousSpot.toString());
//                    System.out.println("direction="+direction);

                    route.add(direction);

                    previousSpot = previousSpot.getPrevious();
                }


                //Creating a path, convert the arraylist to a array
                Richting[] path = route.toArray(new Richting[route.size()]);
                PadImpl finalPath = new PadImpl(path);
                debug.debugPad(kaart, start, finalPath);

                System.out.println("DONE");
            }


            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

            for (Richting direction : directions) {

                System.out.println(direction);
                Terrein currentTerrein = kaart.kijk(currentSpot.getTerrain(), direction);

                System.out.println("terrein=" + currentTerrein.toString());
                Spot tempSpot = new Spot(currentTerrein, end);

                // if neighbor in closedSet
                System.out.println("closed=" + !this.containClosed(tempSpot));
                if (!this.containClosed(tempSpot)) {

                    // The distance from start to a neighbor
                    int tempGScore = currentSpot.getG() + 1;

                    System.out.println("open=" + !this.containOpen(tempSpot));
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

            //testing
            for (int i = 0; i < openSet.size(); i++) {
                System.out.println( openSet.get(i).toString());
            }
            System.exit(0);
            //end testing

            //Remove from open, add to close
            openSet.remove(currentSpot);
            closedSet.add(currentSpot);
            System.out.println("OpensetSize (" + openSet.size() + ") ClosedSetSize (" + closedSet.size() + ")");

        }

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

