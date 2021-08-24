package com.weifuchow.disruptor.queue.impl;

import com.weifuchow.disruptor.queue.IQueue;
import com.weifuchow.disruptor.queue.Main;
import com.weifuchow.disruptor.queue.Message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/26 19:02
 */
public class JVMBlockingQueue implements IQueue {


    private BlockingQueue queue = new LinkedBlockingQueue();

    @Override
    public void send(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }


    public JVMBlockingQueue() {
        new Thread(this::loadMessage).start();
    }

    public void loadMessage(){
        while (true){
            get();
        }
    }

    @Override
    public Message get() {
        try {
            Message message =  (Message) queue.take();
            System.out.println(Main.getRunTime() + "-" + message);
            return message;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
