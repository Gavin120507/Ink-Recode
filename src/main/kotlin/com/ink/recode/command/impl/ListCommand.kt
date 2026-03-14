package com.ink.recode.command.impl

import com.ink.recode.command.Command
import com.ink.recode.ModuleManager

/**
 * .list - 显示模块列表
 */
class ListCommand : Command("list", "显示模块列表", arrayOf("modules", "mods")) {
    override fun execute(args: Array<String>) {
        val filter = args.getOrNull(0)?.lowercase()
        
        sendMessage("§8========== §eModule List §8==========")
        
        val modules = if (filter != null) {
            ModuleManager.modules.filter { it.name.lowercase().contains(filter) }
        } else {
            ModuleManager.modules
        }
        
        modules.forEach { module ->
            val state = if (module.enabled) "§a[ON]" else "§7[OFF]"
            val bind = if (module.key != -1) " §8(${getKey_name(module.key)})" else ""
            sendMessage("$state §e${module.name}§7 - ${module.description}${bind}")
        }
        
        sendMessage("§8==========================================")
        sendMessage("§7Total: ${modules.size}/${ModuleManager.modules.size} modules")
    }
    
    private fun getKey_name(key: Int): String {
        return when (key) {
            -1 -> "None"
            in 0..255 -> {
                // 简单映射，实际应该使用 GLFW 的按键名称函数
                when {
                    key >= 65 && key <= 90 -> ((key - 65) + 'A'.code).toChar().toString()
                    key >= 48 && key <= 57 -> ((key - 48) + '0'.code).toChar().toString()
                    key == 32 -> "SPACE"
                    key == 341 -> "LCTRL"
                    key == 345 -> "RCTRL"
                    key == 340 -> "LSHIFT"
                    key == 344 -> "RSHIFT"
                    else -> "KEY_$key"
                }
            }
            else -> "KEY_$key"
        }
    }
}
