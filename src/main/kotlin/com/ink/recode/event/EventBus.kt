package com.ink.recode.event

import java.lang.reflect.Method

// 1. 先定义 @Listener 注解
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@kotlin.annotation.MustBeDocumented
annotation class Listener(val priority: Int = 0)

// 2. 定义基础 Event 类（带 cancelled 标记）
open class Event {
    var cancelled: Boolean = false
}

object EventBus {
    // 3. 数据类保持不变
    data class ListenerMethod(
        val target: Any,
        val method: Method,
        val priority: Int
    )

    // 4. 注意：变量名统一为小写 listeners（Kotlin 习惯）
    private val listeners = mutableMapOf<Class<*>, MutableList<ListenerMethod>>()

    fun register(listener: Any) {
        val methods = listener::class.java.declaredMethods
            .filter {
                it.isAnnotationPresent(Listener::class.java) &&
                        it.parameterTypes.size == 1
            }

        methods.forEach { method ->
            // 安全获取注解
            val annotation = method.getAnnotation(Listener::class.java)
                ?: return@forEach // 如果没有注解，跳过

            val eventType = method.parameterTypes.first()

            // 修正拼写错误：enventtype → eventType
            listeners.computeIfAbsent(eventType) { mutableListOf() }
                .add(
                    // 修正中文括号，使用英文括号
                    ListenerMethod(
                        target = listener,
                        method = method,
                        priority = annotation.priority // 使用注解中的 priority
                    )
                )
        }
    }

    fun post(event: Any) {
        // 修正变量名：Listeners → listeners
        listeners[event::class.java]
            ?.sortedByDescending { it.priority } // 修正 p → priority
            ?.forEach {
                it.method.invoke(it.target, event)
                // 检查事件是否被取消
                if (event is Event && event.cancelled) {
                    return
                }
            }
    }
}