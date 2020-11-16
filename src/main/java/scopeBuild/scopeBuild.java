package scopeBuild;

import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.types.ClassLoaderReference;
import com.ibm.wala.util.config.AnalysisScopeReader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class scopeBuild {
    public static void main(String[] args) throws IOException, InvalidClassFileException {
    AnalysisScope scope =
        buildScope(
            "D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\1-ALU\\target");
    }

    public static AnalysisScope buildScope(String target) throws IOException, InvalidClassFileException {
        ClassLoader javaLoader;
        AnalysisScope scope =
                AnalysisScopeReader.readJavaScope(
                        "D:\\大三上\\自动化测试\\Coder\\src\\main\\resources\\scope.txt",
                        new File("..\\resources\\exclusion.txt"),
                        null);
//        scope.addToScope(ClassLoaderReference.Application, new JarFile("D:\\大三上\\自动化测试\\Coder\\JarResource\\ALU.jar"));
        ArrayList<String> str = new ArrayList<>();
        ArrayList<String> classesPath = getPath(target,str);
        assert classesPath != null;
        for (String path : classesPath) {
            scope.addClassFileToScope(ClassLoaderReference.Application, new File(path));
        }
//        System.out.println(scope);
        return scope;
    }

    public static ArrayList<String> getPath(String target,ArrayList<String> results){
        File dir = new File(target);
        if(!dir.exists() || !dir.isDirectory()){
            System.out.println("The target is wrong!!");
            return null;
        }
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirfiles = dir.listFiles(file -> (file.isDirectory() || file.getName().endsWith(".class")));
        assert dirfiles != null;
        for(File file: dirfiles){
            if(file.isDirectory()){
                results = getPath(target+"\\"+file.getName(),results);
            }else {
                assert results != null;
                results.add(target+"\\"+file.getName());
            }
        }
        return results;
    }
}
