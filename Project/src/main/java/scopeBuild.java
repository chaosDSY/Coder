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
            "D:\\大三上\\自动化测试\\大作业\\经典大作业\\ClassicAutomatedTesting\\ClassicAutomatedTesting\\1-ALU\\target",
                "D:\\大三上\\自动化测试\\Coder\\Project\\src\\main\\resources\\scope.txt",
                "exclusion.txt");
    }

    /**
     *
     * @description : 创建分析域，向分析域中添加类文件
     * @param target : 目标路径
     * @return AnalysisScope
     * @throws IOException
     * @throws InvalidClassFileException
     */
    public static AnalysisScope buildScope(String target,String scopePath,String exclusionPath) throws IOException, InvalidClassFileException {
        AnalysisScope scope = AnalysisScopeReader.readJavaScope(scopePath, new File(exclusionPath), ClassLoader.getSystemClassLoader());
        // 分别将target目录下classes和test-classes中的class文件加入到分析域中
        ArrayList<String> str1 = new ArrayList<>();
        getPath(target+"\\classes",str1);
        for (String path : str1) {
            scope.addClassFileToScope(ClassLoaderReference.Application, new File(path));
        }
        ArrayList<String> str2 = new ArrayList<>();
        getPath(target+"\\test-classes",str2);
        for (String path : str2) {
            scope.addClassFileToScope(ClassLoaderReference.Application, new File(path));
        }
        return scope;
    }


    /**
     * @description : 获取路径下的所有class文件
     * @param target : 目标路径
     * @param results : 结果数据，方便递归
     * @return ArrayList<String>
     */
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
