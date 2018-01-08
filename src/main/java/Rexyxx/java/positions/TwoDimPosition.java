package Rexyxx.java.positions;

import Rexyxx.java.positions.Position;

import java.io.Serializable;

public class TwoDimPosition extends Position implements Serializable {

    private int y;

    public int getY(){
        return  y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TwoDimPosition(int x, int y){
        setX(x);
        this.y=y;
    }

}
