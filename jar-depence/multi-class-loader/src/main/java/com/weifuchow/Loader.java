package com.weifuchow;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * weifuchow only.
 *
 * @author: weifuchow
 * @date: 2021/6/7 10:31
 */
public class Loader {

    private static String A_JAR_PATH = "D:\\ideaworkspace\\Wlib\\jar-depence\\w-A\\target\\w-A-1.0-SNAPSHOT-jar-with-dependencies.jar";
    private static String B_JAR_PATH = "D:\\ideaworkspace\\Wlib\\jar-depence\\w-B\\target\\w-B-1.1-SNAPSHOT-jar-with-dependencies.jar";
    private static String C_JAR_PATH = "D:\\ideaworkspace\\Wlib\\jar-depence\\w-C\\target\\w-C-1.2-SNAPSHOT-jar-with-dependencies.jar";
    private static String COMMONS_JAR_PATH = "D:\\ideaworkspace\\Wlib\\jar-depence\\w-commons\\target\\w-commons-1.3-SNAPSHOT-jar-with-dependencies.jar";

    private ConcurrentHashMap<String, ClassLoader> maps = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, ClassLoader> getMaps() {
        return maps;
    }

    public Map<String, ClassLoader> loadJars() throws Exception {
        maps.put("A",load(A_JAR_PATH));
        maps.put("B",load(B_JAR_PATH));
        maps.put("C",load(C_JAR_PATH));
        maps.put("COMMON",load(COMMONS_JAR_PATH));
        return maps;
    }

    public ClassLoader getLoader(String name){
        return this.maps.get(name);
    }

    public void updateLoader(String name,String newPath) throws Exception {
        hotLoader(this.maps,name,newPath);
    }

    public void hotLoader(Map<String, ClassLoader> classLoaderMap,String jarName,String newPath) throws Exception {
        URLClassLoader loader = (URLClassLoader) classLoaderMap.get(jarName);
        loader.close();
        classLoaderMap.put(jarName,load(newPath));
    }

    public ClassLoader load(String path) throws Exception {
        URL[] urls =  new URL[1];
        urls[0] = new File(path).toURL();
        return new URLClassLoader(urls);
    }

}
