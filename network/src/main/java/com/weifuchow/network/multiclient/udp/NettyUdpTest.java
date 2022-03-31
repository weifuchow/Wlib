package com.weifuchow.network.multiclient.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * *
 * @Desc NettyUdpTest
 * @Author zhouweifu
 * @Date 2022/2/14
 */
    public class NettyUdpTest implements UdpTest {

    private Bootstrap serverBootStrap;
    private Bootstrap clientBootStrap;

    private static Logger log = LoggerFactory.getLogger(NettyUdpTest.class);

    @Override
    public void initServer() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new Decoder());
                        channel.pipeline().addLast(new Encoder());
                    }
                })
                .localAddress("0.0.0.0",9000);
        ;
        this.serverBootStrap = bootstrap;
    }

    @Override
    public void initClient() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(new Decoder());
                        channel.pipeline().addLast(new Encoder());
                    }
                })
        ;
        this.clientBootStrap = bootstrap;
    }

    @Override
    public void run() {
        try {

            NettyUdpTest tester = new NettyUdpTest();
            tester.initServer();
            tester.initClient();
            //
            tester.serverBootStrap.bind();
    //            Channel channel = tester.clientBootStrap.bind(0).sync().channel();
    //            for (int i = 0; i < 10000; i++) {
    //                String message = "hello - > " + i;
    //                channel.writeAndFlush(message).addListener((ChannelFutureListener) channelFuture -> {
    //                    log.info("client sender is ok message = > {}", message);
    //                });
    //
    //                Thread.sleep(1000);
    //            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static class Encoder extends MessageToMessageEncoder<String> {


        public static InetSocketAddress address = new InetSocketAddress("192.168.110.109", 9000);
    
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
            ByteBuf buf = channelHandlerContext.alloc().buffer();
            buf.writeBytes(s.getBytes(StandardCharsets.UTF_8));
            list.add(new DatagramPacket(buf, address));
        }
    }
    
    public static class Decoder extends MessageToMessageDecoder<DatagramPacket> {
    
        private static Logger log = LoggerFactory.getLogger(Decoder.class);
    
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket, List<Object> list) throws Exception {
            ByteBuf data = datagramPacket.content();
            byte[] bytes = new byte[data.readableBytes()];
            data.readBytes(bytes);
            String str = new String(bytes);
            log.info("receive sender = > {} message = > {}", datagramPacket.sender(), str);
        }
    }


    public static void main(String[] args) {
        NettyUdpTest tester = new NettyUdpTest();
        tester.run();
    }
}
