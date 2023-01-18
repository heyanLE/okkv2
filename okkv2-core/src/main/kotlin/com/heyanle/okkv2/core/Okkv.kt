package com.heyanle.okkv2.core

import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.core.store.Store
import com.heyanle.okkv2.impl.OkkvImpl
import com.heyanle.okkv2.impl.chain.*

/**
 * Created by HeYanLe on 2022/5/27 15:17.
 * https://github.com/heyanLE
 */
interface Okkv {

    class Builder(val store: Store) {

        private var converters = arrayListOf<Converter<*, *>>()
        var interceptorChains = arrayListOf<Interceptor>()
        var cache: Boolean = false
        private var ignoreException: Boolean = true
        private var catchingChain: CatchingInterceptor = object : CatchingInterceptor() {
            override fun onCatching(throwable: Throwable) = Unit
        }

        fun cache() = apply {
            cache = true
        }

        fun cache(boolean: Boolean) = apply {
            cache = boolean
        }

        fun catchingChain(catchingInterceptor: CatchingInterceptor) = apply {
            catchingChain = catchingInterceptor
        }

        fun ignoreException(ignore: Boolean) = apply {
            ignoreException = ignore
        }

        fun addConverter(converter: Converter<*, *>) = apply {
            this.converters.add(converter)
        }

        fun addInterceptorChain(interceptor: Interceptor) = apply {
            this.interceptorChains.add(interceptor)
        }

        fun build(): Okkv {

            val list = buildList<Interceptor> {
                this += catchingChain
                this += ConvertInterceptor()
                if (cache) {
                    this += CacheInterceptor()
                }
                this += interceptorChains
                this += StoreInterceptor(store)
            }

            val head = HeadInterceptor().also {
                var p: Interceptor = it
                for (chain in list) {
                    p.next = chain
                    p = chain
                }
            }

            return OkkvImpl(head, store, converters, ignoreException)
        }
    }

    fun init(): Okkv

    fun canStore(clazz: Class<*>): Boolean

    fun <T : Any> getValue(value: OkkvValue<T>): T?

    fun <T : Any> setValue(value: OkkvValue<T>, v: T?)

    fun <T : Any> covertFrom(clazz: Class<T>): List<Converter<Any, Any>>

    fun ignoreException(): Boolean

    fun default() = apply {
        OkkvDefaultProvider.def(this)
    }

    fun default(key: String) = apply {
        OkkvDefaultProvider.set(key, this)
    }
}