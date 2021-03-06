# MQ消息同步处理

## 概述

- 在一些场景中，需要进行数据同步处理，比如为了实时性，需要立马知道消息的处理情况。比如服务调用及属性设置。

## 流程

- 发送线程：发送下行消息后，方法阻塞。
- 订阅线程：在订阅之时唤醒发送线程。



## （单机）

- 定义一个全局Map<String, ArrayBlockingQueue<Object>>
- 当属性设置或者服务调用时。new ArrayBlockingqueue.然后wait数据到来。并以消息的key写入到map中。然后阻塞。
- 异步订阅线程：匹配到消息在map中。然后往blockingqueue塞数据即可。



## 分布式处理。

- 考虑到若多台机子负载均衡订阅topic时。此时消息有可能被在另一台机器订阅到。即map无法保证映射成功。
- 发送下行消息后，方法阻塞。

- 方式一：
  - rocketmq client以订阅相同topic，不同queue作为负载均衡的实现，若保证请求写入的queue.和响应的queue为同一个时即可。和单机实现。
- 方式二：
  - 利用redis.实现一个同步队列器。
  - 消息发送时，id写入到redis中，并将线程阻塞。
  - 订阅时：检查redis id是否存在。若存在消息写入redis。因为分布式情况。当消息在另一台机器订阅到时，那么需要一个通知到另一台机器中。否则利用定时任务同步处理。



