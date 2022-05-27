package com.heyanle.okkv2.impl.chain

import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.impl.OkkvValueImpl
import kotlin.Exception

/**
 * Created by HeYanLe on 2022/5/27 15:53.
 * https://github.com/heyanLE
 */
class ConvertInterceptor(
): Interceptor() {
    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        if(okkvValue.okkv().canStore(okkvValue.clazz())) {
            return (next ?: throw Exception("ConvertInterceptor as last interceptor")).get(okkvValue)
        }
        val convertList = okkvValue.okkv().covertFrom(okkvValue.clazz())
        if(convertList.isEmpty()){
            throw Exception("Can't convert type ${okkvValue.clazz().simpleName} to storable type")
        }

        var res :Any? = (next ?: throw Exception("ConvertInterceptor as last interceptor")).get(newOkkvValue(convertList.last().rClazz(), okkvValue))
        for(i in convertList.size-1 downTo 0){
            res = convertList[i].convertFrom(res)
        }
        return res as T?
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?){
        var res: Any? = value
        if(okkvValue.okkv().canStore(okkvValue.clazz())){
            next?.set(okkvValue, value)
            return
        }
        val convertList = okkvValue.okkv().covertFrom(okkvValue.clazz())
        if(convertList.isEmpty()){
            throw Exception("Can't convert type ${okkvValue.clazz().simpleName} to storable type")
        }
        for(i in convertList.indices){
            res = convertList[i].convertTo(res)
        }
        next?.set<Any>(newOkkvValue(convertList.last().rClazz(), okkvValue) as OkkvValue<Any>, res) ?:
        throw Exception("ConvertInterceptor as last interceptor")
    }

    private fun newOkkvValue(clazz: Class<*>, okkvValue: OkkvValue<*>): OkkvValueImpl<Any>{
        return OkkvValueImpl(
            okkvFinder = {
                okkvValue.okkv()
            },
            key = okkvValue.key(),
            clazz = clazz,
            nullable = okkvValue.nullable(),
            defaultValue = if(okkvValue.nullable()) null else okkvValue.defaultValue()
        )
    }
}