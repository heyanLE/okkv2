package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.Converter

/**
 * Created by LoliBall on 2023/3/20 9:54.
 * https://github.com/WhichWho
 */
class EmptyConvert: Converter<Any, String> {
    override fun serialize(data: Any, clazz: Class<Any>): String {
        error("Can't serialize $data")
    }

    override fun deserialize(data: String, clazz: Class<Any>): Any {
        error("Can't deserialize $data")
    }
}