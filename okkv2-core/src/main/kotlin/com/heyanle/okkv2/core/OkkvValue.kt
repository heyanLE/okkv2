package com.heyanle.okkv2.core

import kotlin.reflect.KProperty

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
        append(" -> ").append(okkv().covertFrom(clazz()))
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = get()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) = set(value)

}