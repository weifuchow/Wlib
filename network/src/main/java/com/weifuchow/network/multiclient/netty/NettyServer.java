package com.weifuchow.network.multiclient.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    private Map<String, SocketChannel> map = new HashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void init(int port) throws IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("server-boss-group"));
        EventLoopGroup workerGroup2 = new NioEventLoopGroup(16,new DefaultThreadFactory("server-worker-group"));
        try {
            // 服务端启动引导工具类
            ServerBootstrap b = new ServerBootstrap();
            // 配置服务端处理的reactor线程组以及服务端的其他配置
            b.group(bossGroup, workerGroup2);
            b.channel(NioServerSocketChannel.class);
            b.option(ChannelOption.SO_BACKLOG, 100);
            b.handler(new LoggingHandler(LogLevel.DEBUG));
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //
                    map.put(ch.toString(),ch);
//                    logger.info("this {} channel connected ! then will be init channel!",ch);
                    //
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new MyStringDecoder());
                    p.addLast(new MyStringEncoder());
                    p.addLast(new EchoServerHandler());
                    //
                }
            });
            // 通过bind启动服务
            ChannelFuture f = b.bind(port).sync();
            // 阻塞主线程，知道网络服务被关闭
//            f.channel().closeFuture().sync();
            logger.info("server start up !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程组
//            bossGroup.shutdownGracefully();
//            workerGroup2.shutdownGracefully();
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
        channel.writeAndFlush(msg);
    }

    public static class EchoServerHandler extends  SimpleChannelInboundHandler<String>{

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String o) throws Exception {
            logger.info("server receive msg {} from {}",o,channelHandlerContext.channel());
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            logger.info(" {} channelActive success!",ctx.channel());
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            logger.info(" {} channelInactive!",ctx.channel());
        }
    }
}
