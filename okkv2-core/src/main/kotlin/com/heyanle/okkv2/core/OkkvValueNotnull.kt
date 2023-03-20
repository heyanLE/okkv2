package com.heyanle.okkv2.core

/**
 * Created by LoliBall on 2023/3/20 9:14.
 * https://github.com/WhichWho
 */
interface OkkvValueNotnull<T: Any>: OkkvValue<T> {

    override fun defaultValue(): T

    override fun get(): T

}