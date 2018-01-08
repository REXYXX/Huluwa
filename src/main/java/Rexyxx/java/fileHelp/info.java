package Rexyxx.java.fileHelp;

import Rexyxx.java.creatures.State;

import java.io.Serializable;

public class info implements Serializable{
    private String name;
    private State state;
    private int speed;
    private int lifeSpan;
    private int isAlive;
    private int x;
    private int y;

    public void setState(State state) {
        this.state = state;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public State getState() {
        return state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int isAlive() {
        return isAlive;
    }

    public void setAlive(int alive) {
        isAlive = alive;
    }
}
