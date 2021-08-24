## Maven 本地导入

- 引入Jar包报错

- 'dependencies.dependency.systemPath' for xxx:jar should not point at files within the project directory,will be unresolvable by dependent project

- 解决方案两种：

  - 利用命令把项目中的jar直接安装在公司的Maven私服或者本地Maven库中
  - 上面的缺点就是不灵活，办公区域受限制；可以使用maven的插件动态的安装到所在机器的本地仓库([参考这里](https://links.jianshu.com/go?to=http%3A%2F%2Fwww.it610.com%2Farticle%2F3548222.htm))：

- 方案一：

  - 直接使用命令将本地jar安装到本地仓库：

    

    ```shell
    mvn install:install-file -Dfile=lib/alipay-sdk-java20161213173952.jar -DgroupId=com.alipay -DartifactId=alipay-sdk-java -Dversion=20161213173952 -Dpackaging=jar
    ```

  - 附私服部署命令：

    ```shell
    mvn deploy:deploy-file -DgroupId=com.alipay -DartifactId=alipay-sdk-java -Dversion=20161213173952 -Dpackaging=jar -Dfile=<local-jar-file-path> -Durl=<nexus-server-http(s)-full-path> -DrepositoryId=<nexus-server-id>
    
    mvn deploy:deploy-file -DgroupId=com.weifuchow.dm -DartifactId=DmJdbcDriver -Dversion=1.8 -Dpackging=jar -Dfile=./dm/lib/DmJdbcDriver18.jar -Durl=http://nexus.xxx.com/repository/maven-releases/ -DrepositoryId=xx
    ```

    - 指的version 要与Snapshots或者release版本。不然会报405

- 方案二：

  - POM正常引用

  - 在pom中添加插件，如：

    

    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-install-plugin</artifactId>
        <version>2.5.2</version>
        <executions>
            <execution>
                <id>install-external</id>
                <phase>clean</phase>
                <configuration>
                    <file>${basedir}/lib/alipay-sdk-java20161213173952.jar</file>
                    <repositoryLayout>default</repositoryLayout>
                    <groupId>com.alipay</groupId>
                    <artifactId>alipay-sdk-java</artifactId>
                    <version>20161213173952</version>
                    <packaging>jar</packaging>
                    <generatePom>true</generatePom>
                </configuration>
                <goals>
                    <goal>install-file</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
    ```

    - 当项目install的时候就会自动安装到本地仓库





## Maven 分析依赖

- mvn dependency:tree -X