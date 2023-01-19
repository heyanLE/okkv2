### OKKV2 使用文档

[返回目录](./menu.md)

#### Compose 相关

添加了如下两个函数用于支持compose中的自动刷新布局  
`okkvStateOf(key, value)`  
`okkv.observeAsState()`  

如需使用okkv.observeAsState()则需要在初始化Okkv时添加如下拦截器  
```kotlin
Okkv.Builder(MMKVStore(this))
            .cache()
            .composeInterceptor() // 用于监听存储发生的变化，通知compose刷新布局
            .build()
            .init()
            .default()
```

具体使用例子如下  
```kotlin
@Composable
fun OkkvComposeTest() {

    var okkv1 by remember {
        okkvStateOf("OkkvTest1", "12345")
    }

    val okkv2 = remember { okkv("OkkvTest2", "abcde") }
    val ok2state by okkv2.observeAsState()

    Button(onClick = {
        okkv1 = System.currentTimeMillis().toString()
        okkv2.set(System.currentTimeMillis().toString())
    }) {
        Column {
            Text(text = okkv1)
            Text(text = ok2state)
        }
    }
}
```
当点击按钮时会触发自动重组并持久化保存数据
