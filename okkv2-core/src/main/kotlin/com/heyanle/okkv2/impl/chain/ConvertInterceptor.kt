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
    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        val nextInterceptor = next
        if (nextInterceptor == null) throw Exception("ConvertInterceptor as last interceptor")
        if (okkvValue.okkv().canStore(okkvValue.clazz())) {
            return nextInterceptor.get(okkvValue)
        }
        val convertList = okkvValue.okkv().covertFrom(okkvValue.clazz())
        if (convertList.isEmpty()) {
            throw Exception("Can't convert type ${okkvValue.clazz().simpleName} to storable type")
        }
        var res: Any? = nextInterceptor.get(newOkkvValue(convertList.last().rClazz(), okkvValue))
        for (i in convertList.size - 1 downTo 0) {
            res = convertList[i].convertFrom(res)
        }
        @Suppress("UNCHECKED_CAST")
        return res as T?
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        var res: Any? = value
        if (okkvValue.okkv().canStore(okkvValue.clazz())) {
            next?.set(okkvValue, value)
            return
        }
        val convertList = okkvValue.okkv().covertFrom(okkvValue.clazz())
        if (convertList.isEmpty()) {
            throw Exception("Can't convert type ${okkvValue.clazz().simpleName} to storable type")
        }
        for (i in convertList.indices) {
            res = convertList[i].convertTo(res)
        }
        next?.set<Any>(newOkkvValue(convertList.last().rClazz(), okkvValue), res)
            ?: throw Exception("ConvertInterceptor as last interceptor")
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