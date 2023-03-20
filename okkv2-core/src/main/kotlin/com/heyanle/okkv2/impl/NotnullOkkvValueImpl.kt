package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvValueNotnull

/**
 * Created by HeYanLe on 2022/5/27 16:29.
 * https://github.com/heyanLE
 */
class NotnullOkkvValueImpl<T : Any>(
    private val okkvFinder: () -> Okkv,
    private val key: String,
    private val clazz: Class<*>,
    private val defaultValue: T,
    private val ignoreException: Boolean? = true,
) : OkkvValueNotnull<T> {

    private val okkv: Okkv by lazy {
        okkvFinder()
    }

    override fun okkv() = okkv
    override fun key() = key

    @Suppress("UNCHECKED_CAST")
    override fun clazz() = clazz as Class<T>
    override fun nullable() = false
    override fun defaultValue() = defaultValue

    override fun get(): T {
        return okkv.getValue(this) ?: defaultValue
    }

    override fun setNotnull(value: T) {
        okkv.setValue(this, value)
    }

    override fun ignoreException() = ignoreException

}