package com.weifuchow.disruptor.queue;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/26 19:00
 */
public class Message {

    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public Message setName(String name) {
        this.name = name;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }


    public Message(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
