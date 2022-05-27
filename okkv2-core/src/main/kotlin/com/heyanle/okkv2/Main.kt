package com.heyanle.okkv2

import com.heyanle.okkv2.core.Converter
import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvDefaultProvider
import com.heyanle.okkv2.core.okkv
import com.heyanle.okkv2.impl.MemoryStore

/**
 * Created by HeYanLe on 2022/5/27 18:42.
 * https://github.com/heyanLE
 */

data class A(
    val a: String,
)

data class B(
    val d: String,
)

fun main(){
    Okkv.Builder().store(MemoryStore())
        .ignoreException(true)
        .build()
        .init().default()

    Okkv.Builder().store(MemoryStore())
        .ignoreException(false)
        .build()
        .init().default("DEBUG")

    val a by okkv<String>("a", "d")

    val b by OkkvDefaultProvider.get("DEBUG").okkv<String>("")






}