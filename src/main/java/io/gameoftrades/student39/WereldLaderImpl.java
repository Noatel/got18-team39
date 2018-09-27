package io.gameoftrades.student39;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.util.*;

public class WereldLaderImpl implements WereldLader {


    //The world & coordinate
    private int[] coordinate = new int[2];
    private Kaart map;

    //Cities and trade
    private ArrayList<Stad> cities = new ArrayList<Stad>();
    private ArrayList<Handel> trades = new ArrayList<Handel>();
    private ArrayList<Terrein> terrains = new ArrayList<Terrein>();
    private String value = "";


    @Override
    public Wereld laad(String resource) {

        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        //


        int i = 0;

        //initialize scanner and load the file
        Scanner scanner = new Scanner(this.getClass().getResourceAsStream(resource));
        value = scanner.nextLine();
        //check each line in scanner
        while (scanner.hasNextLine()) {
            scanWorld(i, scanner);

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
        Wereld world = new Wereld(map, cities, markets);

        return world;
    }

    public void scanWorld(int i, Scanner scanner) {
        value = value.replaceAll("\\s+", "");

        //Remove all the white spaces


        //Put the scanner data in a string
        //Because the first line always represent the coords
        if (i == 0) {
            assignWorld(value);
        }

        //Kaart output:
        if (i <= map.getHoogte() && i > 0) {
            if (!isInteger(value)) {
                //The map must be the same width as given
                if (value.length() == map.getBreedte()) {
                    //Foreach charachter in the string
                    assignTerrain(i, value);
                } else {
                    //ERROR HANDLING
                    throw new IllegalArgumentException("the map is too wide");
                }
            } else {
                //ERROR HANDLING
                throw new IllegalArgumentException("the map is too small");
            }
        }

        //If it is a integer AND its not the first row
        //Numbers of how many cities there are
        if (isInteger(value) && i > 0) {

            //Create first a final that only can assign once
            final int amountCities = Integer.parseInt(value);

            //If the amount of cities is heiger then 0
            if (amountCities > 0) {
                assignCities(amountCities, scanner);
                assignMarket(scanner);
            }
        }
    }

    public void createCity(String cityData) {
        //Create a city

        String[] splitCity = cityData.split(",");
        int[] cityCoordinates = calculateCoordinates(splitCity);


        //Create the coordinate
        Coordinaat cityCoordinate = Coordinaat.op(cityCoordinates[0], cityCoordinates[1]);

        checkIfCityPosible(cityCoordinate, cityCoordinates);

        String cityName = splitCity[2];

        //Create the city
        Stad city = new Stad(cityCoordinate, cityName);

        //Add the city to the arrayList
        cities.add(city);
    }


    public void assignWorld(String value) {
        //Split the data from the scanner and put it in the
        //Int array coords
        String[] splitCoordinates = value.split(",");

        //Convert the data coords to int
        coordinate[0] = Integer.parseInt(splitCoordinates[0]);
        coordinate[1] = Integer.parseInt(splitCoordinates[1]);

        //Create the map, width - height
        map = new Kaart(coordinate[0], coordinate[1]);
    }

    public void assignTerrain(int i, String value) {

        Integer coordinateY = i - 1;
        Integer coordinateX = 0;

        for (char c : value.toCharArray()) {

            Coordinaat coordinate = Coordinaat.op(coordinateX, coordinateY);

            TerreinType terrainType = TerreinType.fromLetter(c);
            Terrein terrain = new Terrein(map, coordinate, terrainType);

            terrains.add(terrain);
            coordinateX++;
        }

    }

    public void assignCities(int amountCities, Scanner scanner) {
        //For the amount of cities that they are loop through
        for (int j = 0; j < amountCities; j++) {
            String cityData = scanner.nextLine().replaceAll("\\s+", "");
            createCity(cityData);
        }
    }

    public void assignMarket(Scanner scanner) {
        //Create first a final that only can assign once

        String scannerMarkets = scanner.nextLine().replaceAll("\\s+", "");
        final int amountMarkets = Integer.parseInt(scannerMarkets);


        //If the amount of markets is heiger then 0
        if (amountMarkets > 0) {
            for (int j = 0; j < amountMarkets; j++) {
                String marketData = scanner.nextLine().replaceAll("\\s+", "");
                createMarket(marketData);
            }
        }
    }

    public void checkIfCityPosible(Coordinaat cityCoordinate, int[] cityCoordinates) {
        //We need to -1 because the map begins at 0,0 instead of 1,1
        if (cityCoordinates[0] == 0 && cityCoordinates[1] == 0) {
            throw new IllegalArgumentException("The city is standing outside the map");
        } else {
            cityCoordinates[0]--;
            cityCoordinates[1]--;
        }

        if (!map.isOpKaart(cityCoordinate))
            throw new IllegalArgumentException("The city is standing outside the map");

        //The city that we define needs to be between the coordinates
        //Still not working optimal

        if (cityCoordinates[0] >= map.getBreedte())
            throw new IllegalArgumentException("The city is standing outside the map, coordinates are too wide");

        if (cityCoordinates[1] >= map.getHoogte())
            throw new IllegalArgumentException("The city is standing outside the map, coordinates are too high");


    }

    public int[] calculateCoordinates(String[] coordinates) {

        int[] cityCoordinates = new int[2];

        if (Integer.parseInt(coordinates[0]) > 1) {
            cityCoordinates[0] = Integer.parseInt(coordinates[0]) - 1;
        } else {
            cityCoordinates[0] = Integer.parseInt(coordinates[0]);
        }

        if (Integer.parseInt(coordinates[1]) > 1) {
            cityCoordinates[1] = Integer.parseInt(coordinates[1]) - 1;
        } else {
            cityCoordinates[1] = Integer.parseInt(coordinates[1]);
        }

        return cityCoordinates;
    }

    public void createMarket(String marktData) {
        String[] splitMarkt = marktData.split(",");

        String cityName = splitMarkt[0];
        String requestBid = splitMarkt[1];
        String product = splitMarkt[2];
        Integer price = Integer.parseInt(splitMarkt[3]);

        Handelswaar merchandise = new Handelswaar(product);
        HandelType type = HandelType.valueOf(requestBid);

        //Foreach city we got
        for (Stad stad : cities) {

            //If the city name is the same
            if (stad.getNaam().equals(cityName)) {
                //Add handel
                Handel trade = new Handel(stad, type, merchandise, price);
                trades.add(trade);
            }
        }
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
