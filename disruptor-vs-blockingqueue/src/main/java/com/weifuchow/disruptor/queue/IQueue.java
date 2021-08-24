package com.weifuchow.disruptor.queue;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/26 19:00
 */
public interface IQueue {
    
    
   void send(Message message);
   
   Message get();
    
}
