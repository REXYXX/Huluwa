package fileHelp;
import Rexyxx.java.fileHelp.*;
import org.junit.Test;

import static org.junit.Assert.*;

//测试战局保存与读取

public class FileHelperTest {
    @Test
    public void getObjFromFile() throws Exception {

        FileHelper fileHelper = new FileHelper("adfgkagdkfaksdgfsd");

        fileHelper.saveObjToFile(null);
    }

}