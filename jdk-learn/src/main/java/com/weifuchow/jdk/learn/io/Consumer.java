package com.weifuchow.jdk.learn.io;

import java.io.File;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/24 10:16
 */
public class Consumer {

    public static void main(String[] args) {
        // read () 阻塞，调用syscall,从磁盘读取一个字节到内核空间；紧接着在调用一次syscall,从内核到用户空间复制。
        // 最后用户空间再到一次jvm的一次复制。（无法避免）
        // mmap 省去了内核空间到用户空间的copy

        // write() 阻塞，jvm层-clbbuffer【数据写入到用户空间（buffer）】。用户空间再到内核空间，内核空间在写入磁盘文件
        // mmap  jvm_clibbuffer【数据写入“映射的用户空间”】，在写入到磁盘。（）



        File file = new File("D:/test/1.txt");


    }

}
