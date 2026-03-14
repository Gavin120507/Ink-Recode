package com.ink.recode.command.impl

import com.ink.recode.command.Command
import com.ink.recode.ModuleManager
import org.lwjgl.glfw.GLFW

/**
 * .bind - 绑定模块到按键
 */
class BindCommand : Command("bind", "绑定模块到按键", arrayOf("b")) {
    override fun execute(args: Array<String>) {
        if (args.size < 2) {
            sendError("Usage: .bind <module> <key>")
            sendError("Example: .bind Sprint I")
            sendError("Use .bind clear <module> to clear bind")
            return
        }
        
        val moduleName = args[0]
        val action = args[1]
        
        val module = ModuleManager.getModule(moduleName)
        
        if (module == null) {
            sendError("Module not found: $moduleName")
            return
        }
        
        if (action.equals("clear", ignoreCase = true)) {
            module.key = -1
            sendSuccess("Cleared bind for ${module.name}")
            return
        }
        
        // 解析按键名称
        val key = parseKey(action)
        
        if (key == -1) {
            sendError("Unknown key: $action")
            sendError("Use key names like: I, K, L, RCONTROL, etc.")
            return
        }
        
        module.key = key
        sendSuccess("Bound ${module.name} to §e${action}§7")
    }
    
    private fun parseKey(keyName: String): Int {
        // 尝试解析数字
        keyName.toIntOrNull()?.let { return it }
        
        // 解析按键名称
        return when (keyName.uppercase()) {
            "A" -> GLFW.GLFW_KEY_A
            "B" -> GLFW.GLFW_KEY_B
            "C" -> GLFW.GLFW_KEY_C
            "D" -> GLFW.GLFW_KEY_D
            "E" -> GLFW.GLFW_KEY_E
            "F" -> GLFW.GLFW_KEY_F
            "G" -> GLFW.GLFW_KEY_G
            "H" -> GLFW.GLFW_KEY_H
            "I" -> GLFW.GLFW_KEY_I
            "J" -> GLFW.GLFW_KEY_J
            "K" -> GLFW.GLFW_KEY_K
            "L" -> GLFW.GLFW_KEY_L
            "M" -> GLFW.GLFW_KEY_M
            "N" -> GLFW.GLFW_KEY_N
            "O" -> GLFW.GLFW_KEY_O
            "P" -> GLFW.GLFW_KEY_P
            "Q" -> GLFW.GLFW_KEY_Q
            "R" -> GLFW.GLFW_KEY_R
            "S" -> GLFW.GLFW_KEY_S
            "T" -> GLFW.GLFW_KEY_T
            "U" -> GLFW.GLFW_KEY_U
            "V" -> GLFW.GLFW_KEY_V
            "W" -> GLFW.GLFW_KEY_W
            "X" -> GLFW.GLFW_KEY_X
            "Y" -> GLFW.GLFW_KEY_Y
            "Z" -> GLFW.GLFW_KEY_Z
            "0" -> GLFW.GLFW_KEY_0
            "1" -> GLFW.GLFW_KEY_1
            "2" -> GLFW.GLFW_KEY_2
            "3" -> GLFW.GLFW_KEY_3
            "4" -> GLFW.GLFW_KEY_4
            "5" -> GLFW.GLFW_KEY_5
            "6" -> GLFW.GLFW_KEY_6
            "7" -> GLFW.GLFW_KEY_7
            "8" -> GLFW.GLFW_KEY_8
            "9" -> GLFW.GLFW_KEY_9
            "SPACE" -> GLFW.GLFW_KEY_SPACE
            "RCONTROL" -> GLFW.GLFW_KEY_RIGHT_CONTROL
            "LCONTROL" -> GLFW.GLFW_KEY_LEFT_CONTROL
            "RSHIFT" -> GLFW.GLFW_KEY_RIGHT_SHIFT
            "LSHIFT" -> GLFW.GLFW_KEY_LEFT_SHIFT
            "INSERT" -> GLFW.GLFW_KEY_INSERT
            "DELETE" -> GLFW.GLFW_KEY_DELETE
            "HOME" -> GLFW.GLFW_KEY_HOME
            "END" -> GLFW.GLFW_KEY_END
            "PAGE_UP" -> GLFW.GLFW_KEY_PAGE_UP
            "PAGE_DOWN" -> GLFW.GLFW_KEY_PAGE_DOWN
            "UP" -> GLFW.GLFW_KEY_UP
            "DOWN" -> GLFW.GLFW_KEY_DOWN
            "LEFT" -> GLFW.GLFW_KEY_LEFT
            "RIGHT" -> GLFW.GLFW_KEY_RIGHT
            "F1" -> GLFW.GLFW_KEY_F1
            "F2" -> GLFW.GLFW_KEY_F2
            "F3" -> GLFW.GLFW_KEY_F3
            "F4" -> GLFW.GLFW_KEY_F4
            "F5" -> GLFW.GLFW_KEY_F5
            "F6" -> GLFW.GLFW_KEY_F6
            "F7" -> GLFW.GLFW_KEY_F7
            "F8" -> GLFW.GLFW_KEY_F8
            "F9" -> GLFW.GLFW_KEY_F9
            "F10" -> GLFW.GLFW_KEY_F10
            "F11" -> GLFW.GLFW_KEY_F11
            "F12" -> GLFW.GLFW_KEY_F12
            "SEMICOLON" -> GLFW.GLFW_KEY_SEMICOLON
            "APOSTROPHE" -> GLFW.GLFW_KEY_APOSTROPHE
            "COMMA" -> GLFW.GLFW_KEY_COMMA
            "PERIOD" -> GLFW.GLFW_KEY_PERIOD
            "SLASH" -> GLFW.GLFW_KEY_SLASH
            "BACKSLASH" -> GLFW.GLFW_KEY_BACKSLASH
            "MINUS" -> GLFW.GLFW_KEY_MINUS
            "EQUAL" -> GLFW.GLFW_KEY_EQUAL
            "GRAVE_ACCENT" -> GLFW.GLFW_KEY_GRAVE_ACCENT
            "ESCAPE" -> GLFW.GLFW_KEY_ESCAPE
            "ENTER" -> GLFW.GLFW_KEY_ENTER
            "TAB" -> GLFW.GLFW_KEY_TAB
            "BACKSPACE" -> GLFW.GLFW_KEY_BACKSPACE
            else -> -1
        }
    }
}
