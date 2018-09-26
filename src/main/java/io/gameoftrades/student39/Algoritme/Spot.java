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


        // For each node, the cost of getting from the start node to that node.
        if (previous != null) {
            this.g = previous.getG() + terrain.getTerreinType().getBewegingspunten();
        } else {
            this.g = terrain.getTerreinType().getBewegingspunten();
        }

        this.h = this.coordinate.afstandTot(end);
        this.f = this.g + this.h;
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

    public void setCoordinate(Coordinaat coordinate) {
        this.coordinate = coordinate;
    }

    public void setTerrain(Terrein terrain) {
        this.terrain = terrain;
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

    //Because the arraylist, contains make use of the equals function we need to edit it
    //So we check if a object is equal to the other spot
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Spot)) {
            return false;
        }
        Spot other = (Spot) o;

        return other.coordinate.equals(this.coordinate);
    }
}
