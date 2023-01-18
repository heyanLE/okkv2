package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.store.SimpleStore
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by HeYanLe on 2022/5/27 18:54.
 * https://github.com/heyanLE
 */
class MemoryStore : SimpleStore() {

    private val map = ConcurrentHashMap<String, Any>()

    override fun init() = Unit

    override fun getString(key: String) = map[key] as? String
    override fun getDouble(key: String) = map[key] as? Double
    override fun getInt(key: String) = map[key] as? Int
    override fun getLong(key: String) = map[key] as? Long
    override fun getFloat(key: String) = map[key] as? Float
    override fun getBoolean(key: String) = map[key] as? Boolean

    private fun setAny(key: String, value: Any?) {
        if (value != null) map[key] = value
    }

    override fun setString(key: String, value: String?) = setAny(key, value)
    override fun setDouble(key: String, value: Double?) = setAny(key, value)
    override fun setInt(key: String, value: Int?) = setAny(key, value)
    override fun setLong(key: String, value: Long?) = setAny(key, value)
    override fun setFloat(key: String, value: Float?) = setAny(key, value)
    override fun setBoolean(key: String, value: Boolean?) = setAny(key, value)

    override fun remove(key: String) {
        map.remove(key)
    }
}