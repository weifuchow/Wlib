# ClassLoader 类加载器

- 概述
  - ![img](https://upload-images.jianshu.io/upload_images/2025271-71283413c8178179.png?imageMogr2/auto-orient/strip|imageView2/2/w/1086/format/webp)
  - 从文档中对ClassLoader类的介绍可以总结出这个类的作用就是根据一个指定的类的全限定名,找到对应的Class字节码文件,然后加载它转化成一个java.lang.Class类的一个实例.
- ClassLoader 树

![img](https://upload-images.jianshu.io/upload_images/2025271-bc7b0cebbf242d1d.png?imageMogr2/auto-orient/strip|imageView2/2/w/572/format/webp)



**启动类加载器(Bootstrap ClassLoader):**

　　　　这个类加载器负责将\lib目录下的类库加载到虚拟机内存中,用来加载java的核心库,此类加载器并不继承于java.lang.ClassLoader,不能被java程序直接调用,代码是使用C++编写的.是虚拟机自身的一部分.（调用时返回为空）

**扩展类加载器(Extendsion ClassLoader):**

这个类加载器负责加载\lib\ext目录下的类库,用来加载java的扩展库,开发者可以直接使用这个类加载器.

**应用程序类加载器(Application ClassLoader):**

　　　　这个类加载器负责加载用户类路径(CLASSPATH)下的类库,一般我们编写的java类都是由这个类加载器加载,这个类加载器是CLassLoader中的getSystemClassLoader()方法的返回值,所以也称为系统类加载器.一般情况下这就是系统默认的类加载器.



## 双亲委派

类加载器的双亲委派模型:

　　双亲委派模型是一种组织类加载器之间关系的一种规范,他的工作原理是:如果一个类加载器收到了类加载的请求,它不会自己去尝试加载这个类,而是把这个请求委派给父类加载器去完成,这样层层递进,最终所有的加载请求都被传到最顶层的启动类加载器中,只有当父类加载器无法完成这个加载请求(它的搜索范围内没有找到所需的类)时,才会交给子类加载器去尝试加载.

　　这样的好处是:java类随着它的类加载器一起具备了带有优先级的层次关系.这是十分必要的,比如java.langObject,它存放在\jre\lib\rt.jar中,它是所有java类的父类,因此无论哪个类加载都要加载这个类,最终所有的加载请求都汇总到顶层的启动类加载器中,因此Object类会由启动类加载器来加载,所以加载的都是同一个类,如果不使用双亲委派模型,由各个类加载器自行去加载的话,系统中就会出现不止一个Object类,应用程序就会全乱了.



## 实例

- 远程加载jar包。并使用

```java
public class MyUrlClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader originloader = MyUrlClassLoader.class.getClassLoader();
        System.out.println(originloader);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("D:\\ideaworkspace\\Wlib\\reactive\\target\\reactive-1.0-SNAPSHOT.jar").toURL()});
        Class<?> clazz = classLoader.loadClass("org.weifuchow.HelloWorld");
        clazz.newInstance();
        // throw exception class not found
        Class<?> clazz1 = originloader.loadClass("org.weifuchow.HelloWorld");
        System.out.println(clazz1);

    }
}
// 运行结果
sun.misc.Launcher$AppClassLoader@18b4aac2
hello world init success
java.net.URLClassLoader@2503dbd3
Hello world你好
Exception in thread "main" java.lang.ClassNotFoundException: org.weifuchow.HelloWorld
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:335)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	at com.weifuchow.jdk.learn.classloader.MyUrlClassLoader.main(MyUrlClassLoader.java:23)
```

```java
public class HelloWorld {

    public HelloWorld(){
        print();
    }
    public static void main(String[] args) {
        Flowable.just("Hello world你好").subscribe(System.out::println);
        System.out.println(new HelloWorld().getClass().getClassLoader());
    }

    public void print(){
        System.out.println("hello world init success");
        System.out.println(this.getClass().getClassLoader());
        Flowable.just("Hello world你好").subscribe(System.out::println);

    }
}

```







## 遇到问题

-  3.0 使用URLClassLoader 加载外部jar包。

  - ```java
    public class JarLoader extends URLClassLoader {
        public JarLoader(String[] paths) {
            this(paths, JarLoader.class.getClassLoader());
        }
    
        public JarLoader(String[] paths, ClassLoader parent) {
            super(getURLs(paths), parent);
        }
    
        private static URL[] getURLs(String[] paths) {
            Validate.isTrue(null != paths && 0 != paths.length, "jar包路径不能为空.");
    
            List<URL> urls = new ArrayList<>();
            for (String path : paths) {
                try {
                    urls.add(new File(path).toURI().toURL());
                } catch (MalformedURLException e) {
                    throw new RuntimeException("加载jar包出错", e);
                }
            }
            return urls.toArray(new URL[0]);
        }
    }
    ```

- Hive组件，需要通加载类，默认采用的Thread.currentThread().getContextClassLoader() （该值为AppClassLoader）

  - AppClassLoader 通过双亲委派也无法找到urlclassloader 加载的内容

  ```java
  static {
          deprecationContext = new AtomicReference(new Configuration.DeprecationContext((Configuration.DeprecationContext)null, defaultDeprecations));
          ClassLoader cL = Thread.currentThread().getContextClassLoader();
          if (cL == null) {
              cL = Configuration.class.getClassLoader();
          }
  
          if (cL.getResource("hadoop-site.xml") != null) {
              LOG.warn("DEPRECATED: hadoop-site.xml found in the classpath. Usage of hadoop-site.xml is deprecated. Instead use core-site.xml, mapred-site.xml and hdfs-site.xml to override properties of core-default.xml, mapred-default.xml and hdfs-default.xml respectively");
          }
  
          addDefaultResource("core-default.xml");
          addDefaultResource("core-site.xml");
      }
  ```

- 解决方法：覆盖默认的classloader.使用`hdfsConf.setClassLoader(this.getClass().getClassLoader());`

