package io.gameoftrades.student39;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Markt;

import java.util.List;
import java.util.Scanner;


public class WereldLaderImpl implements WereldLader {


    private int[] coordinate = new int[2];

    //The world
    private Wereld wereld;
    private Kaart kaart;


    //Cities and trade
    private List<Stad> steden;
    private List<Handel> handel;


    @Override
    public Wereld laad(String resource) {
        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //

        int i = 0;
        int amountCities = 0;

        //initialize scanner and load the file
        Scanner scanner = new Scanner(this.getClass().getResourceAsStream(resource));


        //check each line in scanner
        while (scanner.hasNextLine()) {
            String value = scanner.nextLine();

            //Put the scanner data in a string

            //Because the first line always represent the coords
            if (i == 0) {
                //Split the data from the scanner and put it in the
                //Int array coords
                String[] p = value.split(",");

                //Convert the data coords to int
                for (int j = 0; j < p.length; j++) {
                    coordinate[j] = Integer.parseInt(p[j]);
                }

                //Create the map, width - height
                kaart = new Kaart(coordinate[0], coordinate[1]);

            }

            //If it is a integer AND its not the first row
            //Numbers of how many cities there are
            if (isInteger(value) && i > 0) {

                amountCities = Integer.parseInt(value);
                System.out.println("Amount of cities="+amountCities);

                //If the amount of cities is heiger then 0
                if (amountCities > 0) {

                    System.out.println("(" + value + ")");
                    Coordinaat coordinaat = Coordinaat.op(coordinate[0], coordinate[1]);
                    Stad stad = new Stad(coordinaat, "temp");

                    //Add the class to the list steden
                    steden.add(stad);
                }
                //If there arent any cities go to trades

            }

            i++;


            //if the scanner has a next line, go to the next line
            if (scanner.hasNextLine()) {
                // Go to the next line:
                scanner.nextLine();
            }


        }


        //Create the world with the variables
//        wereld = new Wereld(kaart, steden, markt);

        //Close the scanner
        scanner.close();

        return null;
    }

    //Check if its a integer
    public static boolean isInteger(String s) {
        //Always false
        boolean isValidInteger = false;
        try {
            //If it can parse
            //its a integer
            Integer.parseInt(s);

            // s is a valid integer
            isValidInteger = true;
        } catch (NumberFormatException ex) {
            // s is not an integer
        }
        return isValidInteger;
    }


}
