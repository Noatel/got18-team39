package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.student39.HandelaarImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class TwoOptAlgortihmTest {


    private Handelaar handelaar;


    @Before
    public void init() {
        handelaar = new HandelaarImpl();
    }

    @Test
    public void bereken() throws Exception {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/westeros-kaart.txt");
        assertNotNull(wereld);

        StedenTourAlgoritme algoritme = handelaar.nieuwStedenTourAlgoritme();
        assertNotNull(algoritme);

        List<Stad> result = algoritme.bereken(wereld.getKaart(), wereld.getSteden());

        //Check the amount of cities are correct
        assertNotNull(result);
        assertEquals(21, result.size());
        assertEquals(21, new HashSet<>(result).size());
    }


    @Test
    public void calculateTotalDistance() throws Exception {
        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/westeros-kaart.txt");
        assertNotNull(wereld);

        List<Stad> cities = wereld.getSteden();
        Kaart map = wereld.getKaart();

        //Keep track of all the distnace
        double distance = 0;
        AStar algorithm = new AStar();

        //Get the previous City
        Stad previousCity = cities.get(0);

        //Loop through all the cities that we get the distnace
        for (Stad city : cities) {
            //get distance from the previous city
            Pad newPath = algorithm.bereken(map, city.getCoordinaat(), previousCity.getCoordinaat());
            distance += newPath.getTotaleTijd();

            //current city will be the previous city next time
            previousCity = city;
        }


        //Give back the distance between all the cities
        assertEquals(0, Double.compare(213.0, distance));
    }

    @Test
    public void calculateDistanceBetweenCities() throws Exception {

        Wereld wereld = handelaar.nieuweWereldLader().laad("/kaarten/westeros-kaart.txt");
        assertNotNull(wereld);

        List<Stad> cities = wereld.getSteden();
        Kaart map = wereld.getKaart();

        //Keep track of all the distnace
        double distance = 0;
        AStar algorithm = new AStar();

        //Get the previous City
        Stad firstCity = cities.get(0);
        Stad secondCity = cities.get(1);

        //Loop through all the cities that we get the distnace

        //get distance from the previous city
        Pad newPath = algorithm.bereken(map, firstCity.getCoordinaat(), secondCity.getCoordinaat());
        newPath.getTotaleTijd();

        assertEquals(12,newPath.getTotaleTijd());
    }
}