package com.heyanle.okkv2

import android.content.Context
import android.content.SharedPreferences
import com.heyanle.okkv2.core.store.SimpleStore

/**
 * Created by HeYanLe on 2022/5/27 20:19.
 * https://github.com/heyanLE
 */
class SPStore(
    context: Context,
    spName: String
): SimpleStore() {
    private val applicationContext = context.applicationContext

    private val sharedPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(
            spName,
            Context.MODE_PRIVATE
        )
    }
    override fun getString(key: String): String? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getString(key, "")
        }
        return null
    }

    override fun getDouble(key: String): Double? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getString(key, "0.0")?.toDouble()
        }
        return null
    }

    override fun getInt(key: String): Int? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getInt(key, 0)
        }
        return null
    }

    override fun getLong(key: String): Long? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getLong(key, 0)
        }
        return null
    }

    override fun getFloat(key: String): Float? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getFloat(key, 0f)
        }
        return null
    }

    override fun getBoolean(key: String): Boolean? {
        if (sharedPreferences.contains(key)) {
            return sharedPreferences.getBoolean(key, false)
        }
        return null
    }

    override fun setString(key: String, value: String?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun setDouble(key: String, value: Double?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putString(key, value.toString()).apply()
    }

    override fun setInt(key: String, value: Int?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putInt(key, value?:0).apply()
    }

    override fun setLong(key: String, value: Long?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun setFloat(key: String, value: Float?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    override fun setBoolean(key: String, value: Boolean?) {
        if(value == null){
            remove(key)
            return
        }
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun init() {

    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key)
    }
}