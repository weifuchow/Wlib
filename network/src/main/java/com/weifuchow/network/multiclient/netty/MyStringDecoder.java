package com.weifuchow.network.multiclient.netty;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

public class MyStringDecoder extends LengthFieldBasedFrameDecoder {

    public MyStringDecoder() {
        super(100 * 1024 * 1024, 0, 4,0,4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        return byteBuf.toString(StandardCharsets.UTF_8);
    }
}
