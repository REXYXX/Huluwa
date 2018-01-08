package Rexyxx.java.creatures;

import Rexyxx.java.maps.Field;
import Rexyxx.java.positions.*;
import java.awt.Image;
import java.io.Serializable;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.ImageIcon;

public class Evil implements Creature,Runnable,Fight{

    private EVILNAME name;
    private Image appearance;
    private Image dieAppear;
    private Field field;
    private int speed;
    private Boolean isAlive;
    private int lifeSpan;
    private int attack;
    private State state;
    //
    private Position position;

    //二维
    private TwoDimPosition twoDPosition;

    public Evil(EVILNAME Evilname){
        this.name = Evilname;
        URL loc = this.getClass().getClassLoader().getResource("evil"+(this.name.ordinal()+1)+".png");
        ImageIcon iia = new ImageIcon(loc);
        this.setAppearance(iia.getImage());
        URL lod = this.getClass().getClassLoader().getResource("evildie"+(this.name.ordinal()+1)+".png");
        ImageIcon iib = new ImageIcon(lod);
        this.dieAppear = iib.getImage();
        this.speed= 1;
        this.isAlive = true;
        this.lifeSpan = 10 - this.name.ordinal();
        this.attack = 4 - this.name.ordinal();
        this.state = State.寻路;
    }

    public EVILNAME getName() {
        return this.name;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public int getSpeed() {
        return speed;
    }

    public State getState() {
        return state;
    }

    public Image getAppearance() {
        if(isAlive)
            return this.appearance;
        else
            return this.dieAppear;
    }

    public TwoDimPosition getTwoDimPosition() {
        return this.twoDPosition;
    }

    public void setPosition(Position position) {
        this.position = position;
        position.setHolder(this);
    }

    public  void setField(Field field){
        this.field = field;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public void setState(State state) {
        this.state = state;
    }


    public void setTwoDimPosition(TwoDimPosition position) {
        try {
            if (position.getX() < 0 || position.getY() < 0) {
                throw new IndexOutOfBoundsException("坐标越界");
            }
            this.leaveTwoDimPosition();
            this.twoDPosition = position;
            twoDPosition.setHolder(this);
        }catch(IndexOutOfBoundsException e){
            System.out.print("重新定义坐标");
        }
    }

    public void leaveTwoDimPosition() {
        if(this.twoDPosition == null)
            ;
        else {
            this.twoDPosition.delHolder();
            this.twoDPosition = null;
        }
    }

    public void setAppearance(Image image){
        this.appearance = image;
    }

    public void report(){
        ;
    }

    public void talk(String str){
        System.out.print(str);
    }

    public void findEnemy(Field FIELD){
        if(!isAlive)
            return ;
        if(FIELD.getHlws().length==0){
            this.state = State.寻路;
            return;
        }
        int MinPath = 10000;
        int CreatureIndex=0;
        for(int i=0; i<=FIELD.getHlws().length-1;i++){
            if(Math.abs(FIELD.getHlws()[i].getTwoDimPosition().getX() - this.getTwoDimPosition().getX()) + Math.abs(FIELD.getHlws()[i].getTwoDimPosition().getY() - this.getTwoDimPosition().getY()) < MinPath){
                MinPath = Math.abs(FIELD.getHlws()[i].getTwoDimPosition().getX() - this.getTwoDimPosition().getX()) + Math.abs(FIELD.getHlws()[i].getTwoDimPosition().getY() - this.getTwoDimPosition().getY());
                CreatureIndex = i;
            }
        }

        if(FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getX() >= this.getTwoDimPosition().getX()){
            if(this.getTwoDimPosition().getX() + this.field.getSPACE() == FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getX()){
                if(FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getY() >= this.getTwoDimPosition().getY()){
                    if(this.getTwoDimPosition().getY() == FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getY()){
                        this.attack(FIELD.getHlws()[CreatureIndex]);
                    }
                    else {
                        this.move(0,this.speed);
                    }
                }
                else{

                        this.move(0,-this.speed);
                }
            }
            else {
                this.move(this.speed,0);
            }
        }
        else {
            if(this.getTwoDimPosition().getX() - this.field.getSPACE() == FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getX()){
                if(FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getY() >= this.getTwoDimPosition().getY()){
                    if(this.getTwoDimPosition().getY() == FIELD.getHlws()[CreatureIndex].getTwoDimPosition().getY()){
                        this.attack(FIELD.getHlws()[CreatureIndex]);
                    }
                    else {
                        this.move(0,this.speed);
                    }
                }
                else{

                        this.move(0,-this.speed);
                }
            }
            else {
                this.move(-this.speed,0);
            }
        }
    }

    public void move(int x, int y){

        lock.lock();
        this.state = State.寻路;
        try {
            if(!this.field.getPositions()[this.getTwoDimPosition().getX()+x*this.field.getSPACE()][this.getTwoDimPosition().getY()+y*this.field.getSPACE()].isEmpty())
                return;
            this.setTwoDimPosition(this.field.getPositions()[this.twoDPosition.getX() + x*this.field.getSPACE()][this.twoDPosition.getY() + y*this.field.getSPACE()]);
        }finally {
            lock.unlock();
        }
    }

    public void attack(Creature creature){
        this.state = State.向前攻击;
        creature.beenAttacked(attack);
    }

    public void beenAttacked(int atc){
        if(lifeSpan - atc < 0){
            lifeSpan = 0;
            isAlive = false;
            this.field.getPositions()[this.getTwoDimPosition().getX()][this.getTwoDimPosition().getY()].setEmpty();
        }
        else {
            lifeSpan = lifeSpan - atc;
        }
    }
    @Override
    public void run() {
        while (!Thread.interrupted()&&!this.field.isCompleted()) {
            Random rand = new Random();

            try {
                if(this.state == State.寻路 || this.state == State.攻击后退) {
                    this.findEnemy(this.field);
                    Thread.sleep(rand.nextInt(1000) + 500);
                    this.field.repaint();
                }
                else if(this.state == State.向前攻击) {
                    this.state = State.攻击后退;
                    Thread.sleep(200);
                    this.field.repaint();
                }
            } catch (Exception e) {

            }
        }
        return;
    }
}

;