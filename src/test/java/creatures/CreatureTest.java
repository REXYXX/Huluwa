package creatures;
import org.junit.Test;
import Rexyxx.java.positions.TwoDimPosition;
import Rexyxx.java.creatures.*;
import static org.junit.Assert.*;

//测试生物是否可以进行攻击
//测试生物的坐标是否越界

public class CreatureTest {

    @Test
    public void attack() throws Exception {

        new Evil(EVILNAME.蛇精).attack(new Huluwa(COLOR.赤,SENIORITY.一));

        new Huluwa(COLOR.赤,SENIORITY.一).attack(new Evil(EVILNAME.蛤蟆怪));

        new Huluwa(COLOR.赤,SENIORITY.一).attack(new Huluwa(COLOR.橙,SENIORITY.二));

        new Evil(EVILNAME.蝎子精).attack(new Evil(EVILNAME.蜈蚣));

        new GrandPa().attack(new Evil(EVILNAME.蝎子精));
    }

    @Test
    public void setTwoDimPosition() throws Exception {

        Huluwa hlw = new Huluwa(COLOR.橙,SENIORITY.二);
        TwoDimPosition position = new TwoDimPosition(-1,-90);
        hlw.setTwoDimPosition(position);

        GrandPa yeye = new GrandPa();
        yeye.setTwoDimPosition(position);

        Evil evil = new Evil(EVILNAME.蜈蚣);
        evil.setTwoDimPosition(position);
    }

}