# Netty Channel.WriteFlush

## 目的

- 验证 Channel.WriteFlush 是否为线程安全



## 发送线程

- AbstractChannelHandlerContext

  - ```java
    private void write(Object msg, boolean flush, ChannelPromise promise) {
            ObjectUtil.checkNotNull(msg, "msg");
    
            try {
                if (this.isNotValidPromise(promise, true)) {
                    ReferenceCountUtil.release(msg);
                    return;
                }
            } catch (RuntimeException var8) {
                ReferenceCountUtil.release(msg);
                throw var8;
            }
    
            AbstractChannelHandlerContext next = this.findContextOutbound(flush ? 98304 : '耀');
            Object m = this.pipeline.touch(msg, next);
            EventExecutor executor = next.executor();
            if (executor.inEventLoop()) {
                if (flush) {
                    next.invokeWriteAndFlush(m, promise);
                } else {
                    next.invokeWrite(m, promise);
                }
            } else {
                AbstractChannelHandlerContext.WriteTask task = AbstractChannelHandlerContext.WriteTask.newInstance(next, m, promise, flush);
                if (!safeExecute(executor, task, promise, m, !flush)) {
                    task.cancel();
                }
            }
    
        }
    
    @Override
    public boolean inEventLoop() {
        return inEventLoop(Thread.currentThread());
    }
    
    public boolean inEventLoop(Thread thread) {
        return thread == this.thread;
    }
    // this.Thread 为channel的绑定的线程。需要加
    
    // safe执行
    private static boolean safeExecute(EventExecutor executor, Runnable runnable, ChannelPromise promise, Object msg, boolean lazy) {
        try {
            if (lazy && executor instanceof AbstractEventExecutor) {
                ((AbstractEventExecutor)executor).lazyExecute(runnable);
            } else {
                executor.execute(runnable);
            }
    
            return true;
        } catch (Throwable var10) {
            Throwable cause = var10;
    
            try {
                promise.setFailure(cause);
            } finally {
                if (msg != null) {
                    ReferenceCountUtil.release(msg);
                }
    
            }
    
            return false;
        }
    }
    
    //
    private void execute(Runnable task, boolean immediate) {
        boolean inEventLoop = inEventLoop();
        addTask(task);
        if (!inEventLoop) {
            startThread();
            if (isShutdown()) {
                boolean reject = false;
                try {
                    if (removeTask(task)) {
                        reject = true;
                    }
                } catch (UnsupportedOperationException e) {
                    // The task queue does not support removal so the best thing we can do is to just move on and
                    // hope we will be able to pick-up the task before its completely terminated.
                    // In worst case we will log on termination.
                }
                if (reject) {
                    reject();
                }
            }
        }
    
        if (!addTaskWakesUp && immediate) {
            wakeup(inEventLoop);
        }
    }
    
    ```





## 通过日志查看效果

- 客户端

```java
ExecutorService pool = Executors.newFixedThreadPool(16);
for (int i = 0; i < 100; i++) {
    pool.submit(() -> {
        try {
            client.write("969-AgvInfoRequest-TYPE_NORMAL_TELEGRAM.sr", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
}
```

- 真正的发送线程**(均是通过指定的线程进行编码 [nioEventLoopGroup-2-5] ，该线程绑定了该channelSelector)**

  - > 2022-03-25 18:23:17.790 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.790 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 111 message => type: MSG_RESPONSE
    > seq: 969
    > session_id: 1638350275627
    > response {
    >   response_type: RESPONSE_INFO
    >   info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.790 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.790 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 112 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >    response {
    >   response_type: RESPONSE_INFO
    >   info {
    >    }
    >   result {
    >  result_state: RESPONSE_OK
    >}
    > }
    > 
    > 2022-03-25 18:23:17.790 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 113 message => type: MSG_RESPONSE
    > seq: 969
    >   session_id: 1638350275627
    >   response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 114 message => type: MSG_RESPONSE
    >    seq: 969
    >   session_id: 1638350275627
    >   response {
    >    response_type: RESPONSE_INFO
    >   info {
    > }
    >result {
    >  result_state: RESPONSE_OK
    > }
    > }
    > 
    > 2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >   2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 115 message => type: MSG_RESPONSE
    >   seq: 969
    >    session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >   2022-03-25 18:23:17.791 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 116 message => type: MSG_RESPONSE
    >   seq: 969
    >    session_id: 1638350275627
    >   response {
    > response_type: RESPONSE_INFO
    >info {
    > }
    > result {
    >  result_state: RESPONSE_OK
    > }
    > }
    >   
    >   2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 117 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >   
    >   2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 118 message => type: MSG_RESPONSE
    >   seq: 969
    > session_id: 1638350275627
    >response {
    > response_type: RESPONSE_INFO
    > info {
    > }
    > result {
    >  result_state: RESPONSE_OK
    >   }
    >   }
    >    
    >    2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.792 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 119 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >   }
    >   }
    >    
    >   2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 120 message => type: MSG_RESPONSE
    >seq: 969
    > session_id: 1638350275627
    > response {
    > response_type: RESPONSE_INFO
    > info {
    > }
    >   result {
    >    result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 121 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >   result {
    >    result_state: RESPONSE_OK
    >    }
    >   }
    > 
    >2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 122 message => type: MSG_RESPONSE
    > seq: 969
    > session_id: 1638350275627
    > response {
    > response_type: RESPONSE_INFO
    >   info {
    >   }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.793 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 123 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >   info {
    >   }
    >    result {
    >    result_state: RESPONSE_OK
    > }
    >}
    > 
    > 2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 124 message => type: MSG_RESPONSE
    > seq: 969
    > session_id: 1638350275627
    >   response {
    >   response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 125 message => type: MSG_RESPONSE
    >    seq: 969
    >    session_id: 1638350275627
    >   response {
    >   response_type: RESPONSE_INFO
    >    info {
    >   }
    > result {
    > result_state: RESPONSE_OK
    > }
    > }
    > 
    > 2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    > 2022-03-25 18:23:17.794 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 126 message => type: MSG_RESPONSE
    >   seq: 969
    >   session_id: 1638350275627
    >    response {
    >    response_type: RESPONSE_INFO
    >    info {
    >    }
    >    result {
    >     result_state: RESPONSE_OK
    >    }
    >    }
    >    
    >    2022-03-25 18:23:17.795 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:94  -this content length 194
    >    2022-03-25 18:23:17.795 [TID: N/A] [nioEventLoopGroup-2-5] INFO  sr.riot.brokerx.test.driver.MockAgvClient line:99  -counter 127 message => type: MSG_RESPONSE
    >   seq: 969
    >   session_id: 1638350275627
    >    response {
    >   response_type: RESPONSE_INFO
    > info {
    >}
    > result {
    >  result_state: RESPONSE_OK
    > }
    > }





## 结论

- channel.WirteFulsh为线程安全。因为他会将任务发送到指定的线程中执行（绑定了该channel selector 所在的线程）从而实现顺序发送。
- 原生的channel.wirte 不是有序的，在多线程的使用上会导致数据混乱，导致编解码异常
