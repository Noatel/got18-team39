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
import io.gameoftrades.model.markt.actie.BeweegActie;
import io.gameoftrades.model.markt.actie.HandelsPositie;

import java.util.ArrayList;
import java.util.List;

public class TradePlan implements HandelsplanAlgoritme, Debuggable {
    private Debugger debug = new DummyDebugger();
    private Kaart map = null;
    private Wereld world = null;
    private ArrayList<Actie> navigationActions = null;
    private ArrayList<Stad> availableCities = new ArrayList<>();
    private List<Stad> allCities = new ArrayList<>();
    List<Actie> actions = new ArrayList<>();


    @Override
    public Handelsplan bereken(Wereld wereld, HandelsPositie handelsPositie) {

        Stad currentCity =  wereld.getSteden().get(0);
        this.map = wereld.getKaart();
        this.world = wereld;
        this.navigationActions = new ArrayList<>();

        //Get all the trades
        List<Handel> getTrades = this.world.getMarkt().getHandel();

        //Create the trades and sort it
        List<Trade> sortedTrades = new ArrayList<>();
        Trade currentTrade = null;

        System.out.println("Start creating trades");
        //Create the offer trades
        for (Handel trade : getTrades) {
            if (currentTrade != null) {
                if (getTrades.contains(trade)) {
                    currentTrade = new Trade(trade.getStad());
                    sortedTrades.add(currentTrade);
                }
            } else {
                currentTrade = new Trade(trade.getStad());
                sortedTrades.add(currentTrade);
            }
        }


        int bestPoints = 0;
        for (Trade trade : sortedTrades) {

            AStar algorthim = new AStar();
            Pad path = algorthim.bereken(map,currentCity.getCoordinaat(),trade.getCity().getCoordinaat());

            BeweegActie moveAction = new BeweegActie(map, currentCity, trade.getCity(), path);

            currentCity = trade.getCity();


            navigationActions.add(moveAction);
            for(Handel market : trade.getAsks()){
                System.out.println(market);
            }
        }
        //Check if the cities are in range
        System.out.println("Aantal stappen: " + handelsPositie.getMaxActie());
        System.out.println("Bezochte steden: " + handelsPositie.getBezochteSteden());
        System.out.println("Get Trade length: " + getTrades.size());
        System.out.println("Sorted trade length: " + sortedTrades.size());


        return new Handelsplan(this.navigationActions);
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
