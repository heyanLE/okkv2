package com.heyanle.okkv2.core.chain

import com.heyanle.okkv2.core.OkkvValue

/**
 * Created by HeYanLe on 2022/5/27 15:00.
 * https://github.com/heyanLE
 */
interface InterceptorChain {

    fun <T : Any> get(okkvValue: OkkvValue<T>): T?

    fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?)

}