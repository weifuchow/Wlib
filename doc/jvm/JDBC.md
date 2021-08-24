# JDBC 

## 批量读取数据优化

- 问题

  - 假如:通过jdbc获取大批量数据,通过普通的方式很容易造成OOM

- 解决方式：

  - 以Mysql 为例：
  - 设置流式ResultSet进行处理。
  - 具体做法：
    - setuseCursorFetch(true);
    - stmt.setFetchSize(100)

  ```java
  conn = DriverManager.getConnection("jdbc:mysql://localhost/?useCursorFetch=true", "user", "s3cr3t");
  stmt = conn.createStatement();
  stmt.setFetchSize(100);
  rs = stmt.executeQuery("SELECT * FROM your_table_here");
  ```

  

## 参考文档

- 有关FetchSize 描述
- 可以理解为一个Buffer大小。一次加载若干大小到内存中。当到达底部就会加载下一个Buffer.

> 
>
> - Whenever you execute any query, a cursor is maintianed at database and every resultset.next() makes cursor to move at next row.
>   Hence every rs.next() casues a round trip to database, and current row is sent to your machine.

> - Again, this is not entirely correct. Except the cases described above, when the whole result set is retrieved and cached by the driver, there's also such a thing as fetch size (see Statement and ResultSet.setFetchSize()). A JDBC driver will typically fetch more than one row at once (e.g. 100 rows) and provide you with rows from that buffer; when you've gotten to the end of that buffer the driver will fetch another set of rows. The number of rows fetched can be controlled via the setFetchSize() methods mentioned above.

> - By default, ResultSets are completely retrieved and stored in memory. In most cases this is the most efficient way to operate and, due to the design of the MySQL network protocol, is easier to implement. If you are working with ResultSets that have a large number of rows or large values and cannot allocate heap space in your JVM for the memory required, you can tell the driver to stream the results back one row at a time.
>
>   To enable this functionality, create a `Statement` instance in the following manner:
>
>   ```java
>   stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
>                 java.sql.ResultSet.CONCUR_READ_ONLY);
>   stmt.setFetchSize(Integer.MIN_VALUE);
>   ```
>
>   The combination of a forward-only, read-only result set, with a fetch size of `Integer.MIN_VALUE` serves as a signal to the driver to stream result sets row-by-row. After this, any result sets created with the statement will be retrieved row-by-row.
>
>   There are some caveats with this approach. You must read all of the rows in the result set (or close it) before you can issue any other queries on the connection, or an exception will be thrown.
>
>   The earliest the locks these statements hold can be released (whether they be `MyISAM` table-level locks or row-level locks in some other storage engine such as `InnoDB`) is when the statement completes.
>
>   If the statement is within scope of a transaction, then locks are released when the transaction completes (which implies that the statement needs to complete first). As with most other databases, statements are not complete until all the results pending on the statement are read or the active result set for the statement is closed.
>
>   Therefore, if using streaming results, process them as quickly as possible if you want to maintain concurrent access to the tables referenced by the statement producing the result set.
>
>   Another alternative is to use cursor-based streaming to retrieve a set number of rows each time. This can be done by setting the connection property `useCursorFetch` to true, and then calling `setFetchSize(int)` with `int` being the desired number of rows to be fetched each time:
>
>   ```java
>   conn = DriverManager.getConnection("jdbc:mysql://localhost/?useCursorFetch=true", "user", "s3cr3t");
>   stmt = conn.createStatement();
>   stmt.setFetchSize(100);
>   rs = stmt.executeQuery("SELECT * FROM your_table_here");
>   ```
>
>   





