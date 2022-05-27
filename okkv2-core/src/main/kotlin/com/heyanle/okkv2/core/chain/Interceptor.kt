package com.heyanle.okkv2.core.chain

/**
 * Created by HeYanLe on 2022/5/27 15:51.
 * https://github.com/heyanLE
 */
abstract class  Interceptor (
    var next: Interceptor? = null
): InterceptorChain {

}