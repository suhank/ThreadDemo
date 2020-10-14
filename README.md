# ThreadDemo
线程测试例子
Java通过Executors提供四种线程池，分别为：
1、newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

2、newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

3、newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。

4、newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
https://www.cnblogs.com/yinfj/p/9844778.html

volatile保证不了线程安全。想要线程安全必须保证原子性，可见性，有序性。而volatile只能保证可见性和有序性。
如果赋值是没有问题的，进行++等非原子操作就有线程安全问题。
https://blog.csdn.net/qq_33330687/article/details/80990729

线程的通讯：
两个线程，轮流打印信息，来自马士兵培训
1.Atomic和Volatile类似，利用线程安全的变量控制。空循环也会耗损cpu，但是因为等待时间很短影响不大。
2.Pipestream效率低
3.blockingqueue 阻塞队列 take阻塞直到能有数据put进去
4.通过synchronize操作+wait+notify
5.ReentrantLock
6.locksupport park锁定当前线程，unpark(thread对象)解锁指定线程

控制线程执行顺序 
0.join
1.通过线程安全变量，后执行进行空循环也就是自旋等待
2.通过synchronize操作，通过锁对象要后执行的线程操作进行wait操作，等待前面线程notify唤醒。
3.countdownlatch, 构造latch的数量。后执行线程通过wait方法等待，直到别的线程调用countdown等于0之后执行。
4.ReentrantLock 的condition来控制  这里控制顺序的时候await不能先执行，否则卡死。
也就是如果要后执行的线程已经后执行了就不要await了。
5.locksupport


ReentrantLock和synchronize 区别:
ReentrantLock锁在同一个时间点只能被一个线程锁持有；而可重入的意思是，ReentrantLock锁，可以被单个线程多次获取。ReentrantLock分为“公平锁”和“非公平锁”。
与 synchronized 相同的并发性和内存语义，但是添加了类似锁投票、定时锁等候和可中断锁等候的一些特性
（1）synchronized是独占锁，加锁和解锁的过程自动进行，易于操作，但不够灵活。ReentrantLock也是独占锁，加锁和解锁的过程需要手动进行，不易操作，但非常灵活。
（2）synchronized可重入，因为加锁和解锁自动进行，不必担心最后是否释放锁；ReentrantLock也可重入，但加锁和解锁需要手动进行，且次数需一样，否则其他线程无法获得锁。
（3）synchronized不可响应中断，一个线程获取不到锁就一直等着；ReentrantLock可以相应中断。
一个最主要的就是ReentrantLock还可以实现公平锁机制。什么叫公平锁呢？也就是在锁上等待时间最长的线程将获得锁的使用权。通俗的理解就是谁排队时间最长谁先执行获取锁。
线程重复n次获取了锁，随后在n次释放该锁后，其他线程能够获取该锁。锁的最终释放要求锁对于获取进行计数自增，计数表示当前锁被重复获取的次数，
而锁被释放时，计数自减，当计数等于0时表示锁已经释放。

Condition类能实现synchronized和wait、notify搭配的功能，另外比后者更灵活，Condition可以实现多路通知功能，
也就是在一个Lock对象里可以创建多个Condition（即对象监视器）实例，线程对象可以注册在指定的Condition中，
从而可以有选择的进行线程通知，在调度线程上更加灵活。

volatile仅仅用来保证该变量对所有线程的可见性，但不保证原子性。


控制线程执行的并发数量： 
1.fix线程池
2.semaphore 如果是1就是sychronize
