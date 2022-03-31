package com.weifuchow.network.multiclient.netty.memory;

import cn.hutool.core.util.RandomUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

/**
 *
 * @Desc MemoryTester
 *
 * @Author zhouweifu
 * @Date 2022/3/7
 */
public class MemoryTester {



    public static void main(String[] args) {


//        ByteBufAllocator.DEFAULT => pool 、
        // netty 内存如何分配
        String data = RandomUtil.randomString(4096);

        //
//        ByteBufAllocator.DEFAULT
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
//        allocator.buffer();//
        ByteBuf byteBuf = allocator.buffer(286);
        System.out.println(byteBuf);

        ByteBuf byteBuf2  = allocator.buffer(287);
        System.out.println(byteBuf2);

        ByteBuf byteBuf3  = allocator.buffer(288);
        System.out.println(byteBuf3);

        byteBuf.release();

        ByteBuf byteBuf4 = allocator.buffer(286);
        System.out.println(byteBuf4);


        ByteBuf byteBuf5 = allocator.buffer(8193);
        System.out.println(byteBuf5);


        System.out.println(byteBuf == byteBuf2);




    }

}
