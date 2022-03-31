package com.weifuchow.network.multiclient.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private ServerSocketChannel serverChannel;
    private Selector selector;
    private Logger logger = LoggerFactory.getLogger(Server.class);
    private Map<String, SocketChannel> map = new HashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private ByteBuffer headByteBuffer;
    private Integer length;
    private ByteBuffer bodyByteBuffer;

    public void init(int port) throws IOException {
        //
        selector = Selector.open();
        //
        serverChannel = ServerSocketChannel.open();
        serverChannel.socket().bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        //
        logger.info("server 启动成功!");
    }

    public void start() {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    int selNums = selector.select();
                    logger.info("client event loop selector nums {}", selNums);
                    if (selNums > 0) {
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey next = iterator.next();
                            iterator.remove();
                            if (next.isAcceptable()) {
                                active(next);
                            } else if (next.isReadable()) {
                                request(next);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("IO EVENT FAIL", e);
                }
            }
        }, "server-event-loop").start();

    }


    private void request(SelectionKey next) throws IOException {
        // TODO 利用bytebuffer 直接读任意长度，然后通过复位操作
        // 怪不得netty需要byteBuf,因为ByteBuf 可以复位操作
        // 测试粘包问题。减慢发送速率
        SocketChannel socketChannel = (SocketChannel) next.channel();
        if(length == null) {
            if (headByteBuffer == null) {
                this.headByteBuffer = ByteBuffer.allocate(4);
            }
            logger.info("add head byte");
            socketChannel.read(headByteBuffer);
            if (headByteBuffer.position() < 4) {
                return;
            }
            headByteBuffer.flip();
            this.length =  headByteBuffer.getInt();
        }

        if(bodyByteBuffer == null){
            this.bodyByteBuffer = ByteBuffer.allocate(length);
        }
        logger.info("add body byte");
        socketChannel.read(bodyByteBuffer);
        if(bodyByteBuffer.position() == length){
            bodyByteBuffer.flip();
            String content = new String(bodyByteBuffer.array(), StandardCharsets.UTF_8);
            logger.info("server {} receive message {}", socketChannel, content);
            // 复位
            headByteBuffer = null;
            length = null;
            bodyByteBuffer = null;
        }
    }

    private void active(SelectionKey next) {
        try {
            ServerSocketChannel channel = (ServerSocketChannel) next.channel();
            SocketChannel socketChannel = channel.accept();
            if (socketChannel == null) {
                return;
            }
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            map.put(socketChannel.getRemoteAddress().toString(), socketChannel);
            logger.info("localAddress {} 《=》 remoteAddress {} 连接成功 ！", socketChannel.getLocalAddress(), socketChannel.getRemoteAddress());
        } catch (Exception e) {
            logger.error("active error", e);
        }
    }


    public void sendData(String msg) {
        map.entrySet().stream().forEach(entry -> {
            try {
                String myMsg = msg + "->" + atomicInteger.incrementAndGet();
                String k = entry.getKey();
                SocketChannel v = entry.getValue();
                sendData(v, myMsg);
                logger.info("server send {} {} {}", k, myMsg, v);
            } catch (Exception e) {
                logger.error("send error ", e);
            }
        });
        logger.info("send finished!");
    }

    public void sendData(SocketChannel channel, String msg) throws IOException {
        int length = msg.getBytes(StandardCharsets.UTF_8).length;
        ByteBuffer buffer = ByteBuffer.allocate(4 + length);
        buffer.putInt(length);
        buffer.put(msg.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
    }

}
