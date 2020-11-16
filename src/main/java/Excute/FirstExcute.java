package Excute;

import com.ibm.wala.classLoader.Language;
import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.ipa.callgraph.*;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.JarFile;

public class FirstExcute {
    public static String[] getSystemInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("please input your command: ");
        String command = input.nextLine();
        return command.split(" ");
    }

    // 基于类
    public static void ExcuteC(AnalysisScope scope) throws ClassHierarchyException, CancelException {
        ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
        Iterable<Entrypoint> eps = new AllApplicationEntrypoints(scope, cha);
        CHACallGraph cg = new CHACallGraph(cha);
        cg.init(eps);
        for(CGNode node: cg) {
           // node中包含了很多信息，包括类加载器、方法信息等，这里只筛选出需要的信息
           if(node.getMethod() instanceof ShrikeBTMethod) {
               // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
               // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
               ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
               // 使用Primordial类加载器加载的类都属于Java原生类，我们一般不关心。
               if("Application".equals(method.getDeclaringClass().getClassLoader().toString())) {
                   // 获取声明该方法的类的内部表示
                   String classInnerName = method.getDeclaringClass().getName().toString();
                   // 获取方法签名
                   String signature = method.getSignature();
                   System.out.println(classInnerName + " " + signature);
               }
           }
       }
    }

    // 基于方法
    public static void ExcuteM(AnalysisScope scope) throws ClassHierarchyException {
        ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
        AllApplicationEntrypoints entrypoints = new AllApplicationEntrypoints(scope,
                cha);
        AnalysisOptions option = new AnalysisOptions(scope, entrypoints);
        SSAPropagationCallGraphBuilder builder = Util.makeZeroCFABuilder(
                Language.JAVA, option, new AnalysisCacheImpl(), cha, scope
        );
    }

    // 读入txt文件
    public static ArrayList<String> changeInfoRead(String change_info){
        ArrayList<String> change_info_message = new ArrayList<>();
        try (FileReader reader = new FileReader(change_info);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                change_info_message.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return change_info_message;
    }

    //输出到路径下文件
    public static void writeFile(ArrayList<String> message,String outPutPath) {
        try {
            File writeName = new File(outPutPath);
            writeName.createNewFile();
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)) {
                for(String mes:message){
                    out.write(mes+"\n");
                }
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassHierarchyException, CancelException, IOException, InvalidClassFileException {
            String methods = "-c";
            String project_target =
                    "D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\1-ALU\\target";
            String change_info = "D:\\大三上\\自动化测试\\Coder\\change_info.txt";
            AnalysisScope scope = scopeBuild.scopeBuild.buildScope(project_target);
            ExcuteC(scope);
            ArrayList<String> mesg = changeInfoRead(
            "D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\1-ALU\\data\\change_info.txt");
//        for(String msg:mesg){
//            System.out.println(msg);
//        }
//        writeFile(mesg,"outPut.txt");
    }
}
