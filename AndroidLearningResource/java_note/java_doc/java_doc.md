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
 * @Title: TestA.java 
 * @Author:jixiaoyong
 * @Emil:jixiaoyong1995@gmail.com
 * @Website:www.android666.cf
 */
package cf.android666;

/**
 * TODO  description
 * @author jixiaoyong1995@gmail.com
 * @version 
 */
public class TestA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

```

