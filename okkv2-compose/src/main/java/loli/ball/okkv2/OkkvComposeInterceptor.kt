package loli.ball.okkv2

import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvDefaultProvider
import com.heyanle.okkv2.core.OkkvValue
import com.heyanle.okkv2.core.chain.Interceptor
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by LoliBall on 2023/1/18 23:43.
 * https://github.com/WhichWho
 */

object OkkvComposeInterceptor : Interceptor() {

    private val listener = ConcurrentHashMap<Any, OkkvObserveBean<Any?>>()

    fun addListener(
        hash: Any,
        key: String,
        okkv: Okkv = OkkvDefaultProvider.def(),
        action: (Any?) -> Unit
    ) {
        listener += hash to OkkvObserveBean(okkv, key, hash, action)
    }

    fun removeListener(hash: Any) {
        listener -= hash
    }

    override fun <T : Any> get(okkvValue: OkkvValue<T>): T? {
        return next?.get(okkvValue)
    }

    override fun <T : Any> set(okkvValue: OkkvValue<T>, value: T?) {
        listener.forEach { (_, bean) ->
            if (bean.key == okkvValue.key() && bean.okkv == okkvValue.okkv()) {
                bean.action.invoke(value)
            }
        }
        next?.set(okkvValue, value)
    }

    data class OkkvObserveBean<T : Any?>(
        val okkv: Okkv,
        val key: String,
        val hash: Any,
        val action: (T) -> Unit
    )

}
