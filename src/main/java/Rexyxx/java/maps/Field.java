package Rexyxx.java.maps;

import Rexyxx.java.creatures.*;
import Rexyxx.java.fileHelp.FileHelper;
import Rexyxx.java.formations.ChangSheFormation;
import Rexyxx.java.formations.HeYiFormation;
import Rexyxx.java.positions.TwoDimPosition;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import  java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.*;

public class Field extends JPanel implements Serializable{

    private TwoDimPosition[][] positions;
    private ArrayList<Creature> creatures;
    private Creature[] hlws;
    private Creature[] evils;
    private ArrayList<Thread> threads;
    private final int SPACE = 70;

    private Image background;
    private int w = 0;
    private int h = 0;
    private boolean runing = false;
    private boolean completed = false;
    private boolean isReplay = false;
    private boolean replayOver = false;
    private FileHelper fileHelper;
    int count=0;

    public Field() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public int getSPACE(){
        return  this.SPACE;
    }

    public Creature[] getHlws() {
        int j =0;
        GrandPa yeye = null;
        for(int i=0;i<=creatures.size()-1;i++){
            if(creatures.get(i) instanceof  GrandPa)
                yeye = (GrandPa) creatures.get(i);
        }

        for(int i=0;i<hlws.length;i++){
            Creature temph = this.hlws[i];
            if(((Huluwa) temph).getAlive()){
                j++;
            }
        }
        Creature[] temp;
        if(yeye == null) {
            temp = new Creature[j];
        }
        else if(yeye.getAlive()){
            temp = new Creature[j+1];
        }
        else{
            temp = new Creature[j];
        }
        j=0;
        for(int i=0;i<hlws.length;i++){
            Creature temph = this.hlws[i];
            if(((Huluwa) temph).getAlive()){
                temp[j] = hlws[i];
                j++;
            }
        }
        if(yeye.getAlive())
            temp[j] = yeye;
        return temp;
    }

    public Creature[] getEvils() {
        int j =0;
        for(int i=0;i<evils.length;i++){
            Creature temph = this.evils[i];
            if(((Evil) temph).getAlive()){
                j++;
            }
        }
        Creature[] temp = new Creature[j];
        j=0;
        for(int i=0;i<evils.length;i++){
            Creature temph = this.evils[i];
            if(((Evil) temph).getAlive()){
                temp[j] = evils[i];
                j++;
            }
        }
        return temp;
    }

    public void setBackground(Image image){
        this.background = image;
    }

    public final void initWorld() {

        this.intiBackground();
        this.initPositions();
        this.initCreatures();
        this.initFormation();
    }

    public void replayBattle(Graphics g){
        g.drawImage(this.background,0,0,this.getWidth(),this.getHeight(),this);

        ArrayList<Creature> die = new ArrayList<Creature>();
        for (int i = 0; i <= this.creatures.size() - 1; i++) {
            Creature temp = this.creatures.get(i);
            if (this.creatures.get(i) instanceof Huluwa) {
                if (!((Huluwa) temp).getAlive()) {
                    die.add(temp);
                }
            }
            if (this.creatures.get(i) instanceof Evil) {
                if (!((Evil) temp).getAlive()) {
                    die.add(temp);
                }
            }

            if (this.creatures.get(i) instanceof GrandPa) {
                if (!((GrandPa) temp).getAlive()) {
                    die.add(temp);
                }
            }
        }
        for (int i = 0; i <= die.size() - 1; i++) {
            Creature temp = die.get(i);
            if (temp instanceof Huluwa) {
                g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
            if (temp instanceof Evil) {
                g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
            if (temp instanceof GrandPa) {
                g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
        }
        for (int i = 0; i <= this.creatures.size() - 1; i++) {
            Creature temp = this.creatures.get(i);
            if (this.creatures.get(i) instanceof Huluwa) {
                if (((Huluwa) temp).getState() == State.向前攻击 && ((Huluwa) temp).getAlive()) {
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if (((Huluwa) temp).getState() == State.攻击后退 && ((Huluwa) temp).getAlive()) {
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                }else if(((Huluwa) temp).getState() == State.寻路&& ((Huluwa) temp).getAlive()){
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
            if (this.creatures.get(i) instanceof Evil) {
                if (((Evil) temp).getState() == State.向前攻击 && ((Evil) temp).getAlive()) {
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                } else if (((Evil) temp).getState() == State.攻击后退 && ((Evil) temp).getAlive()) {
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if(((Evil) temp).getState() == State.寻路&& ((Evil) temp).getAlive()){
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
            if (this.creatures.get(i) instanceof GrandPa) {
                if (((GrandPa) temp).getState() == State.向前攻击 && ((GrandPa) temp).getAlive()) {
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if (((GrandPa) temp).getState() == State.攻击后退 && ((GrandPa) temp).getAlive()) {
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                }else if(((GrandPa) temp).getState() == State.寻路&& ((GrandPa) temp).getAlive()){
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
        }
    }

    public void buildWorld(Graphics g) {
        g.drawImage(this.background,0,0,this.getWidth(),this.getHeight(),this);
        if(this.runing)
            fileHelper.saveObjToFile(creatures);
        if(this.getHlws().length ==0 || this.getEvils().length ==0) {
            this.completed = true;
            fileHelper.saveObjToFile(null);
        }

        ArrayList<Creature> die = new ArrayList<Creature>();
        for (int i = 0; i <= this.creatures.size() - 1; i++) {
            Creature temp = this.creatures.get(i);
            if (this.creatures.get(i) instanceof Huluwa) {
                if (!((Huluwa) temp).getAlive()) {
                    die.add(temp);
                }

            }
            if (this.creatures.get(i) instanceof Evil) {
                if (!((Evil) temp).getAlive()) {
                    die.add(temp);
                }
            }
            if (this.creatures.get(i) instanceof GrandPa) {
                if (!((GrandPa) temp).getAlive()) {
                    die.add(temp);
                }
            }
        }

        for (int i = 0; i <= die.size() - 1; i++) {
            Creature temp = die.get(i);
            if (temp instanceof Huluwa) {
                g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
            if (temp instanceof Evil) {
                g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
            if (temp instanceof GrandPa) {
                g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
            }
        }
        for (int i = 0; i <= this.creatures.size() - 1; i++) {
            Creature temp = this.creatures.get(i);
            if (this.creatures.get(i) instanceof Huluwa) {
                if (((Huluwa) temp).getState() == State.向前攻击 && ((Huluwa) temp).getAlive()) {
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if (((Huluwa) temp).getState() == State.攻击后退 && ((Huluwa) temp).getAlive()) {
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                }else if(((Huluwa) temp).getState() == State.寻路&& ((Huluwa) temp).getAlive()){
                    g.drawImage(((Huluwa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
            if (this.creatures.get(i) instanceof Evil) {
                if (((Evil) temp).getState() == State.向前攻击 && ((Evil) temp).getAlive()) {
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                } else if (((Evil) temp).getState() == State.攻击后退 && ((Evil) temp).getAlive()) {
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if(((Evil) temp).getState() == State.寻路&& ((Evil) temp).getAlive()){
                    g.drawImage(((Evil) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
            if (this.creatures.get(i) instanceof GrandPa) {
                if (((GrandPa) temp).getState() == State.向前攻击 && ((GrandPa) temp).getAlive()) {
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX() - 20, temp.getTwoDimPosition().getY(), this);
                } else if (((GrandPa) temp).getState() == State.攻击后退 && ((GrandPa) temp).getAlive()) {
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX() + 20, temp.getTwoDimPosition().getY(), this);
                }else if(((GrandPa) temp).getState() == State.寻路&& ((GrandPa) temp).getAlive()){
                    g.drawImage(((GrandPa) temp).getAppearance(), temp.getTwoDimPosition().getX(), temp.getTwoDimPosition().getY(), this);
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(isReplay)
            replayBattle(g);
        else
            buildWorld(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT) {

            } else if (key == KeyEvent.VK_RIGHT) {

            } else if (key == KeyEvent.VK_UP) {

            } else if (key == KeyEvent.VK_DOWN) {

            } else if (key == KeyEvent.VK_S) {

            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            }else if(key == KeyEvent.VK_SPACE){
                fileHelper = new FileHelper();
                runing = true;
                for(int i=0;i<=threads.size()-1;i++){
                    threads.get(i).start();
                }
            }else if(key == KeyEvent.VK_L){
                completed = true;
                JFileChooser jfc=new JFileChooser();
                if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File f=jfc.getSelectedFile();
                    replay(new FileHelper(f));
                }
                else
                    System.out.println("No file is selected!");
            }
            repaint();
        }
    }

    public void restartLevel() {
        completed = false;
        isReplay = false;
        replayOver = true;
        runing = false;
        initWorld();
    }

    public void replay(FileHelper FILEHELPER){
        isReplay = true;
        replayOver = false;
        final ArrayList<Creature> replayCreatures = FILEHELPER.getObjFromFile();
        final int length = this.creatures.size();
        Thread rebattle = new Thread(new Runnable(){
            public void run(){
                ArrayList<Creature> tempC = new ArrayList<Creature>();
                for(int i=0;i<=length-1;i++){
                    tempC.add(replayCreatures.remove(0));
                }
                while (!Thread.interrupted()&&!replayOver&&replayCreatures.size()>0) {
                    try {
                        creatures = tempC;
                        tempC.clear();
                        for(int i=0;i<=length-1;i++){
                            tempC.add(replayCreatures.remove(0));
                        }
                        if(replayCreatures.size() <= 0) {
                            replayOver = true;
                            isReplay = false;
                        }
                        repaint();
                        Thread.sleep(50);
                    }catch (Exception e) {

                    }
                }
            }
        });
        rebattle.start();
    }

    public TwoDimPosition[][] getPositions() {
        return positions;
    }

    public void intiBackground(){
        URL loc = this.getClass().getClassLoader().getResource("background.jpg");
        ImageIcon iia = new ImageIcon(loc);
        this.background = iia.getImage();
        this.w = this.background.getWidth(this);
        this.h = this.background.getHeight(this);
    }

    public  void initPositions(){
        this.positions = new TwoDimPosition[this.w][this.h];
        for(int i=0;i<=this.w-1;i++){
            for(int j=0;j<=this.h-1;j++) {
                this.positions[i][j] = new TwoDimPosition(i,j);
            }
        }
    }

    public void initCreatures(){
        this.creatures = new ArrayList<Creature>();
        this.threads = new ArrayList<Thread>();
        hlws = new Creature[7];
        evils = new Creature[9];

        Huluwa hlw1 = new Huluwa(COLOR.赤, SENIORITY.一);
        hlw1.setTwoDimPosition(this.positions[20][100]);
        hlw1.setField(this);
        this.creatures.add(hlw1);
        hlws[0]=hlw1;
        threads.add(new Thread(hlw1));

        Huluwa hlw2 = new Huluwa(COLOR.橙, SENIORITY.二);
        hlw2.setTwoDimPosition(this.positions[20][100]);
        hlw2.setField(this);
        this.creatures.add(hlw2);
        threads.add(new Thread(hlw2));
        hlws[1]=hlw2;

        Huluwa hlw3 = new Huluwa(COLOR.黄, SENIORITY.三);
        hlw3.setTwoDimPosition(this.positions[20][100]);
        hlw3.setField(this);
        this.creatures.add(hlw3);
        threads.add(new Thread(hlw3));
        hlws[2]=hlw3;

        Huluwa hlw4 = new Huluwa(COLOR.绿, SENIORITY.四);
        hlw4.setTwoDimPosition(this.positions[20][100]);
        hlw4.setField(this);
        this.creatures.add(hlw4);
        threads.add(new Thread(hlw4));
        hlws[3]=hlw4;

        Huluwa hlw5 = new Huluwa(COLOR.蓝, SENIORITY.五);
        hlw5.setTwoDimPosition(this.positions[20][100]);
        hlw5.setField(this);
        this.creatures.add(hlw5);
        threads.add(new Thread(hlw5));
        hlws[4]=hlw5;

        Huluwa hlw6 = new Huluwa(COLOR.青, SENIORITY.六);
        hlw6.setTwoDimPosition(this.positions[20][100]);
        hlw6.setField(this);
        this.creatures.add(hlw6);
        threads.add(new Thread(hlw6));
        hlws[5]=hlw6;

        Huluwa hlw7 = new Huluwa(COLOR.紫, SENIORITY.七);;
        hlw7.setTwoDimPosition(this.positions[20][100]);
        hlw7.setField(this);
        this.creatures.add(hlw7);
        threads.add(new Thread(hlw7));
        hlws[6]=hlw7;

        GrandPa yeye = new GrandPa();
        yeye.setTwoDimPosition(this.positions[0][490]);
        yeye.setField(this);
        this.creatures.add(yeye);
        threads.add(new Thread(yeye));

        Evil evil1 = new Evil(EVILNAME.蛇精);
        evil1.setTwoDimPosition(this.positions[800][100]);
        evil1.setField(this);
        this.creatures.add(evil1);
        threads.add(new Thread(evil1));
        evils[0] = evil1;

        Evil evil2 = new Evil(EVILNAME.蝎子精);
        evil2.setTwoDimPosition(this.positions[800][100]);
        evil2.setField(this);
        this.creatures.add(evil2);
        threads.add(new Thread(evil2));
        evils[1] = evil2;

        Evil evil3 = new Evil(EVILNAME.蛤蟆怪);
        evil3.setTwoDimPosition(this.positions[800][100]);
        evil3.setField(this);
        this.creatures.add(evil3);
        threads.add(new Thread(evil3));
        evils[2] = evil3;


        Evil evil4 = new Evil(EVILNAME.蜈蚣);
        evil4.setTwoDimPosition(this.positions[800][100]);
        evil4.setField(this);
        this.creatures.add(evil4);
        threads.add(new Thread(evil4));
        evils[3] = evil4;

        Evil evil5 = new Evil(EVILNAME.蝙蝠);
        evil5.setTwoDimPosition(this.positions[800][100]);
        evil5.setField(this);
        this.creatures.add(evil5);
        threads.add(new Thread(evil5));
        evils[4] = evil5;

        Evil evil6 = new Evil(EVILNAME.蛤蟆怪);
        evil6.setTwoDimPosition(this.positions[800][100]);
        evil6.setField(this);
        this.creatures.add(evil6);
        threads.add(new Thread(evil6));
        evils[5] = evil6;

        Evil evil7 = new Evil(EVILNAME.蝙蝠);
        evil7.setTwoDimPosition(this.positions[800][100]);
        evil7.setField(this);
        this.creatures.add(evil7);
        threads.add(new Thread(evil7));
        evils[6] = evil7;

        Evil evil8 = new Evil(EVILNAME.蜈蚣);
        evil8.setTwoDimPosition(this.positions[800][100]);
        evil8.setField(this);
        this.creatures.add(evil8);
        threads.add(new Thread(evil8));
        evils[7] = evil8;

        Evil evil9 = new Evil(EVILNAME.蛤蟆怪);
        evil9.setTwoDimPosition(this.positions[800][100]);
        evil9.setField(this);
        this.creatures.add(evil9);
        threads.add(new Thread(evil9));
        evils[8] = evil9;
    }

    public void initFormation(){
        ChangSheFormation formation1 = new ChangSheFormation(hlws);
        formation1.setFormation(this,0,0,this.SPACE);
        HeYiFormation formation2 = new HeYiFormation(evils);
        formation2.setFormation(this,this.SPACE*10,0,this.SPACE);
    }

    public String[] getBattleFiles(){
        File file = new File("battle");
        File[] array = file.listFiles();
        if(array.length<=0)
            return null;
        else {
            String[] filesName = new String[array.length];
            for(int i=0;i<=array.length-1;i++){
                filesName[i] = array[i].getName();
            }
            return filesName;
        }
    }
}


