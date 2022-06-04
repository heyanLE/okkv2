### OKKV2 使用文档

[返回目录](./menu.md)

#### Convertor

如果我们有实体类 B

```kotlin
class B(
    var s: String = ""
)
```

想要存储 B 类型，可以通过添加 Convertor 实现：

```kotlin
Okkv.Builder()
    .store(MMKVStore(this)) // 使用 MMKV 储存
    .addConverter(object : Converter<B, String> {
        override fun tClazz(): Class<B> {
            return B::class.java
        }

        override fun rClazz(): Class<String> {
            return String::class.java
        }

        override fun convertFrom(r: String?): B? {
            return B(r?:"")
        }

        override fun convertTo(t: B?): String? {
            return t?.s?:""
        }
    })
    .build() // 创建 Okkv 对象
    .init() // 初始化
    .default("mmkv") // 作为默认，key 为 mmkv
```

在使用中我们如果指定类型为 B ，则会自动调用转换器转换为 String，进行存取。

可以进行多重 Convertor：

例如有 AB 类型

```kotlin
class B(
    var s: String = ""
){
    fun getConvertToString(): Converter<B, String>{
        return object : Converter<B, String> {
            override fun tClazz(): Class<B> {
                return B::class.java
            }

            override fun rClazz(): Class<String> {
                return String::class.java
            }

            override fun convertFrom(r: String?): B? {
                return B(r?:"")
            }

            override fun convertTo(t: B?): String? {
                return t?.s?:""
            }
        }
    }
}

class A(
    var s : String = ""
){
    fun getConvertToB(): Converter<A, B>{
        return object : Converter<A, B> {
            override fun tClazz(): Class<A> {
                return A::class.java
            }

            override fun rClazz(): Class<B> {
                return B::class.java
            }

            override fun convertFrom(r: B?): A? {
                return if(r == null) r else A(r.s)
            }

            override fun convertTo(t: A?): B? {
                return if(t == null) t else B(t.s)
            }
        }
    }
}
```

```kotlin
Okkv.Builder()
        .store(MMKVStore(this)) // 使用 MMKV 储存
        // A 转换为 B
        .addConverter(A().getConvertToB())
        // B 转换为 String
        .addConverter(B().getConvertToString())
        .build() // 创建 Okkv 对象
        .init() // 初始化
        .default("mmkv") // 作为默认，key 为 mmkv
```

当我们制定 A 类型使用时，会自动将 A 类型转换为 B 类型，然后将 B 类型转换为 String 类型进行存取。

实际上在 ConvertInterceptor 中是使用 dfs 进行搜索，寻找到转换为对应 Store 支持的类型的最短路径.
具体可查看 [ConvertInterceptor](../okkv2-core/src/main/kotlin/com/heyanle/okkv2/impl/chain/ConvertInterceptor.kt)
