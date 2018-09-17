# javadoc 笔记

**Eclipse设置**

1. 打开设置

   `Window -> Preferences -> java -> Code Templates - comments `

2. 文件注释

   `Files -> edit`，生成的文件头部注释

```
 /**
 * @Package ${package_name}
 * @Title: ${file_name}
 * @Author:jixiaoyong
 * @Emil:jixiaoyong1995@gmail.com
 * @Website:www.android666.cf 
 */
```

3. 类注释 

   `Types -> edit`，生成的类名和导入语句之间的注释

```
 /**
 *  TODO description //这个是类的注释
 * @author jixiaoyong1995@gmail.com
 * @version ${tags} 
 */
```

4. 生成java doc

   `src - Expotr... - javadoc - next * 3 ... finish`

5. 解决不可映射的GBK

   `finish 之前 VM Options：-encoding UTF-8 -charset UTF-8`

6. 生成Java文件预览

```java
/**
 * Copyright © 2018 jixiaoyong All rights reserved.
 * @Project TestJava
 * @Package cf.android666
 * @Title: TestJava.java 
 * @Author:jixiaoyong
 * @Emil:jixiaoyong1995@gmail.com
 * @Website:www.android666.cf
 */
package cf.android666;

/**
 * 说明如何配置文件说明信息
 * 
 * @author jixiaoyong1995@gmail.com
 * @version 1.0
 */
public class TestJava {

	/**
	 * 主方法
	 * 
	 * @param args 入参
	 */
	public static void main(String[] args) {

	}
}
```

