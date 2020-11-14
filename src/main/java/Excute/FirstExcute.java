package Excute;

import com.ibm.wala.classLoader.Language;
import com.ibm.wala.ipa.callgraph.AnalysisCacheImpl;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.jar.JarFile;

public class FirstExcute {
    public static String[] getSystemInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("please input your command: ");
        String command = input.nextLine();
        String[] commandList = command.split(" ");
        for (String s : commandList) {
            System.out.print(s + ",");
        }
        return null;
    }

    public static AnalysisScope scopeBuild(String target, JarFile jar) throws IOException {
        AnalysisScope scope =
                AnalysisScopeReader.readJavaScope(
                        "D:\\大三上\\自动化测试\\Coder\\src\\main\\resources\\scope.txt",
                        new File("D:\\大三上\\自动化测试\\Coder\\src\\main\\resources\\exclusion.txt"),
                        ClassLoader.getSystemClassLoader());
//        ClassHierarchy cha = ClassHierarchyFactory.make(scope);
        scope.addToScope(
                ClassLoaderReference.Application, jar);
        return scope;
    }

    // 基于类
    public static void ExcuteC(AnalysisScope scope) throws ClassHierarchyException, CancelException {
        ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
        Iterable<Entrypoint> eps = new AllApplicationEntrypoints(scope, cha);
        CHACallGraph cg = new CHACallGraph(cha);
        cg.init(eps);
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

    public static void main(String[] args) throws ClassHierarchyException, CancelException, IOException {
        String[] commandList = getSystemInput();
        assert commandList != null;
        if(!commandList[0].equals("java") || !commandList[1].equals("-jar")){
            System.out.println("The command is error!");
            return;
        }
        String testSelection = commandList[2];
        JarFile jar = new JarFile(testSelection, false);
        String methods = commandList[3];
        String project_target = commandList[4];
        String change_info = commandList[5];
        AnalysisScope scope = scopeBuild(project_target, jar);
        if(methods.equals("-c")){
            ExcuteC(scope);
        }else if(methods.equals("-m")){
            ExcuteM(scope);
        }
    }
}
