package com.heyanle.okkv2.core

import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.core.store.Store
import com.heyanle.okkv2.impl.EmptyConvert
import com.heyanle.okkv2.impl.OkkvImpl
import com.heyanle.okkv2.impl.chain.*

/**
 * Created by HeYanLe on 2022/5/27 15:17.
 * https://github.com/heyanLE
 */
interface Okkv {

    class Builder(val store: Store) {

        var converters = mutableMapOf<Class<*>, ConverterBean<*, *>>()
        private var fallbackConverter: Converter<Any, String> = EmptyConvert()
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

        inline fun <reified T : Any, reified R : Any> converter(cov: Converter<T, R>) = apply {
            converters[T::class.java] = ConverterBean(T::class.java, R::class.java, cov)
        }

        fun fallbackConverter(cov: Converter<Any, String>) = apply {
            fallbackConverter = cov
        }

        fun interceptor(interceptor: Interceptor) = apply {
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

            return OkkvImpl(head, store, converters, fallbackConverter, ignoreException)
        }
    }

    fun init(): Okkv

    fun canStore(clazz: Class<*>): Boolean

    fun <T : Any> getValue(value: OkkvValue<T>): T?

    fun <T : Any> setValue(value: OkkvValue<T>, v: T?)

    fun <T : Any> covertFrom(clazz: Class<T>): ConverterBean<Any, Any>?

    val fallbackConverter: Converter<Any, String>

    fun ignoreException(): Boolean

    fun default() = apply {
        OkkvDefaultProvider.def(this)
    }

    fun default(key: String) = apply {
        OkkvDefaultProvider.set(key, this)
    }
}