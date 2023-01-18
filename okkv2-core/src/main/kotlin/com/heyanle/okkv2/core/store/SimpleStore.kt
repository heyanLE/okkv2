package com.heyanle.okkv2.core.store

/**
 * Created by HeYanLe on 2022/5/27 16:01.
 * https://github.com/heyanLE
 */
abstract class SimpleStore : Store {

    companion object {
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
            Integer::class.java,
        )
    }

    override fun canStore(clazz: Class<*>): Boolean = STORABLE_SET.contains(clazz)

    override fun <T : Any> set(key: String, clazz: Class<T>, value: T?) {
        when (clazz) {
            String::class.java -> setString(key, value as String?)
            Double::class.java, java.lang.Double::class.java -> setDouble(key, value as Double?)
            Int::class.java, Integer::class.java -> setInt(key, value as Int?)
            Long::class.java, java.lang.Long::class.java -> setLong(key, value as Long?)
            Float::class.java, java.lang.Float::class.java -> setFloat(key, value as Float?)
            Boolean::class.java, java.lang.Boolean::class.java -> setBoolean(key, value as Boolean?)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(key: String, clazz: Class<T>): T? {
        return when (clazz) {
            String::class.java -> getString(key)
            Double::class.java, java.lang.Double::class.java -> getDouble(key)
            Int::class.java, Integer::class.java -> getInt(key)
            Long::class.java, java.lang.Long::class.java -> getLong(key)
            Float::class.java, java.lang.Float::class.java -> getFloat(key)
            Boolean::class.java, java.lang.Boolean::class.java -> getBoolean(key)
            else -> null
        } as T?
    }

    abstract fun getString(key: String): String?
    abstract fun getDouble(key: String): Double?
    abstract fun getInt(key: String): Int?
    abstract fun getLong(key: String): Long?
    abstract fun getFloat(key: String): Float?
    abstract fun getBoolean(key: String): Boolean?

    abstract fun setString(key: String, value: String?)
    abstract fun setDouble(key: String, value: Double?)
    abstract fun setInt(key: String, value: Int?)
    abstract fun setLong(key: String, value: Long?)
    abstract fun setFloat(key: String, value: Float?)
    abstract fun setBoolean(key: String, value: Boolean?)
}