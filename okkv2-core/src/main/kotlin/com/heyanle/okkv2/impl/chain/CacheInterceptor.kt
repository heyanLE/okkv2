package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by HeYanLe on 2022/5/27 17:47.
 * https://github.com/heyanLE
 */
class CacheInterceptor : Interceptor() {

    private val hashMap = ConcurrentHashMap<String, Any>()

    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        if (hashMap.containsKey(okkvValue.key())) {
            try {
                @Suppress("UNCHECKED_CAST")
                return hashMap[okkvValue.key()] as T?
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return next?.get(okkvValue)?.also {
            hashMap[okkvValue.key()] = it
        }
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        next?.set(okkvValue, value)
        if (value == null) {
            hashMap.remove(okkvValue.key())
        } else {
            hashMap[okkvValue.key()] = value
        }
    }
}