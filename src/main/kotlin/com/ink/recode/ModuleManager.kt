// 路径：src/main/kotlin/com/ink/recode/ModuleManager.kt
package com.ink.recode

import com.ink.recode.event.EventBus
import java.util.concurrent.CopyOnWriteArrayList

object ModuleManager {
    val modules: CopyOnWriteArrayList<Module> = CopyOnWriteArrayList()

    /**
     * 注册单个模块
     */
    fun register(module: Module) {
        modules.add(module)
        EventBus.register(module)
        println("[ModuleManager] Registered module: ${module.name}")
    }

    /**
     * 注册多个模块
     */
    fun register(vararg modules: Module) {
        modules.forEach { register(it) }
    }

    /**
     * 注册模块列表
     */
    fun register(modules: List<Module>) {
        modules.forEach { register(it) }
    }

    /**
     * 卸载模块
     */
    fun unregister(module: Module) {
        if (module.enabled) {
            module.disable()
        }
        modules.remove(module)
        println("[ModuleManager] Unregistered module: ${module.name}")
    }

    /**
     * 根据名称查找模块
     */
    fun getModule(name: String): Module? {
        return modules.find { it.name.equals(name, ignoreCase = true) }
    }

    /**
     * 根据分类获取模块列表
     */
    fun getModulesByCategory(category: Category): List<Module> {
        return modules.filter { it.category == category }
    }

    /**
     * 获取所有启用的模块
     */
    fun getEnabledModules(): List<Module> {
        return modules.filter { it.enabled }
    }

    /**
     * 获取所有禁用的模块
     */
    fun getDisabledModules(): List<Module> {
        return modules.filter { !it.enabled }
    }

    /**
     * 启用所有模块
     */
    fun enableAll() {
        modules.forEach { it.enable() }
    }

    /**
     * 禁用所有模块
     */
    fun disableAll() {
        modules.forEach { it.disable() }
    }

    /**
     * 切换所有模块
     */
    fun toggleAll() {
        modules.forEach { it.toggle() }
    }

    /**
     * 根据名称搜索模块
     */
    fun searchModules(query: String): List<Module> {
        val lowerQuery = query.lowercase()
        return modules.filter {
            it.name.lowercase().contains(lowerQuery) ||
            it.description.lowercase().contains(lowerQuery)
        }
    }

    /**
     * 获取模块数量
     */
    fun getModuleCount(): Int {
        return modules.size
    }

    /**
     * 获取启用模块数量
     */
    fun getEnabledCount(): Int {
        return modules.count { it.enabled }
    }

    /**
     * 清除所有模块
     */
    fun clear() {
        disableAll()
        modules.clear()
    }
}