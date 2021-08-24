## Reactive 

- java 响应式用法的基本用法。
- 基于rxjava

### 术语表

- flow (流程)
- item （流程的一项任务）

> Base classes
RxJava 3 features several base classes you can discover operators on:

- io.reactivex.rxjava3.core.Flowable:
    - 0..N flows, supporting Reactive-Streams and backpressure
- io.reactivex.rxjava3.core.Observable:
    - 0..N flows, no backpressure,
- io.reactivex.rxjava3.core.Single:
    - a flow of exactly 1 item or an error,
- io.reactivex.rxjava3.core.Completable:
    - a flow without items but only a completion or error signal,
- io.reactivex.rxjava3.core.Maybe:
    - a flow with no items, exactly one item or an error.

### 目标
- 熟悉响应式编程思想。
- 在日常运用中灵活运用
- 例如
    - 是












