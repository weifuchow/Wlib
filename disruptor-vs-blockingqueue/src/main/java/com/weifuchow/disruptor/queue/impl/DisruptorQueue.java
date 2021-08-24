package com.weifuchow.disruptor.queue.impl;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.weifuchow.disruptor.queue.IQueue;
import com.weifuchow.disruptor.queue.Main;
import com.weifuchow.disruptor.queue.Message;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/26 19:07
 */
public class DisruptorQueue implements IQueue {

    private ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
    private WaitStrategy waitStrategy = new BusySpinWaitStrategy();
    Disruptor<MessageEvent> disruptor
            = new Disruptor<>(
            MessageEvent.EVENT_FACTORY,
            4096,
            threadFactory,
            ProducerType.MULTI,
            waitStrategy);


    private RingBuffer<MessageEvent> ringBuffer ;
    {
        disruptor.handleEventsWith(new EventHandler<MessageEvent>() {
            @Override
            public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {
                System.out.println(Main.getRunTime() +  Thread.currentThread().getName() + " - messageEvent => " + messageEvent.getMessage());
//                System.out.println(Main.getRunTime() +  Thread.currentThread().getName() + " - messageEvent => " + messageEvent.getMessage() + "\t sequence =>" + l + " endOfBatch => " + b);
            }
        });
        ringBuffer = disruptor.start();
    }


    @Override
    public void send(Message message) {
        long sequenceId = ringBuffer.next();
        MessageEvent messageEvent = ringBuffer.get(sequenceId);
        messageEvent.setMessage(message);
        ringBuffer.publish(sequenceId);
//        System.out.println("publish sequenceId => " + sequenceId);
    }

    @Override
    public Message get() {
        return null;
    }






    private static class MessageEvent{
        private Message message;
        public final static EventFactory EVENT_FACTORY = () -> new MessageEvent();

        public Message getMessage() {
            return message;
        }

        public MessageEvent setMessage(Message message) {
            this.message = message;
            return this;
        }


    }


}
