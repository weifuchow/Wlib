package com.weifuchow.jdk.learn.mmap;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/5 16:07
 */
public class Consumer {

    static final int LENGTH = 4096*1024; // 4kb
    static final int cout = 10000;
    static FileChannel fc;

    public static void main(String[] args) throws Exception {
        fc = new RandomAccessFile("d:/mmap/test.dat", "rw").getChannel();
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);

        // 本质是针对是ByteBuffer 做操作 实际就是一个流
        for (int i = 0; i < cout; i++) {
//            Thread.sleep(10);
            int result = out.getInt();
            String message = String.format("the number:%s put success ;the buffer positon %s , capacity %s,remain %s,limit %s ", result, out.position(), out.capacity(),out.remaining(),out.limit());
//            if(out.remaining() == 0){
//                // 默认就limit  = capacity
//                // bytebuffer 维护三个指针。position ，当前写入位置/读取的位置
//                // capacity：在读/写模式下都是固定的，就是我们分配的缓冲大小（容量）。
//                // limit 写模式，与capacity一致。读取模式则为写入时记录。
//                // 所以一般写入后，需要通过flip 进行读取。
//                out.flip(); // 1.limit = position ,2.flip // 循环读读。
//            }
            System.out.println(message);
        }

        Thread.sleep(1000000);

    }
}
