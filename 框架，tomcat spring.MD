## 4.5.5 简单讲讲tomcat结构，以及其类加载器流程，线程模型等。

  　1 使用bootstrap引导类加载器加载

　　2 使用system系统类加载器加载

　　3 使用应用类加载器在webAPP WEB-INF/classes中加载   实现应用隔离，未遵循双亲委派机制

　　4 使用应用类加载器在webAPP WEB-INF/lib中加载 

　　5 使用common类加载器在CATALINA_HOME/lib中加载

设来自客户的请求为：http://localhost:8080/test/index.jsp
请求被发送到本机端口8080，被在那里侦听的Coyote HTTP/1.1 Connector获得

Connector把该请求交给它所在的Service的Engine来处理，并等待Engine的回应

Engine获得请求localhost:8080/test/index.jsp，匹配它所有虚拟主机Host

Engine匹配到名为localhost的Host（即使匹配不到也把请求交给该Host处理，因为该Host被定义为该Engine的默认主机）

localhost Host获得请求/test/index.jsp，匹配它所拥有的所有Context

Host匹配到路径为/test的Context（如果匹配不到就把该请求交给路径名为""的Context去处理）

path="/test"的Context获得请求/index.jsp，在它的mapping table中寻找对应的servlet

Context匹配到URL PATTERN为*.jsp的servlet，对应于JspServlet类

构造HttpServletRequest对象和HttpServletResponse对象，作为参数调用JspServlet的doGet或doPost方法

Context把执行完了之后的HttpServletResponse对象返回给Host

Host把HttpServletResponse对象返回给Engine

Engine把HttpServletResponse对象返回给Connector

Connector把HttpServletResponse对象返回给客户browser

4个子容器的作用分别是：

（1）Engine：引擎，用来管理多个站点，一个Service最多只能有一个Engine； （2）Host：代表一个站点，也可以叫虚拟主机，通过配置Host就可以添加站点； （3）Context：代表一个应用程序，对应着平时开发的一套程序，或者一个WEB-INF目录以及下面的web.xml文件； （4）Wrapper：每一Wrapper封装着一个Servlet；

BIO,NIO,AIO,APR

## 4.5.6 tomcat如何调优，涉及哪些参数 

硬件上选择，操作系统选择，版本选择，jdk选择，配置jvm参数，配置connector的线程数量，开启gzip压缩，trimSpaces，集群等


## 4.5.7 讲讲Spring加载流程。

通过listener入口，核心是在AbstractApplicationContext的refresh方法，在此处进行装载bean工厂，bean，创建bean实例，拦截器，后置处理器等。
讲不清楚

## 4.5.8 Spring AOP的实现原理。
jdk动态代理和cglib动态daili

## 4.5.9 讲讲Spring事务的传播属性。
（2）spring的事务传播行为：

spring事务的传播行为说的是，当多个事务同时存在的时候，spring如何处理这些事务的行为。

① PROPAGATION_REQUIRED：如果当前没有事务，就创建一个新事务，如果当前存在事务，就加入该事务，该设置是最常用的设置。

② PROPAGATION_SUPPORTS：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就以非事务执行。‘

③ PROPAGATION_MANDATORY：支持当前事务，如果当前存在事务，就加入该事务，如果当前不存在事务，就抛出异常。

④ PROPAGATION_REQUIRES_NEW：创建新事务，无论当前存不存在事务，都创建新事务。

⑤ PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。

⑥ PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。

⑦ PROPAGATION_NESTED：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则按REQUIRED属性执行。

## 4.6.0 Spring如何管理事务的。
编程式和声明式

## 4.6.1 Spring怎么配置事务（具体说出一些关键的xml 元素）。

## 4.6.2 说说你对Spring的理解，非单例注入的原理？它的生命周期？循环注入的原理，aop的实现原理，说说aop中的几个术语，它们是怎么相互工作的。
非单列，每次获取就创建一个新的bean，
循环注入通过多级缓存解决，
## 4.6.3 Springmvc 中DispatcherServlet初始化过程。
 HttpServletBean、FrameworkServlet、DispatcherServlet三个不同的类层次，SpringMVC对三个以抽象和继承来实现不用的功能，分工合作，实现了解耦的设计原则。

我们在回顾一下，各自做了什么事情，HttpServletBean是实现了获取web.xml中的<init-param>配置元素的值；FrameworkServlet实现了SpringMVC上下文并根据不同的DispatcherServlet放在以servlet-name为key值severtContext中；DispatcherServlet主要实现了初始化SpringMVC组件元素。

原文链接：https://blog.csdn.net/congcong68/article/details/40451233

## 4.6.4 netty的线程模型，netty如何基于reactor模型上实现的。

1、单线程模型

所有I/O操作都由一个线程完成，即多路复用、事件分发和处理都是在一个Reactor线程上完成的，一个 Reactor 线程就是一个 NIO 线程。
Reactor 单线程模型使用的是一个 NIO 线程， NIO 使用的是非阻塞 I/O，所有的 I/O 操作都不会阻塞，所以一个线程可以处理多个 TCP 连接请求。

对于一些小容量应用场景，可以使用单线程模型，但是对于高负载、大并发的应用却不合适，主要原因如下：


2,Reactor多线程模型

Reactor多线程模型与单线程模型最大区别就是有一组 NIO 线程处理 I/O 操作，它的特点如下：

有一个专门的 NIO 线程用于监听服务端，接收客户端的TCP连接请求；
网络I/O读、写操作等由一个NIO线程池负责，线程池可以采用标准的 JDK 线程池实现（Netty 扩展了 JDK 线程池），它包含一个任务队列和N个可用的线程，由这些 NIO 线程负责消息的读取、解码、编码和发送；

3、主从Reactor多线程模型
服务端用于接收客户端连接的不再是1个单独的NIO线程，而是一个独立的NIO线程池。Acceptor接收到客户端TCP连接请求处理完成后（可能包含接入认证等），将新创建的SocketChannel注册到I/O线程池（sub reactor线程池）的某个I/O线程上，由它负责SocketChannel的读写和编解码工作。

Acceptor线程池只用于客户端的登录、握手和安全认证，一旦链路建立成功，就将链路注册到后端subReactor线程池的I/O线程上，有I/O线程负责后续的I/O操作。


作者：jijs
链接：https://www.jianshu.com/p/6ae30cf5ae9e

## 4.6.5 为什么选择netty。

https://www.cnblogs.com/wuzhenzhao/p/11202952.html

1.Netty对NIO的API进行了封装，通过以下手段让性能又得到了一定程度的提升 
 使用多路复用技术，提高处理连接的并发性 

2. 零拷贝： 
Netty 的“零拷贝”主要体现在如下三个方面：

1) Netty 的接收和发送ByteBuffer 采用DIRECT BUFFERS，使用堆外直接内存进行Socket 读写，不需要进行字节缓冲区的二次拷贝。如果使用传统的堆内存（HEAP BUFFERS）进行Socket 读写，JVM 会将堆内存Buffer 拷贝一份到直接内存中，然后才写入Socket 中。相比于堆  外直接内存，消息在发送过程中多了一次缓冲区的内存拷贝。当进行Socket IO 读写的时候，为了避免从堆内存拷贝一份副本到直接内存，Netty 的ByteBuf 分配器直接创建非堆内存避免缓冲区的二次拷贝，通过“零拷贝”来提升读写性能。
2) Netty 提供了组合Buffer 对象，可以聚合多个ByteBuffer 对象，用户可以像操作一个Buffer 那样方便的对组合Buffer进行操作，避免了传统通过内存拷贝的方式将几个小Buffer 合并成一个大的Buffer。
3) Netty 的文件传输采用了transferTo()方法，它可以直接将文件缓冲区的数据发送到目标Channel，避免了传统通过循环write()方式导致的内存拷贝问题。对于很多操作系统它直接将文件缓冲区的内容发送到目标Channel 中，而不需要通过拷贝的方式，这是一种更加高效的传输方式，它实现了文件传输的“零拷贝”


3.内存池
三个维度：
Pooled与UnPooled（池化与非池化）
UnSafe和非UnSafe（底层读写与应用程序读写）
Heap和Direct（堆内存与堆外内存）

4. 使用主从Reactor多线程模型，提高并发性 
5. 采用了串行无锁化设计，在IO线程内部进行串行操作，避免多线程竞争导致的性能下降 
内存队列
## 5.5 Netty 的高效并发编程主要体现在如下几点：
volatile 的大量、正确使用;
CAS 和原子类的广泛使用；
线程安全容器的使用；
通过读写锁提升并发性能。


6.高性能的序列化框架
 默认使用Protobuf的序列化框架 
影响序列化性能的关键因素总结如下：

序列化后的码流大小（网络带宽的占用）；
序列化&反序列化的性能（CPU 资源占用）；
是否支持跨语言（异构系统的对接和开发语言切换
7. 灵活的TCP参数配置

合理设置TCP 参数在某些场景下对于性能的提升可以起到显著的效果，例如SO_RCVBUF 和SO_SNDBUF。如果设置不当，对性能的影响是非常大的。下面我们总结下对性能影响比较大的几个配置项：

## 4.6.6 什么是TCP粘包，拆包。解决方式是什么。
1)、客户端在发送数据包的时候，每个包都固定长度，比如1024个字节大小，如果客户端发送的数据长度不足1024个字节，则通过补充空格的方式补全到指定长度；Netty提供的FixedLengthFrameDecoder

2)、客户端在每个包的末尾使用固定的分隔符，例如rn，如果一个包被拆分了，则等待下一个包发送过来之后找到其中的rn，然后对其拆分后的头部部分与前一个包的剩余部分进行合并，这样就得到了一个完整的包；Netty提供LineBasedFrameDecoder与DelimiterBasedFrameDecoder

3)、将消息分为头部和消息体，在头部中保存有当前整个消息的长度，只有在读取到足够长度的消息之后才算是读到了一个完整的消息；Netyy提供了LengthFieldBasedFrameDecoder与LengthFieldPrepender

4)、通过自定义协议进行粘包和拆包的处理。Netty提供了通过实现MessageToByteEncoder和ByteToMessageDecoder来实现


   1.消息定长，例如每个报文的大小固定为多少字节，如果不够，就空格补充

   2 在包尾部增加特殊的字符进行分隔，例如$

   3 将消息分为消息头和消息体，在消息头中包含表示消息总长度的字段，然后就行业务逻辑的处理
   
## 4.6.7 netty的fashwheeltimer的用法，实现原理，是否出现过调用不够准时，怎么解决。

## 4.6.8 netty的心跳处理在弱网下怎么办。

## 4.6.9 netty的通讯协议是什么样的。

https://segmentfault.com/a/1190000020591659?utm_source=tag-newest

## 4.7.0 springmvc用到的注解，作用是什么，原理。
1. 客户端请求提交到DispatcherServlet

2. 由DispatcherServlet控制器查询一个或多个HandlerMapping，找到处理请求的Controller

3. DispatcherServlet将请求提交到Controller

4. Controller调用业务逻辑处理后，返回ModelAndView

5. DispatcherServlet查询一个或多个ViewResoler视图解析器，找到ModelAndView指定的视图

6. 视图负责将结果显示到客户端

DispatcherServlet是整个Spring MVC的核心。它负责接收HTTP请求组织协调Spring MVC的各个组成部分。其主要工作有以下三项：

       1. 截获符合特定格式的URL请求。
       2. 初始化DispatcherServlet上下文对应的WebApplicationContext，并将其与业务层、持久化层的WebApplicationContext建立关联。
       3. 初始化Spring MVC的各个组成组件，并装配到DispatcherServlet中。

 

 

 

<mvc:annotation-driven /> 说明:

 
是一种简写形式，可以让初学者快速成应用默认的配置方案，会默认注册 DefaultAnnotationHandleMapping以及AnnotionMethodHandleAdapter 这两个 Bean, 这两个 Bean ，前者对应类级别， 后者对应到方法级别；
 
上在面的 DefaultAnnotationHandlerMapping和AnnotationMethodHandlerAdapter 是 Spring 为 @Controller 分发请求所必需的。
 
annotation-driven 扫描指定包中类上的注解，常用的注解有:
 
复制代码
@Controller 声明Action组件
@Service 声明Service组件 @Service("myMovieLister")
@Repository 声明Dao组件
@Component 泛指组件, 当不好归类时.
@RequestMapping("/menu") 请求映射
@Resource 用于注入，( j2ee提供的 ) 默认按名称装配，@Resource(name="beanName")
@Autowired 用于注入，(srping提供的) 默认按类型装配
@Transactional( rollbackFor={Exception.class}) 事务管理
@ResponseBody
@Scope("prototype") 设定bean的作用域
 
## 4.7.1 springboot启动机制。
@Configuration（@SpringBootConfiguration点开查看发现里面还是应用了@Configuration）
@EnableAutoConfiguration
@ComponentScan

@EnableAutoConfiguration主要就是这个包
1.3.1 当我们运行SpringApplication的main方法时,调用静态方法run()首先是实例化,SpringApplication初始化的时候主要做主要做三件事：

根据classpath下是否存在(ConfigurableWebApplicationContext)判断是否要启动一个web applicationContext。
SpringFactoriesInstances加载classpath下所有可用的ApplicationContextInitializer
SpringFactoriesInstances加载classpath下所有可用的ApplicationListener


1.3.2 SpringApplicatio实例化完成并且完成配置后调用run()方法,首先遍历初始化过程中加载的SpringApplicationRunListeners，然后调用starting(),开始监听springApplication的启动。



1.3.3 加载SpringBoot配置环境(ConfigurableEnvironment)，如果是通过web容器发布，会加载StandardEnvironment。将配置环境(Environment)加入到监听器对象中(SpringApplicationRunListeners)。

1.3.4 banner属性的设置



1.3.5 ConfigurableApplicationContext(应用配置上下文)创建，根据webEnvironment是否是web环境创建默认的contextClass 
,AnnotationConfigEmbeddedWebApplicationContext(通过扫描所有注解类来加载bean)和ConfigurableWebApplicationContext),最后通过BeanUtils实例化上下文对象，并返回。



1.3.6 prepareContext()方法将listeners、environment、applicationArguments、banner等重要组件与上下文对象关联。



1.3.7 refreshContext(context),bean的实例化完成IoC容器可用的最后一道工序。


1.3.8 最后springboot做一些收尾工作。自此springboot的简单流程到此结束。
https://blog.csdn.net/u011961421/article/details/80227453
