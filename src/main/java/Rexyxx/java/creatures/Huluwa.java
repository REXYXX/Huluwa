package Rexyxx.java.creatures;

import Rexyxx.java.maps.Field;
import Rexyxx.java.positions.*;
import Rexyxx.java.sorters.Comparable;

import java.io.Serializable;
import java.net.URL;
import java.util.Random;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.math.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Huluwa implements Creature,Comparable,Runnable,Fight{

    private COLOR color;
    private SENIORITY seniority;
    private Image appearance;
    private Image dieAppear;
    private Field field;
    private int speed;
    private Boolean isAlive;
    private int lifeSpan;
    private int attack;
    private State state;
    //一维
    private Position position;

    //二维
    private TwoDimPosition twoDPosition;

    public Huluwa(COLOR color, SENIORITY seiority) {
        this.color = color;
        this.seniority = seiority;
        URL loc = this.getClass().getClassLoader().getResource("huluwa"+(this.seniority.ordinal()+1)+".png");
        ImageIcon iia = new ImageIcon(loc);
        this.setAppearance(iia.getImage());
        URL lod = this.getClass().getClassLoader().getResource("huluwadie"+(this.seniority.ordinal()+1)+".png");
        ImageIcon iib = new ImageIcon(lod);
        this.dieAppear = iib.getImage();
        this.speed = 1;
        this.isAlive = true;
        this.lifeSpan = 4;
        this.attack = 3;
        this.state = State.寻路;
    }

    public COLOR getColor() {
        return color;
    }

    public Boolean getAlive() {
        return isAlive;
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

    public SENIORITY getSeniority() {
        return seniority;
    }

    public Position getPosition() {
        return position;
    }

    public Image getAppearance() {
        if(isAlive)
            return this.appearance;
        else
            return this.dieAppear;
    }

    public TwoDimPosition getTwoDimPosition() {
        return twoDPosition;
    }

    public void findEnemy(Field FIELD){
        if(!isAlive)
            return ;
        if(FIELD.getEvils().length==0){
            this.state = State.寻路;
            return;
        }
        int MinPath = 10000;
        int CreatureIndex=-1;
        for(int i=0; i<=FIELD.getEvils().length-1;i++){
            if(Math.abs(FIELD.getEvils()[i].getTwoDimPosition().getX() - this.getTwoDimPosition().getX()) + Math.abs(FIELD.getEvils()[i].getTwoDimPosition().getY() - this.getTwoDimPosition().getY()) < MinPath){
                MinPath = Math.abs(FIELD.getEvils()[i].getTwoDimPosition().getX() - this.getTwoDimPosition().getX()) + Math.abs(FIELD.getEvils()[i].getTwoDimPosition().getY() - this.getTwoDimPosition().getY());
                CreatureIndex = i;
            }
        }
        if(FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getX() >= this.getTwoDimPosition().getX()){
            if(this.getTwoDimPosition().getX() + this.field.getSPACE() == FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getX()){
                if(FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getY() >= this.getTwoDimPosition().getY()){
                    if(this.getTwoDimPosition().getY() == FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getY()){
                        this.attack(FIELD.getEvils()[CreatureIndex]);
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
            if(this.getTwoDimPosition().getX() - this.field.getSPACE()< FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getX()){
                if(FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getY() >= this.getTwoDimPosition().getY()){
                    if(this.getTwoDimPosition().getY() == FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getY()){
                        this.attack(FIELD.getEvils()[CreatureIndex]);
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

    public void setPosition(Position position) {
        this.position = position;
        position.setHolder(this);
    }

    public  void  setAppearance(Image image){
        this.appearance = image;
    }

    public  void setField(Field field){
        this.field = field;
    }

    public void report() {
        ;
    }

    public boolean biggerThan(Comparable brother){

        if (brother instanceof  Huluwa)
            return this.getSeniority().ordinal()> ((Huluwa) brother).getSeniority().ordinal();
        else
            return false;
    }
    @Override
    public void run() {

        while (!Thread.interrupted()&&!this.field.isCompleted()) {
            Random rand = new Random();
            try {
  
                if(this.state == State.寻路 ) {
                    this.findEnemy(this.field);
                    Thread.sleep(rand.nextInt(1000) + 500);
                    this.field.repaint();
                }
                else if(this.state == State.向前攻击) {
                    this.state = State.攻击后退;
                    Thread.sleep(200);
                    this.field.repaint();
                }
                else{
                    this.findEnemy(this.field);
                    Thread.sleep(rand.nextInt(1000) + 500);
                    this.field.repaint();
                }

            } catch (Exception e) {

            }
        }
        return;
    }

}

