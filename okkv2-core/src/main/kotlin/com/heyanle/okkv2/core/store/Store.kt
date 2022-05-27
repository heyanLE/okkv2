package com.heyanle.okkv2.core.store

/**
 * Created by HeYanLe on 2022/5/27 15:09.
 * https://github.com/heyanLE
 */
interface Store {

    fun canStore(clazz: Class<*>): Boolean

    fun init()

    fun <T: Any> get(key: String, clazz: Class<T>) : T?
    fun <T: Any> set(key: String, clazz: Class<T>, value: T?)

    fun remove(key: String)
}