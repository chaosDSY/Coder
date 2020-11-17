package Excute;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMember;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.WalaRuntimeException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.FileProvider;

import java.io.File;
import java.io.IOException;

public class WALA {
    public static void main(String args[]) throws IOException,
            ClassHierarchyException,IllegalArgumentException,
            InvalidClassFileException, CancelException{
    // 获取一个文件
    File exFile =
        new FileProvider().getFile("D:\\大三上\\自动化测试\\Coder\\src\\main\\resources\\exclusion.txt");
    // 将分析域存到文件

//    AnalysisScope scope =
//        AnalysisScopeReader.makeJavaBinaryAnalysisScope(
//            "D:\\大三上\\自动化测试\\Coder\\JarResource\\ALU.jar", exFile);
//        // 构建ClassHierarchy,相当于类的一个层级结构
//        System.out.println(scope);
//        ClassHierarchy cha = ClassHierarchyFactory.make(scope);
        // 循环遍历每一个类
//        for(IClass klass:cha){
//            // 打印类名
//            System.out.print(klass.getName().toString()+":");
//            // 判断当前类是否在jar中
//            if(scope.isApplicationLoader(klass.getClassLoader())){
//                // 对jar中的类的每个函数遍历，并打印函数名
//                for(IMember m:klass.getAllMethods()){
//                    System.out.print(m.getName().toString()+",");
//                }
//            }
//        }
    }

}
