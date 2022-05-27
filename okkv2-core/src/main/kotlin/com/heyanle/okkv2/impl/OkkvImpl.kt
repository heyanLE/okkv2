package com.heyanle.okkv2.impl

import com.heyanle.okkv2.core.*
import com.heyanle.okkv2.core.chain.InterceptorChain
import com.heyanle.okkv2.core.store.Store
import java.util.Collections
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.Exception
import kotlin.concurrent.read
import kotlin.concurrent.write

/**
 * Created by HeYanLe on 2022/5/27 15:29.
 * https://github.com/heyanLE
 */
class OkkvImpl(
    private val okkvChain: InterceptorChain,
    private val store: Store,
    converter: List<Converter<*, *>>,
    private val ignoreException: Boolean,
): Okkv {

    private val map :Map<Class<*>, List<Converter<Any, Any>>>

    init {
        val m = HashMap<Class<*>, ArrayList<Converter<Any, Any>>>()
        converter.forEach {
            m[it.tClazz()] = m.getOrDefault(it.tClazz(), arrayListOf()).apply {
                add(it as Converter<Any, Any>)
            }
        }
        val ma = HashMap<Class<*>, List<Converter<Any, Any>>>()
        m.forEach { (t, u) ->
            ma[t] = Collections.unmodifiableList(u)
        }
        map = Collections.unmodifiableMap(ma)

    }

    override fun init(): Okkv {
        store.init()
        return this
    }

    override fun <T : Any> getValue(value: OkkvValue<T>): T? {
        return okkvChain.get(value)
    }

    override fun <T : Any> setValue(value: OkkvValue<T>, v: T?){
        okkvChain.set(value, v)
    }

    private val hashMap = HashMap<Class<*>, List<Converter<Any, Any>>>()
    private val lock = ReentrantReadWriteLock()


    override fun <T : Any> covertFrom(clazz: Class<T>): List<Converter<Any, Any>> {
        var res : List<Converter<Any, Any>>? = null
        lock.read {
            if(hashMap.containsKey(clazz)){
                res =  hashMap[clazz]
            }
        }
        if(res == null){
            findCovert(clazz)
        }
        lock.read {
            if(hashMap.containsKey(clazz)){
                res =  hashMap[clazz]
            }
        }
        return res?: emptyList()
    }

    override fun canStore(clazz: Class<*>): Boolean {
        return store.canStore(clazz)
    }

    private fun findCovert(clazz: Class<*>){
        dfsRes.clear()
        val list = map[clazz]?: emptyList()
        list.forEach {
            dfs(it, arrayListOf(), hashSetOf())
        }
        if(dfsRes.isNotEmpty()){
            lock.write {
                hashMap[clazz] = Collections.unmodifiableList(dfsRes)
            }
        }
    }



    /**
     * 深度优先搜索
     * 寻找从 原类型 转换为 可储存类型的最短路径
     */
    private val dfsRes = arrayListOf<Converter<Any, Any>>()
    private fun dfs(
        current: Converter<Any, Any>,
        line: ArrayList<Converter<Any, Any>>,
        set: HashSet<Converter<Any, Any>>
    ){
        if(set.contains(current)){
            return
        }
        if(canStore(current.rClazz())){
            if(dfsRes.isEmpty() || line.size < dfsRes.size) {
                line.add(current)
                dfsRes.clear()
                dfsRes.addAll(line)
                line.remove(current)
            }
            return
        }
        set.add(current)
        line.add(current)
        val next = map[current.rClazz()]?: emptyList()
        next.forEach {
            dfs(it, line, set)
        }
        set.remove(current)
        line.remove(current)
    }

    override fun ignoreException(): Boolean {
        return ignoreException
    }
}