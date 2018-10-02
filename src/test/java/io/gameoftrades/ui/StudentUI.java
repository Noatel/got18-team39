package io.gameoftrades.ui;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.student39.HandelaarImpl;

/**
 * Toont de visuele gebruikersinterface.
 *
 * Let op: dit werkt alleen als je de WereldLader hebt geimplementeerd (Anders
 * krijg je een NullPointerException).
 */
public class StudentUI {

    public static void main(String[] args) {
        MainGui.toon(new HandelaarImpl(), TileSet.T16, "/kaarten/westeros-kaart.txt");
    }

}