package com.weifuchow.network.multiclient.nio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {

    private String id ;
    private String name;
    private SocketChannel socketChannel;
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(String id,String name){
        this.id = id;
        this.name = name;
    }


    public void connect(String host, int port, Selector selector) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        this.socketChannel.connect(new InetSocketAddress(host,port));
    }

    public void sendData(String msg){
        try {
            msg += "  : " +this.name;
            int length = msg.getBytes(StandardCharsets.UTF_8).length;
            ByteBuffer buffer = ByteBuffer.allocate(4 + length);
            buffer.putInt(length);
            buffer.put(msg.getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            this.socketChannel.write(buffer);
            logger.info("{} {} send finish", this.id, this.name);
        }catch (Exception e){
            logger.error("client send data error",e);
        }
    }

}
