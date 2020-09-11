

http和rpc各有千秋，调用原理没有什么很大的区别

http，一般基于httpclient，restful风格调用，序列化协议是xml或者json，，这个通用性比较好，http协议报文头占用太多字节

rpc，常用的dubbo，thrift，grpc，feign底层有基于http的，也有基于tcp的，序列化协议用的都不一样，保温协议也有用自定义的，

最大的区别，rpc集成配套服务治理的功能！

https://www.cnblogs.com/jokerjason/p/10696467.html
