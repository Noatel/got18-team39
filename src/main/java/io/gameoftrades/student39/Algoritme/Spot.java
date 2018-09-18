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
    private ArrayList<Coordinaat> neighbors;


    public Spot(Terrein terrain, Coordinaat end) {
        //Get the coordinate from terrain
        this.coordinate = terrain.getCoordinaat();
        this.terrain = terrain;

        //Tutorial stuff maybe remove later
        this.f = 0.00;
        this.h = terrain.getCoordinaat().afstandTot(end);
        this.g = terrain.getTerreinType().getBewegingspunten();

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


}
