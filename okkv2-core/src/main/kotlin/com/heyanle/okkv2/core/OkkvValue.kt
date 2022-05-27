package com.heyanle.okkv2.core

import com.heyanle.okkv2.core.chain.InterceptorChain
import kotlin.reflect.KProperty

/**
 * Created by HeYanLe on 2022/5/27 14:57.
 * https://github.com/heyanLE
 */
interface OkkvValue<T: Any> {

    fun okkv(): Okkv

    fun key(): String

    fun clazz(): Class<T>

    fun nullable(): Boolean

    fun defaultValue(): T?

    fun get(): T?

    fun set(value: T?)

    fun ignoreException():Boolean?

    fun covertLine():String {
        val stringBuilder  = StringBuilder()
        stringBuilder.append(clazz().simpleName)
        okkv().covertFrom(clazz()).forEach {
            stringBuilder.append(" -> ").append(it.rClazz().simpleName)
        }
        return stringBuilder.toString()
    }

}