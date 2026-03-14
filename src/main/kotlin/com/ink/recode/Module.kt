package com.ink.recode

import com.ink.recode.Category
import com.ink.recode.i18n.translation
import com.ink.recode.notification.NotificationManager
import com.ink.recode.value.Value
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.world.ClientWorld

open class Module(
    var name: String, 
    var description: String, 
    var category: Category,
    val aliases: Array<String> = emptyArray()
) {
    var enabled = false
        private set
    var key = -1
    val mc = MinecraftClient.getInstance()
    
    // Value 配置列表
    private val values = mutableListOf<Value<*>>()
    
    // 便捷访问（类似 LiquidBounce 的 QuickImports）
    val player: ClientPlayerEntity? get() = mc.player
    val world: ClientWorld? get() = mc.world
    val network: ClientPlayNetworkHandler? get() = mc.networkHandler
    
    // 翻译键
    open val translationBaseKey: String
        get() = "ink.recode.module.${name.lowercase()}"
    
    // 模块标签（用于 HUD 显示）
    open val tag: String? = null
    
    // 是否隐藏
    var hidden = false
    
    // 是否在退出时禁用
    var disableOnQuit = false
    
    /**
     * 获取翻译后的描述
     */
    fun getTranslatedDescription(): String {
        return translation("$translationBaseKey.description", description)
    }
    
    /**
     * 获取翻译后的名称
     */
    fun getTranslatedName(): String {
        return translation("$translationBaseKey.name", name)
    }
    
    open fun onTick() {}
    open fun onEnable() {}
    open fun onDisable() {}
    
    /**
     * 模块初始化（在注册后调用）
     */
    open fun init() {}
    
    fun enable() {
        if (enabled) return
        
        this.enabled = true
        onEnable()
        
        // 发送启用通知
        NotificationManager.enabled(name)
    }
    
    fun disable() {
        if (!enabled) return
        
        this.enabled = false
        onDisable()
        
        // 发送禁用通知
        NotificationManager.disabled(name)
    }
    
    fun toggle() {
        if (enabled) {
            disable()
        } else {
            enable()
        }
    }
    
    /**
     * 检查是否应该处理事件
     */
    open fun handleEvents(): Boolean {
        return enabled && player != null && world != null
    }
    
    /**
     * 添加 Value 配置
     */
    fun addValue(value: Value<*>) {
        values.add(value)
    }
    
    /**
     * 获取所有 Value 配置
     */
    fun getValues(): List<Value<*>> {
        return values
    }
    
    /**
     * 根据名称获取 Value
     */
    fun <T : Value<*>> getValue(name: String): T? {
        @Suppress("UNCHECKED_CAST")
        return values.find { it.name == name } as? T
    }
    
    /**
     * 获取 HUD 标签
     */
    fun getHudTag(): String {
        return tag ?: ""
    }
}