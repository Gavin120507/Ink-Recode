package com.ink.recode.command.impl

import com.ink.recode.command.Command
import com.ink.recode.ModuleManager
import com.ink.recode.config.ConfigSystem

/**
 * .help - 显示帮助信息
 */
class HelpCommand : Command("help", "显示命令帮助", arrayOf("h")) {
    override fun execute(args: Array<String>) {
        sendMessage("§8========== §eInk-Recode Commands §8==========")
        
        CommandManager.getCommands().forEach { command ->
            sendMessage("§e.${command.name} §7- ${command.description}")
        }
        
        sendMessage("§8==========================================")
        sendMessage("§7Total: ${CommandManager.getCommands().size} commands")
    }
}
