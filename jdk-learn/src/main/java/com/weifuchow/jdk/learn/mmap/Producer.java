package com.weifuchow.jdk.learn.mmap;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copyright © weifuchow., Ltd. . .
 * 主要利用pageCache 技术，异步刷盘。提高吞吐量，无需手动flush,程序崩溃消息也都在。
 * 数据的写入，OS会先写入至Cache内，随后通过异步的方式由pdflush内核线程将Cache内的数据刷盘至物理磁盘上
 * @author: weifuchow
 * @date: 2021/2/5 16:07
 */
public class Producer {

    static final int LENGTH = 409600*1024; //
    static final int cout = 10001; // int 4byte 1024, 只能写入1024次。不然就会报错
    static FileChannel fc;

    public static void main(String[] args) throws Exception {
        fc = new RandomAccessFile("d:/mmap/test.dat", "rw").getChannel();
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);

        for (int i = 0; i < cout; i++) {
//            Thread.sleep(50);
            out.putInt(i);
            String message = String.format("the number:%s put success ;the buffer positon %s , capacity %s,remain %s,limit %s ", i, out.position(), out.capacity(),out.remaining(),out.limit());
            if(out.remaining() == 0){
                //                // 默认就limit  = capacity
                // bytebuffer 维护三个指针。position ，当前写入位置/读取的位置
                // capacity：在读/写模式下都是固定的，就是我们分配的缓冲大小（容量）。
                // limit 写模式，与capacity一致。读取模式则为写入时记录。
                // 所以一般写入后，需要通过flip 进行读取。
                // public final Buffer flip() {
                //        limit = position;
                //        position = 0;
                //        mark = -1;
                //        return this;
                // }
                out.flip();
            }
            System.out.println(message);
        }

        Thread.sleep(1000000);

    }

}
