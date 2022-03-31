# netty 内存管理

## 背景

- 由于网络传输过程中，需要开辟大量的byte容器作为缓冲区用于对套接字流的数据进行接受和发送。在jvm 内部这必然涉及GC。Netty为了更高效的利用内存，提高分配速度及减少GC压力。

- 如何才能充分使用内存，提高内存的利用率。那必然的需要减少**内存碎片**

- 内存碎片

  - 内部碎片
    - 所申请的资源比最小的资源分配单位还小（造成空间浪费）

  - 外部碎片
    - 系统的总空闲空间比所需要申请的空间大，但是因为不连续，因此无法分配。

- 常见的内存分配算法

  - 动态分配算法
    - （First fit）最先首次适应
      - ![image-20220310161249068](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310161249068.png)
      - 缺点：
        - 当低地址回收之后会造成低地址不连续。会产生外部碎片。
    - (Next fit) 循环首次适应
      - ![image-20220310161328837](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310161328837.png)
    - (Best fit)最佳适应算法
      - ![image-20220310161422167](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310161422167.png)

- 没有完美的内存分配算法，因此需要找到最符合业务场景所使用的方法，达到性能和利用率的平衡。

- netty 内部采用了**jemalloc** 相关的实现进行内存分配

  - https://blog.51cto.com/quantfabric/2575161

## ByteBuf 三大特性

- **Pool** 

  - 池化对象，增加对象的复用，减少重新分配过程
  - 与之对应的为非池化**UnPoo**l, 即每次都创建新的对象，不进行复用

- **Direct**

  - 使用直接内存（堆外内存），通过手动进行申请及释放。减轻GC压力。减少内核空间到用户空间的一次copy。
  - 与之对应的为**堆内存**，被jvm管理着，会进行gc。

- **Unsafe**

  - Unsafe 是jdk 提供的一种操作内存、访问内存的一种方式。

  - 与之对应的为**Safe**，通过内存数组和索引进行数据的访问。

  - **拓展**

    - **why Unsafe so faster**

      - 因为jvm 将他映射成为非常低的指令。
      - 而非Unsafe 在一些方面做了很多检查，表现来说相当时一系列符合指令构成的操作。

    - **效率方面**

      - > 我认为您发布的两个函数基本相同，因为它们只读取 1 个字节，然后将其转换为 int 并进行进一步比较。
        >
        > 每次读取 4-Byte int 或 8-Byte long 更有效。我写了两个函数来做同样的事情：比较两个 byte[] 的内容，看看它们是否相同：
        >
        > 功能一：
        >
        > ```java
        > public static boolean hadoopEquals(byte[] b1, byte[] b2)
        > {
        >  if(b1 == b2)
        >  {
        >    return true;
        >  }
        >  if(b1.length != b2.length)
        >  {
        >    return false;
        >  }
        >  // Bring WritableComparator code local
        > 
        >  for(int i = 0;i < b1.length; ++i)
        >  {
        >   int a = (b1[i] & 0xff);
        >   int b = (b2[i] & 0xff);
        >   if (a != b) 
        >   {
        >     return false;
        >   }
        >  }
        >  return true;
        > }
        > ```
        >
        > 功能二：
        >
        > ```java
        > public static boolean goodEquals(byte[] b1,byte[] b2)
        > {   
        >  if(b1 == b2)
        >  {
        >    return true;
        >  }
        >  if(b1.length != b2.length)
        >  {
        >    return false;
        >  }
        >  int baseOffset = UnSafe.arrayBaseOffset(byte[].class);
        > 
        >  int numLongs = (int)Math.ceil(b1.length / 8.0);
        > 
        >  for(int i = 0;i < numLongs; ++i)
        >  {
        >    long currentOffset = baseOffset + (i * 8);
        >    long l1 = UnSafe.getLong(b1, currentOffset);
        >    long l2 = UnSafe.getLong(b2, currentOffset);
        >    if(0L != (l1 ^ l2))
        >    {
        >      return false;
        >    }
        >  }
        >  return true;    
        > }
        > ```
        >
        > 我在笔记本电脑上运行了这两个函数（corei7 2630QM，8GB DDR3，64bit win 7，64bit Hotspot JVM），比较两个400MB byte[]，结果如下：
        >
        > 功能1：~670ms
        >
        > 功能2：~80ms



## Netty 默认实现

- Pool
- Direct
- Unsafe

```java
static final ByteBufAllocator DEFAULT_ALLOCATOR;

static {
    String allocType = SystemPropertyUtil.get(
        "io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
    allocType = allocType.toLowerCase(Locale.US).trim();

    ByteBufAllocator alloc;
    if ("unpooled".equals(allocType)) {
        alloc = UnpooledByteBufAllocator.DEFAULT;
        logger.debug("-Dio.netty.allocator.type: {}", allocType);
    } else if ("pooled".equals(allocType)) {
        alloc = PooledByteBufAllocator.DEFAULT;
        logger.debug("-Dio.netty.allocator.type: {}", allocType);
    } else {
        alloc = PooledByteBufAllocator.DEFAULT;
        logger.debug("-Dio.netty.allocator.type: pooled (unknown: {})", allocType);
    }

    DEFAULT_ALLOCATOR = alloc;

    THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 0);
    logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", THREAD_LOCAL_BUFFER_SIZE);

    MAX_CHAR_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16 * 1024);
    logger.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", MAX_CHAR_BUFFER_SIZE);
}
```



## Netty 的资源分配过程

### Netty 资源分配对象

- PoolThreadCache

  - 为了减少竞争，为默认开启CPU * 2 PoolArena对象。再线程使用的使用绑定到具体的线程中。
  - 每次绑定从PoolArena池中选取使用率最小的一个。

- PoolArena

  - **资源分配管理者；**

  - 维护PoolChunkList 及 tinySubpagePools、smallSubpage

    ![13-3.png](D:/weifuchow_space/Wlib/doc/netty/images/CgqCHl_DokyATcJIAAVsDxEMVzc445-16469078294825.png)

- PoolChunk 

  - **Netty向OS申请的最小内存，系统16MB**

  - Netty 资源分配的主要区域与结构。

  - 以PAGE为节点（8K)的一个满二叉树)

  - 属性介绍

    - memoryMap  数组大小为4096（第一个值去掉），表示能够分配 2^(11 -value)的page，memory[1] = 0,第一个节点可以分配2的11次方的page.
    - deptMap   数组大小为4096 存储节点对应的高度。
    - **因为是满二叉树（堆），所以父子关系可以通过索引计算得出，子节点 2048 >>>1 = 1024（父节点）**

  - ![image-20220310154603940](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310154603940.png)

  - ```java
    final class PoolChunk<T> implements PoolChunkMetric {
    
        final PoolArena<T> arena;
    
        final T memory; // 存储的数据
    
        private final byte[] memoryMap; // 满二叉树中的节点是否被分配，数组大小为 4096
    
        private final byte[] depthMap; // 满二叉树中的节点高度，数组大小为 4096
    
        private final PoolSubpage<T>[] subpages; // PoolChunk 中管理的 2048 个 8K 内存块
    
        private int freeBytes; // 剩余的内存大小
    
        PoolChunkList<T> parent;
    
        PoolChunk<T> prev;
    
        PoolChunk<T> next;
        // 省略其他代码
    
    }
    ```

- Page

  - PoolChunck所管理的最小内存单位
  - 资源单位节点；（8KB)

- PoolSubpage

  - 对小规格（小于8KB）的资源分配进行维护。

  - ![13-1.png](D:/weifuchow_space/Wlib/doc/netty/images/Ciqc1F_DocOAeFTdAATI3WZluTY249.png)

  - ```java
    final class PoolSubpage<T> implements PoolSubpageMetric {
    
        final PoolChunk<T> chunk;
    
        private final int memoryMapIdx; // 对应满二叉树节点的下标
    
        private final int runOffset; // PoolSubpage 在 PoolChunk 中 memory 的偏移量
    
        private final long[] bitmap; // 记录每个小内存块的状态
    
        // 与 PoolArena 中 tinySubpagePools 或 smallSubpagePools 中元素连接成双向链表
    
        PoolSubpage<T> prev;
    
        PoolSubpage<T> next;
    
        int elemSize; // 每个小内存块的大小
    
        private int maxNumElems; // 最多可以存放多少小内存块：8K/elemSize
    
        private int numAvail; // 可用于分配的内存块个数
        // 省略其他代码
    
    }
    ```

  - 

### 资源分配总体架构图

- ![13-2.png](D:/weifuchow_space/Wlib/doc/netty/images/Ciqc1F_DohmABGJKAA4YPK4ef2s293.png)



### 资源分配源码解析

- 线程局部map绑定Arena ，（ 启动默认开启CPU * 2 个 PoolArena）

  - ```java
    // 构造allocator 初始化Arena
    public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder,
                                      int tinyCacheSize, int smallCacheSize, int normalCacheSize,
                                      boolean useCacheForAllThreads, int directMemoryCacheAlignment) {
        super(preferDirect);
        // ....
        // 创建
        if (nHeapArena > 0) {
            heapArenas = newArenaArray(nHeapArena);
            List<PoolArenaMetric> metrics = new ArrayList<PoolArenaMetric>(heapArenas.length);
            for (int i = 0; i < heapArenas.length; i ++) {
                PoolArena.HeapArena arena = new PoolArena.HeapArena(this,
                                                                    pageSize, maxOrder, pageShifts, chunkSize,
                                                                    directMemoryCacheAlignment);
                heapArenas[i] = arena;
                metrics.add(arena);
            }
            heapArenaMetrics = Collections.unmodifiableList(metrics);
        } else {
            heapArenas = null;
            heapArenaMetrics = Collections.emptyList();
        }
    
        if (nDirectArena > 0) {
            directArenas = newArenaArray(nDirectArena);
            List<PoolArenaMetric> metrics = new ArrayList<PoolArenaMetric>(directArenas.length);
            for (int i = 0; i < directArenas.length; i ++) {
                PoolArena.DirectArena arena = new PoolArena.DirectArena(
                    this, pageSize, maxOrder, pageShifts, chunkSize, directMemoryCacheAlignment);
                directArenas[i] = arena;
                metrics.add(arena);
            }
            directArenaMetrics = Collections.unmodifiableList(metrics);
        } else {
            directArenas = null;
            directArenaMetrics = Collections.emptyList();
        }
        metric = new PooledByteBufAllocatorMetric(this);
    }
    
    
    // 本地线程变量绑定Arena
    private V initialize(InternalThreadLocalMap threadLocalMap) {
        V v = null;
        try {
            v = initialValue();
        } catch (Exception e) {
            PlatformDependent.throwException(e);
        }
    
        threadLocalMap.setIndexedVariable(index, v);
        addToVariablesToRemove(threadLocalMap, this);
        return v;
    }
    
    protected synchronized PoolThreadCache initialValue() {
        final PoolArena<byte[]> heapArena = leastUsedArena(heapArenas);
        final PoolArena<ByteBuffer> directArena = leastUsedArena(directArenas);
    
        final Thread current = Thread.currentThread();
        if (useCacheForAllThreads || current instanceof FastThreadLocalThread) {
            final PoolThreadCache cache = new PoolThreadCache(
                heapArena, directArena, tinyCacheSize, smallCacheSize, normalCacheSize,
                DEFAULT_MAX_CACHED_BUFFER_CAPACITY, DEFAULT_CACHE_TRIM_INTERVAL);
    
            if (DEFAULT_CACHE_TRIM_INTERVAL_MILLIS > 0) {
                final EventExecutor executor = ThreadExecutorMap.currentExecutor();
                if (executor != null) {
                    executor.scheduleAtFixedRate(trimTask, DEFAULT_CACHE_TRIM_INTERVAL_MILLIS,
                                                 DEFAULT_CACHE_TRIM_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
                }
            }
            return cache;
        }
        // No caching so just use 0 as sizes.
        return new PoolThreadCache(heapArena, directArena, 0, 0, 0, 0, 0);
    }
    ```

- 分配小于Page资源过程。

  - ```java
    // 计算规格，向上取整
    final int normCapacity = normalizeCapacity(reqCapacity);
    if (isTinyOrSmall(normCapacity)) { // capacity < pageSize
        int tableIdx;
        PoolSubpage<T>[] table;
        boolean tiny = isTiny(normCapacity);
        if (tiny) { // < 512
            // 从缓存上看有没有能否直接被分配
            if (cache.allocateTiny(this, buf, reqCapacity, normCapacity)) {
                // was able to allocate out of the cache so move on
                return;
            }
            // 计算小规格的索引
            tableIdx = tinyIdx(normCapacity);
            table = tinySubpagePools;
        } else {
            if (cache.allocateSmall(this, buf, reqCapacity, normCapacity)) {
                // was able to allocate out of the cache so move on
                return;
            }
            tableIdx = smallIdx(normCapacity);
            table = smallSubpagePools;
        }
    
        // poolArena 内部初始化了所有规格对象。并设置了头部不赋值
        final PoolSubpage<T> head = table[tableIdx];
    
        /**
          * 为什么有了本地线程变量之后还要加锁，因为当线程数量大于PoolArena初始化数量时，就会出现竞争。
          * Synchronize on the head. This is needed as {@link PoolChunk#allocateSubpage(int)} and
          * {@link PoolChunk#free(long)} may modify the doubly linked list as well.
         */
        synchronized (head) {
            final PoolSubpage<T> s = head.next;
            // 该规格已经分配过一次时，进入此
            if (s != head) {
                assert s.doNotDestroy && s.elemSize == normCapacity;
                long handle = s.allocate();
                assert handle >= 0;
                s.chunk.initBufWithSubpage(buf, null, handle, reqCapacity);
                incTinySmallAllocation(tiny);
                return;
            }
        }
        synchronized (this) {
            allocateNormal(buf, reqCapacity, normCapacity);
        }
    
        incTinySmallAllocation(tiny);
        return;
    }
    
    // 进行分配 ，先从0~50分配，这么顺序分配主要是为了防止在临界值拍好导致PoolChunk在不同的队列中移动
    private void allocateNormal(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        if (q050.allocate(buf, reqCapacity, normCapacity) || q025.allocate(buf, reqCapacity, normCapacity) ||
            q000.allocate(buf, reqCapacity, normCapacity) || qInit.allocate(buf, reqCapacity, normCapacity) ||
            q075.allocate(buf, reqCapacity, normCapacity)) {
            return;
        }
    
        // Add a new chunk.
        PoolChunk<T> c = newChunk(pageSize, maxOrder, pageShifts, chunkSize);
        boolean success = c.allocate(buf, reqCapacity, normCapacity);
        assert success;
        qInit.add(c);
    }
    // chunk 分配page过程
    boolean allocate(PooledByteBuf<T> buf, int reqCapacity, int normCapacity) {
        final long handle;
        if ((normCapacity & subpageOverflowMask) != 0) { // >= pageSize
            handle =  allocateRun(normCapacity);
        } else {
            handle = allocateSubpage(normCapacity);
        }
    
        if (handle < 0) {
            return false;
        }
        ByteBuffer nioBuffer = cachedNioBuffers != null ? cachedNioBuffers.pollLast() : null;
        initBuf(buf, nioBuffer, handle, reqCapacity);
        return true;
    }
    
    /**
         * Create / initialize a new PoolSubpage of normCapacity
         * Any PoolSubpage created / initialized here is added to subpage pool in the PoolArena that owns this PoolChunk
         *
         * @param normCapacity normalized capacity
         * @return index in memoryMap
         */
    private long allocateSubpage(int normCapacity) {
        // Obtain the head of the PoolSubPage pool that is owned by the PoolArena and synchronize on it.
        // This is need as we may add it back and so alter the linked-list structure.
        PoolSubpage<T> head = arena.findSubpagePoolHead(normCapacity);
        int d = maxOrder; // subpages are only be allocated from pages i.e., leaves
        synchronized (head) {
            // 分配节点及递归修改父节点的值
            int id = allocateNode(d);
            if (id < 0) {
                return id;
            }
    
            final PoolSubpage<T>[] subpages = this.subpages;
            final int pageSize = this.pageSize;
    
            freeBytes -= pageSize;
    
            int subpageIdx = subpageIdx(id);
            PoolSubpage<T> subpage = subpages[subpageIdx];
            if (subpage == null) {
                subpage = new PoolSubpage<T>(head, this, id, runOffset(id), pageSize, normCapacity);
                subpages[subpageIdx] = subpage;
            } else {
                subpage.init(head, normCapacity);
            }
            return subpage.allocate();
        }
    }
    
    ```

  - chunk 分配变化前后

    - Before

      - ![image-20220310175444318](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310175444318.png)

    - After

      - ##### ![image-20220310175459222](D:/weifuchow_space/Wlib/doc/netty/images/image-20220310175459222.png)

    - 抽象成树表示

      - ![图片3.png](D:/weifuchow_space/Wlib/doc/netty/images/Ciqc1F_HcU2AUwUeAAS29sFoCrk381.png)

- 分配大于Page过程

  - 基本过程于分配小于Page一样。不用去操作SubPage

- 复用Arena的过程。

  - 释放资源后，数据不会被立马被清空，而是会放到cache中，便于下一次直接从缓存直接加载byteBuffer对象。

    

### 总结

- 分四种内存规格管理内存，分别为 Tiny、Samll、Normal、Huge，PoolChunk 负责管理 8K 以上的内存分配，PoolSubpage 用于管理 8K 以下的内存分配。当申请内存大于 16M 时，不会经过内存池，直接分配。
- 设计了本地线程缓存机制 PoolThreadCache，用于提升内存分配时的并发性能。用于申请 Tiny、Samll、Normal 三种类型的内存时，会优先尝试从 PoolThreadCache 中分配。
- PoolChunk 以二叉树的数据结构实现（数组表达），是整个内存池分配的核心所在。
- 每调用 PoolThreadCache 的 allocate() 方法到一定次数，会触发检查 PoolThreadCache 中缓存的使用频率，使用频率较低的内存块会被释放。
- 线程退出时，Netty 会回收该线程对应的所有内存。

- Netty 使用jemalloc 能够充分利用内存利用率。尽可能覆盖所有的规格的资源申请。保证空闲内存的连续性。大大减少外部碎片。

  





