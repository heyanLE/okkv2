package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.*
import com.heyanle.okkv2.core.chain.InterceptorChain
import com.heyanle.okkv2.core.store.Store

/**
 * Created by HeYanLe on 2022/5/27 15:29.
 * https://github.com/heyanLE
 */
class OkkvImpl(
    private val okkvChain: InterceptorChain,
    private val store: Store,
    private val converter: Map<Class<*>, ConverterBean<*, *>>,
    override val fallbackConverter: Converter<Any, String>,
    private val ignoreException: Boolean
) : Okkv {

    override fun init() = apply {
        store.init()
    }

    override fun <T : Any> getValue(value: OkkvValue<T>): T? = okkvChain.get(value)

    override fun <T : Any> setValue(value: OkkvValue<T>, v: T?) = okkvChain.set(value, v)

    override fun <T : Any> covertFrom(clazz: Class<T>): ConverterBean<Any, Any>? {
        @Suppress("UNCHECKED_CAST")
        return converter[clazz] as? ConverterBean<Any, Any>
    }

    override fun canStore(clazz: Class<*>): Boolean = store.canStore(clazz)

    override fun ignoreException(): Boolean  = ignoreException
}