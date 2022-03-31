# MultiClient 研究



## 问题

- 现在许多设备都是作为服务端使用。我们平台需要创建客户端进行连接。而且每个客户端连接不一样。当前一个客户端client.对应着一个线程，无法支持多台client连接。为此需要统一管理这些客户端的连接资源。



## 解决方案

- 由于Server端是支持多路复用，监听server 端的连接、读取、写入等感兴趣事件。客户端也应该可以利用类似的方案。将socketchannel 的感兴趣事件都绑定在selector之上。
- **（Selector 的本质管理一大堆fd (channel),通过轮询就绪事件，编解码及业务逻辑绑定channel上，等事件触发时进行绑定。这实际就是事件回调的过程，实现异步操作，减少线程开销）**
- **单线程可以控制管理上1000 or more clients的连接。**

- ![image-20211102152045153](D:/weifuchow_space/Wlib/doc/%E7%BD%91%E7%BB%9C/images/MultiClient%E7%A0%94%E7%A9%B6/image-20211102152045153.png)



## 实现方案

### 服务端

- 简单服务端，EchoServer  客户端连接成功之后，将向其输出hello world 加自增ID，客户端打印数据内容,。循环1000
- 客户端主动发起数据写入Server中。Server 打印数据内容。
- 协议格式 length + body
- 用body 为字符串UTF 进行序列化/反序列化。

### 客户端

- 先编写一个client。Selector 作为参数，监听client的connect、read事件。
- 模拟2000个client,用一个selector 监听这些事件.在一个线程进行事件循环。每个socketchannel写数据。

## NIO 实现

- Client 的注册监听事件，统一绑定到外部Selector当中。

  - ```java
    public void connect(String host, int port, Selector selector) throws IOException {
        this.socketChannel = SocketChannel.open();
        this.socketChannel.configureBlocking(false);
        this.socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        this.socketChannel.connect(new InetSocketAddress(host,port));
    }
    ```

- 由统一的外部Selector 进行开辟线程进行事件的一个轮询。

  - **（这里可以将读取事件交由Client处理，只需要进行判断对应的socketChannel.再关联到具体的client 即可）**

  - ```java
    public static void eventLoop(Selector sel) throws Exception {
        while (!Thread.interrupted()) {
            //
            if (sel.select() > 0) {
                Set<SelectionKey> selectionKeys = sel.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    if (next.isConnectable()) {
                        processConnect(next);
                    } else if (next.isReadable()) {
                        try {
                            proccessRead(next);
                        } catch (Exception e) {
                            next.cancel();
                            logger.error("error", e);
                        }
                    }
                }
            }
        }
    }
    ```

## Netty 实现

- Netty 的实现更加简单且紧凑。只需要Client 绑定时将NioEventGroup 用同一个即可。多个client 连接、读取由该线程池进行处理。

```JAVA
public void connect(String host, int port, NioEventLoopGroup workGroup) {
    Bootstrap bootstrap = new Bootstrap();


    bootstrap.group(workGroup);

    bootstrap.channel(NioSocketChannel.class);
    bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
    bootstrap.option(ChannelOption.TCP_NODELAY, true);

    bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);

    bootstrap.handler(new ChannelInitializer<SocketChannel>() {
        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new MyStringDecoder());
            p.addLast(new MyStringEncoder());
            p.addLast(new EchoClientHandler(self));
        }
    });

    ChannelFuture channelFuture = bootstrap.connect(host, port);

    channelFuture.addListener((ChannelFuture future) -> {
        if (future.isSuccess()) {
            logger.info("client {} connected", future.channel());
            this.channel = future.channel();
        } else {
            logger.info("client {} connected error", future.channel());
            logger.error("error",future.cause());
        }
    });
}
```

## 测试用例

```java
public class Test {

    private static AtomicInteger counter = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static NettyServer server = new NettyServer();
    private static List<NettyClient> list = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9090;
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(16,new DefaultThreadFactory("client-worker-group"));
        //
        createServer(port);
        //
        for (int i = 0; i < 5000; i++) {
            createClient(host, port, eventLoopGroup);
        }
        //
        Thread.sleep(5000);
        server.sendData("hello world!");
//        //
        Thread.sleep(1000);
//        //
        list.forEach(client -> {
            client.sendData("hello world server " + UUID.randomUUID().toString());
        });
    }
    public static void createServer(int port) throws IOException {
        server.init(port);
    }

    public static void createClient(String host, int port, NioEventLoopGroup eventLoopGroup) throws IOException {
        int id = counter.incrementAndGet();
        NettyClient client = new NettyClient("test-" + id, "name-" + id);
        client.connect(host, port,eventLoopGroup);
        list.add(client);
    }

}
```

## 总结

- 在使用Netty作为客户端管理工具时，可利用全局NioEventGroup进行多客户端eventgroup管理。经过测试可得。NioEventGroup 配置多个线程时，会对立面的每个channel进行负载均衡，即每个线程处理平均处理不同Channel的eventLoop。

  - 日志

    - 连接时

    ```html
    2021-11-05 14:46:44 INFO  client-worker-group-1-14 NettyClient:69 - client [id: 0xad7893f7, L:/127.0.0.1:3150 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-16 NettyClient:69 - client [id: 0x43b7edae, L:/127.0.0.1:3151 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-7 NettyClient:69 - client [id: 0x48c1b144, L:/127.0.0.1:3143 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-15 NettyClient:69 - client [id: 0xaa3c4ff7, L:/127.0.0.1:3152 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-4 NettyClient:69 - client [id: 0x5d2c134b, L:/127.0.0.1:3146 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-9 NettyClient:69 - client [id: 0x56fbde02, L:/127.0.0.1:3153 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-3 NettyClient:69 - client [id: 0xbcdc6e13, L:/127.0.0.1:3144 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-10 NettyClient:69 - client [id: 0x8e430a51, L:/127.0.0.1:3148 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-11 NettyClient:69 - client [id: 0x7e6abb75, L:/127.0.0.1:3154 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-2 NettyClient:69 - client [id: 0x41b29c05, L:/127.0.0.1:3141 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-6 NettyClient:69 - client [id: 0xd8b188d5, L:/127.0.0.1:3155 - R:localhost/127.0.0.1:9090] connected
    2021-11-05 14:46:44 INFO  client-worker-group-1-4 NettyClient:69 - client [id: 0x2d0fdd8c, L:/127.0.0.1:3159 - R:localhost/127.0.0.1:9090] connected
    ```

    - 读取时

    ```html
    2021-11-05 14:46:49 INFO  client-worker-group-1-11 NettyClient:90 - client channel [id: 0x7e6abb75, L:/127.0.0.1:3154 - R:localhost/127.0.0.1:9090] receive msg hello world!->15
    2021-11-05 14:46:49 INFO  server-worker-group-4-15 NettyServer:95 - server receive msg hey server i am client = >name-5 from [id: 0xe43f12e1, L:/127.0.0.1:9090 - R:/127.0.0.1:3149]
    2021-11-05 14:46:49 INFO  client-worker-group-1-6 NettyClient:92 - client send msg to server : hey server i am client = >name-6
    2021-11-05 14:46:49 INFO  client-worker-group-1-13 NettyClient:92 - client send msg to server : hey server i am client = >name-13
    2021-11-05 14:46:49 INFO  client-worker-group-1-1 NettyClient:92 - client send msg to server : hey server i am client = >name-17
    2021-11-05 14:46:49 INFO  client-worker-group-1-11 NettyClient:92 - client send msg to server : hey server i am client = >name-11
    2021-11-05 14:46:49 INFO  client-worker-group-1-12 NettyClient:90 - client channel [id: 0xbdd201d0, L:/127.0.0.1:3147 - R:localhost/127.0.0.1:9090] receive msg hello world!->4
    2021-11-05 14:46:49 INFO  client-worker-group-1-8 NettyClient:90 - client channel [id: 0xd1d813af, L:/127.0.0.1:3156 - R:localhost/127.0.0.1:9090] receive msg hello world!->8
    2021-11-05 14:46:49 INFO  client-worker-group-1-14 NettyClient:90 - client channel [id: 0xad7893f7, L:/127.0.0.1:3150 - R:localhost/127.0.0.1:9090] receive msg hello world!->2
    2021-11-05 14:46:49 INFO  server-worker-group-4-1 NettyServer:95 - server receive msg hey server i am client = >name-11 from [id: 0xd01b4627, L:/127.0.0.1:9090 - R:/127.0.0.1:3154]
    2021-11-05 14:46:49 INFO  client-worker-group-1-16 NettyClient:90 - client channel [id: 0x43b7edae, L:/127.0.0.1:3151 - R:localhost/127.0.0.1:9090] receive msg hello world!->5
    2021-11-05 14:46:49 INFO  server-worker-group-4-2 NettyServer:95 - server receive msg hey server i am client = >name-18 from [id: 0xbd66dbcc, L:/127.0.0.1:9090 - R:/127.0.0.1:3158]
    2021-11-05 14:46:49 INFO  client-worker-group-1-3 NettyClient:90 - client channel [id: 0xf261e412, L:/127.0.0.1:3157 - R:localhost/127.0.0.1:9090] receive msg hello world!->11
    2021-11-05 14:46:49 INFO  client-worker-group-1-9 NettyClient:90 - client channel [id: 0x56fbde02, L:/127.0.0.1:3153 - R:localhost/127.0.0.1:9090] receive msg hello world!->7
    2021-11-05 14:46:49 INFO  client-worker-group-1-14 NettyClient:92 - client send msg to server : hey server i am client = >name-14
    2021-11-05 14:46:49 INFO  client-worker-group-1-16 NettyClient:92 - client send msg to server : hey server i am client = >name-16
    2021-11-05 14:46:49 INFO  server-worker-group-4-10 NettyServer:95 - server receive msg hey server i am client = >name-14 from [id: 0x6b66b571, L:/127.0.0.1:9090 - R:/127.0.0.1:3150]
    2021-11-05 14:46:49 INFO  client-worker-group-1-9 NettyClient:92 - client send msg to server : hey server i am client = >name-9
    2021-11-05 14:46:49 INFO  client-worker-group-1-4 NettyClient:92 - client send msg to server : hey server i am client = >name-4
    2021-11-05 14:46:49 INFO  client-worker-group-1-3 NettyClient:92 - client send msg to server : hey server i am client = >name-19
    ```

  - **线程 client-worker-group-1-9 始终处理着L127.0.0.1:3153 - R:localhost/127.0.0.1:9090 的channel.说明每个线程将会平均处理channel的eventloop.**

