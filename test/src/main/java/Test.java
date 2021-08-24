import java.io.*;
import java.nio.charset.Charset;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/3/9 18:49
 */
public class Test {

    // java 远程调式
    // linux 中启动jar时增加参数(java 自带的调式协议)   -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 （8000即为）
    // idea端Edit-Configurations ,添加remote,把对应ip 和端口填入即可。！
    // 本质利用的传输协议
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("file.encoding"));
        Charset.defaultCharset();
        Integer integer = startTask("/opt/3.0/worknode/_psc/bin/start.sh", args[0]);
        System.out.println("pid = >" + integer);
    }


    public static Integer startTask(String scriptPath, String taskId) throws Exception {
        if (taskId == null || "".equals(taskId.trim())) {
            throw new IllegalArgumentException("task id can't be null");
        }
        String sh = String.format("sh %s %s", scriptPath, taskId);
        System.out.println("command:=>" + sh);
        try (BufferedReader inputReader =
                     new BufferedReader(new InputStreamReader(
                             Runtime.getRuntime().exec(sh
                             ).getInputStream())
                     )) {
            //当前输出
            String line = null;
            //最后一次输出
            String last = null;
            while ((line = inputReader.readLine()) != null) {
                last = line;
            }
            //这个能够成功执行，则会返回PID，否则表示执行失败
            return Integer.valueOf(last); //todo
        } catch (Exception e) {
            throw e;
        }
    }


}
