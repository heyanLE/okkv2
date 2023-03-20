package loli.ball.okkv2

import androidx.compose.runtime.*
import com.heyanle.okkv2.core.*

/**
 * Created by LoliBall on 2023/1/18 22:06.
 * https://github.com/WhichWho
 */
fun <T : Any> okkvStateOf(
    key: String,
    value: T,
    okkv: Okkv = OkkvDefaultProvider.def(),
    observeOtherSet: Boolean = false,
    policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
) = OkkvStateImpl(key, value, okkv, observeOtherSet, policy)

@Composable
fun <T : Any> OkkvValue<T>.observeAsState(): State<T?> {
    val state = remember { mutableStateOf(get()) }
    DisposableEffect(this) {
        OkkvComposeInterceptor.addListener(state, key(), okkv()) {
            @Suppress("UNCHECKED_CAST")
            state.value = it as? T
        }
        onDispose { OkkvComposeInterceptor.removeListener(state) }
    }
    return state
}

@Composable
fun <T : Any> OkkvValueNotnull<T>.observeAsState(): State<T> {
    val state = remember { mutableStateOf(get()) }
    DisposableEffect(this) {
        OkkvComposeInterceptor.addListener(state, key(), okkv()) {
            @Suppress("UNCHECKED_CAST")
            state.value = it as T
        }
        onDispose { OkkvComposeInterceptor.removeListener(state) }
    }
    return state
}

fun Okkv.Builder.composeInterceptor() = apply {
    interceptor(OkkvComposeInterceptor)
}
