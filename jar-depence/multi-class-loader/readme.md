# 验证多个urlClassloader加载使用，及更新替换问题

## 可以进行替换更新。相当于每个UrlClassLoader,是独立的。可以执行动态替换及更新。

## 并发控制
 *      1.当存在有其他线程使用着classloader则不更新。直到为使用完成。才能更新
 *      2.更新时，直到更新完成。其他线程获取会一直阻塞直到更新完成。
 - ConcurrentControllerByReadWriteLock 使用读写锁进行实现。
 - ConcurrentControllerByCounter 根据计数器队列进行实现。
 
- 队列 https://blog.csdn.net/yanyan19880509/article/details/52435135
- 重入锁https://blog.csdn.net/yanyan19880509/article/details/52345422


## 非公平和公平锁
- 是否允许别的线程插队，抢占执行权。
- 公平锁需要先进入。然后等待唤醒。然后去执行.
- 非公平锁则不需要进入队列中，判断当前是否有符合执行条件。若无则排队。有则抢占式。会导致饥饿