package com.ink.recode.command

import com.ink.recode.ModuleManager
import net.minecraft.client.MinecraftClient

/**
 * 命令管理器
 */
object CommandManager {
    private val commands = mutableListOf<Command>()
    private val mc = MinecraftClient.getInstance()
    
    /**
     * 注册命令
     */
    fun register(command: Command) {
        commands.add(command)
        println("[CommandManager] Registered command: ${command.name}")
    }
    
    /**
     * 注册多个命令
     */
    fun register(vararg commands: Command) {
        commands.forEach { register(it) }
    }
    
    /**
     * 执行命令
     */
    fun executeCommand(input: String): Boolean {
        if (!input.startsWith(".")) {
            return false
        }
        
        val args = input.substring(1).split(" ").toTypedArray()
        if (args.isEmpty()) {
            return false
        }
        
        val commandName = args[0]
        val commandArgs = args.drop(1).toTypedArray()
        
        // 查找命令
        val command = findCommand(commandName)
        
        if (command == null) {
            sendMessage("§cUnknown command. Type .help for help.")
            return true
        }
        
        // 执行命令
        try {
            command.execute(commandArgs)
        } catch (e: Exception) {
            command.sendError("Failed to execute command: ${e.message}")
            e.printStackTrace()
        }
        
        return true
    }
    
    /**
     * 根据名称查找命令
     */
    private fun findCommand(name: String): Command? {
        return commands.find {
            it.name.equals(name, ignoreCase = true) ||
            it.aliases.any { alias -> alias.equals(name, ignoreCase = true) }
        }
    }
    
    /**
     * 获取所有命令
     */
    fun getCommands(): List<Command> {
        return commands
    }
    
    /**
     * 自动补全
     */
    fun autoComplete(input: String): List<String> {
        if (!input.startsWith(".")) {
            return emptyList()
        }
        
        val partialName = input.substring(1).lowercase()
        return commands.filter {
            it.name.lowercase().startsWith(partialName) ||
            it.aliases.any { alias -> alias.lowercase().startsWith(partialName) }
        }.map { ".${it.name}" }
    }
    
    /**
     * 发送消息到聊天栏
     */
    private fun sendMessage(message: String) {
        mc.player?.sendMessage(net.minecraft.text.Text.literal(message), false)
    }
}
