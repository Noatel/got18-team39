package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.debug.DummyDebugger;
import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.algoritme.HandelsplanAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Handelsplan;
import io.gameoftrades.model.markt.actie.Actie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

import java.util.ArrayList;
import java.util.List;

public class TradePlan implements HandelsplanAlgoritme, Debuggable {
    private Debugger debug = new DummyDebugger();
    private Kaart map = null;
    private Wereld world = null;
    private ArrayList<Stad> availableCities = new ArrayList<>();
    private List<Stad> allCities = new ArrayList<>();
    List<Actie> actions = new ArrayList<>();


    @Override
    public Handelsplan bereken(Wereld wereld, HandelsPositie handelsPositie) {

        this.map = wereld.getKaart();
        this.world = wereld;


        System.out.println(handelsPositie.toString());


        allCities = wereld.getSteden();
        for (Stad city : allCities) {
            System.out.println(this.calculateDistance(handelsPositie.getStad(), city));
        }

        //Get all the trades
        List<Handel> getTrades = this.world.getMarkt().getHandel();
        List<Handel> sortedTrades = new ArrayList<>();

        System.out.println("Offer");
        for (Handel trade : getTrades) {

            if (sortedTrades.contains(trade)) {
                Trade newTrade = new Trade(trade.getStad());
                sortedTrades.add(trade);
            }


        }

        for(Handel trade : sortedTrades){
            System.out.println(trade.getStad().getNaam());
        }
        //Check if the cities are in range
        System.out.println("Aantal stappen: " + handelsPositie.getMaxActie());
        System.out.println("Bezochte steden: " + handelsPositie.getBezochteSteden());


        return null;
    }


    public int calculateDistance(Stad city1, Stad city2) {

        //Calculate the path distance between the start city and the next city
        AStar algorthim = new AStar();
        Pad path = algorthim.bereken(this.map, city1.getCoordinaat(), city2.getCoordinaat());

        return path.getBewegingen().length;
    }


    @Override
    public String toString() {
        return "Trade plan";
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debug = debugger;
    }

}
