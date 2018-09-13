package io.gameoftrades.student39;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.Arrays;
import java.util.Collections;

public class PadImpl implements Pad {

    private Richting[] moves;

    public PadImpl(Richting[] moves){
        this.moves = moves;
    }

    @Override
    public int getTotaleTijd() {

        return 0;
    }


    @Override
    public Richting[] getBewegingen() {
        return moves;
    }

    @Override
    public Pad omgekeerd(){
        //Reverse path
        PadImpl reverse = new PadImpl(this.moves);

        //Reverse the array
        for (int i =0; i < this.moves.length; i++){
            System.out.println("moves="+this.moves);
        }

        return reverse;
    }

    @Override
    public Coordinaat volg(Coordinaat var1){

        return var1;
    }
}
