package com.heyanle.okkv2.core

/**
 * Created by LoliBall on 2023/3/20 9:04.
 * https://github.com/WhichWho
 */
data class ConverterBean<T : Any, R : Any>(
    val from: Class<T>,
    val to: Class<R>,
    val converter: Converter<T, R>
)
