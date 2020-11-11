package Try;

import com.ibm.wala.util.io.FileProvider;

import java.io.File;
import java.io.IOException;

public class Hello {
    public static void main(String[] args) throws IOException {
        File exFile = new File("D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\2-DataLog\\data\\change_info.txt");
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(exFile);
    }
}
