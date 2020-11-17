package Excute;

import com.ibm.wala.cast.java.loader.JavaSourceLoaderImpl;
import com.ibm.wala.classLoader.ClassLoaderFactory;
import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.ShrikeBTMethod;
import com.ibm.wala.examples.drivers.ScopeFileCallGraph;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.Entrypoint;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.ipa.callgraph.impl.AllApplicationEntrypoints;
import com.ibm.wala.ipa.callgraph.impl.Util;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.FileProvider;
import com.sun.jmx.remote.util.ClassLoaderWithRepository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

public class Readme {
    public static void main(String[] args) throws ClassHierarchyException, IOException, CancelException, InvalidClassFileException {
        File exFile = new FileProvider().getFile("D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\2-DataLog\\data\\change_info.txt");
    //    AnalysisScope scope =
    //        AnalysisScopeReader.makeJavaBinaryAnalysisScope(
    //            "D:\\大三上\\自动化测试\\Coder\\Datalog.jar", null);
    File jarFile = new File("D:\\大三上\\自动化测试\\Coder\\JarResource\\Datalog.jar");
    System.out.println(jarFile.getClass().getClassLoader());

    AnalysisScope scope =
        AnalysisScopeReader.readJavaScope(
            "D:\\大三上\\自动化测试\\Coder\\src\\main\\resources\\scope.txt",
            new File("..\\resources\\exclusion.txt"),
                jarFile.getClass().getClassLoader());
    System.out.println(scope);
//            scope.addToScope(
//                    ClassLoaderReference.Application,
//                    new JarFile("D:\\大三上\\自动化测试\\Coder\\JarResource\\Datalog.jar", false));
    scope.addClassFileToScope(
        ClassLoaderReference.Application,
        new File(
            "D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\1-ALU\\target\\classes\\net\\mooctest\\ALU.class"));
        scope.getModules(ClassLoaderReference.Application).clear();
        System.out.println(scope);
        Scope(scope);
    }

   public static void Scope(AnalysisScope scope) throws ClassHierarchyException, CancelException {
       ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
       for (IClass iClass : cha) {
           if(iClass.toString().contains("Application"))
               System.out.println(iClass);
       }
       /* 省略构建分析域（AnalysisScope）对象scope的过程 */

        // 1.生成类层次关系对象
//       ClassHierarchy cha = ClassHierarchyFactory.makeWithRoot(scope);
//
//        // 2.生成进入点
//       Iterable<Entrypoint> eps = new AllApplicationEntrypoints(scope, cha);
//        // 3.利用CHA算法构建调用图
//       CHACallGraph cg = new CHACallGraph(cha);
//       cg.init(eps);
//        // 4.遍历cg中所有的节点
//       for(CGNode node: cg) {
//           // node中包含了很多信息，包括类加载器、方法信息等，这里只筛选出需要的信息
//           if(node.getMethod() instanceof ShrikeBTMethod) {
//               // node.getMethod()返回一个比较泛化的IMethod实例，不能获取到我们想要的信息
//               // 一般地，本项目中所有和业务逻辑相关的方法都是ShrikeBTMethod对象
//               ShrikeBTMethod method = (ShrikeBTMethod) node.getMethod();
//               // 使用Primordial类加载器加载的类都属于Java原生类，我们一般不关心。
//               if("Application".equals(method.getDeclaringClass().getClassLoader().toString())) {
//                   // 获取声明该方法的类的内部表示
//                   String classInnerName = method.getDeclaringClass().getName().toString();
//                   // 获取方法签名
//                   String signature = method.getSignature();
//                   System.out.println(classInnerName + " " + signature);
//               }
//           } else {
//               System.out.println(String.format("'%s' 不是一个ShrikeBTMethod： %s",
//                       node.getMethod(), node.getMethod().getClass()));
//           }
//       }
   }
}
