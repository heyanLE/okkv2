package com.heyanle.okkv2

import android.util.Log
import com.heyanle.okkv2.core.*
import com.heyanle.okkv2.core.chain.Interceptor
import com.heyanle.okkv2.core.chain.InterceptorChain
import com.heyanle.okkv2.impl.chain.CatchingInterceptor

/**
 * Created by HeYanLe on 2022/6/4 15:53.
 * https://github.com/heyanLE
 */

class B(
    var s: String = ""
){
    fun getConvertToString(): Converter<B, String>{
        return object : Converter<B, String> {
            override fun tClazz(): Class<B> {
                return B::class.java
            }

            override fun rClazz(): Class<String> {
                return String::class.java
            }

            override fun convertFrom(r: String?): B? {
                return B(r?:"")
            }

            override fun convertTo(t: B?): String? {
                return t?.s?:""
            }
        }
    }
}

class A(
    var s : String = ""
){
    fun getConvertToB(): Converter<A, B>{
        return object : Converter<A, B> {
            override fun tClazz(): Class<A> {
                return A::class.java
            }

            override fun rClazz(): Class<B> {
                return B::class.java
            }

            override fun convertFrom(r: B?): A? {
                return if(r == null) r else A(r.s)
            }

            override fun convertTo(t: A?): B? {
                return if(t == null) t else B(t.s)
            }
        }
    }
}

fun main(){



    Okkv.Builder()
        .store(MMKVStore(this)) // 使用 MMKV 储存
        .addConverter(A().getConvertToB())
        .addConverter(B().getConvertToString())
        .build() // 创建 Okkv 对象
        .init() // 初始化
        .default("mmkv") // 作为默认，key 为 mmkv


}