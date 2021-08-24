package com.weifuchow;

/**
 *  使用读写锁进行实现
 *  要求，
 *      1.当存在有其他线程使用着classloader则不更新。直到为使用完成。才能更新
 *      2.更新时，直到更新完成。其他线程获取会一直阻塞直到更新完成。
*   实现：
 *      1.使用两个队列，一个读队列，一个写队列。
 *      2.读过程：读取任务时，先加到读队列中。读取读队列的内容。唤醒读取。当写任务有队列时，则加读任务阻塞。直到写任务处理完毕。
 *        // 这样会导致。写任务优先。后续的读取。
 *      3.写任务时，从队列里面读取。进行写操作
 * @author: weifuchow
 * @date: 2021/6/8 14:23
 */
public class ConcurrentControllerByCounter extends Loader {


}
