package com.weifuchow.network.multiclient.netty;


import com.weifuchow.network.multiclient.nio.Client;
import com.weifuchow.network.multiclient.nio.Server;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    private static AtomicInteger counter = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static NettyServer server = new NettyServer();
    private static List<NettyClient> list = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9090;
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(16,new DefaultThreadFactory("client-worker-group"));
        //
        createServer(port);
        //
        for (int i = 0; i < 5000; i++) {
            createClient(host, port, eventLoopGroup);
        }
        //
        Thread.sleep(5000);
        server.sendData("hello world!");
//        //
//        Thread.sleep(1000);
//        //
//        list.forEach(client -> {
//            client.sendData("hello world server " + UUID.randomUUID().toString());
//        });
    }

    public static void createServer(int port) throws IOException {
        server.init(port);
    }

    public static void createClient(String host, int port, NioEventLoopGroup eventLoopGroup) throws IOException {
        int id = counter.incrementAndGet();
        NettyClient client = new NettyClient("test-" + id, "name-" + id);
        client.connect(host, port,eventLoopGroup);
        list.add(client);
    }



}
