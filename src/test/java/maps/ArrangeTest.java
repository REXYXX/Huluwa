package maps;

import Rexyxx.java.creatures.COLOR;
import Rexyxx.java.creatures.Huluwa;
import Rexyxx.java.creatures.SENIORITY;
import Rexyxx.java.formations.ChangSheFormation;
import Rexyxx.java.formations.HeYiFormation;
import Rexyxx.java.maps.*;
import org.junit.Test;

import static org.junit.Assert.*;

//判断阵型是否超过地图边界
public class ArrangeTest {
    @Test
    public void setFormation() throws Exception {
        Field field = new Field();
        Huluwa[] hlws = new Huluwa[1];
        hlws[0] = new Huluwa(COLOR.橙, SENIORITY.二);

        ChangSheFormation formation1 = new ChangSheFormation(hlws);
        formation1.setFormation(field,8000,500,   1500);

        HeYiFormation formation2 = new HeYiFormation(hlws);
        formation2.setFormation(field,5000,0,1500);
    }

}