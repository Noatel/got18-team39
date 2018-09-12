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
        this.testing(kaart);
        //Remove testing

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
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }
            }

            //Get the lowest f score spot in the arraylist
            Spot currentSpot = openSet.get(lowestSpot);

            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            Coordinaat currentCoordinate = openSet.get(lowestSpot).getCoordinate();

            // if current = goal
            System.out.println("End spot=" + endSpot.getCoordinate().toString());
            System.out.println("current spot=" + currentCoordinate.toString());

            if (endSpot.getCoordinate().toString().equals(currentCoordinate.toString())) {
                //Because we cant assign the size, we first going to make a arraylist
                //After that a array because Richting[] expect a array

                ArrayList<Richting> route = new ArrayList<>();
                Spot previousSpot = currentSpot.getPrevious();

                //Get the first
                while (previousSpot.getPrevious() != null) {
                    Richting direction = Richting.tussen(previousSpot.getCoordinate(),previousSpot.getPrevious().getCoordinate());
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
                System.out.println("terrein="+currentTerrein.toString());
                Spot tempSpot = new Spot(currentTerrein, end);

                // if neighbor in closedSet
                System.out.println(!closedSet.contains(tempSpot));
                if (!closedSet.contains(tempSpot)) {

                    // The distance from start to a neighbor
                    int tempGScore = currentSpot.getG() + 1;

                    System.out.println("thisone("+openSet.contains(tempSpot)+")");
                    if (openSet.contains(tempSpot)) {
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
            System.out.println("OpensetSize (" + openSet.size() + ") ClosedSetSize (" + closedSet.size() + ")");

        }

        return null;
    }

    public void testing(Kaart kaart) {
        Scanner scanner = new Scanner(kaart.toString());
        System.out.println(kaart.toString());
        //check each line in scanner
        int x = 0;
        int y = 0;

        int test = 1;
        try {
            while (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                value = value.replaceAll("\\s+", "");

                testPrintWithCitiesAndSea(value, x, y);
//                testPrintCoordinates(value, x, y);

                x = 0;
                y++;
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println(e);
        }
    }

    public void testPrintWithCitiesAndSea(String value, int x, int y) {
        for (char c : value.toCharArray()) {
            if (c == 'S') {
                System.out.print("[ S ]");
            } else if (c == 'Z') {
                System.out.print("[ Z ]");
            } else {

                System.out.print("[" + (x) + "," + (y) + "]");
            }
            x++;
        }
        System.out.println("");
    }

    public void testPrintWithSea(String value, int x, int y) {
        for (char c : value.toCharArray()) {

            if (c == 'Z') {
                System.out.print("[ Z ]");
            } else {

                System.out.print("[" + (x) + "," + (y) + "]");
            }
            x++;
        }
        System.out.println("");
    }

    public void testPrintCoordinates(String value, int x, int y) {
        for (char c : value.toCharArray()) {
            System.out.print("[" + (x) + "," + (y) + "]");
            x++;
        }
        System.out.println("");

    }

    @Override
    public String toString() {
        return "A* ";
    }

}

