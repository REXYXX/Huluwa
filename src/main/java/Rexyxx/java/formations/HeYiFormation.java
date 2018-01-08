package Rexyxx.java.formations;

import Rexyxx.java.formations.FORMATIONNAME;
import Rexyxx.java.formations.Formation;
import Rexyxx.java.creatures.*;
import Rexyxx.java.positions.*;
import Rexyxx.java.maps.*;

public class HeYiFormation extends Formation implements Arrange{

    public HeYiFormation(Creature[] creatures){

        this.setFormationName(FORMATIONNAME.鹤翼);
        this.setCreatures(creatures);
        TwoDimPosition[][] twoDimPositions = new TwoDimPosition[creatures.length][creatures.length];
        for(int i = 0; i<=creatures.length-1;i++) {
            for(int j = 0; j<=creatures.length-1;j++){
                twoDimPositions[i][j] = new TwoDimPosition(i,j);
            }
        }
        this.setTwoDimPositions(twoDimPositions);
    }

    public void setFormation(Field field, int left, int down,int seperate) {

        try {
            int Mid = this.getCreatures().length / 2;
            for (int i = 0; i <= Mid; i++) {
                if (i == 0)
                    this.getTwoDimPositions()[i][Mid].setHolder(this.getCreatures()[i]);
                else {
                    if ((2 * Mid - 1 < this.getCreatures().length) && (Mid + i < this.getCreatures().length))
                        this.getTwoDimPositions()[i][Mid + i].setHolder(this.getCreatures()[2 * i - 1]);
                    if (2 * i < this.getCreatures().length)
                        this.getTwoDimPositions()[i][Mid - i].setHolder(this.getCreatures()[2 * i]);
                }
            }

            TwoDimPosition[][] twoDimPositions = this.getTwoDimPositions();

            for (int i = 0; i <= twoDimPositions.length - 1; i++) {
                for (int j = 0; j <= twoDimPositions[0].length - 1; j++) {
                    if (!twoDimPositions[i][j].isEmpty()) {
                        if (i * seperate + left > field.getPositions().length|| j * seperate + down > field.getPositions()[0].length) {
                            throw new IndexOutOfBoundsException("阵型越界");
                        }
                        twoDimPositions[i][j].getHolder().setTwoDimPosition(field.getPositions()[i * seperate + left][j * seperate + down]);
                    }
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.print("阵型越界，重新变化阵型");
        }
    }

}
