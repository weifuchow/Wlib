# Maven 插件开发

## 目的

- 执行 mvn install 之后，需要将jar包进行压缩并上传到内部服务器当中，进行一些额外的操作。



## maven 自定义Plugin

- MOJO
  - Mojo 就是 Maven plain Old Java Object。每一个 Mojo 就是 Maven 中的一个执行目标（executable goal），而插件则是对单个或多个相关的 Mojo 做统一分发。一个 Mojo 包含一个简单的 Java 类。插件中多个类似 Mojo 的通用之处可以使用抽象父类来封装。
- 创建 Mojo 工程

![这里写图片描述](https://img-blog.csdn.net/20171128103042929?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMjYyMDE1MA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- Maven 引入

  ```java
  <dependencies>
  
          <dependency>
              <groupId>org.apache.maven</groupId>
              <artifactId>maven-plugin-api</artifactId>
              <version>2.0</version>
          </dependency>
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>3.8.1</version>
              <scope>test</scope>
          </dependency>
          <dependency>
              <groupId>org.apache.maven.plugin-tools</groupId>
              <artifactId>maven-plugin-annotations</artifactId>
              <version>3.1</version>
          </dependency>
  
  </dependencies>
  ```

- Mojo 类编写

  ```java
  @Mojo(name = "Mymojo")
  public class MyMojo extends AbstractMojo {
      /**
       * Location of the file.
       *
       * @parameter expression="${project.build.directory}"
       * @required
       */
      private File outputDirectory;
  
      @Override
      public void execute() throws MojoExecutionException {
          System.out.println("fuck you!");
          try {
              Thread.sleep(10000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
          System.out.println("is finished!");
      }
  }
  
  ```



## 使用

- 与mvn install 执行绑定。

```xml
<plugin>
    <groupId>org.weifuchow</groupId>
    <artifactId>wplugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <executions>
        <execution>
            <!-- phase  执行阶段-->
            <phase>install</phase>
            <goals>
                <goal>Mymojo</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

- goal 为mojo 类的name

- 结果

  > [INFO] --- maven-install-plugin:2.4:install (default-install) @ test ---
  > [INFO] Installing D:\ideaworkspace\Wlib\test\target\test-1.0-SNAPSHOT.jar to D:\repository\org\weifuchow\test\1.0-SNAPSHOT\test-1.0-SNAPSHOT.jar
  > [INFO] Installing D:\ideaworkspace\Wlib\test\pom.xml to D:\repository\org\weifuchow\test\1.0-SNAPSHOT\test-1.0-SNAPSHOT.pom
  > [INFO] 
  > [INFO] --- wplugin:1.0-SNAPSHOT:Mymojo (default) @ test ---
  > fuck you!
  > is finished!
  > [INFO] ------------------------------------------------------------------------
  > [INFO] BUILD SUCCESS
  > [INFO] ------------------------------------------------------------------------
  > [INFO] Total time:  11.138 s
  > [INFO] Finished at: 2021-03-31T17:11:35+08:00
  > [INFO] ------------------------------------------------------------------------

  

