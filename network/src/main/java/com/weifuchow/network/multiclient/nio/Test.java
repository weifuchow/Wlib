package com.weifuchow.network.multiclient.nio;


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
    private static Server server = new Server();
    private static Selector sel ;
    private static List<Client> list = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9090;
        Selector sel = Selector.open();
        //
        createServer(port);
        //
        for (int i = 0; i < 1000; i++) {
            createClient(host, port, sel);
        }
        //
        new Thread(() -> {
            try {
                eventLoop(sel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"client pool").start();
        //
        Thread.sleep(5000);
        server.sendData("hello world!");
        //
        list.forEach(client -> {
            client.sendData("hello world server");
        });
    }

    public static void createServer(int port) throws IOException {
        server.init(port);
        server.start();
    }

    public static void createClient(String host, int port, Selector sel) throws IOException {
        int id = counter.incrementAndGet();
        Client client = new Client("test-" + id, "name-" + id);
        client.connect(host, port, sel);
        list.add(client);
    }

    public static void eventLoop(Selector sel) throws Exception {
        while (!Thread.interrupted()) {
            //
            if (sel.select() > 0) {
                Set<SelectionKey> selectionKeys = sel.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isConnectable()) {
                        processConnect(next);
                    } else if (next.isReadable()) {
                        try {
                            proccessRead(next);
                        } catch (Exception e) {
                            next.cancel();
                            logger.error("error", e);
                        }
                    }
                }
            }
        }

    }

    private static void proccessRead(SelectionKey next) throws IOException {
        SocketChannel socketChannel = (SocketChannel) next.channel();
        ByteBuffer sizeBuf = ByteBuffer.allocate(4);
        int count = socketChannel.read(sizeBuf);
        sizeBuf.flip();
        int length = sizeBuf.getInt();
        ByteBuffer contentBuf = ByteBuffer.allocate(length);
        socketChannel.read(contentBuf);
        String content = new String(contentBuf.array(), StandardCharsets.UTF_8);
        logger.info("channel {} receive message {}", socketChannel, content);
        //
    }

    private static void processConnect(SelectionKey next) {
        SocketChannel socketChannel = (SocketChannel) next.channel();
        try {
            if (socketChannel.isConnectionPending() && socketChannel.finishConnect()) {
                logger.info("client localAddress {} remoteAddress {} connected ", socketChannel.getLocalAddress(),socketChannel.getRemoteAddress());
            }
        } catch (IOException e) {
            logger.error("processConnect error");
        }
    }
}
