package com.ink.recode.command.impl

import com.ink.recode.command.Command
import com.ink.recode.ModuleManager
import com.ink.recode.config.ConfigSystem

/**
 * .config - 配置管理
 */
class ConfigCommand : Command("config", "配置管理", arrayOf("cfg")) {
    override fun execute(args: Array<String>) {
        if (args.isEmpty()) {
            showHelp()
            return
        }
        
        val action = args[0].lowercase()
        
        when (action) {
            "save" -> {
                ConfigSystem.saveAll()
                sendSuccess("Configuration saved")
            }
            "load" -> {
                ConfigSystem.loadAll()
                sendSuccess("Configuration loaded")
            }
            "reset" -> {
                ConfigSystem.reset()
                sendSuccess("Configuration reset")
            }
            else -> {
                sendError("Unknown action: $action")
                showHelp()
            }
        }
    }
    
    override fun showHelp() {
        sendMessage("§8========== §eConfig §8==========")
        sendMessage("§7.config save - 保存配置")
        sendMessage("§7.config load - 加载配置")
        sendMessage("§7.config reset - 重置配置")
    }
}
