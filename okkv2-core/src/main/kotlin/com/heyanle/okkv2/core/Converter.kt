package com.heyanle.okkv2.core

/**
 * Created by HeYanLe on 2022/5/27 15:12.
 * https://github.com/heyanLE
 */
interface Converter<T : Any, R : Any> {

    fun serialize(data: T, clazz: Class<T>): R

    fun deserialize(data: R, clazz: Class<T>): T

}