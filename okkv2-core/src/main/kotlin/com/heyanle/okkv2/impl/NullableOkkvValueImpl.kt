package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvValue
import kotlin.reflect.KProperty

/**
 * Created by HeYanLe on 2022/5/27 18:43.
 * https://github.com/heyanLE
 */
class NullableOkkvValueImpl<T: Any>(
    private val okkvFinder: ()-> Okkv,
    private val key: String,
    private val clazz: Class<*>,
    private val ignoreException: Boolean? = null,
): OkkvValue<T> {

    private val okkv: Okkv by lazy {
        okkvFinder()
    }


    override fun okkv(): Okkv {
        return okkv
    }

    override fun key(): String {
        return key
    }

    override fun clazz(): Class<T> {
        return clazz as Class<T>
    }

    override fun nullable(): Boolean {
        return true
    }

    override fun defaultValue(): T {
        throw Exception("There are no default value in nullable okkv value")
    }

    override fun get(): T? {
        return okkv().getValue(this)
    }

    override fun set(value: T?) {
        okkv().setValue(this, value)
    }

    override fun ignoreException(): Boolean? {
        return ignoreException
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        return set(value)
    }


}