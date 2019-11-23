21.深拷贝和浅拷贝区别。
复制引用和复制值

22. 数组和链表数据结构描述，各自的时间复杂度。

 数组　是将元素在内存中连续存放，由于每个元素占用内存相同，可以通过下标迅速访问数组中任何元素。但是如果要在数组中增加一个元素，需要移动大量元素，在内存中空出一个元素的空间，然后将要增加的元素放在其中。同样的道理，如果想删除一个元素，同样需要移动大量元素去填掉被移动的元素。如果应用需要快速访问数据，很少插入和删除元素，就应该用数组。

　　链表　中的元素在内存中不是顺序存储的，而是通过存在元素中的指针联系到一起，每个结点包括两个部分：一个是存储 数据元素 的　数据域，另一个是存储下一个结点地址的 指针。 
　　如果要访问链表中一个元素，需要从第一个元素开始，一直找到需要的元素位置。但是增加和删除一个元素对于链表数据结构就非常简单了，只要修改元素中的指针就可以了。如果应用需要经常插入和删除元素你就需要用链表。

23.error和exception的区别，CheckedException，RuntimeException的区别。

Error：Error类对象由 Java 虚拟机生成并抛出，大多数错误与代码编写者所执行的操作无关。例如，Java虚拟机运行错误（Virtual MachineError），当JVM不再有继续执行操作所需的内存资源时，将出现 OutOfMemoryError。这些异常发生时，Java虚拟机（JVM）一般会选择线程终止；还有发生在虚拟机试图执行应用时，如类定义错误（NoClassDefFoundError）、链接错误（LinkageError）。这些错误是不可查的，因为它们在应用程序的控制和处理能力之 外，而且绝大多数是程序运行时不允许出现的状况。对于设计合理的应用程序来说，即使确实发生了错误，本质上也不应该试图去处理它所引起的异常状况。在Java中，错误通常是使用Error的子类描述。

Exception：在Exception分支中有一个重要的子类RuntimeException（运行时异常），该类型的异常自动为你所编写的程序定义ArrayIndexOutOfBoundsException（数组下标越界）、NullPointerException（空指针异常）、ArithmeticException（算术异常）、MissingResourceException（丢失资源）、ClassNotFoundException（找不到类）等异常，这些异常是不检查异常，程序中可以选择捕获处理，也可以不处理。这些异常一般是由程序逻辑错误引起的，程序应该从逻辑角度尽可能避免这类异常的发生；而RuntimeException之外的异常我们统称为非运行时异常，类型上属于Exception类及其子类，从程序语法角度讲是必须进行处理的异常，如果不处理，程序就不能编译通过。如IOException、SQLException等以及用户自定义的Exception异常，一般情况下不自定义检查异常。


24. 请列出5个运行时异常
ArrayIndexOutOfBoundsException，NullPointerException，ArithmeticException，MissingResourceException，ClassNotFoundException。

25. 在自己的代码中，如果创建一个java.lang.String类，这个类是否可以被类加载器加载？为什么
26.说一说你对java.lang.Object对象中hashCode和equals方法的理解。在什么场景下需要重新实现这两个方法。
27.在jdk1.5中，引入了泛型，泛型的存在是用来解决什么问题
28. 这样的a.hashcode() 有什么用，与a.equals(b)有什么关系。
29.有没有可能2个不相等的对象有相同的hashcode。
30. Java中的HashSet内部是如何工作的。
31. 什么是序列化，怎么序列化，为什么序列化，反序列化会遇到什么问题，如何解决。
32. java8的新特性。
