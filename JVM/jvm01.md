4.4.2 什么情况下会发生栈内存溢出。
如果线程请求的栈深度大于虚拟机所允许的深度，将抛出StackOverflowError异常。 如果虚拟机在动态扩展栈时无法申请到足够的内存空间，则抛出OutOfMemoryError异常。


4.4.3 JVM的内存结构，Eden和Survivor比例。
Eden区是一块，Survivor区是两块。

Eden区和Survivor区的比例是8：1：1

JVM内存的结构为
堆：存放对象
栈：运行时存放栈帧
程序计数器
方法区：存放类和常量

Jdk 1.8之后好像取消了方法区，直接将永久代放到了本地内存里面。

4.4.4 JVM内存为什么要分成新生代，老年代，持久代。新生代中为什么要分为Eden和Survivor。

当Eden区装满后，Minor GC进行垃圾回收，幸存的对象会直接放入老年代，可以想到，要不了多久老年代就会装满，便会进行Major GC且连带Minor GC也就是Full GC，每次Full GC都会消耗大量的时间。

当Eden区填满后，Minor GC进行垃圾回收，幸存的对象会移动到Survivor区，这样循环往复。此时，Survivor区被装满了，也会进行Minor GC，将一些对象kill掉，幸存的对象只能保存在原来的位置，这样就会出现大量的内存碎片（被占用内存不连续）。

4.4.5 JVM中一次完整的GC流程是怎样的，对象如何晋升到老年代，说说你知道的几种主要的JVM参数。
  对象诞生即新生代->eden，在进行minor gc过程中，如果依旧存活，移动到from，变成Survivor，进行标记代数，如此检查一定次数后，晋升为老年代，

4.4.6 你知道哪几种垃圾收集器，各自的优缺点，重点讲下cms和G1，包括原理，流程，优缺点。
Serial、parNew、ParallelScavenge、SerialOld、ParallelOld、CMS、G1

  CMS收集器是一种以获取最短回收停顿时间为目标的收集器。基于“标记-清除”算法实现，它的运作过程如下：

1）初始标记

2）并发标记

3）重新标记

4）并发清除

  初始标记、从新标记这两个步骤仍然需要“stop the world”，初始标记仅仅只是标记一下GC Roots能直接关联到的对象，熟读很快，并发标记阶段就是进行GC Roots Tracing，而重新标记阶段则是为了修正并发标记期间因用户程序继续运作而导致标记产生表动的那一部分对象的标记记录，这个阶段的停顿时间一般会比初始标记阶段稍长点，但远比并发标记的时间短。
    CMS是一款优秀的收集器，主要优点：并发收集、低停顿。

缺点：

1）CMS收集器对CPU资源非常敏感。在并发阶段，它虽然不会导致用户线程停顿，但是会因为占用了一部分线程而导致应用程序变慢，总吞吐量会降低。

2）CMS收集器无法处理浮动垃圾，可能会出现“Concurrent Mode Failure（并发模式故障）”失败而导致Full GC产生。

浮动垃圾：由于CMS并发清理阶段用户线程还在运行着，伴随着程序运行自然就会有新的垃圾不断产生，这部分垃圾出现的标记过程之后，CMS无法在当次收集中处理掉它们，只好留待下一次GC中再清理。这些垃圾就是“浮动垃圾”。

3）CMS是一款“标记--清除”算法实现的收集器，容易出现大量空间碎片。当空间碎片过多，将会给大对象分配带来很大的麻烦，往往会出现老年代还有很大空间剩余，但是无法找到足够大的连续空间来分配当前对象，不得不提前触发一次Full GC。

G1是一款面向服务端应用的垃圾收集器。G1具备如下特点：

1、并行于并发：G1能充分利用CPU、多核环境下的硬件优势，使用多个CPU（CPU或者CPU核心）来缩短stop-The-World停顿时间。部分其他收集器原本需要停顿Java线程执行的GC动作，G1收集器仍然可以通过并发的方式让java程序继续执行。

2、分代收集：虽然G1可以不需要其他收集器配合就能独立管理整个GC堆，但是还是保留了分代的概念。它能够采用不同的方式去处理新创建的对象和已经存活了一段时间，熬过多次GC的旧对象以获取更好的收集效果。

3、空间整合：与CMS的“标记--清理”算法不同，G1从整体来看是基于“标记整理”算法实现的收集器；从局部上来看是基于“复制”算法实现的。

4、可预测的停顿：这是G1相对于CMS的另一个大优势，降低停顿时间是G1和ＣＭＳ共同的关注点，但Ｇ１除了追求低停顿外，还能建立可预测的停顿时间模型，能让使用者明确指定在一个长度为M毫秒的时间片段内，

5、G1运作步骤：

1、初始标记；2、并发标记；3、最终标记；4、筛选回收

上面几个步骤的运作过程和CMS有很多相似之处。初始标记阶段仅仅只是标记一下GC Roots能直接关联到的对象，并且修改TAMS的值，让下一个阶段用户程序并发运行时，能在正确可用的Region中创建新对象，这一阶段需要停顿线程，但是耗时很短，并发标记阶段是从GC Root开始对堆中对象进行可达性分析，找出存活的对象，这阶段时耗时较长，但可与用户程序并发执行。而最终标记阶段则是为了修正在并发标记期间因用户程序继续运作而导致标记产生变动的那一部分标记记录，虚拟机将这段时间对象变化记录在线程Remenbered Set Logs里面，最终标记阶段需要把Remembered Set Logs的数据合并到Remembered Set Logs里面，最终标记阶段需要把Remembered Set Logs的数据合并到Remembered Set中，这一阶段需要停顿线程，但是可并行执行。最后在筛选回收阶段首先对各个Region的回收价值和成本进行排序，根据用户所期望的GC停顿时间来制定回收计划。

4.4.7 垃圾回收算法的实现原理。

1、标记 -清除算法

“标记-清除”（Mark-Sweep）算法，如它的名字一样，算法分为“标记”和“清除”两个阶段：首先标记出所有需要回收的对象，在标记完成后统一回收掉所有被标记的对象。之所以说它是最基础的收集算法，是因为后续的收集算法都是基于这种思路并对其缺点进行改进而得到的。

它的主要缺点有两个：一个是效率问题，标记和清除过程的效率都不高；另外一个是空间问题，标记清除之后会产生大量不连续的内存碎片，空间碎片太多可能会导致，当程序在以后的运行过程中需要分配较大对象时无法找到足够的连续内存而不得不提前触发另一次垃圾收集动作。

复制算法

“复制”（Copying）的收集算法，它将可用内存按容量划分为大小相等的两块，每次只使用其中的一块。当这一块的内存用完了，就将还存活着的对象复制到另外一块上面，然后再把已使用过的内存空间一次清理掉。

这样使得每次都是对其中的一块进行内存回收，内存分配时也就不用考虑内存碎片等复杂情况，只要移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。只是这种算法的代价是将内存缩小为原来的一半，降低了内存的利用率，持续复制长生存期的对象则导致效率降低，还有在分配对象较大时，该种算法也存在效率低下的问题。
3、标记-整理算法

复制收集算法在对象存活率较高时就要执行较多的复制操作，效率将会变低。更关键的是，如果不想浪费50%的空间，就需要有额外的空间进行分配担保，以应对被使用的内存中所有对象都100%存活的极端情况，所以在老年代一般不能直接选用这种算法（老年代一般是存活时间较长的大对象）。

根据老年代的特点，有人提出了另外一种“标记-整理”（Mark-Compact）算法，标记过程仍然与“标记-清除”算法一样，但后续步骤不是直接对可回收对象进行清理，而是让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存，这种算法克服了复制算法的低效问题，同时克服了标记清除算法的内存碎片化的问题；
4、分代收集算法

GC分代的基本假设：绝大部分对象的生命周期都非常短暂，存活时间短。

“分代收集”（Generational Collection）算法，是一种划分的策略，把Java堆分为新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法。在新生代中，每次垃圾收集时都发现有大批对象死去，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成收集。而老年代中因为对象存活率高、没有额外空间对它进行分配担保，就必须使用“标记-清理”或“标记-整理”算法来进行回收。

4.4.8 当出现了内存溢出，你怎么排错。
2.1 收集内存溢出Dump文件

收集Dump文件有两种方式：

设置JVM启动参数

-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/opt/jvmdump
在每次发生内存溢出时，JVM会自动将堆转储，dump文件存放在-XX:HeapDumpPath指定的路径下。

使用jmap命令收集

通过jmap -dump:live,format=b,file=/opt/jvm/dump.hprof pid。
2.2 分析Dump文件

4.4.9 JVM内存模型的相关知识了解多少，比如重排序，内存屏障，happen-before，主内存，工作内存等。

上面java内存模型中讲到的volatile是基于Memory Barrier实现的”

如果一个变量是volatile修饰的，JMM会在写入这个字段之后插进一个Write-Barrier指令，并在读这个字段之前插入一个Read-Barrier指令。这意味着，如果写入一个volatile变量，就可以保证：

一个线程写入变量a后，任何线程访问该变量都会拿到最新值。

在写入变量a之前的写入操作，其更新的数据对于其他线程也是可见的。因为Memory Barrier会刷出cache中的所有先前的写入。
JMM基于保守策略的JMM内存屏障插入策略：

1.在每个volatile写操作的前面插入一个StoreStore屏障

2.在每个volatile写操作的后面插入一个SotreLoad屏障

3.在每个volatile读操作的后面插入一个LoadLoad屏障

4.在每个volatile读操作的后面插入一个LoadStore屏障


#happens-before规则。Happens-before的前后两个操作不会被重排序且后者对前者的内存可见。

程序次序法则：线程中的每个动作A都happens-before于该线程中的每一个动作B，其中，在程序中，所有的动作B都能出现在A之后。
监视器锁法则：对一个监视器锁的解锁 happens-before于每一个后续对同一监视器锁的加锁。
volatile变量法则：对volatile域的写入操作happens-before于每一个后续对同一个域的读写操作。
线程启动法则：在一个线程里，对Thread.start的调用会happens-before于每个启动线程的动作。
线程终结法则：线程中的任何动作都happens-before于其他线程检测到这个线程已经终结、或者从Thread.join调用中成功返回，或Thread.isAlive返回false。
中断法则：一个线程调用另一个线程的interrupt happens-before于被中断的线程发现中断。
终结法则：一个对象的构造函数的结束happens-before于这个对象finalizer的开始。
传递性：如果A happens-before于B，且B happens-before于C，则A happens-before于C
如果一个变量是volatile修饰的，JMM会在写入这个字段之后插进一个Write-Barrier指令，并在读这个字段之前插入一个Read-Barrier指令。这意味着，如果写入一个volatile变量，就可以保证：

一个线程写入变量a后，任何线程访问该变量都会拿到最新值。

在写入变量a之前的写入操作，其更新的数据对于其他线程也是可见的。因为Memory Barrier会刷出cache中的所有先前的写入。


4.5.0 简单说说你了解的类加载器，可以打破双亲委派么，怎么打破。
为什么双亲委派机制；
保证基础类的统一
1)根类加载器（bootstrap class loader）:它用来加载 Java 的核心类，是用原生代码来实现的，并不继承自 java.lang.ClassLoader（负责加载$JAVA_HOME中jre/lib/rt.jar里所有的class，由C++实现，不是ClassLoader子类）。由于引导类加载器涉及到虚拟机本地实现细节，开发者无法直接获取到启动类加载器的引用，所以不允许直接通过引用进行操作。
 2)扩展类加载器（extensions class loader）：它负责加载JRE的扩展目录，lib/ext或者由java.ext.dirs系统属性指定的目录中的JAR包的类。由Java语言实现，父类加载器为null。

  3)系统类加载器（system class loader）：被称为系统（也称为应用）类加载器，它负责在JVM启动时加载来自Java命令的-classpath选项、java.class.path系统属性，或者CLASSPATH换将变量所指定的JAR包和类路径。程序可以通过ClassLoader的静态方法getSystemClassLoader()来获取系统类加载器。如果没有特别指定，则用户自定义的类加载器都以此类加载器作为父加载器。由Java语言实现，父类加载器为ExtClassLoader。

java中所有涉及spi机制的操纵都需要在启动类加载其中加载classpath你的内容


4.5.1 讲讲JAVA的反射机制。
    在运行状态中，对于任意一个类，都能够获取到这个类的所有属性和方法，对于任意一个对象，都能够调用它的任意一个方法和属性(包括私有的方法和属性)，这种动态获取的信息以及动态调用对象的方法的功能就称为java语言的反射机制。通俗点讲，通过反射，该类对我们来说是完全透明的，想要获取任何东西都可以。

　　　想要使用反射机制，就必须要先获取到该类的字节码文件对象(.class)，通过字节码文件对象，就能够通过该类中的方法获取到我们想要的所有信息(方法，属性，类名，父类名，实现的所有接口等等)，每一个类对应着一个字节码文件也就对应着一个Class类型的对象，也就是字节码文件对象。

4.5.2 你们线上应用的JVM参数有哪些。
-Xms :设置Java堆栈的初始化大小
-Xmx :设置最大的java堆大小
-Xmn :设置Young区大小
-Xss :设置java线程堆栈大小
-XX:PermSize and MaxPermSize :设置持久带的大小
-XX:NewRatio :设置年轻代和老年代的比值
-XX:NewSize :设置年轻代的大小
-XX:SurvivorRation=n :设置年轻代中E去与俩个S去的比值

$ java -Xmx128 -Xms64 -Xmn32m -Xss16m Main

4.5.3 g1和cms区别,吞吐量优先和响应优先的垃圾收集器选择。
(1) 吞吐量优先的并行收集器 
参数配置： 
1, -Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseParallelGC -XX:ParallelGCThreads=8 
说明：选择Parallel Scavenge收集器，然后配置多少个线程进行回收，最好与处理器数目相等。

2，-Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseParallelGC -XX:ParallelGCThreads=8 -XX:+UseParallelOldGC 
说明：配置老年代使用Parallel Old

3，-Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseParallelGC -XX:MaxGCPauseMills=100 
说明：设置每次年轻代垃圾回收的最长时间。如何不能满足，那么就会调整年轻代大小，满足这个设置

4，-Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseParallelGC -XX:MaxGCPauseMills=100 -XX:+UseAdaptiveSizePolicy 
说明：并行收集器会自动选择年轻代区大小和Survivor区的比例。

(2)响应时间优先的并发收集器 
1， -Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseConcMarkSweepGC -XX:+UseParNewGC 
说明：设置老年代的收集器是CMS，年轻代是ParNew

2，-Xmx4g -Xms4g -Xmn2g -Xss200k -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=5 -XX:+UseCMSCompactAtFullCollection 
说明：首先设置运行多少次GC后对内存空间进行压缩，整理。同时打开对年老代的压缩（会影响性能）

spark面试顺便问道，Spark Streaming应该选择何种垃圾收集器？

--driver-java-options和 spark.executor.extraJavaOptions这两个参数将driver和Executor垃圾回收器设置为cms，以提高响应速度。

4.5.4 怎么打出线程栈信息。
或者top -Hp pid，pid是进程id，可以看到最耗cpu的线程id
jstack 21251 | grep 5431 

在发生死锁时可以用jstack -l pid来观察锁持有情况 -m mixed mode，不仅会
