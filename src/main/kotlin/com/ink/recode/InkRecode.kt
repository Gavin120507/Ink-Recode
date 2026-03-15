package com.ink.recode

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import org.slf4j.LoggerFactory
import com.ink.recode.event.*
import com.ink.recode.modules.impl.movement.Sprint
import com.ink.recode.modules.impl.render.ClickGUIModule
import com.ink.recode.config.ConfigSystem
import com.ink.recode.command.CommandSystem
import com.ink.recode.i18n.LanguageManager

object InkRecode : ModInitializer {
    private val logger = LoggerFactory.getLogger("ink-recode")

    override fun onInitialize() {
        logger.info("Initializing Ink-Recode Client...")
        
        // 初始化语言系统
        LanguageManager.init()
        
        // 初始化事件系统
        EventManager.init()
        
        // 初始化配置系统
        ConfigSystem.init()
        
        // 初始化命令系统
        CommandSystem.init()
        
        // 注册模块
        ModuleManager.register(Sprint)
        ModuleManager.register(ClickGUIModule())
        
        // 注册配置加载/保存事件
        ClientLifecycleEvents.CLIENT_STARTED.register {
            logger.info("Loading configuration...")
            ConfigSystem.loadAll()
        }
        
        ClientLifecycleEvents.CLIENT_STOPPING.register {
            logger.info("Saving configuration...")
            ConfigSystem.saveAll()
        }
        
        logger.info("Ink-Recode Client initialized with ${ModuleManager.modules.size} modules")
        logger.info("Current language: ${LanguageManager.currentLanguage}")
    }
}