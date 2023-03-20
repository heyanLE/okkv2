package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.impl.NotnullOkkvValueImpl
import com.heyanle.okkv2.impl.NullableOkkvValueImpl

/**
 * Created by HeYanLe on 2022/5/27 15:53.
 * https://github.com/heyanLE
 */
class ConvertInterceptor(
) : Interceptor() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        val nextInterceptor = next
        if (nextInterceptor == null) throw Exception("ConvertInterceptor as last interceptor")
        val okkv = okkvValue.okkv()
        val clazz = okkvValue.clazz()
        if (okkv.canStore(clazz)) {
            return nextInterceptor.get(okkvValue)
        }
        val convert = okkv.covertFrom(clazz)
        if (convert != null) {
            var res: Any? = nextInterceptor.get(newOkkvValue(convert.to, okkvValue))
            if (res != null) {
                res = convert.converter.deserialize(res, clazz as Class<Any>)
            }
            return res as T?
        } else {
            var res: Any? = nextInterceptor.get(newOkkvValue(String::class.java, okkvValue))
            if (res is String) {
                res = okkv.fallbackConverter.deserialize(res, clazz as Class<Any>)
            }
            return res as T?
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        val nextInterceptor = next
        if (nextInterceptor == null) throw Exception("ConvertInterceptor as last interceptor")
        val okkv = okkvValue.okkv()
        val clazz = okkvValue.clazz()
        if (okkv.canStore(clazz)) {
            nextInterceptor.set(okkvValue, value)
            return
        }
        val convert = okkv.covertFrom(clazz)
        if (convert != null) {
            var res: Any? = value
            if (res != null) {
                res = convert.converter.serialize(res, clazz as Class<Any>)
            }
            nextInterceptor.set(newOkkvValue(convert.to, okkvValue), res)
        } else {
            var res: Any? = value
            if (res != null) {
                res = okkv.fallbackConverter.serialize(res, clazz as Class<Any>)
            }
            nextInterceptor.set(newOkkvValue(String::class.java, okkvValue), res)
        }
    }

    private fun newOkkvValue(clazz: Class<*>, okkvValue: OkkvValue<*>): OkkvValue<Any> {
        if (okkvValue.nullable()) {
            return NullableOkkvValueImpl(
                okkvFinder = { okkvValue.okkv() },
                key = okkvValue.key(),
                clazz = clazz
            )
        } else {
            return NotnullOkkvValueImpl(
                okkvFinder = { okkvValue.okkv() },
                key = okkvValue.key(),
                clazz = clazz,
                defaultValue = okkvValue.defaultValue()!!
            )
        }
    }

}