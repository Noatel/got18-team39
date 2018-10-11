package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.markt.Handel;

import java.util.ArrayList;
import java.util.Objects;

public class Trade {
    private Stad city;
    private ArrayList<Handel> offers;
    private ArrayList<Handel> asks;


    public Trade(Stad city) {
        this.city = city;
        this.offers = new ArrayList<>();
        this.asks = new ArrayList<>();

    }

    public void addOffer(Handel offer) {
        offers.add(offer);
    }

    public void addAsks(Handel ask) {
        asks.add(ask);
    }

    public Stad getCity() {
        return city;
    }


    public ArrayList<Handel> getOffers() {
        return offers;
    }

    public ArrayList<Handel> getAsks() {
        return asks;
    }

}
