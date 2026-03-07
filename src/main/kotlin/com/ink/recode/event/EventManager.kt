package com.ink.recode.event

import com.ink.recode.event.events.TickEvent
import com.ink.recode.modules.*
import com.ink.recode.Module
import com.ink.recode.ModuleManager
import com.ink.recode.event.Listener
import com.ink.recode.event.events.KeyboardEvent

object EventManager {
    fun init()
    {
        EventBus.register(EventManager)
    }
    @Listener
    fun onTick(event: TickEvent)
    {
        ModuleManager.modules
            .filter{it.enabled}
            .forEach{it.onTick()}
    }
    @Listener
    fun onKey(event: KeyboardEvent)
    {
        System.out.println("[DEBUG] 收到按键：" + event.key)
        System.out.println("[DEBUG] 模块数量：" + ModuleManager.modules.size)
        ModuleManager.modules.forEach { module ->
            System.out.println("[DEBUG] 模块：" + module.name + " 按键：" + module.key)
            if(module.key == event.key)
            {
                System.out.println("[DEBUG] 匹配！切换 " + module.name)
                module.toggle()
            }
        }
    }
}