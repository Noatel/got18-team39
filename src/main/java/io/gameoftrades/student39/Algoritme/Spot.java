package io.gameoftrades.student39.Algoritme;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;

import java.util.ArrayList;

public class Spot {
    private Coordinaat coordinate;


    private Terrein terrain;

    private int f;
    private int g;
    private double h;
    private ArrayList<Coordinaat> neighbors;


    public Spot(Terrein terrain, Coordinaat end) {
        //Get the coordinate from terrain
        this.coordinate = terrain.getCoordinaat();
        this.terrain = terrain;

        //Tutorial stuff maybe remove later
        this.f = 0;
        this.g = 0;
        this.h = terrain.getCoordinaat().afstandTot(end);

        //Check if it got any neighbors
        this.neighbors = new ArrayList<Coordinaat>();
    }

    public void addNeighbors(Coordinaat coordinaat) {


    }

    public Coordinaat getCoordinate() {
        return coordinate;
    }

    public Terrein getTerrain() {
        return terrain;
    }


    public int getF() {
        return f;
    }

    public void setF(int f){
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
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
