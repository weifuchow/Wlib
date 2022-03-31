package com.weifuchow.jdk.learn.io;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/24 10:15
 */
public class Producer {


    static File file = new File("D:/test/1.txt");
    public static void main(String[] args) throws Exception {
//        testFileOs();

        // stream 流式对象，就是一个队列，从头一直往下读。类似的byteBuffer 三个指针控制位置
//        testbyteArrayStream();
        testbyteArray();
    }

    private static void testFileOs() throws IOException, InterruptedException {
        OutputStream os = new FileOutputStream(file);
        for (int i = 0; i < 1024; i++) {
            os.write(new String("测试测试中文\t " + i + " \n").getBytes(Charset.defaultCharset()));
        }
        System.out.println("写入完毕");
//        os.close();
        Thread.sleep(1000000);
    }


    // 不同outputStream 对应不同的载体，File , 一级流，file、byteArray、byte
    // 耳机，需要outstream 作为初始化操作。将结果输出到一级流。如objectOutputsteam.
    public static void testbyteArray() throws Exception {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(1024);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);
        dataOutputStream.writeUTF("fuck u hhelooss  你好");
        dataOutputStream.flush();
//        objectOutputStream.writeObject(new Integer(11));
        System.out.println(byteOutputStream.size());
        System.out.println(byteOutputStream.toString());
//        dataOutputStream.write();
    }

    public static void testbyteArrayStream() throws IOException {
        // 会扩容
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(1024);
        byte[] bytes = new byte[4096];
        for (int i = 0; i < 4096; i++) {
            bytes[i] = 'a';
        }

        byteOutputStream.write(bytes);
        //
        System.out.println(byteOutputStream.toByteArray());



    }

}
