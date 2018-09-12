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
        System.out.println(start.toString());

        //Define the end spot
        Terrein endTerrain = kaart.getTerreinOp(end);
        Spot endSpot = new Spot(endTerrain, end);

        System.out.println("size");
        System.out.println(openSet.size());


        //while openSet is not empty
        while (!openSet.isEmpty()) {
            int lowestSpot = 0;


            //current := the node in openSet having the lowest fScore[] value
            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(lowestSpot).getF()) {
                    lowestSpot = i;
                }
            }
            System.out.println("lowestSpot = " + lowestSpot);

            Spot currentSpot = openSet.get(lowestSpot);


            //First get the coordinate
            //Second compare the coordinate to the end coordinate
            String endCoordinate = endSpot.getCoordinate().toString();
            String currentCoordinate = openSet.get(lowestSpot).getCoordinate().toString();


            // if current = goal
            if (endCoordinate.equals(currentCoordinate)) {
                // return
                System.out.println("DONE");
            }


            //Remove from open, add to close
            openSet.remove(currentSpot);
            closedSet.add(currentSpot);


            // for each neighbor of current
            Richting[] directions = currentSpot.getTerrain().getMogelijkeRichtingen();

            for (Richting direction : directions) {
                Terrein currentTerrein = kaart.kijk(currentSpot.getTerrain(), direction);
                Spot tempSpot = new Spot(currentTerrein, end);

                // if neighbor in closedSet
                if (!closedSet.contains(tempSpot)) {

                    // The distance from start to a neighbor

                    int tempGScore = currentSpot.getG() + 1;

                    if (!openSet.contains(tempSpot)) {
                        if (tempGScore < tempSpot.getG()) {
                            tempSpot.setG(tempGScore);
                        }
                    } else {
                        tempSpot.setG(tempGScore);
                        openSet.add(tempSpot);
                    }


                    tempSpot.setH(tempSpot.getCoordinate().afstandTot(end));

                    //Turn H score dobule into a int
                    int hScore = (int) Math.round(tempSpot.getH());
                    tempSpot.setF(tempSpot.getG() + hScore);
                }


//                System.out.println("Spot added=" + tempSpot.toString());
            }


            //Check the neighbours
            System.out.println("size open" + openSet.size());
            System.out.println("size closed" + closedSet.size());


        }
//        PadImpl path = new PadImpl();
//        debug.debugPad(kaart, start, path);

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

