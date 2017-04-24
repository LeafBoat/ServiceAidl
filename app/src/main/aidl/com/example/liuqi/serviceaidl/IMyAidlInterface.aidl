// IMyAidlInterface.aidl
package com.example.liuqi.serviceaidl;

/**
* 定义一个aidl文档并编译，编译后的文档生成对应的java文件，该文件的包名需要和第三方应用的aidl文件包名一致
* ServiceAidl\app\build\generated\source\aidl\debug\com\example\liuqi\serviceaidl\IMyAidlInterface.java
**/
interface IMyAidlInterface {
   Messenger getMessenger();
}
