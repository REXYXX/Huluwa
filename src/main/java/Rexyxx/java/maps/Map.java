package Rexyxx.java.maps;

import Rexyxx.java.maps.Field;
import Rexyxx.java.creatures.*;
import Rexyxx.java.creatures.Evil;
import Rexyxx.java.creatures.GrandPa;
import Rexyxx.java.creatures.Huluwa;
import Rexyxx.java.positions.TwoDimPosition;
import javax.swing.JFrame;

public class Map extends JFrame {

    private final int OFFSET = 30;

    public Map() {
        initUI();
    }

    public void initUI(){
        Field field = new Field();
        add(field);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET,
                field.getBoardHeight() + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Huluwa vs Evil");
    }
}
