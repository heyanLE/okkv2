### OKKV2 使用文档

[返回目录](./menu.md)

#### 自定义 Store

可以继承 Store 或继承 SimpleStore 实现：

```kotlin
interface Store {

    // 是否可以存储该类型
    fun canStore(clazz: Class<*>): Boolean

    // 初始化
    fun init()

    // getter 和 setter 与对应类型
    fun <T: Any> get(key: String, clazz: Class<T>) : T?
    fun <T: Any> set(key: String, clazz: Class<T>, value: T?)

    // 删除（存储 null）
    fun remove(key: String)
}
```

```kotlin
abstract class SimpleStore: Store {

    companion object {
        // 可存储基本数据类型，包括 java 与 kotlin 中的类型
        private val STORABLE_SET = setOf(
            String::class.java,
            Double::class.java,
            java.lang.Double::class.java,
            Int::class.java,
            Long::class.java,
            java.lang.Long::class.java,
            Float::class.java,
            java.lang.Float::class.java,
            Boolean::class.java,
            java.lang.Boolean::class.java,
            Integer::class.java,)
    }

    override fun canStore(clazz: Class<*>): Boolean {
        val set = STORABLE_SET
        val res = set.contains(clazz)
        return STORABLE_SET.contains(clazz)
    }

    override fun <T : Any> set(key: String, clazz: Class<T>, value: T?) {
        when(clazz){
            String::class.java -> setString(key, value as String?)
            Double::class.java, java.lang.Double::class.java -> setDouble(key, value as Double?)
            Int::class.java , Integer::class.java-> setInt(key, value as Int?)
            Long::class.java, java.lang.Long::class.java -> setLong(key, value as Long?)
            Float::class.java, java.lang.Float::class.java -> setFloat(key, value as Float?)
            Boolean::class.java , java.lang.Boolean::class.java-> setBoolean(key, value as Boolean?)
        }
    }

    override fun <T : Any> get(key: String, clazz: Class<T>): T? {
        return when(clazz){
            String::class.java -> getString(key)
            Double::class.java, java.lang.Double::class.java  -> getDouble(key)
            Int::class.java, Integer::class.java -> getInt(key)
            Long::class.java, java.lang.Long::class.java -> getLong(key)
            Float::class.java, java.lang.Float::class.java -> getFloat(key)
            Boolean::class.java, java.lang.Boolean::class.java -> getBoolean(key)
            else -> null
        }as T?
    }

    // 主要实现的抽象方法

    abstract fun getString(key: String) : String?
    abstract fun getDouble(key: String) : Double?
    abstract fun getInt(key: String) : Int?
    abstract fun getLong(key: String) : Long?
    abstract fun getFloat(key: String) : Float?
    abstract fun getBoolean(key: String) : Boolean?

    abstract fun setString(key: String, value: String?)
    abstract fun setDouble(key: String, value: Double?)
    abstract fun setInt(key: String, value: Int?)
    abstract fun setLong(key: String, value: Long?)
    abstract fun setFloat(key: String, value: Float?)
    abstract fun setBoolean(key: String, value: Boolean?)
}
```