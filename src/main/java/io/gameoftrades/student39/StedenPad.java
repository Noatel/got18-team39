/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.gameoftrades.student39;

import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;

/**
 *
 * @author billy
 */
public class StedenPad extends Path {
    /**
     * begin stad
     */
    private Stad start;
    /**
     * eind stad
     */
    private Stad end;
    
    /**
     * Constructor
     * @param start begin stad
     * @param end eind stad
     * @param pad pad
     */
    public StedenPad(Stad start, Stad end, Pad pad) {
        super(start.getCoordinaat(), end.getCoordinaat(), pad);

        this.start = start;
        this.end = end;
    }
    /**
     * Constructor
     * @param start begin stad
     * @param end eind stad
     * @param pad pad
     */
//    public StedenPad(Stad start, Stad end, Pad pad){
//        this(start, end, pad.getPad());
//    }
    /**
     * get begin stad
     * @return  begin stad
     */
    public Stad getStartStad() {
        return this.start;
    }
    /**
     * get eind stad
     * @return eind stad
     */
    public Stad getEndStad() {
        return this.end;
    }
    /**
     * check stad
     * @param start start stad
     * @param end eind stad
     * @return True als stad klopt, anders False
     */
    public boolean stadChecker(Stad start, Stad end) {
        return super.padChecker(start.getCoordinaat(), end.getCoordinaat());
    }
    
   @Override
    public StedenPad reverse() {
        return new StedenPad(this.end, this.start, getPad().omgekeerd());
    }
    //test
}
