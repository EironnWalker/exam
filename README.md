# 面试题
#### 问题1

 请安装一个mongo数据库，使用spring boot和spring security框架提供如下接口，并使用spring test提供单元测试：

##### 1 POST /info/create

```json
{"name": "Leviathan Wakes", "author": "James S.A. Corey", "release_date": "2011-06-02", "page_count": 561}

 {"name": "Hyperion", "author": "Dan Simmons", "release_date": "1989-05-26", "page_count": 482}

 {"name": "Dune", "author": "Frank Herbert", "release_date": "1965-06-01", "page_count": 604}

 {"name": "delete_by", "author": "any", "release_date": "2011-11-11", "page_count": 1}
```

>  要求：
>
>  1 此接口需要amdin角色访问
>
>  2 成功返回 ok
>
>  3 如果没有admin角色，返回 error600
>
>  4 数据需要存储到Mongo数据库
>
>  5 上述4条数据，4次POST请求

#####  2 POST /info/delete

```json
 {"name": "delete_by"}
```

>  要求:
>
>  1 删除最后一次插入的数据（name=delete_by）
>
>  2 接口需要admin权限，返回结构同上

#####  3 POST /info/update

```json
 "name": " Hyperion"，update：Dune
```

>  要求:
>
>  1 修改name=Hyperion的数据为name=Dune
>
>  2 接口需要admin权限，返回结构同上

#####  4 POST /info/group/name

>  要求:
>
>  1 根据name聚合数据条数
>
>  2 接口任何人员都可以访问

####  问题2

 有如下元素

A B C D E … 数量不确定，请输出所有可能的组合，如：

1 1 0 0 0 … 表示选取A、B，不选 C、D、E …

####  问题3

请起一个3线程的线程池，计算斐波那契数列前20项，输出：线程id，第几项，和该项数据