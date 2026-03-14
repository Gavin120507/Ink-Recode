package com.ink.recode.command

import net.fabricmc.fabric.api.client.message.v1.ClientChatEvents
import com.ink.recode.command.CommandManager

/**
 * 命令系统初始化
 */
object CommandSystem {
    fun init() {
        println("[CommandSystem] Initializing command system...")
        
        // 注册内置命令
        CommandManager.register(
            com.ink.recode.command.impl.HelpCommand(),
            com.ink.recode.command.impl.BindCommand(),
            com.ink.recode.command.impl.ToggleCommand(),
            com.ink.recode.command.impl.ConfigCommand(),
            com.ink.recode.command.impl.ListCommand()
        )
        
        // 注册聊天事件监听器
        ClientChatEvents.MODIFY_CLIENT_CHAT.register { message ->
            if (message.startsWith(".")) {
                // 执行命令
                CommandManager.executeCommand(message)
                // 取消发送聊天消息
                return@register null
            }
            message
        }
        
        println("[CommandSystem] Command system initialized with ${CommandManager.getCommands().size} commands")
    }
}
