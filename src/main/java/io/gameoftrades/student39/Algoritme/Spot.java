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

        this.h = coordinate.afstandTot(end);
        this.f = this.g + this.h;
    }

    //For the previous spot
    public Spot(Terrein terrain, Coordinaat end, Spot previous) {
        //Get the coordinate from terrain
        this.coordinate = terrain.getCoordinaat();
        this.terrain = terrain;

        // For each node, the cost of getting from the start node to that node.
        if (previous != null) {
            this.g = previous.getG() + terrain.getTerreinType().getBewegingspunten();
        } else {
            this.g = terrain.getTerreinType().getBewegingspunten();
        }

        this.h = coordinate.afstandTot(end);
        this.f = this.g + this.h;
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

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
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
