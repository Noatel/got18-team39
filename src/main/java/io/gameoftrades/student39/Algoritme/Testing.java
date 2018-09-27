package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Kaart;

import java.util.ArrayList;
import java.util.Scanner;

public class Testing {

    public static void testingMap(Kaart kaart) {
        Scanner scanner = new Scanner(kaart.toString());
        System.out.println(kaart.toString());
        int x = 0;
        int y = 0;

        while (scanner.hasNextLine()) {
            String value = scanner.nextLine();
            value = value.replaceAll("\\s+", "");
            testPrintWithCitiesAndSea(value, x, y);
            x = 0;
            y++;
        }
    }

    public static void testPrintWithCitiesAndSea(String value, int x, int y) {
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

    public static void testPrintWithSea(String value, int x, int y) {
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

    public static void testPrintCoordinates(String value, int x, int y) {
        for (char c : value.toCharArray()) {
            System.out.print("[" + (x) + "," + (y) + "]");
            x++;
        }
        System.out.println("");

    }

    public static void printAllSpotsInArray(ArrayList<Spot> spots) {

        int i = 0;
        System.out.println();
        System.out.println("=================");
        for (Spot spot : spots) {
            System.out.println(i + ". " + spot.toString());
            i++;
        }
        System.out.println("=================");
        System.out.println();


    }

}
