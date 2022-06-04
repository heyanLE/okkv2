### OKKV —— 一个好用的数据持久化工具类

[![](https://jitpack.io/v/heyanLE/okkv2.svg)](https://jitpack.io/#heyanLE/okkv2)

[使用文档点击这里](https://github.com/heyanLE/okkv2/doc/menu.md)

* 1.0.7 2022/05/27  
支持自定义 Store 支持的类型  
支持多类型的 Covert（采用 DFS 寻找最短路径）  
支持可空类型

### 依赖

```groovy
repositories {
    // Other
    maven {url 'https://jitpack.io'}
}
```

```groovy
implementation 'com.github.heyanLE.okkv2:okkv2-core:1.0.7' // 核心代码
implementation 'com.github.heyanLE.okkv2:okkv2-sp:1.0.7' // 使用 SharedPreference 储存
implementation 'com.github.heyanLE.okkv2:okkv2-mmkv:1.0.7' // 使用 MMKV 储存
```