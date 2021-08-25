# Mysql 索引查询
- 文章
- https://www.jianshu.com/p/8a1706910b55

## page大分页解决
- https://explainextended.com/2009/10/23/mysql-order-by-limit-performance-late-row-lookups/
- ```sql
  SELECT  l.id, value, LENGTH(stuffing) AS len
  FROM    (
  SELECT  id
  FROM    t_limit
  ORDER BY
  id
  LIMIT 150000, 10
  ) o
  JOIN    t_limit l
  ON      l.id = o.id
  ```

- 总结：
    - 最左前缀只能通过以什么开头才能确定数据所在的页。若是中间的只能全表查询。
    - key的构成为所有索引项拼接在一起构起。按照从左到右进行排序
    - 关键核心是必须筛选到所在的页范围。哪怕只有一层。（id,name,age)通过查 id  age
    ------
    - 在区分度高的字段上面建立索引可以有效的使用索引，区分度太低，无法有效的利用索引，可能需要扫描所有数据页，此时和不使用索引差不多
    - **联合索引注意最左匹配原则：必须按照从左到右的顺序匹配，mysql会一直向右匹配直到遇到范围查询(>、<、between、like)就停止匹配，比如a = 1 and b = 2 and c > 3 and d = 4 如果建立(a,b,c,d)顺序的索引，d是用不到索引的，如果建立(a,b,d,c)的索引则都可以用到，a,b,d的顺序可以任意调整**
    - 查询记录的时候，少使用*，尽量去利用索引覆盖，可以减少回表操作，提升效率
    - 有些查询可以采用联合索引，进而使用到索引下推（IPC），也可以减少回表操作，提升效率
    - 禁止对索引字段使用函数、运算符操作，会使索引失效
    - 字符串字段和数字比较的时候会使索引无效
    - 模糊查询'%值%'会使索引无效，变为全表扫描，但是'值%'这种可以有效利用索引
    -  排序中尽量使用到索引字段，这样可以减少排序，提升查询效率
      
    