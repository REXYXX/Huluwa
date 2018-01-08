package Rexyxx.java.creatures;

import Rexyxx.java.creatures.Creature;
import Rexyxx.java.maps.Field;
import Rexyxx.java.positions.*;
import java.awt.Image;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.ImageIcon;

public class GrandPa implements Creature,Runnable,Fight{

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

    public GrandPa(){
        URL loc = this.getClass().getClassLoader().getResource("grandpa.png");
        ImageIcon iia = new ImageIcon(loc);
        this.setAppearance(iia.getImage());
        URL lod = this.getClass().getClassLoader().getResource("grandpadie.png");
        ImageIcon iib = new ImageIcon(lod);
        this.dieAppear = iib.getImage();
        this.speed= 0;
        this.isAlive = true;
        this.lifeSpan = 5;
        this.attack = 1;
        this.state = State.寻路;
    }

    public Position getPosition() {
        return this.position;
    }

    public TwoDimPosition getTwoDimPosition() {
        return this.twoDPosition;
    }

    public Image getAppearance() {
        if(isAlive)
            return this.appearance;
        else
            return this.dieAppear;
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

    public void setState(State state) {
        this.state = state;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public void setPosition(Position position) {
        this.leaveTwoDimPosition();
        this.position = position;
        position.setHolder(this);
    }

    public  void  setAppearance(Image image){
        this.appearance = image;
    }

    public  void setField(Field field){
        this.field = field;
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

    public void report(){
        ;
    }

    public void talk(String str){
        System.out.print(str);
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
            if(this.getTwoDimPosition().getX() - this.field.getSPACE() < FIELD.getEvils()[CreatureIndex].getTwoDimPosition().getX()){
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
        try {
            if(!this.field.getPositions()[this.getTwoDimPosition().getX()+x*this.field.getSPACE()][this.getTwoDimPosition().getY()+y*this.field.getSPACE()].isEmpty())
                return;
            this.setTwoDimPosition(this.field.getPositions()[this.twoDPosition.getX() + x*this.field.getSPACE()][this.twoDPosition.getY() + y*this.field.getSPACE()]);
        }finally {
            lock.unlock();
        }
    }

    public void attack(Creature creature){
        state = State.向前攻击;
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
            try {
                if(this.state == State.寻路 || this.state == State.攻击后退) {
                    this.findEnemy(this.field);
                    Thread.sleep( 1000);
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
    }
}
