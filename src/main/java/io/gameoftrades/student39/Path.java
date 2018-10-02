/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;

/**
 *
 * @author billy
 */
public class Path {
    /**
     * begin coordinaat
     */
    private Coordinaat start;
    /**
     * eind coordinaat
     */
    private Coordinaat end;
    /**
     * de pad
     */
    private Pad pad;

    /**
     * Constructor
     * 
     * @param start coordinaat
     * @param end coordinaat
     * @param pad pad
     */
    public Path(Coordinaat start, Coordinaat end, Pad pad) {
        this.start = start;
        this.end = end;
        this.pad = pad;
    }

    /**
     * get start coordinaat
     * @return start coordinaat 
     */
    public Coordinaat getStart() {
        return this.start;
    }

    /**
     * get eind coordinaat
     * @return eind coordinaat
     */
    public Coordinaat getEnd() {
        return this.end;
    }

    /**
     * pad checker
     * @param start start coordinaat
     * @param end eind coordinaat
     * @return True als pad klopt, anders False
     */
    public boolean padChecker(Coordinaat start, Coordinaat end) {
        return this.start.equals(start) && this.end.equals(end);
    }

    /**
     * get pad
     * @return pad 
     */
    public Pad getPad() {
        return this.pad;
    }
    
    /**
     * get padLengte
     * @return padLengte
     */
    public int getLengte() {
        return this.pad.getTotaleTijd();
    }
    
    /**
     * omgekeerd pad
     * @return omgekeerd pad
     */
    public Path reverse() {
        return new Path(this.end, this.start, this.pad.omgekeerd());
    }
    //test
}
