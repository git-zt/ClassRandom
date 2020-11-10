ClassRandom Project
=======

## 1.简介

​    这是一款使用 `Java` 语言，基于 `Java.Swing` 可视化开发的一款多场景适用的点名软件。

## 2.已经完成的功能

- 基础点名功能
- 查看当前的名单
- 去除重复点同一个人
- 查看被去除的名单

## 3.远景规划

- 语音播报功能
- 统计功能
- 更美观的图形界面

## 4.随机性测试

​    随机点名的实现基于 `Java` 自带的 `Random` 库，由于 `Random` 库底层实现方式为**伪随机**，故进行了 `3000` 次测试证明了数据的随机性。

## 5.名单的修改方式

​    该软件名单存在**内存而非储存**中，故**名单只能在代码层面实现修改**。名单数组存放在 `Spanel.java` 的第 `13` 行`(v1.0.2)`，修改时注意数组的合法性即可。

## 6.保留权利及声明

​    该项目遵守 **[GPL-3.0](https://github.com/git-zt/ClassRandom/blob/main/LICENSE)** 协议开源。

​    软件当前版本：`v1.0.2.20201025`

​    作者：Zt 

​    作者个人网站：[https://www.zhen-t.com/](https://www.zhen-t.com/)

​    项目开源地址：[https://github.com/git-zt/ClassRandom](https://github.com/git-zt/ClassRandom)
