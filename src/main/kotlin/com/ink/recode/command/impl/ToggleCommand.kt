package com.ink.recode.command.impl

import com.ink.recode.command.Command
import com.ink.recode.ModuleManager

/**
 * .toggle - 切换模块状态
 */
class ToggleCommand : Command("toggle", "切换模块状态", arrayOf("t")) {
    override fun execute(args: Array<String>) {
        if (args.isEmpty()) {
            sendError("Usage: .toggle <module>")
            sendError("Example: .toggle Sprint")
            return
        }
        
        val moduleName = args[0]
        val module = ModuleManager.getModule(moduleName)
        
        if (module == null) {
            sendError("Module not found: $moduleName")
            return
        }
        
        module.toggle()
        val state = if (module.enabled) "enabled" else "disabled"
        sendSuccess("${module.name} is now §e${state}§7")
    }
}
