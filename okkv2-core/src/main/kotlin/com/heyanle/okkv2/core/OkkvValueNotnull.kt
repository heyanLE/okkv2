package com.heyanle.okkv2.core

import kotlin.reflect.KProperty

/**
 * Created by LoliBall on 2023/3/20 9:14.
 * https://github.com/WhichWho
 */
interface OkkvValueNotnull<T : Any> : OkkvValue<T> {

    override fun defaultValue(): T

    override fun get(): T

    override fun set(value: T?) {
        if (value == null) {
            error("Can't set null to nonnull okkv value")
        }
        setNotnull(value)
    }

    fun setNotnull(value: T)

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = get()

}