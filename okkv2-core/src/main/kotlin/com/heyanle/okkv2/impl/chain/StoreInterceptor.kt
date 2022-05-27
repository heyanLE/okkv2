package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.core.store.Store

/**
 * Created by HeYanLe on 2022/5/27 17:10.
 * https://github.com/heyanLE
 */
class StoreInterceptor(
    private val store: Store
): Interceptor() {
    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        return store.get(okkvValue.key(), okkvValue.clazz())
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        if(value == null){
            store.remove(okkvValue.key())
            return
        }
        store.set(okkvValue.key(), okkvValue.clazz(), value)
    }
}