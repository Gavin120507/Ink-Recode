package com.ink.recode.command

import com.ink.recode.Module
import com.ink.recode.ModuleManager
import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

/**
 * 命令基类
 */
abstract class Command(
    val name: String,
    val description: String,
    val aliases: Array<String> = emptyArray()
) {
    val mc = MinecraftClient.getInstance()
    
    /**
     * 执行命令
     */
    abstract fun execute(args: Array<String>)
    
    /**
     * 获取用法
     */
    fun getUsage(): String {
        return ".${name} - ${description}"
    }
    
    /**
     * 发送消息到聊天栏
     */
    fun sendMessage(message: String) {
        mc.player?.sendMessage(Text.literal(message), false)
    }
    
    /**
     * 发送错误消息
     */
    fun sendError(message: String) {
        sendMessage("§c[Error] §7${message}")
    }
    
    /**
     * 发送成功消息
     */
    fun sendSuccess(message: String) {
        sendMessage("§a[Success] §7${message}")
    }
    
    /**
     * 显示帮助信息
     */
    fun showHelp() {
        sendMessage("§8========== §e${name} §8==========")
        sendMessage("§7${description}")
        sendMessage("§7Usage: ${getUsage()}")
        if (aliases.isNotEmpty()) {
            sendMessage("§7Aliases: §e${aliases.joinToString(", ")}")
        }
    }
}
