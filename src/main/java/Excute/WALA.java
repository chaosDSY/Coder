package Excute;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMember;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.ipa.cha.ClassHierarchy;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.ipa.cha.ClassHierarchyFactory;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.config.AnalysisScopeReader;
import com.ibm.wala.util.io.FileProvider;

import java.io.File;
import java.io.IOException;

public class WALA {
    public static void main(String args[]) throws IOException,
            ClassHierarchyException,IllegalArgumentException,
            InvalidClassFileException, CancelException{
        // 获取一个文件
        File exFile = new FileProvider().getFile("Java60RegressionExclusions.txt");
        // 将分析域存到文件中
        AnalysisScope scope = AnalysisScopeReader.makeJavaBinaryAnalysisScope("zookeeper-3.3.6.jar",exFile);
        // 构建ClassHierarchy,相当于类的一个层级结构
        ClassHierarchy cha = ClassHierarchyFactory.make(scope);
        // 循环遍历每一个类
        for(IClass klass:cha){
            // 打印类名
            System.out.println(klass.getName().toString());
            // 判断当前类是否在zookeeper中
            if(scope.isApplicationLoader(klass.getClassLoader())){
                // 对zookeeper中的类的每个函数遍历，并打印函数名
                for(IMember m:klass.getAllMethods()){
                    System.out.println(m.getName().toString());
                }
            }
        }
    }

}
