package com.heyanle.okkv2.core

/**
 * Created by HeYanLe on 2022/5/27 15:12.
 * https://github.com/heyanLE
 */
interface Converter<T : Any, R : Any> {

    fun tClazz(): Class<T>
    fun rClazz(): Class<R>

    fun convertFrom(r: R?): T?
    fun convertTo(t: T?): R?
}