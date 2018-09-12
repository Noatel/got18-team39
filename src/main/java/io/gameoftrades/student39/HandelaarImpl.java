package io.gameoftrades.student39;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.student39.Algoritme.AStar;

/**
 * Welkom bij Game of Trades!
 * <p>
 * Voordat er begonnen kan worden moet eerst de 'student39' package omgenoemd worden
 * zodat iedere groep zijn eigen namespace heeft. Vervang de NN met je groep nummer.
 * Dus als je in groep 3 zit dan wordt de packagenaam 'student03' en ben je in groep
 * 42 dan wordt de package naam 'student42'.
 * <p>
 * Om te controleren of je het goed hebt gedaan is er de ProjectSanityTest die je kan draaien.
 */
public class HandelaarImpl implements Handelaar {


    /**
     * Opdracht 1, zie ook de handige test-set in WereldLaderImplTest.
     */
    @Override
    public WereldLader nieuweWereldLader() {
        return new WereldLaderImpl();
    }

    /**
     * Opdracht 2
     */
    @Override
    public SnelstePadAlgoritme nieuwSnelstePadAlgoritme()  {
        // TODO Auto-generated method stub
        WereldLader lader = nieuweWereldLader();

        //Load the world
        Wereld wereld = lader.laad("/kaarten/voorbeeld-kaart.txt");


        //Get the map and the cities
        Kaart map = wereld.getKaart();
        Stad van = wereld.getSteden().get(0);
        Stad naar = wereld.getSteden().get(1);

        Coordinaat beginLocatie = van.getCoordinaat();
        Coordinaat eindLocatie = naar.getCoordinaat();

        //Algoritme

        //Calculate the map, coord start and coord end
        AStar astar = new AStar();
        System.out.println("------- START -------");
        Pad pad = astar.bereken(map,beginLocatie,eindLocatie);
        System.out.println("-------- END --------");

        return astar;
        
    }

    /**
     * Opdracht 3
     */
    @Override
    public StedenTourAlgoritme nieuwStedenTourAlgoritme() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Opdracht 4
     */
    @Override
    public HandelsplanAlgoritme nieuwHandelsplanAlgoritme() {
        // TODO Auto-generated method stub
        return null;
    }
}
