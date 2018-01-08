package Rexyxx.java.formations;

import Rexyxx.java.formations.FORMATIONNAME;
import Rexyxx.java.formations.Formation;
import Rexyxx.java.creatures.*;
import Rexyxx.java.positions.*;
import Rexyxx.java.maps.*;


public class ChangSheFormation extends Formation implements Arrange {

    public ChangSheFormation(Creature[] creatures){

        this.setFormationName(FORMATIONNAME.长蛇);
        this.setCreatures(creatures);
        TwoDimPosition[][] twoDimPositions = new TwoDimPosition[creatures.length][creatures.length];
        for(int i = 0; i<=creatures.length-1;i++) {
            for(int j = 0; j<=creatures.length-1;j++){
                twoDimPositions[i][j] = new TwoDimPosition(i,j);
            }
        }
        this.setTwoDimPositions(twoDimPositions);
    }

    public void setFormation(Field field, int left, int down,int seperate){

        try {
            for (int i = 0; i <= this.getCreatures().length - 1; i++) {
                this.getTwoDimPositions()[0][i].setHolder(this.getCreatures()[i]);
            }

            TwoDimPosition[][] twoDimPositions = this.getTwoDimPositions();
            for (int i = 0; i <= twoDimPositions.length - 1; i++) {
                for (int j = 0; j <= twoDimPositions.length - 1; j++) {
                    if (!twoDimPositions[i][j].isEmpty()) {
                        if (i * seperate + left > field.getPositions().length|| j * seperate + down > field.getPositions()[0].length) {
                            throw new IndexOutOfBoundsException("阵型越界");
                        }
                        twoDimPositions[i][j].getHolder().setTwoDimPosition(field.getPositions()[i * seperate + left][j * seperate + down]);
                    }
                }
            }
        }catch (IndexOutOfBoundsException e){
            System.out.print("重新变化阵型");
        }
    }

}
