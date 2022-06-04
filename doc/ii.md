### OKKV2 使用文档

[返回目录](./menu.md)

#### 1.初始化

在 Application 中初始化：

```kotlin
class MyApplication: Application {
    
    override fun onCreate(){
        super.onCreate()
        Okkv.Builder()
            .store(MMKVStore(this)) // 使用 MMKV 储存
            //.store(SPStore(this, "my_data"))  使用 SharedPreference 储存
            .build() // 创建 Okkv 对象
            .init() // 初始化
            .default() // 作为默认
    }
}
```

#### 2.使用

##### 委托使用：

```kotlin
// 不可空类型，需要传入默认值，类型自动根据默认值指定
var dataNonnull: String by okkv("key", "defValue")

// 可空类型，不需要传入默认值，默认值为 null，需要指定泛型
var dataNullable: String? by okkv<String>("key_n")

dataNonnull = "存入"
dataNullable = null

print(dataNullable) // 直接使用则为读取
```

##### 对象使用：



```kotlin
// 对象使用，同样分为可空不可空

val dataNonnull = okkv("key", "def")
val dataNullable = okkv<String>("key_n")

dataNonnull.set("存入")
val res = dataNonnull.get()?:"如果为不可空类型，则不可能走到这" // 返回是可空类型
dataNonnull.require() // 返回不可空类型，如果为空则抛异常

// 具体值是否为空取决于对象的初始化方式，get 和 require 只是直接返回以及检测后抛异常，不会影响具体的存值
```

对象使用中，get 返回永远是 T? 类型，require 永远返回 T 类型，但不会影响存值，是否可以存 null 或者返回值是否为 null 只取决于初始化的类型。

例如：
```kotlin
val dataNullable: OkkvValue<String?> = okkv<String>("key_n")
val res = dataNullable.get() // 如果第一次存储，则为 null
val r = dataNullable.require() // 因为存的值为 null，必定抛异常
```

```kotlin
val dataNonnull: OkkvValue<String> = okkv("key_n","def")
val res = dataNullable.get() // 如果第一次存储，则为 def，但类型依然为 String? 需要进行处理
val r = dataNullable.require() // 为 def，因为本身为不可空类型，因此必定不抛异常
```

同时是否可以存入 null 值也与初始化方式有关：
```kotlin
val dataNullable: OkkvValue<String?> = okkv<String>("key_n")
val dataNonnull: OkkvValue<String> = okkv("key_n","def")
dataNullable.set(null) // 成功
dataNonnull.set(null) // 抛异常
```

##### 使用多套 OKKV

使用默认提供者：
可以在初始化时 default 方法中传入别名
```kotlin
class MyApplication: Application {
    
    override fun onCreate() {
        Okkv.Builder()
            .store(MMKVStore(this)) // 使用 MMKV 储存
            .build() // 创建 Okkv 对象
            .init() // 初始化
            .default("mmkv") // 作为默认，key 为 mmkv

        Okkv.Builder()
            .store(SPStore(this, "my_data")) // 使用 MMKV 储存
            .build() // 创建 Okkv 对象
            .init() // 初始化
            .default("sp") // 作为默认，key 为 sp
    }
}
```

从提供者中拿出指定 Okkv 进行使用：
```kotlin
// 使用 mmkv 存储的不可空类型的委托使用
val data1 by OkkvDefaultProvider.get("mmkv").okkv("key", "def")

// 使用 mmkv 存储的可空类型的委托使用
val data2 by OkkvDefaultProvider.get("mmkv").okkv<String>("key", )

// 使用 sp 存储的不可空类型的对象使用
val data3 = OkkvDefaultProvider.get("sp").okkv("key", "def")

// 使用 sp 存储的可空类型的对象使用
val data4 = OkkvDefaultProvider.get("sp").okkv<String>("key")
```

还可以直接自己管理 Okkv 对象：
```kotlin
val okkvWithCache = Okkv.Builder()
    .store(MMKVStore(this)) // 使用 MMKV 储存
    .cache(true) // 使用缓存
    .build() // 创建 Okkv 对象
    .init() // 初始化
val data1 by okkvWithCache.okkv("key", "def")
// 同样分为 可空类型和不可空类型，对象使用与委托使用
```

okkv 包含了责任链，拦截链，store 与 converter，可以在一个项目中使用多套 Okkv，使用时可选择使用。
