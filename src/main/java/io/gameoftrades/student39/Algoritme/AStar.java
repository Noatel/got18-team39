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
//                    openSet.remove(lowestSpot);
                }
            }


            //Get the lowest f score spot in the arraylist
            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Spot currentSpot = openSet.get(lowestSpot);
            closedSet.add(currentSpot);

            Coordinaat currentCoordinate = currentSpot.getCoordinate();



            // if current = goal
            if (currentCoordinate.equals(endSpot.getCoordinate())) {
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array
                //Get the previous spot and override
                Spot previousSpot = currentSpot;

                //Get the first
                while (previousSpot.getPrevious() != null) {
                    Coordinaat lastSpot = previousSpot.getTerrain().getCoordinaat();
                    Coordinaat lastPreviousSpot = previousSpot.getPrevious().getTerrain().getCoordinaat();

                    System.out.println(lastSpot);
                    Richting direction = Richting.tussen(lastSpot, lastPreviousSpot);

                    route.add(direction);

                    //Change the previous spot
                    previousSpot = previousSpot.getPrevious();
                }
                //Creating a path, convert the arraylist to a array
                //Got the path , return it
                break;
            }

            // for each direction of current Spot
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();



            for (Richting direction : directions) {
                Terrein currentTerrein = kaart.kijk(currentSpot.getTerrain(), direction);
                Spot tempSpot = new Spot(currentTerrein, end);

                // if neighbor in closedSet
                if (!closedSet.contains(tempSpot)) {

                    // The distance from start to a neighbor
                    double tempGScore = currentSpot.getG() + 1;

                    System.out.println("currentTempGScore" + tempGScore);
                    System.out.println("pot.getG()" + tempSpot.getG());


                    if (openSet.contains(tempSpot) && closedSet.contains(tempSpot)) {
                        if (tempGScore < tempSpot.getG()) {
                            System.out.println("===========================");
                            System.out.println(tempGScore);
                            System.out.println("===========================");
                            tempSpot.setG(tempGScore);
                        }
                    } else{
                        tempSpot.setG(tempGScore);
                        openSet.add(tempSpot);
                    }

                    tempSpot.setH(tempSpot.getCoordinate().afstandTot(end));
                    tempSpot.setPrevious(currentSpot);
                    tempSpot.setF(tempSpot.getG() + tempSpot.getH());
                    System.out.println("F="+tempSpot.getF());
                }
            }


            //Remove from open, add to close
            openSet.remove(currentSpot);

        }//End while

        //Creating a path, convert the arraylist to a array
        Richting[] path = route.toArray(new Richting[route.size()]);
        PadImpl reversePath = new PadImpl(path);


        //Reverse the array
        Pad finalPath = reversePath.omgekeerd();
        debug.debugPad(kaart, start, finalPath);

        System.out.println("DONE");
        System.out.println("amount" + finalPath.getTotaleTijd());

        //Probaly never comes here
        return finalPath;

    }


    @Override
    public String toString() {
        return "A* ";
    }

    public boolean hasDuplicatesInArrayList(ArrayList<Spot> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) == list.get(j)) {
                    return true;
                }
            }
        }
        return false;
    }
}

