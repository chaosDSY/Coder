import java.io.*;
import java.util.ArrayList;

public class TestSelection {
    public static void main(String[] args){
        String method = args[0];
        String project_target = args[1];
        String change_info = args[2];
    }

    /**
     * @description : 获取本地Jar包路径
     */
    public static String getLocalJarPath(){
        String path = TestSelection.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        try
        {
            path = java.net.URLDecoder.decode(path, "UTF-8"); // 处理中文
            File file = new File(path);
            return file.getParent();
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description : 初始化方法，初始化本地用scope.txt以及exclusion.txt文件
     */
    public static void init(){
        ArrayList<String> scope = new ArrayList<>();
        scope.add("Primordial,Java,stdlib,none");
        ArrayList<String> exclusion = new ArrayList<>();
        exclusion.add("apple\\/.*\n" +
                "com\\/apple\\/.*\n" +
                "com\\/ibm\\/.*\n" +
                "com\\/oracle\\/.*\n" +
                "com\\/sun\\/.*\n" +
                "dalvik\\/.*\n" +
                "java\\/beans\\/.*\n" +
                "java\\/io\\/ObjectStreamClass*\n" +
                "java\\/rmi\\/.*\n" +
                "java\\/text\\/.*\n" +
                "java\\/time\\/.*\n" +
                "javafx\\/.*\n" +
                "javafx\\/beans\\/.*\n" +
                "javafx\\/collections\\/.*\n" +
                "javafx\\/scene\\/.*\n" +
                "javax\\/accessibility\\/.*\n" +
                "javax\\/activation\\/.*\n" +
                "javax\\/activity\\/.*\n" +
                "javax\\/annotation\\/.*\n" +
                "javax\\/crypto\\/.*\n" +
                "javax\\/imageio\\/.*\n" +
                "javax\\/jnlp\\/.*\n" +
                "javax\\/jws\\/.*\n" +
                "javax\\/management\\/.*\n" +
                "javax\\/net\\/.*\n" +
                "javax\\/print\\/.*\n" +
                "javax\\/rmi\\/.*\n" +
                "javax\\/script\\/.*\n" +
                "javax\\/smartcardio\\/.*\n" +
                "javax\\/sound\\/.*\n" +
                "javax\\/sql\\/.*\n" +
                "javax\\/tools\\/.*\n" +
                "jdk\\/.*\n" +
                "netscape\\/.*\n" +
                "oracle\\/jrockit\\/.*\n" +
                "org\\/apache\\/xerces\\/.*\n" +
                "org\\/ietf\\/.*\n" +
                "org\\/jcp\\/.*\n" +
                "org\\/netbeans\\/.*\n" +
                "org\\/omg\\/.*\n" +
                "org\\/openide\\/.*\n" +
                "sun\\/.*\n" +
                "sun\\/awt\\/.*\n" +
                "sun\\/swing\\/.*\n");
        Analysis.writeFile(scope,getLocalJarPath()+"\\scope.txt");
        Analysis.writeFile(exclusion,getLocalJarPath()+"\\exclusion.txt");
    }

}
