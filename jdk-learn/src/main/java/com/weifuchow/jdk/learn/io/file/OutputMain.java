package com.weifuchow.jdk.learn.io.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Copyright © weifuchow., Ltd. . .
 * @desc: 写入文件 (https://blog.csdn.net/mrliuzhao/article/details/89453082)
 * @author: weifuchow
 * @date: 2021/4/19 15:56
 */
public class OutputMain {


    public static void main(String[] args) throws IOException {
        File file = new File("D:/test/number.in");
        byte[] bytes = new byte[4];
        // write
//        try(FileOutputStream fos = new FileOutputStream(file)){
//            for (int i = 0; i < 1; i++) {
//                bytes [0] = 0x10;
//                bytes [1] = 0x00;
//                bytes [2] = 0x00;
//                bytes [3] = 0x00;
//                System.out.println(Arrays.toString(bytes));
//                fos.write(bytes);
//            }
//        }
        // read
//        try(FileInputStream fis = new FileInputStream(file)){
//            fis.read(bytes);
//        }
//        System.out.println(Arrays.toString(bytes));
        ByteBuffer buffer = ByteBuffer.allocate(4);
        //
        bytes [0] = 0x10;
        bytes [1] = 0x00;
        bytes [2] = 0x00;
        bytes [3] = 0x00;
        //
        buffer.put(bytes);
        buffer.flip();
        System.out.println(Arrays.toString(bytes));
        System.out.println(buffer.getInt());
        buffer.flip();
        //
        bytes[3] = 0x10;
        bytes[2] = 0x00;
        bytes[1] = 0x00;
        bytes[0] = 0x00;
        //
        buffer.put(bytes);
        buffer.flip();
        System.out.println(Arrays.toString(bytes));
        System.out.println(buffer.getInt());

    }


}
