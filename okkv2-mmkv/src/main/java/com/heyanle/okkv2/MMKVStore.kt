package com.heyanle.okkv2

import android.content.Context
import com.heyanle.okkv2.core.store.SimpleStore
import com.tencent.mmkv.MMKV

/**
 * Created by HeYanLe on 2022/5/27 20:13.
 * https://github.com/heyanLE
 */
class MMKVStore(context: Context): SimpleStore() {

    private val applicationContext = context.applicationContext
    override fun getString(key: String): String? {
        return MMKV.defaultMMKV().decodeString(key)
    }

    override fun getDouble(key: String): Double? {
        if (MMKV.defaultMMKV().containsKey(key)){
            return MMKV.defaultMMKV().decodeDouble(key)
        }
        return null
    }

    override fun getInt(key: String): Int? {
        if (MMKV.defaultMMKV().containsKey(key)){
            return MMKV.defaultMMKV().decodeInt(key)
        }
        return null
    }

    override fun getLong(key: String): Long? {
        if (MMKV.defaultMMKV().containsKey(key)){
            return MMKV.defaultMMKV().decodeLong(key)
        }
        return null
    }

    override fun getFloat(key: String): Float? {
        if (MMKV.defaultMMKV().containsKey(key)){
            return MMKV.defaultMMKV().decodeFloat(key)
        }
        return null
    }

    override fun getBoolean(key: String): Boolean? {
        if (MMKV.defaultMMKV().containsKey(key)){
            return MMKV.defaultMMKV().decodeBool(key)
        }
        return null
    }

    override fun setString(key: String, value: String?) {
        if(value == null){
            remove(key)
            return
        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun setDouble(key: String, value: Double?) {
        if(value == null){
            remove(key)
            return
        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun setInt(key: String, value: Int?) {
        if(value == null){
            remove(key)
            return

        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun setLong(key: String, value: Long?) {
        if(value == null){
            remove(key)
            return
        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun setFloat(key: String, value: Float?) {
        if(value == null){
            remove(key)
            return
        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun setBoolean(key: String, value: Boolean?) {
        if(value == null){
            remove(key)
            return
        }
        MMKV.defaultMMKV().encode(key, value)
    }

    override fun init() {
        MMKV.initialize(applicationContext)
    }

    override fun remove(key: String) {
        MMKV.defaultMMKV().removeValueForKey(key)
    }
}