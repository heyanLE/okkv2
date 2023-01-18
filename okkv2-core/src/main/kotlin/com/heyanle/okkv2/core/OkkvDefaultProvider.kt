package com.heyanle.okkv2.core

/**
 * Created by LoliBall on 2023/1/18 21:41.
 * https://github.com/WhichWho
 */
object OkkvDefaultProvider {
    const val DEFAULT_KEY = "DEFAULT"
    private var okkv = HashMap<String, Okkv>()

    fun get(key: String): Okkv {
        return okkv[key] ?: throw Exception("Okkv[${OkkvDefaultProvider.DEFAULT_KEY}] is null")
    }

    fun set(key: String, okkv: Okkv) {
        this.okkv[key] = okkv
    }

    fun def() = get(DEFAULT_KEY)

    fun def(okkv: Okkv) = set(DEFAULT_KEY, okkv)

}