package io.gameoftrades.student39;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PadImpl implements Pad {

    private Richting[] moves;

    public PadImpl(Richting[] moves) {
        this.moves = moves;
    }

    @Override
    public int getTotaleTijd() {
        return moves.length;
    }


    @Override
    public Richting[] getBewegingen() {
        return moves;
    }

    @Override
    public PadImpl omgekeerd() {
        //Reverse path
        //http://www.java2s.com/Tutorial/Java/0140__Collections/Reversestheorderofthegivenobjectarray.htm

        int i = 0;
        int j = this.moves.length - 1;


        Richting tmp;
        while (j > i) {
            tmp = this.moves[j];
            this.moves[j] = this.moves[i];
            this.moves[i] = tmp;
            j--;
            i++;
        }

        ArrayList<Richting> temp = new ArrayList<>();
        for(Richting direction : this.moves){
            temp.add(direction.omgekeerd());
        }

        Richting[] finalPath = temp.toArray(new Richting[temp.size()]);

        return new PadImpl(finalPath);
    }

    @Override
    public Coordinaat volg(Coordinaat coordinaat) {
        //Pakt de volgende waarde uit de richting array
        for (Richting direction : moves) {
            coordinaat = coordinaat.naar(direction);
        }
        return coordinaat;
    }
}
