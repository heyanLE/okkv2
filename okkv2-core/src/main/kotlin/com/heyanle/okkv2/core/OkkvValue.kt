package com.heyanle.okkv2.core

/**
 * Created by HeYanLe on 2022/5/27 14:57.
 * https://github.com/heyanLE
 */
interface OkkvValue<T : Any> {

    fun okkv(): Okkv

    fun key(): String

    fun clazz(): Class<T>

    fun nullable(): Boolean

    fun defaultValue(): T?

    fun get(): T?

    fun require(): T = get() ?: throw NullPointerException("get() return null")

    fun set(value: T?)

    fun ignoreException(): Boolean?

    fun covertLine() = buildString {
        append(clazz().simpleName)
        okkv().covertFrom(clazz()).forEach {
            append(" -> ").append(it.rClazz().simpleName)
        }
    }

}