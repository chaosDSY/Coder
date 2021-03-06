import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaException;

import java.io.*;
import java.util.ArrayList;

public class TestSelection {
    /**
     * @description : 项目入口
     * @param args : 函数输入
     * 通过try-catch简单处理异常
     */
    public static void main(String[] args){
        try{
            init();
            System.out.println("local file init");
            String method = args[0];
            boolean judge;
            if(method.equals("-c")){
                judge = true;
            }else if(method.equals("-m")){
                judge = false;
            }else {
                System.out.println("Command Error!");
                return;
            }
            String project_target = args[1];
            String change_info = args[2];
            String scopePath = getLocalJarPath()+"\\scope.txt";
            String exclusionPath = getLocalJarPath()+"\\exclusion.txt";
            AnalysisScope scope = scopeBuild.buildScope(project_target,scopePath,exclusionPath);
            CHACallGraph cg = Analysis.GraphMake(scope);
            ArrayList<String> classRelation = Analysis.getClassDot(cg);
            ArrayList<String> methodRelation = Analysis.getMethodDot(cg);
            ArrayList<String> mesg = Analysis.changeInfoRead(change_info);
            System.out.println("change_info loaded");
            if(judge){
                Analysis.ExcuteC(cg,mesg,classRelation);
            }else {
                Analysis.ExcuteM(cg,mesg);
            }
            System.out.println("complete");
        }catch (IOException | InvalidClassFileException | WalaException | CancelException e){
            e.printStackTrace();
        }
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
