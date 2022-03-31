package com.weifuchow.network.multiclient.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient {

    private String id;
    private String name;
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    private Channel channel;
    private NettyClient self;

    public NettyClient(String id, String name) {
        this.id = id;
        this.name = name;
        self = this;
    }



    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void connect(String host, int port, NioEventLoopGroup workGroup) {
        Bootstrap bootstrap = new Bootstrap();


        bootstrap.group(new NioEventLoopGroup());

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);

        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast(new MyStringDecoder());
                p.addLast(new MyStringEncoder());
                p.addLast(new EchoClientHandler(self));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener((ChannelFuture future) -> {
            if (future.isSuccess()) {
                logger.info("client {} connected", future.channel());
                this.channel = future.channel();
            } else {
                logger.info("client {} connected error", future.channel());
                logger.error("error",future.cause());
            }
        });
    }

    public void sendData(String msg){
        this.channel.writeAndFlush(msg);
    }

    public static class EchoClientHandler extends SimpleChannelInboundHandler<String> {

        private NettyClient instance;
        EchoClientHandler(NettyClient self){
            this.instance = self;
        }
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String string) throws Exception {
            logger.info("client channel {} receive msg {}", channelHandlerContext.channel(), string);
            channelHandlerContext.writeAndFlush("hey server i am client = >" + instance.getName());
            logger.info("client send msg to server : hey server i am client = >" + instance.getName());

        }
    }
}
