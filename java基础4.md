## 21.深拷贝和浅拷贝区别。
复制引用和复制值

## 22. 数组和链表数据结构描述，各自的时间复杂度。

 数组　是将元素在内存中连续存放，由于每个元素占用内存相同，可以通过下标迅速访问数组中任何元素。但是如果要在数组中增加一个元素，需要移动大量元素，在内存中空出一个元素的空间，然后将要增加的元素放在其中。同样的道理，如果想删除一个元素，同样需要移动大量元素去填掉被移动的元素。如果应用需要快速访问数据，很少插入和删除元素，就应该用数组。

　　链表　中的元素在内存中不是顺序存储的，而是通过存在元素中的指针联系到一起，每个结点包括两个部分：一个是存储 数据元素 的　数据域，另一个是存储下一个结点地址的 指针。 
　　如果要访问链表中一个元素，需要从第一个元素开始，一直找到需要的元素位置。但是增加和删除一个元素对于链表数据结构就非常简单了，只要修改元素中的指针就可以了。如果应用需要经常插入和删除元素你就需要用链表。

## 23.error和exception的区别，CheckedException，RuntimeException的区别。

Error：Error类对象由 Java 虚拟机生成并抛出，大多数错误与代码编写者所执行的操作无关。例如，Java虚拟机运行错误（Virtual MachineError），当JVM不再有继续执行操作所需的内存资源时，将出现 OutOfMemoryError。这些异常发生时，Java虚拟机（JVM）一般会选择线程终止；还有发生在虚拟机试图执行应用时，如类定义错误（NoClassDefFoundError）、链接错误（LinkageError）。这些错误是不可查的，因为它们在应用程序的控制和处理能力之 外，而且绝大多数是程序运行时不允许出现的状况。对于设计合理的应用程序来说，即使确实发生了错误，本质上也不应该试图去处理它所引起的异常状况。在Java中，错误通常是使用Error的子类描述。

Exception：在Exception分支中有一个重要的子类RuntimeException（运行时异常），该类型的异常自动为你所编写的程序定义ArrayIndexOutOfBoundsException（数组下标越界）、NullPointerException（空指针异常）、ArithmeticException（算术异常）、MissingResourceException（丢失资源）、ClassNotFoundException（找不到类）等异常，这些异常是不检查异常，程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的，程序应该从逻辑角度尽可能避免这类异常的发生；而RuntimeException之外的异常我们统称为非运行时异常，类型上属于Exception类及其子类，从程序语法角度讲是必须进行处理的异常，如果不处理，程序就不能编译通过。如IOException、SQLException等以及用户自定义的Exception异常，一般情况下不自定义检查异常。


## 24. 请列出5个运行时异常
ArrayIndexOutOfBoundsException，NullPointerException，ArithmeticException，MissingResourceException，ClassNotFoundException。

## 25. 在自己的代码中，如果创建一个java.lang.String类，这个类是否可以被类加载器加载？为什么
答案是否定的。我们不能实现。为什么呢？我看很多网上解释是说双亲委托机制解决这个问题，其实不是非常的准确。因为双亲委托机制是可以打破的，你完全可以自己写一个classLoader来加载自己写的java.lang.String类，但是你会发现也不会加载成功，具体就是因为针对java.*开头的类，jvm的实现中已经保证了必须由bootstrp来加载。

因加载某个类时，优先使用父类加载器加载需要使用的类。如果我们自定义了java.lang.String这个类，	加载该自定义的String类，该自定义String类使用的加载器是AppClassLoader，根据优先使用父类加载器原理，	AppClassLoader加载器的父类为ExtClassLoader，所以这时加载String使用的类加载器是ExtClassLoader，	但是类加载器ExtClassLoader在jre/lib/ext目录下没有找到String.class类。然后使用ExtClassLoader父类的加载器BootStrap，	父类加载器BootStrap在JRE/lib目录的rt.jar找到了String.class，将其加载到内存中。这就是类加载器的委托机制。

#  26.说一说你对java.lang.Object对象中hashCode和equals方法的理解。在什么场景下需要重新实现这两个方法。

概念：
这里有一个约定：hashCode相等，对象不一定相等，对象相等，hashCode一定相等。

为什么需要hashCode?
1、 在map等集合中，通过hashCode可以很快的找到元素的位置
2、比较两个对象是否相等时，先通过hashCode比较，这样速度比较快，如果不相等直接返回false

为什么要重载equal方法？
Object对象默认比较的是两个对象的内存地址是否一样，正常大家应该比较的是对象里面的值是否一样。

为什么重载hashCode方法？
如果我们只重写equals，而不重写hashCode方法，就会出现两个对象一样，但是hashCode不相等情况，在map等集合中应用时，就会出现问题，因为hashCode不一样，两个一样的对象会放到集合中。


#  27.在jdk1.5中，引入了泛型，泛型的存在是用来解决什么问题

泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数，泛型的好处是在编译的时候检查类型安全，并且所有的强制转换都是自动和隐式的，以提高代码的重用率 

## 28. 这样的a.hashcode() 有什么用，与a.equals(b)有什么关系。
## 29.有没有可能2个不相等的对象有相同的hashcode。
## 30. Java中的HashSet内部是如何工作的。
HashSet在存元素时，会调用对象的hashCode方法计算出存储位置，然后和该位置上所有的元素进行equals比较， 
如果该位置没有其他元素或者比较的结果都为false就存进去，否则就不存。 
这样的原理注定了元素是按照哈希值来找存储位置，所有无序，而且可以保证无重复元素 
我们在往HashSet集合存储元素时，对象应该正确重写Object类的hashCode和equals方法 
正因为这样的原理，HashSet集合是非常高效的。 
比如，要查找集合中是否包含某个对象，首先计算对象的hashCode，折算出位置号，到该位置上去找就可以了，而不用和所有的元素都比较一遍 

## 三、Collection和Collections的区别 
两者没有任何关系 
Collection是单列集合的父接口，JDK1.5中定义了Iterable接口作为Collection父类，为了实现增强for循环 
Collections是工具类，提供了关于集合的常用操作，例如，排序、二分法查找、反转元素等
## 31. 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
序列化：把对象转换为字节序列的过程称为对象的序列化。
反序列化：把字节序列恢复为对象的过程称为对象的反序列化。

网络传输，或者磁盘存储，是传输二进制，所以必须序列化

实现这个Serializable 接口的时候，一定要给这个 serialVersionUID 赋值

## 32. java8的新特性。
Stream
