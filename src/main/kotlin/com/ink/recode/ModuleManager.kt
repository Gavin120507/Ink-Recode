// 路径：src/main/kotlin/com/ink/recode/ModuleManager.kt
package com.ink.recode

import java.util.concurrent.CopyOnWriteArrayList

object ModuleManager {
    // 全局模块列表（线程安全的 List，避免并发修改问题）
    val modules: CopyOnWriteArrayList<Module> = CopyOnWriteArrayList()

    // 注册模块的方法
    fun register(module: Module) {
        modules.add(module)
    }

    // 卸载模块的方法
    fun unregister(module: Module) {
        modules.remove(module)
    }

    // 可选：根据名称查找模块
    fun getModule(name: String): Module? {
        return modules.find { it.name.equals(name, ignoreCase = true) }
    }

}