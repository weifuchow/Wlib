## master 序列化过程

- 1.定义注解Store event

  ```java
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface StoreEvent {
  
  }
  ```

- 2.定义切面内（在注解标注的方法运行返回后执行切面））

  - 注解后面执行发布事件

  ```java
  @Component
  @Aspect
  public class StatusChangeAspect {
      private static final Logger logger = LoggerFactory.getLogger(StatusChangeAspect.class);
  
      @Autowired
      ApplicationEventPublisher applicationEventPublisher;
  
      @AfterReturning("@annotation(xx.anotation.StoreEvent)")
      public void sendEvent() {
          logger.info("Publish Store Event");
          applicationEventPublisher.publishEvent(new StoreEvent("store"));
      }
  }
  ```

- 3.定义StoreListener 处理(StoreEvent 事件)

  ```java
  @Component
  public class StoreListener implements ApplicationListener<StoreEvent> {
      private static final Logger logger = LoggerFactory.getLogger(StoreListener.class);
  
      @Autowired
      StoreOperation storeOperation;
  
      @Override
      @Async
      public void onApplicationEvent(StoreEvent event) {
          storeOperation.writeStatus();
      }
  
  }
  ```


- 总结：
  - 使用AOP ,加spring 事件处理器执行响应的操作。
    - aop
    - spring event listener （实现类似于Blocking Queue ,Put event 相当于异步执行)