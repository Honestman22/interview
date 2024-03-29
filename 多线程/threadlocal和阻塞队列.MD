## 常见的阻塞队列：

前面我们介绍了线程池内部有一个排队策略，任务可能需要在队列中进行排队等候。常见的阻塞队列包括如下的三种，接下来我们一起来看看吧。

#### ArrayBlockingQueue:

内部使用一个数组作为其存储空间，数组的存储空间是预先分配的
优点是 put 和 take操作不会增加GC的负担（因为空间是预先分配的）
缺点是 put 和 take操作使用同一个锁，可能导致锁争用，导致较多的上下文切换。
ArrayBlockingQueue适合在生产者线程和消费者线程之间的并发程序较低的情况下使用。

#### LinkedBlockingQueue：

是一个无界队列（其实队列长度是Integer.MAX_VALUE）
内部存储空间是一个链表，并且链表节点所需的存储空间是动态分配的
优点是 put 和 take 操作使用两个显式锁（putLock和takeLock）
缺点是增加了GC的负担，因为空间是动态分配的。
LinkedBlockingQueue适合在生产者线程和消费者线程之间的并发程序较高的情况下使用。

#### SynchronousQueue：
SynchronousQueue可以被看做一种特殊的有界队列。生产者线程生产一个产品之后，会等待消费者线程来取走这个产品，才会接着生产下一个产品，适合在生产者线程和消费者线程之间的处理能力相差不大的情况下使用。


我们前边介绍newCachedThreadPool时候说，这个线程池来一个线程就创建一个，这是因为其内部队列使用了SynchronousQueue，所以不存在排队。
## （3）ThreadLocal有了解吗？
答：使用ThreadLocal维护变量时，其为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立的改变自己的副本，而不会影响其他线程对应的副本。



ThreadLocal内部实现机制：
https://zhuanlan.zhihu.com/p/102571059

每个线程内部都会维护一个类似HashMap的对象，称为ThreadLocalMap，和当前线程唯一关联，  
Thread t = Thread.currentThread();
      //实际存储的数据结构类型
      ThreadLocalMap map = getMap(t);
      //如果存在map就直接set，没有则创建map并set
      if (map != null)
          map.set(this, value);
      else
          createMap(t, value);
  }
  
里边会包含若干了Entry（K-V键值对），相应的线程被称为这些Entry的属主线程
Entry的Key是一个ThreadLocal实例，Value是一个线程特有对象(set的值)。Entry的作用是为其属主线程建立起一个ThreadLocal实例与一个线程特有对象之间的对应关系
Entry对Key的引用是弱引用；Entry对Value的引用是强引用。
内存泄漏的原因，key是弱引用，回收了就是null，但是value不是null， key为null了，value永远用不到，threadlocalMap 又被线程引用，线程不消亡threadlocalMap 就不不会消亡
然后下一次 有创建了这个entry
