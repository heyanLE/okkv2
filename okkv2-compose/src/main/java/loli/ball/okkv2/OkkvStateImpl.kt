package loli.ball.okkv2

import androidx.compose.runtime.SnapshotMutationPolicy
import androidx.compose.runtime.snapshots.*
import androidx.compose.runtime.structuralEqualityPolicy
import com.heyanle.okkv2.core.Okkv
import com.heyanle.okkv2.core.OkkvDefaultProvider
import com.heyanle.okkv2.core.okkv

/**
 * Created by LoliBall on 2023/1/18 22:10.
 * https://github.com/WhichWho
 */
open class OkkvStateImpl<T : Any>(
    key: String,
    def: T,
    okkv: Okkv = OkkvDefaultProvider.def(),
    observeOtherSet: Boolean = false,
    override val policy: SnapshotMutationPolicy<T> = structuralEqualityPolicy()
) : StateObject, SnapshotMutableState<T> {

    val innerOkkv = okkv.okkv(key, def, null)

    @Suppress("UNCHECKED_CAST")
    override var value: T
        get() {
            val value1 = next.readable(this).value
            val okkvGet = innerOkkv.get()
            return if (value1 == okkvGet) {
                value1
            } else {
                value = okkvGet
                okkvGet
            }
        }
        set(value) = next.withCurrent {
            innerOkkv.set(value)
            if (!policy.equivalent(it.value, value)) {
                next.writable(this) { this.value = value }
            }
        }

    init {
        if (observeOtherSet) {
            OkkvComposeInterceptor.addListener(this, key, okkv) {
                @Suppress("UNCHECKED_CAST")
                val value = it as T
                next.withCurrent {
                    if (!policy.equivalent(it.value, value)) {
                        next.writable(this) { this.value = value }
                    }
                }
            }
        }
    }

    fun releaseListener() {
        OkkvComposeInterceptor.removeListener(this)
    }

    private var next: StateStateRecord<T> = StateStateRecord(def)

    override val firstStateRecord: StateRecord
        get() = next

    override fun prependStateRecord(value: StateRecord) {
        @Suppress("UNCHECKED_CAST")
        next = value as StateStateRecord<T>
    }

    @Suppress("UNCHECKED_CAST")
    override fun mergeRecords(
        previous: StateRecord,
        current: StateRecord,
        applied: StateRecord
    ): StateRecord? {
        val previousRecord = previous as StateStateRecord<T>
        val currentRecord = current as StateStateRecord<T>
        val appliedRecord = applied as StateStateRecord<T>
        return if (policy.equivalent(currentRecord.value, appliedRecord.value))
            current
        else {
            val merged = policy.merge(
                previousRecord.value,
                currentRecord.value,
                appliedRecord.value
            )
            if (merged != null) {
                appliedRecord.create().also {
                    (it as StateStateRecord<T>).value = merged
                }
            } else {
                null
            }
        }
    }

    override fun toString(): String = next.withCurrent {
        "MutableState(value=${it.value})@${hashCode()}"
    }

    private class StateStateRecord<T>(myValue: T) : StateRecord() {
        override fun assign(value: StateRecord) {
            @Suppress("UNCHECKED_CAST")
            this.value = (value as StateStateRecord<T>).value
        }

        override fun create(): StateRecord = StateStateRecord(value)

        var value: T = myValue
    }

    /**
     * The componentN() operators allow state objects to be used with the property destructuring
     * syntax
     *
     * ```
     * var (foo, setFoo) = remember { mutableStateOf(0) }
     * setFoo(123) // set
     * foo == 123 // get
     * ```
     */
    override operator fun component1(): T = value

    override operator fun component2(): (T) -> Unit = { value = it }

    /**
     * A function used by the debugger to display the value of the current value of the mutable
     * state object without triggering read observers.
     */
    @Suppress("unused")
    val debuggerDisplayValue: T
        @JvmName("getDebuggerDisplayValue")
        get() = next.withCurrent { it }.value
}