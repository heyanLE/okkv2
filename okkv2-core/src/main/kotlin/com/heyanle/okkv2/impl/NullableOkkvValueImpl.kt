package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvValue

/**
 * Created by HeYanLe on 2022/5/27 18:43.
 * https://github.com/heyanLE
 */
class NullableOkkvValueImpl<T : Any>(
    private val okkvFinder: () -> Okkv,
    private val key: String,
    private val clazz: Class<*>,
    private val ignoreException: Boolean? = null,
) : OkkvValue<T> {

    private val okkv: Okkv by lazy {
        okkvFinder()
    }

    override fun okkv() = okkv

    override fun key() = key

    @Suppress("UNCHECKED_CAST")
    override fun clazz() = clazz as Class<T>

    override fun nullable() = true

    override fun defaultValue() =
        throw Exception("There are no default value in nullable okkv value")

    override fun get(): T? = okkv.getValue(this)

    override fun set(value: T?) = okkv.setValue(this, value)

    override fun ignoreException() = ignoreException

}