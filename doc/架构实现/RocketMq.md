# Rocket mq

- 概述
  - Rocket mq 通过NameSrv 获取broker 地址。NameSrv 相当于一个注册中心。
  - 不是代理。而是通过NameSrv 获取地址。

## Rocket mq 客户端负载均衡

- 如何保证一个group的consumer 不会消费同一个topic 下的queue.
- 客户端通过请求获取服务端的消息。若多个客户端同时请求进来。是broker 通知 consumer 请求该队列。还是consumer 通过broker 消息进行判断。这样肯定会有可能出现错误消费。
- rocket mq 存储模型



## Rocket mq 筛选 tag的过程

- tag 通配符影响性能不。影响消费端性能。



