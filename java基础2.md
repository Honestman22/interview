#### **题目**：java基础2

#### **参考答案**：
5. 讲讲类的实例化顺序，比如父类静态数据，构造函数，字段，子类静态数据，构造函数，字段，当new的时候，他们的执行顺序
父类静态变量、 
父类静态代码块、 
子类静态变量、 
子类静态代码块、 
父类非静态变量（父类实例成员变量）、 
父类构造函数、 
子类非静态变量（子类实例成员变量）、 
子类构造函数。 


6. 用过哪些Map类，都有什么区别，HashMap是线程安全的吗,并发下使用的Map是什么，他们内部原理分别是什么，比如存储方式，hashcode，扩容，默认容量等。
https://zhuanlan.zhihu.com/p/21673805

（1）HashMap：它根据键的hashCode值存储数据，大多数情况下可以直接定位到它的值，因而具有很快的访问速度，但遍历顺序却是不确定的。 HashMap最多只允许一条记录的键为null，允许多条记录的值为null。HashMap非线程安全，即任一时刻可以有多个线程同时写HashMap，可能会导致数据的不一致。如果需要满足线程安全，可以用 Collections的synchronizedMap方法使HashMap具有线程安全的能力，或者使用ConcurrentHashMap。

(2) Hashtable：Hashtable是遗留类，很多映射的常用功能与HashMap类似，不同的是它承自Dictionary类，并且是线程安全的，任一时间只有一个线程能写Hashtable，并发性不如ConcurrentHashMap，因为ConcurrentHashMap引入了分段锁。Hashtable不建议在新代码中使用，不需要线程安全的场合可以用HashMap替换，需要线程安全的场合可以用ConcurrentHashMap替换。

(3) LinkedHashMap：LinkedHashMap是HashMap的一个子类，保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的，也可以在构造时带参数，按照访问次序排序。

(4) TreeMap：TreeMap实现SortedMap接口，能够把它保存的记录根据键排序，默认是按键值的升序排序，也可以指定排序的比较器，当用Iterator遍历TreeMap时，得到的记录是排过序的。如果使用排序的映射，建议使用TreeMap。在使用TreeMap时，key必须实现Comparable接口或者在构造TreeMap传入自定义的Comparator，否则会在运行时抛出java.lang.ClassCastException类型的异常。

存储方式是
(1) 扩容是一个特别耗性能的操作，所以当程序员在使用HashMap的时候，估算map的大小，初始化的时候给一个大致的数值，避免map进行频繁的扩容。

(2) 负载因子是可以修改的，也可以大于1，但是建议不要轻易修改，除非情况非常特殊。

(3) HashMap是线程不安全的，不要在并发的环境中同时操作HashMap，建议使用ConcurrentHashMap。

(4) JDK1.8引入红黑树大程度优化了HashMap的性能，还优化了hashmap死循环的问题。

(5) 还没升级JDK1.8的，现在开始升级吧。HashMap的性能提升仅仅是JDK1.8的冰山一角。

7.JAVA8的ConcurrentHashMap为什么放弃了分段锁，有什么问题吗，如果你来设计，你如何设计。
8.有没有有顺序的Map实现类，如果有，他们是怎么保证有序的。
9.抽象类和接口的区别，类可以继承多个类么，接口可以继承多个接口么,类可以实现多个接口么。
10.  继承和聚合的区别在哪
11.IO模型有哪些，讲讲你理解的nio ，他和bio，aio的区别是啥，谈谈reactor模型。
12.反射的原理，反射创建类实例的三种方式是什么。
13. 反射中，Class.forName和ClassLoader区别 。
14.描述动态代理的几种实现方式，分别说出相应的优缺点。
15.动态代理与cglib实现的区别。
16. 为什么CGlib方式可以对接口实现代理。
17.final的用途
18. 写出三种单例模式实现 。
19.如何在父类中为子类自动完成所有的hashcode和equals实现？这么做有何优劣
20.请结合OO设计理念，谈谈访问修饰符public、private、protected、default在应用设计中的作用。
21.深拷贝和浅拷贝区别。
22. 数组和链表数据结构描述，各自的时间复杂度。
23.error和exception的区别，CheckedException，RuntimeException的区别。
24. 请列出5个运行时异常
25. 在自己的代码中，如果创建一个java.lang.String类，这个类是否可以被类加载器加载？为什么
26.说一说你对java.lang.Object对象中hashCode和equals方法的理解。在什么场景下需要重新实现这两个方法。
27.在jdk1.5中，引入了泛型，泛型的存在是用来解决什么问题
28. 这样的a.hashcode() 有什么用，与a.equals(b)有什么关系。
29.有没有可能2个不相等的对象有相同的hashcode。
30. Java中的HashSet内部是如何工作的。
31. 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
32. java8的新特性。
