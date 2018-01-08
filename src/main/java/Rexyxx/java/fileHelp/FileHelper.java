package Rexyxx.java.fileHelp;

import java.io.*;
import java.util.ArrayList;

import Rexyxx.java.creatures.*;
import Rexyxx.java.positions.TwoDimPosition;

public class FileHelper {
    private String fileName;
    private File file;

    public FileHelper(){
        File file = new File("battle\\battle.txt");
        int i=1;
        String filename = "battle\\battle.txt";
        while(file.exists()){
            filename = "battle\\battle" +i+".txt";
            file = new File(filename);
            i++;
        }
        this.fileName = filename;

    }
    public FileHelper(String fileName){
        this.fileName=fileName;
    }

    public FileHelper(File file){
        this.file = file;
    }
    public void saveObjToFile(ArrayList<Creature> creatures){
        try {
            //写对象流的对象
            ObjectOutputStream oos = null;
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file, true);
            if(file.length() <= 0)
                oos = new ObjectOutputStream(fos);
            else
                oos = new MyObjectWrite(fos);
            if(creatures == null){
                oos.writeObject(null);
                return;
            }
            for(int i=0;i<=creatures.size()-1;i++){
                info creatureInfo = new info();
                Creature temp = creatures.get(i);
                if(temp instanceof Huluwa){
                    creatureInfo.setX(((Huluwa) temp).getTwoDimPosition().getX());
                    creatureInfo.setY(((Huluwa) temp).getTwoDimPosition().getY());
                    creatureInfo.setLifeSpan(((Huluwa) temp).getLifeSpan());
                    creatureInfo.setState(((Huluwa) temp).getState());
                    creatureInfo.setSpeed(((Huluwa) temp).getSpeed());
                    if(((Huluwa) temp).getAlive())
                        creatureInfo.setAlive(1);
                    else
                        creatureInfo.setAlive(0);
                    creatureInfo.setName(((Huluwa) temp).getColor().toString());
                }
                if(temp instanceof Evil){
                    creatureInfo.setX(((Evil) temp).getTwoDimPosition().getX());
                    creatureInfo.setY(((Evil) temp).getTwoDimPosition().getY());
                    creatureInfo.setLifeSpan(((Evil) temp).getLifeSpan());
                    creatureInfo.setState(((Evil) temp).getState());
                    creatureInfo.setSpeed(((Evil) temp).getSpeed());
                    if(((Evil) temp).getAlive())
                        creatureInfo.setAlive(1);
                    else
                        creatureInfo.setAlive(0);
                    creatureInfo.setName(((Evil) temp).getName().toString());
                }
                if(temp instanceof GrandPa){
                    creatureInfo.setX(((GrandPa) temp).getTwoDimPosition().getX());
                    creatureInfo.setY(((GrandPa) temp).getTwoDimPosition().getY());
                    creatureInfo.setLifeSpan(((GrandPa) temp).getLifeSpan());
                    creatureInfo.setState(((GrandPa) temp).getState());
                    creatureInfo.setSpeed(((GrandPa) temp).getSpeed());
                    if(((GrandPa) temp).getAlive())
                        creatureInfo.setAlive(1);
                    else
                        creatureInfo.setAlive(0);
                    creatureInfo.setName("爷爷");
                }
                oos.writeObject(creatureInfo);
            }
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Creature> getObjFromFile(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
            ArrayList<Creature> creatures = new ArrayList<Creature>();
            Object tempI = ois.readObject();
            while(tempI != null) {
                info information = (info) tempI;
                tempI = ois.readObject();
                if (information.getName().equals("赤")) {
                    Huluwa hlw = new Huluwa(COLOR.赤, SENIORITY.一);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("橙")) {
                    Huluwa hlw = new Huluwa(COLOR.橙, SENIORITY.二);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("黄")) {
                    Huluwa hlw = new Huluwa(COLOR.黄, SENIORITY.三);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("绿")) {
                    Huluwa hlw = new Huluwa(COLOR.绿, SENIORITY.四);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("青")) {
                    Huluwa hlw = new Huluwa(COLOR.青, SENIORITY.五);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("蓝")) {
                    Huluwa hlw = new Huluwa(COLOR.蓝, SENIORITY.六);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("紫")) {
                    Huluwa hlw = new Huluwa(COLOR.紫, SENIORITY.七);
                    hlw.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    hlw.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        hlw.setAlive(true);
                    else
                        hlw.setAlive(false);
                    creatures.add(hlw);
                }

                if (information.getName().equals("蛇精")) {
                    Evil evil = new Evil(EVILNAME.蛇精);
                    evil.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    evil.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        evil.setAlive(true);
                    else
                        evil.setAlive(false);
                    creatures.add(evil);
                }

                if (information.getName().equals("蝎子精")) {
                    Evil evil = new Evil(EVILNAME.蝎子精);
                    evil.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    evil.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        evil.setAlive(true);
                    else
                        evil.setAlive(false);
                    creatures.add(evil);
                }

                if (information.getName().equals("蛤蟆怪")) {
                    Evil evil = new Evil(EVILNAME.蛤蟆怪);
                    evil.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    evil.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        evil.setAlive(true);
                    else
                        evil.setAlive(false);
                    creatures.add(evil);
                }

                if (information.getName().equals("蝙蝠")) {
                    Evil evil = new Evil(EVILNAME.蝙蝠);
                    evil.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    evil.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        evil.setAlive(true);
                    else
                        evil.setAlive(false);
                    creatures.add(evil);
                }

                if (information.getName().equals("蜈蚣")) {
                    Evil evil = new Evil(EVILNAME.蜈蚣);
                    evil.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    evil.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        evil.setAlive(true);
                    else
                        evil.setAlive(false);
                    creatures.add(evil);
                }

                if (information.getName().equals("爷爷")) {
                    GrandPa grandPa = new GrandPa();
                    grandPa.setState(information.getState());
                    TwoDimPosition position = new TwoDimPosition(information.getX(), information.getY());
                    grandPa.setTwoDimPosition(position);
                    if(information.isAlive()==1)
                        grandPa.setAlive(true);
                    else
                        grandPa.setAlive(false);
                    creatures.add(grandPa);
                }
            }
            ois.close();
            return creatures;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    static class MyObjectWrite extends ObjectOutputStream {

        public MyObjectWrite() throws IOException {
            super();
        }
        public MyObjectWrite(OutputStream out) throws IOException {
            super(out);// 会调用writeStreamHeader()
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            super.reset();
        }
    }
}