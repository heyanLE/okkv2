package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor

/**
 * Created by HeYanLe on 2022/5/27 18:17.
 * https://github.com/heyanLE
 */
class HeadInterceptor : Interceptor() {
    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        return next?.next?.get(okkvValue)
            ?: if (okkvValue.nullable()) {
                null
            } else {
                okkvValue.defaultValue()
            }
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        next?.next?.set(okkvValue, value)
    }
}