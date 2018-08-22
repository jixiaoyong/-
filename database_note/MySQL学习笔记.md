#  MySQL学习笔记

> MySQL 教程 笔记 https://www.imooc.com/learn/122

# 常用命令

```
net start mysql //开启数据库mysql

net stop mysql //关闭数据库mysql
```

mysql是数据库名称

**创建mysql:**

`mysql -init mysql_name（没有的话默认为mysql）`

**登录mysql**

`mysql -uroot -p -P3306 -h127.0.0.1`

用户名-uroot （端口3306 服务器地址127.0.0.1，如果是默认的就可以不写这两个参数）

**修改提示符**
1.mysql -uroot -p --prompt tishifu
2.登录后：prompt tishifu


tishifu可以是\d当前数据库 \D完整的日期 \h服务器名称 \u用户名

例如：
prompt \u@\d:
PROMPT set to '\u@\d:'
root@(none):

**退出**
exit
quit
\q

**SQL命令以“;”结尾**

# 创建数据库

```
CREATE {DATABASE | SCHRMA} [IF NOT EXISTS] db_name [DEFAULT] CHARACTER SET [=] charset_name
```

{} -> 必须要有的内容

| -> 可以从这几项中选择

[] -> 有无都可以

* DATABASE | SCHRMA 完全相同，任取其一
* IF NOT EXISTS 如果数据库已经存在则忽略报错
* [DEFAULT] CHARACTER SET 数据库编码方式，无则默认

# 查看数据库

```
SHOW {DATABASE | SCHEMAS} [LIKE 'pattern' | WHERE expr]
```

查看数据库创建命令

`SHOW CREATE DATABASE database_name`

查看警告

`SHOW WARNINGS`

# 修改数据库

````
ALTER {DATABASE | SCHEMA}  [db_name] [DEFAULT] CHARACTERSET [=] chaeset_name
````

# 删除数据库

```
DROP {DATABASE | SCHEMA} [IF EXISTS] db_name
```

# 数据类型

整型：

| TINYINT | SMALLINT | MEDIUMINT | INT  | BIGIN | 数据类型 |
| ------- | -------- | :-------: | ---- | ----- | -------- |
| 1       | 2        |     3     | 4    | 8     | 字节     |

浮点型

| 数据类型     | FLOAT[(M,D)]        | DOUBLE[(M,D)]         |
| ------------ | ------------------- | --------------------- |
| M,数字总位数 | D，小数点后面的位数 | M,D被省略则由硬件决定 |

日期时间型

| 数据类型 | YEAR | TIME | DATE | DATETIME | TIMESTAMP |
| -------- | ---- | ---- | ---- | -------- | --------- |
| 存储需求 | 1    | 3    | 3    | 8        | 4         |

字符型

| 数据类型       | CHAR(M)        | VARCHAR(M) | TINYTEXT | TEXT | MEDIUMTEXT | LONGTEXT | ENUM('value1','value2'...)                    | SET('value1';'value2')                             |
| -------------- | -------------- | ---------- | -------- | ---- | ---------- | -------- | --------------------------------------------- | -------------------------------------------------- |
| 存储需求(字节) | M（0<=M<=255） | L+1        | L+1      | L+2  | L+3        | L+4      | 1或者2字节，取决于枚举值个数（最多65535个值） | 1,2,3,4,8个字节，取决于set成员数目（最多64个成员） |

CHAR(M) 占用M个字节，不足补齐

VARCHAR(M) 占用L+1个字节

ENUM只能取某一个值

SET可以取值的任意组合

# 数据表

行 ——记录

列 ——字段

* 打开数据库

`USE DATABASE_NAME`

* 显示用户当前打开的数据库

`SELECT DATABASE();`

* 创建数据表

```
CREATE TABLE [IF NOT EXISTS] table_name(
column_name data_type,
如：username VARCHAR(20),
xxx,
xxx (最后一项不需要逗号)
)
```

例如：

```
root@test1:CREATE TABLE tb1(
    -> username VARCHAR(20),
    -> age TINYINT UNSIGNED,
    -> salary FLOAT(8,2) UNSIGNED)
    -> ;
```

`UNSIGNED `是无符号的意思



* 查看数据表

```
SHOW TABLES [FROM db_name] [LIKE 'pattern' | WHERE expr]
```

`SHOW TABLES` 查看当前数据库的数据表

`SHOW TABLES FROM db_name` 查看db_name的数据表

* 查看数据表结构

```
SHOW COLUMNS FROM tbl_name
```

* 插入记录

```
INSERT [INTO] tbl_name [(col_name,...)] VALUES(val,...)
```

例如：

全部赋值：`INSERT tb1 VALUES('TOM',25,100);`

部分赋值：`INSERT tb1(username,salary) VALUES('shayn',2500);`

* 查找记录

```
SELECT expr,... FROM tbl_name
```

例如：`SELECT * FROM tb1`

* 约束

约束保证数据的完整性和一致性，分为表级约束和列级约束。主要包括：

**1.非空约束**

空值`NULL` 与 非空`NOT NULL` 

**自动编号**

```
AUTO_INCREMENT
```

自动编号，必须与**主键**配合使用，默认为1，每次+1

**2.主键约束**

```
PRIMARY KEY
```

每个数据表只能有一个主键，必须唯一，自动为NOT NULL

例如：

```
CREATE TABLE tb_name(
id SMALLINT UNSIGNED AUTO_INCREMENT,
...
);
```

*AUTO_INCREMENT一定与主键一起使用，但是主键不必须和AUTO_INCREMENT使用*

**3.唯一约束**

```
UNIQUE KEY
```

保证该字段唯一不重复，可以为NULL，一个表可以有多个唯一约束的字段

**4.默认约束**

```
DEFAULT
```

如果没有为字段赋值，则默认赋值

例如：

性别有1，2，3，默认为3

```
sex ENUM('1','2','3') DEFAULT '3'，
```

**5.外键约束**

保持数据一致性、完整性。实现一对一、一对多关系。

1) 父子表必须都是用NNODB存储引擎

```
//my.ini
default-storage-engine=INNODB
```

2) 外键列和参照列数据类型必须相同，数字长度、是否有符号位必须一致，文本则长度无需一致

3) 外键列和参照列必须存在索引，如果外键列无索引MySQL将会自动创建



```
CREATE TABLE father(
id SMALLINT(5) UNSIGNED PRIMARY KEY AUTO_INCREMENT,//参照列，PRIMARY KEY自动创建了索引
...
);

CREATE TABLE son(
fid SMALLINT(5) ,//外键列
FOREIGN KEY (fid) REFERENCES father (id)//fid参照father表中的id列
);
```

查看table_name的索引（ *\G可以切换显示样式* ）

`SHOW INDEXS FROM table_name\G;`

外键约束的参照操作

1) CASCADE 父表更新/删除时，也自动更新/删除子表中的行

2) SET NULL 父表更新/删除时，设置字表外键列为NULL（需确保子表列不是NOT NULL）

3) RESTRICT 拒绝对父表的删除/更新操作

4) NO ACTION 标准SQL关键字，在MySQL中通RESTRICT

用法：

```
FOREIGN KEY (fid) REFERENCES father (id) CASCADE 
```

* 修改数据表

**插入列**

插入一条列

```
ALTER TABLE tbl_name ADD [COLUMN] col_name column_definition [FIRST|AFTER col_name]
//FIRST 所有列前面 AFTER col_name 位于col_name后面
```

例如：

```
ALTER TABLE son ADD age TINYINT UNSIGNED NOT NULL DEFAULT 10;//默认在所有列后面
```

添加多列

```
ALTER TABLE tbl_name ADD [COLUMN] (col_name column_definition,...)
```

**删除列**

```
//删除单列
ALTER TABLE tbl_name DROP [COLUMN] col_name
//删除多列
ALTER TABLE tbl_name DROP [COLUMN] col_name，DROP [COLUMN] col_name
```

其实删除、增加都可以同时写到语句中，用逗号隔开即可

**修改数据约束**

```
//添加主键约束
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] PRIMARY KEY [index_type] (index_col_name)//只有一个
//添加唯一约束
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] UNIQUE [INDEX|KEY] [index_name] [index_type] (index_col_name,...)
//添加外键约束
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] FROEIGN KEY [index_name](index_col_name,...) reference_definition
例如：ALTER TABLE son ADD FOREIGN KEY (fid) PREFERENCES father(id)
//添加、删除默认约束
ALTER TABL tbl_nam ALTER [COLUMN] col_name {
    SET DEFAULT Literal | DROP DEFAULT 
}
```

若添加CONSTRAINT [symbol] 可以指定symbol为约束名称

Literal 为默认值

* 删除约束

```
//删除主键约束
ALTER TABLE tbl_name DROP PRIMARY KEY
//删除唯一约束
ALTER TABLE tbl_name DROP {INDEX|KEY} index_name
//删除外键约束
ALTER TABLE tbl_name DROP FOREIGN KEY fk_symbol
```

fk_symbol 外键约束的名称