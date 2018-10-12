package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.student39.PadImpl;
import io.gameoftrades.student39.WereldLaderImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AStarTest {

    private WereldLaderImpl lader;
    private AStar algorthim;

    @Before
    public void init() {
        lader = new WereldLaderImpl();
        algorthim = new AStar();
    }

    @Test
    public void bereken() throws Exception {
        Wereld wereld = lader.laad("/kaarten/westeros-kaart.txt");
        Kaart kaart = wereld.getKaart();
        Stad startCity = wereld.getSteden().get(0);
        Stad endCity = wereld.getSteden().get(1);

        ArrayList<Richting> directions = new ArrayList<>();

        //Most efficient
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.WEST);
        directions.add(Richting.ZUID);
        directions.add(Richting.ZUID);
        directions.add(Richting.WEST);
        directions.add(Richting.WEST);

        System.out.println("direction = "+ directions.size());

        Pad algothim = algorthim.bereken(kaart, startCity.getCoordinaat(), endCity.getCoordinaat());
        Richting[] path =  algothim.getBewegingen();
        ArrayList<Richting> paths =  new ArrayList<>(Arrays.asList(path));

        assertArrayEquals(directions.toArray(),paths.toArray());
    }
}