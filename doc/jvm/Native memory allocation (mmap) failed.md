# 记一次报错

## 问题分析

- Native memory allocation (mmap) failed to map 413138944 bytes for committing reserved memory.
- 本地内存分配(mmap)映射413138944字节以提交预留内存失败。（394MB）

- https://bugs.openjdk.java.net/browse/JDK-8187709

- hs_err_pid 文件

- > There is insufficient memory for the Java Runtime Environment to continue.
  > Native memory allocation (mmap) failed to map 356515840 bytes for committing reserved memory.
  > Possible reasons:
  >    The system is out of physical RAM or swap space
  >    The process is running with CompressedOops enabled, and the Java Heap may be blocking the growth of the native heap
  >  Possible solutions:
  >   Reduce memory load on the system
  >   Increase physical memory or swap space
  >  Check if swap backing store is full
  >  Decrease Java heap size (-Xmx/-Xms)
  >  Decrease number of Java threads
  >  Decrease Java thread stack sizes (-Xss)
  >  Set larger code cache with -XX:ReservedCodeCacheSize=
  >  JVM is running with Zero Based Compressed Oops mode in which the Java heap is
  >      placed in the first 32GB address space. The Java Heap base address is the
  >      maximum limit for the native heap growth. Please use -XX:HeapBaseMinAddress
  >      to set the Java Heap base and to place the Java Heap above 32GB virtual address.
  > This output file may be truncated or incomplete.
  >
  >   Out of Memory Error (os_linux.cpp:2756), pid=16129, tid=0x00007fe345744700
  >
  >  JRE version: Java(TM) SE Runtime Environment (8.0_281-b09) (build 1.8.0_281-b09)
  >  Java VM: Java HotSpot(TM) 64-Bit Server VM (25.281-b09 mixed mode linux-amd64 compressed oops)
  >  Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again

- 内存信息

> /proc/meminfo:
> MemTotal:       32779236 kB
> MemFree:          968444 kB
> MemAvailable:   22419196 kB
> Buffers:          404464 kB
> Cached:         20733324 kB
>
> Memory: 4k page, physical 32779236k(968072k free), swap 0k(0k free)

- 分析
  - allocation 356515840（340MB） 但是内存中确有 968444 kB  （945.74609MB ）(空闲内存)
  - 按道理不应该啊。（暂时没搞清楚）



## 解决方案

- 增加最小堆内存核最大堆内存

  -Xmx1G -Xms1G

- 增加mmap文件数量的限制

  sysctl -w vm.max_map_count=131072

- > Basically, the JVM will allocate whatever you put in `-Xms` as soon as the JVM starts, then grow as required to `-Xmx`, once that is reached, it goes garbage collecting (flushing things no longer used).
  >
  > Running GC on lots of objects (here 7Gb worth of objects) is not a good idea because it will take time and a lot of resources. Setting them to the same value is OK, as GC is collected along the way. GC has operations that are "stop-the-world", where nothing else can run while garbage is collected. Now imagine cleaning up 7Gb of garbage, that is going to take a non-negligible amount of time and cause long pauses.
  >
  > 基本上，一旦JVM启动，JVM就会分配您放在“-Xms”中的任何东西，然后根据需要增长到“-Xmx”，一旦达到这个值，它就进行垃圾收集(清除不再使用的东西)。
  >
  > 在许多对象(这里是7Gb的对象)上运行GC不是一个好主意，因为这会花费时间和大量资源。将它们设置为相同的值就可以了，因为GC会在此过程中进行收集。GC具有“停止整个世界”的操作，即在垃圾收集时不能运行其他任何操作。现在想象一下清理7Gb的垃圾，这将花费不可忽视的时间，并导致长时间暂停。
  >
  > https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/introduction.html
  >
  > These kinds of errors can not only occur because the total amount of heap space is exhausted, but also when the *number* of memory-mapped areas is exhausted.
  >
  > In Linux, the maximum per-process number of map areas is controlled by `vm.max_map_count` sysctl parameter (which defaults to 65536). So, i would try, for example, to double it and examine what happens:
  >
  > ```java
  > sysctl -w vm.max_map_count=131072
  > ```
  >
  > An indicator that you have hit into this problem is when the heap dump lists a huge number of open mmap areas (under "Dynamic Libraries" section)

- https://stackoverflow.com/questions/48592602/native-memory-allocation-mmap-failed-to-map

## jvm 命令

-  **-XX:+HeapDumpOnOutOfMemoryError，-XX:HeapDumpPath=/tmp/heapdump.hprof 异常自动生成堆文件**

- **-XX:+HeapDumpBeforeFullGC**

  **当 JVM 执行 FullGC 前执行 dump。**

- **-XX:+HeapDumpAfterFullGC**

  **当 JVM 执行 FullGC 后执行 dump。**

- **-XX:+HeapDumpOnCtrlBreak**

  **交互式获取dump。在控制台按下快捷键Ctrl + Break时，JVM就会转存一下堆快照。**

-  **-XX:+PrintGCDetails  打印gc信息**

- **-Djava.rmi.server.hostname=10.201.0.3**

  **-Dcom.sun.management.jmxremote.port=6666**

   **-Dcom.sun.management.jmxremote.ssl=false**

   **-Dcom.sun.management.jmxremote.authenticate=false**

  **开启远程监控**

- 例：
  - java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.hprof  -XX:+PrintGCDetails -XX:-UseCompressedOops -Djava.rmi.server.hostname=10.201.0.3 -Dcom.sun.management.jmxremote.port=6666 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false  -jar main.jar test

