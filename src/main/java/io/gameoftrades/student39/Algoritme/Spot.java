package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;

import java.util.ArrayList;

public class Spot {
    private Coordinaat coordinate;


    private Terrein terrain;

    private double f;
    private double g;
    private double h;
    private Spot previous = null;



    public Spot(Terrein terrain, Coordinaat end) {
        //Get the coordinate from terrain
        this.coordinate = terrain.getCoordinaat();
        this.terrain = terrain;

        //Tutorial stuff maybe remove later
        this.h = terrain.getCoordinaat().afstandTot(end);

        if(previous != null){
            this.g = previous.getG() + terrain.getTerreinType().getBewegingspunten();
        } else {
            this.g = terrain.getTerreinType().getBewegingspunten();
        }

        this.f = this.h + this.g;


    }

    public void setPrevious(Spot previous) {
        this.previous = previous;
    }

    public Spot getPrevious() {
        return this.previous;
    }

    public Coordinaat getCoordinate() {
        return coordinate;
    }

    public Terrein getTerrain() {
        return terrain;
    }


    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return "[" + this.coordinate.getX() + ", " + this.coordinate.getY() + "]";
    }


    //https://stackoverflow.com/questions/45616794/arraylist-contains-method-not-work-as-i-would-except
    //Because the arraylist make use of the equals function we need to edit it
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Spot)){
            return false;
        }
        Spot other = (Spot) o;

        return other.coordinate.equals(this.coordinate);
    }
}
