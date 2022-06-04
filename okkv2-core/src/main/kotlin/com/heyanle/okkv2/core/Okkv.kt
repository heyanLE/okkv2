package com.heyanle.okkv2.core

import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.core.store.Store
import com.heyanle.okkv2.impl.NullableOkkvValueImpl
import com.heyanle.okkv2.impl.OkkvImpl
import com.heyanle.okkv2.impl.OkkvValueImpl
import com.heyanle.okkv2.impl.chain.*
import java.lang.NullPointerException

/**
 * Created by HeYanLe on 2022/5/27 15:17.
 * https://github.com/heyanLE
 */

fun <T: Any> okkv(
    key: String,
    def: T,
    ignoreException: Boolean? = null,
    ): OkkvValueImpl<T>{
    return OkkvValueImpl<T>(
        okkvFinder = {
            OkkvDefaultProvider.okkv[OkkvDefaultProvider.DEFAULT_KEY]?:throw Exception("Okkv[${OkkvDefaultProvider.DEFAULT_KEY}] is null")
        },
        nullable = false,
        key = key,
        clazz = def::class.java,
        defaultValue = def,
        ignoreException = ignoreException
    )
}

fun <T: Any> Okkv.okkv(key: String, def: T, ignoreException: Boolean? = null): OkkvValueImpl<T>{
    return OkkvValueImpl<T>(
        okkvFinder = {
               this
        },
        nullable = false,
        key = key,
        clazz = def::class.java,
        defaultValue = def,
        ignoreException = ignoreException
    )
}

inline fun <reified T: Any > okkv(
    key: String,
    ignoreException: Boolean? = null,): NullableOkkvValueImpl<T> {
    return NullableOkkvValueImpl<T>(
        okkvFinder = {
            OkkvDefaultProvider.okkv[OkkvDefaultProvider.DEFAULT_KEY]?:throw Exception("Okkv[${OkkvDefaultProvider.DEFAULT_KEY}] is null")
        },
        key = key,
        clazz = T::class.java,
        ignoreException = ignoreException
    )
}

inline fun <reified T: Any > Okkv.okkv(key: String, ignoreException: Boolean? = null): NullableOkkvValueImpl<T> {
    return NullableOkkvValueImpl<T>(
        okkvFinder = {
            this
        },
        key = key,
        clazz = T::class.java,
        ignoreException = ignoreException
    )
}




object OkkvDefaultProvider {
    const val DEFAULT_KEY = "DEFAULT"
    var okkv = HashMap<String, Okkv>()

    fun get(key: String): Okkv{
        return okkv[key]?: throw Exception("Okkv[${OkkvDefaultProvider.DEFAULT_KEY}] is null")
    }

    fun def(okkv: Okkv){
        this.okkv[DEFAULT_KEY]= okkv
    }
    fun def(key: String, okkv: Okkv){
        this.okkv[key] = okkv
    }
}

interface Okkv {

    class Builder {
        var store: Store? = null
        private var converters  = arrayListOf<Converter<*, *>>()
        var interceptorChains = arrayListOf<Interceptor>()
        var cache: Boolean = false
        private var ignoreException: Boolean = true
        private var catchingChain : CatchingInterceptor = object: CatchingInterceptor(){
            override fun onCatching(throwable: Throwable) {

            }
        }

        fun cache(): Builder {
            cache = true
            return this
        }

        fun catchingChain(catchingInterceptor: CatchingInterceptor): Builder{
            catchingChain = catchingInterceptor
            return this
        }

        fun cache(boolean: Boolean):Builder{
            cache = boolean
            return this
        }

        fun store(store: Store): Builder{
            this.store = store
            return this
        }

        fun ignoreException(ignore: Boolean): Builder{
            ignoreException = ignore
            return this
        }


        fun addConverter(converter: Converter<*, *>): Builder {
            this.converters.add(converter)
            return this
        }

        fun addInterceptorChain(interceptor: Interceptor):Builder{
            this.interceptorChains.add(interceptor)
            return this

        }

        fun build(): Okkv{

            val list = arrayListOf<Interceptor>()
            list.add(catchingChain)
            list.add(ConvertInterceptor())

            if(cache){
                list.add(CacheInterceptor())
            }

            list.addAll(interceptorChains)

            val st = store?:throw NullPointerException("store can't be null")


            list.add(StoreInterceptor(st))

            val head = HeadInterceptor()
            var p: Interceptor = head
            for(chain in list){
                p.next = chain
                p = chain
            }
            return OkkvImpl(head, st, converters, ignoreException)
        }
    }

    fun init(): Okkv

    fun canStore(clazz: Class<*>) : Boolean

    fun <T: Any> getValue(value: OkkvValue<T>): T?

    fun <T: Any> setValue(value: OkkvValue<T>, v: T?)

    fun<T: Any> covertFrom(clazz: Class<T>): List<Converter<Any, Any>>

    fun ignoreException():Boolean

    fun default(): Okkv{
        OkkvDefaultProvider.def(this)
        return this
    }

    fun default(key: String): Okkv{
        OkkvDefaultProvider.def(key,this)
        return this
    }
}