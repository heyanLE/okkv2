package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by HeYanLe on 2022/5/27 17:47.
 * https://github.com/heyanLE
 */
class CacheInterceptor: Interceptor() {

    private val hashMap = ConcurrentHashMap<String, Any>()

    override fun <T: Any> get(okkvValue: OkkvValue<T>): T? {
        if(hashMap.containsKey(okkvValue.key())){
            try {
                return hashMap[okkvValue.key()] as T?
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
        val o = next?.get(okkvValue)
        o?.let {
            hashMap[okkvValue.key()] = o
        }
        return o
    }

    override fun <T: Any> set(okkvValue: OkkvValue<T>, value: T?) {
        next?.set(okkvValue, value)
        if(value == null){
            hashMap.remove(okkvValue.key())
        }else
            hashMap[okkvValue.key()] = value
    }
}