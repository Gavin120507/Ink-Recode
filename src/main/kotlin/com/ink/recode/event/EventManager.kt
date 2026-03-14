package com.ink.recode.event

import com.ink.recode.Module
import com.ink.recode.ModuleManager
import com.ink.recode.event.Listener
import com.ink.recode.event.events.*
import com.ink.recode.notification.NotificationManager

object EventManager {
    fun init() {
        EventBus.register(EventManager)
        
        // 打印事件注册表信息
        println("[EventManager] Initializing event system...")
        println("[EventManager] Registered ${EventRegistry.getEventCount()} events")
    }
    
    @Listener
    fun onTick(event: TickEvent) {
        ModuleManager.modules
            .filter { it.enabled }
            .filter { it.handleEvents() }
            .forEach { it.onTick() }
    }
    
    @Listener
    fun onKey(event: KeyboardEvent) {
        ModuleManager.modules.forEach { module ->
            if (module.key == event.key) {
                module.toggle()
            }
        }
    }
    
    @Listener
    fun onRender2D(event: Render2DEvent) {
        // 渲染通知
        NotificationManager.renderNotifications(event)
    }
}