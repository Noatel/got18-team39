package io.gameoftrades.student39;

import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.student39.Algoritme.*;

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
    public SnelstePadAlgoritme nieuwSnelstePadAlgoritme() {
        //Load in the algorithm
        return new AStar();
    }

    /**
     * Opdracht 3
     */
    @Override
    public StedenTourAlgoritme nieuwStedenTourAlgoritme() {
        //Load in the algorithm
        return new TwoOptAlgortihm();
    }

    /**
     * Opdracht 4
     */
    @Override
    public HandelsplanAlgoritme nieuwHandelsplanAlgoritme() {
        return new TradePlan();
    }
}
