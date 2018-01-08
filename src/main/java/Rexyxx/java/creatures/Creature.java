package Rexyxx.java.creatures;
import Rexyxx.java.positions.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface Creature {

    Lock lock = new ReentrantLock();

    public void report();

    //一维
    public void setPosition(Position position);

    public Position getPosition();

    //二维
    public void setTwoDimPosition(TwoDimPosition position);

    public TwoDimPosition getTwoDimPosition();

    public void leaveTwoDimPosition();

    //攻击
    public void attack(Creature creature);

    public void beenAttacked(int atc);
}

