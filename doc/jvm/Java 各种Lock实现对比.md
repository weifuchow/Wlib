# Java 锁实现

## AQS

- 两大法宝

- CAS操作双向队列
- Lock Support 去阻塞或者去唤醒线程
  - park/unpark能够精准的对线程进行唤醒和等待。
  - linux上的实现是通过POSIX的线程API的等待、唤醒、互斥、条件来进行实现的
  - park在执行过程中首选看是否有许可，有许可就立马返回，而每次unpark都会给许可设置成有，这意味着，可以先执行
  - unpark，给予许可，再执行park立马自行，适用于producer快，而consumer还未完成的场景
  - **代替（wait、notity、notity-all操作）**





- AQS 源码 (AbstractQueuedSynchronizer)

  - 结构：

    - 内部维护一个双向链表。 node

      - 队列内部维护一个waitStatus 记录等待状态

        - 通过这些状态可以实现独占、共享、条件锁

        - ```java
          /** waitStatus value to indicate thread has cancelled */
          static final int CANCELLED =  1;
          /** waitStatus value to indicate successor's thread needs unparking */
          static final int SIGNAL    = -1;
          /** waitStatus value to indicate thread is waiting on condition */
          static final int CONDITION = -2;
          /**
           * waitStatus value to indicate the next acquireShared should
           * unconditionally propagate
          */
          static final int PROPAGATE = -3;
          ```

    - 同步状态标识 state

  - 常用的方法解析：

    - ```java
      public final void acquire(int arg) {
          if (!tryAcquire(arg) &&
              // 从队头获取若没有则阻塞
              acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
              selfInterrupt();
      }
      ```

    - ```java
      final boolean acquireQueued(final Node node, int arg) {
          boolean failed = true;
          try {
              boolean interrupted = false;
              for (;;) {
                  final Node p = node.predecessor();
                  if (p == head && tryAcquire(arg)) {
                      setHead(node);
                      p.next = null; // help GC
                      failed = false;
                      return interrupted;
                  }
                  if (shouldParkAfterFailedAcquire(p, node) &&
                      // 使用Lock Support 进行挂起阻塞。
                      parkAndCheckInterrupt())
                      interrupted = true;
              }
          } finally {
              if (failed)
                  // 取消获取，调整队列及其内部状态。
                  cancelAcquire(node);
          }
      }
      ```

    - ```java
      public final boolean release(int arg) {
          if (tryRelease(arg)) {
              Node h = head;
              if (h != null && h.waitStatus != 0)
                  // 调整队列及唤醒下一个节点。LockSupport.unlock
                  unparkSuccessor(h);
              return true;
          }
          return false;
      }
      ```

    - 通知下一位

      ```java
      private void unparkSuccessor(Node node) {
          /*
               * If status is negative (i.e., possibly needing signal) try
               * to clear in anticipation of signalling.  It is OK if this
               * fails or if status is changed by waiting thread.
               */
          int ws = node.waitStatus;
          if (ws < 0)
              compareAndSetWaitStatus(node, ws, 0);
      
          /*
               * Thread to unpark is held in successor, which is normally
               * just the next node.  But if cancelled or apparently null,
               * traverse backwards from tail to find the actual
               * non-cancelled successor.
               */
          Node s = node.next;
          if (s == null || s.waitStatus > 0) {
              s = null;
              for (Node t = tail; t != null && t != node; t = t.prev)
                  if (t.waitStatus <= 0)
                      s = t;
          }
          if (s != null)
              LockSupport.unpark(s.thread);
      }
      ```

      

  - 抽象行为（子类需事先）

    - AQS 抽象了 以下方法。作为子类实现。通过实现其形成不一样的特性。

      - ```java
        protected boolean tryAcquire(int arg); 
        protected boolean tryRelease(int arg);
        protected int tryAcquireShared(int arg);
        protected boolean tryReleaseShared(int arg);
        protected boolean isHeldExclusively();
        
        ```

- 子类继承拓展
  - // todo 先埋个坑。后续完善。底层是公平锁和非公平锁怎么实现
    - NoFair锁。先CAS抢一下看一下能否获得锁。不行再入队。
    - Fair锁。判断是否为对头若不是。若是CAS一下。插入队尾。等待唤醒
  - //  debugger 记录一下完整的状态
  - **Sync (java 对象生成的锁。非关键字)**
    - 其集成了AbstractQueuedSynchronizer





- 相关文章
  - https://blog.csdn.net/qq_29373285/article/details/85164190?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-4.base&spm=1001.2101.3001.4242

## synchronize

- 基于jmm 中Lock 指令。只能有一个cpu 抢到独占锁。
- mutex 管程



## Lock

- 通过队列 及状态state 确定状态

- ![img](image/20181221162554125)

- lock的存储结构：一个int类型状态值（用于锁的状态变更），一个双向链表（用于存储等待中的线程）

  lock获取锁的过程：本质上是通过CAS来获取状态值修改，如果当场没获取到，会将该线程放在线程等待链表中。

  lock释放锁的过程：修改状态值，调整等待链表。

  可以看到在整个实现过程中，lock大量使用CAS+自旋。因此根据CAS特性，lock建议使用在低锁冲突的情况下。目前java1.6以后，官方对synchronized做了大量的锁优化（偏向锁、自旋、轻量级锁）。因此在非必要的情况下，建议使用synchronized做同步操作。

  最后，希望我的分析，能对你理解锁的实现有所帮助。



- 做个试验。了解AQS

