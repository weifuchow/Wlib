package com.weifuchow.jdk.learn.io.file;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TransferToSendFile {

    private static void doCopyNIO(String inFile, String outFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel cis = null;
        FileChannel cos = null;

        long len = 0, pos = 0;

        try {
            fis = new FileInputStream(inFile);
            cis = fis.getChannel();
            fos = new FileOutputStream(outFile);
            cos = fos.getChannel();
            len = cis.size();
            /*while (pos < len) {
                pos += cis.transferTo(pos, (1024 * 1024 * 10), cos);    // 10M
            }*/
            cis.transferTo(0, len, cos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
