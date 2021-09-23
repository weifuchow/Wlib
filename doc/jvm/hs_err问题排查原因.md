# jvm 发生堆溢出 

## 问题现象

- java 进程启动到一半后。发生jvm 异常。生成hs_error文件。

## hs_error 文件

```html
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 288358400 bytes for committing reserved memory.
# Possible reasons:
#   The system is out of physical RAM or swap space
#   The process is running with CompressedOops enabled, and the Java Heap may be blocking the growth of the native heap
# Possible solutions:
#   Reduce memory load on the system
#   Increase physical memory or swap space
#   Check if swap backing store is full
#   Decrease Java heap size (-Xmx/-Xms)
#   Decrease number of Java threads
#   Decrease Java thread stack sizes (-Xss)
#   Set larger code cache with -XX:ReservedCodeCacheSize=
#   JVM is running with Zero Based Compressed Oops mode in which the Java heap is
#     placed in the first 32GB address space. The Java Heap base address is the
#     maximum limit for the native heap growth. Please use -XX:HeapBaseMinAddress
#     to set the Java Heap base and to place the Java Heap above 32GB virtual address.
# This output file may be truncated or incomplete
```



- https://stackoverflow.com/questions/46801741/jvm-crashes-with-error-cannot-allocate-memory-errno-12

