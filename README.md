# 自动化测试-经典方向

刁苏阳 181250026

# 1. 环境配置

1. 创建maven依赖项目，将wala环境需要的依赖通过maven进行导入
2. 从Graphviz官网下载安装程序，将安装完成后Craphviz目录下的bin路径添加到环境变量系统变量，终端测试dot -version检验环境安装
3. 项目环境，将提供的配置文件样例中的三个文件复制到项目resources目录下，修改wala.properities下java_runtime_dir变量值为 **.** 。将本地java jdk目录下的/jre/lib/rt.jar文件复制到项目resources目录下，resources目录下内容配置完成。
4. 在项目中打开File，选择Project Structure，选择Artifacts，点击“+”，选择JAR，From modules with dependencies，点击OK并返回。(注意需要配置META-INF包下的MANIFEST.MF文件，指定JAR包的主清单属性)。在项目页面打开Build，使用Build Artifacts完成JAR包打包

# 2. 项目实现

项目主要分三个类完成，Analysis、scopeBuild、TestSelection。

其中scopeBuild完成对分析域scope的构建；Analysis完成CHACallGraph cg图像的生成、对cg图的类分析/方法分析获得的依赖结果、根据change_info信息的类粒度/方法粒度测试用例筛选；TestSelection是JAR包执行的入口，初始化本地配置文件并通过对Analysis和scopeBuild的调用完成项目目标。

(注：其实可以一类到底，但这样的划分使得结构稍微清晰一点，也方便自己理解和调试)

