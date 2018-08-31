package io.gameoftrades.student39;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.io.FileNotFoundException;
import java.util.*;

public class WereldLaderImpl implements WereldLader {


    //The world & coordinate
    private int[] coordinate = new int[2];
    private Kaart kaart;

    //Cities and trade
    private ArrayList<Stad> steden = new ArrayList<Stad>();
    private ArrayList<Handel> trades = new ArrayList<Handel>();


    @Override
    public Wereld laad(String resource)  {

        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //


        int i = 0;
        //initialize scanner and load the file
        Scanner scanner = new Scanner(this.getClass().getResourceAsStream(resource));
        String value = scanner.nextLine();
        //check each line in scanner
        while (scanner.hasNextLine()) {

            //Put the scanner data in a string
            //Because the first line always represent the coords
            if (i == 0) {
                //Split the data from the scanner and put it in the
                //Int array coords
                String[] splitCoordinates = value.split(",");

                //Convert the data coords to int
                coordinate[0] = Integer.parseInt(splitCoordinates[0]);
                coordinate[1] = Integer.parseInt(splitCoordinates[1]);

                //Create the map, width - height
                kaart = new Kaart(coordinate[0], coordinate[1]);
            }

            //Kaart output:

            if (i <= kaart.getHoogte() && i > 0) {
                if (!isInteger(value)) {
                    System.out.println("Char");
                } else {
                    //ERROR HANDLING
                    throw new java.lang.RuntimeException("De map is te kort");
                }

            }

            //If it is a integer AND its not the first row
            //Numbers of how many cities there are
            if (isInteger(value) && i > 0) {

                //Create first a final that only can assign once
                final int amountCities = Integer.parseInt(value);

                //If the amount of cities is heiger then 0
                if (amountCities > 0) {

                    //For the amount of cities that they are loop through
                    for (int j = 0; j < amountCities; j++) {

                        //Split the cities
                        String cityData = scanner.nextLine();
                        String[] splitCity = cityData.split(",");

                        Integer coordX = Integer.parseInt(splitCity[0]);
                        Integer coordY = Integer.parseInt(splitCity[1]);
                        String cityName = splitCity[2];

                        //Create the coordinate
                        Coordinaat cityCoordinate = Coordinaat.op(coordX, coordY);

                        //Create the city
                        Stad stad = new Stad(cityCoordinate, cityName);

                        steden.add(stad);
                    }

                    final int amountMarkets = Integer.parseInt(scanner.nextLine());

                    for (int j = 0; j < amountMarkets; j++) {

                        //Split the cities
                        String marktData = scanner.nextLine();
                        String[] splitMarkt = marktData.split(",");

                        String cityName = splitMarkt[0];
                        String VraagBod = splitMarkt[1];
                        String product = splitMarkt[2];
                        Integer prijs = Integer.parseInt(splitMarkt[3]);

                        Handelswaar handelswaar = new Handelswaar(product);
                        HandelType type = HandelType.valueOf(VraagBod);

                        for (Stad stad : steden) {
                            if (cityName.equals(stad.getNaam())) {
                                Handel handel = new Handel(stad, type, handelswaar, prijs);
                                trades.add(handel);
                            }
                        }
                    }
                }
            }

            i++;
            //if the scanner has a next line, go to the next line
            if (scanner.hasNextLine()) {
                // Go to the next line:
                value = scanner.nextLine();
            }
        }

        //Close the scanner
        scanner.close();

        //Create a marketplace with all the trades
        Markt markets = new Markt(trades);

        //Create the world with the variables
        Wereld world = new Wereld(kaart, steden, markets);

        return world;
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
