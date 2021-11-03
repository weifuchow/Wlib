package com.weifuchow.network.multiclient.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class MyStringEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String s, ByteBuf byteBuf) throws Exception {
        byte[] arrays = s.getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(arrays.length);
        byteBuf.writeBytes(arrays);
    }
}
