package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor

/**
 * Created by HeYanLe on 2022/5/27 17:51.
 * https://github.com/heyanLE
 */
abstract class CatchingInterceptor : Interceptor() {

    abstract fun onCatching(throwable: Throwable)

    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        return runCatching {
            next?.get(okkvValue)
        }.onFailure {
            it.printStackTrace()
        }.getOrElse {
            onCatching(it)
            if (!(okkvValue.ignoreException() ?: okkvValue.okkv().ignoreException())) {
                throw it
            }
            null
        }
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        runCatching {
            next?.set(okkvValue, value)
        }.onFailure {
            it.printStackTrace()
            onCatching(it)
            if (!(okkvValue.ignoreException() ?: okkvValue.okkv().ignoreException())) {
                throw it
            }
        }
    }
}