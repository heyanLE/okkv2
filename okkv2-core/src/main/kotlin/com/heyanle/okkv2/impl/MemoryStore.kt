package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.store.SimpleStore
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by HeYanLe on 2022/5/27 18:54.
 * https://github.com/heyanLE
 */
class MemoryStore: SimpleStore() {

    private val map = ConcurrentHashMap<String, Any>()

    override fun init() {

    }

    override fun getString(key: String): String? {
        return runCatching {
            map[key] as String?
        }.getOrNull()
    }

    override fun getDouble(key: String): Double? {
        return runCatching {
            map[key] as Double?
        }.getOrNull()
    }

    override fun getInt(key: String): Int? {
        return runCatching {
            map[key] as Int?
        }.getOrNull()
    }

    override fun getLong(key: String): Long? {
        return runCatching {
            map[key] as Long?
        }.getOrNull()
    }

    override fun getFloat(key: String): Float? {
        return runCatching {
            map[key] as Float?
        }.getOrNull()
    }

    override fun getBoolean(key: String): Boolean? {
        return runCatching {
            map[key] as Boolean?
        }.getOrNull()
    }

    override fun setString(key: String, value: String?) {
        if(value != null)
            map[key] = value
    }

    override fun setDouble(key: String, value: Double?) {
        if(value != null)
            map[key] = value
    }

    override fun setInt(key: String, value: Int?) {
        if(value != null)
            map[key] = value
    }

    override fun setLong(key: String, value: Long?) {
        if(value != null)
            map[key] = value
    }

    override fun setFloat(key: String, value: Float?) {
        if(value != null)
            map[key] = value
    }

    override fun setBoolean(key: String, value: Boolean?) {
        if(value != null)
            map[key] = value
    }

    override fun remove(key: String) {
        map.remove(key)
    }
}