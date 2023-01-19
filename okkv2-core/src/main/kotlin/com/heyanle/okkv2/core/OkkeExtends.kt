package com.heyanle.okkv2.core

import com.heyanle.okkv2.impl.NotnullOkkvValueImpl
import com.heyanle.okkv2.impl.NullableOkkvValueImpl
import kotlin.reflect.KProperty

/**
 * Created by LoliBall on 2023/1/18 21:42.
 * https://github.com/WhichWho
 */

fun <T : Any> okkv(
    key: String,
    def: T,
    ignoreException: Boolean? = null,
) = NotnullOkkvValueImpl<T>(
    okkvFinder = { OkkvDefaultProvider.def() },
    key = key,
    clazz = def::class.java,
    defaultValue = def,
    ignoreException = ignoreException
)

fun <T : Any> Okkv.okkv(
    key: String,
    def: T,
    ignoreException: Boolean? = null
) = NotnullOkkvValueImpl<T>(
    okkvFinder = { this },
    key = key,
    clazz = def::class.java,
    defaultValue = def,
    ignoreException = ignoreException
)

inline fun <reified T : Any> okkv(
    key: String,
    ignoreException: Boolean? = null,
) = NullableOkkvValueImpl<T>(
    okkvFinder = { OkkvDefaultProvider.def() },
    key = key,
    clazz = T::class.java,
    ignoreException = ignoreException
)

inline fun <reified T : Any> Okkv.okkv(
    key: String,
    ignoreException: Boolean? = null
): NullableOkkvValueImpl<T> {
    return NullableOkkvValueImpl<T>(
        okkvFinder = { this },
        key = key,
        clazz = T::class.java,
        ignoreException = ignoreException
    )
}

operator fun <T : Any> OkkvValue<T>.getValue(thisRef: T?, property: KProperty<*>): T? {
    return this.get()
}

operator fun <T : Any> OkkvValue<T>.setValue(thisRef: T?, property: KProperty<*>, value: T?) {
    this.set(value)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
operator fun <T : Any> NullableOkkvValueImpl<T>.getValue(thisRef: T?, property: KProperty<*>): T? {
    return this.get()
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
operator fun <T : Any> NullableOkkvValueImpl<T>.setValue(thisRef: T?, property: KProperty<*>, value: T?) {
    this.set(value)
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
operator fun <T : Any> NotnullOkkvValueImpl<T>.getValue(thisRef: T?, property: KProperty<*>): T {
    return this.get()
}

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
operator fun <T : Any> NotnullOkkvValueImpl<T>.setValue(thisRef: T?, property: KProperty<*>, value: T) {
    this.set(value)
}
