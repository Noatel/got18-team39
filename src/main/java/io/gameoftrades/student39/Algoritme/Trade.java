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


    public Stad getCity() {
        return city;
    }


    public ArrayList<Handel> getOffers() {
        return offers;
    }

    public ArrayList<Handel> getAsks() {
        return asks;
    }


    //https://stackoverflow.com/questions/45616794/arraylist-contains-method-not-work-as-i-would-except

    //Because the arraylist, contains make use of the equals function we need to edit it
    //So we check if a object is equal to the other spot
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Handel)) {
            return false;
        }
        Handel other = (Handel) o;

        return Objects.equals(other, o);
    }

}
