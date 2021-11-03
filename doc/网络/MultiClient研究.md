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



