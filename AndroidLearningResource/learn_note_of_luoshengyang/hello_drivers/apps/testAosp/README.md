#说明
这个工程是罗老师AOSP系列中通过IHelloService操作虚拟设备读取/写入值的APP。

其中通过通用的方法**获取IHelloService服务**：
```
mHelloService = (IHelloService) getSystemService("hello");
```
使用的SDK是使用修改后的AOSP编译出来的sdk，根目录的`android.jar`为从中获取到的jar包。
**编译SDK命令如下**：
```
//首先是初始化编译环境:

source build/envsetup.sh

//接下来是设定编译目标

lunch sdk-eng

//最后通过以下命令编译SDK

make sdk
```

具体可以参考[
自己动手编译最新Android源码及SDK(Ubuntu)](https://blog.csdn.net/dd864140130/article/details/51718187)
