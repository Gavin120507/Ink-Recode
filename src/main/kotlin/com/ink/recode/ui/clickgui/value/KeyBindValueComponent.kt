package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.KeyBindValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.utils.GUIUtil
import org.jetbrains.skija.Canvas
import org.lwjgl.glfw.GLFW
import java.awt.Color

/**
 * 按键绑定配置组件
 */
class KeyBindValueComponent(
    value: KeyBindValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<KeyBindValue>(value, x, y, width) {
    
    private val keyBindValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    
    // 状态
    private var isListening = false
    
    override val height: Double = 20.0
    
    override fun draw(canvas: Canvas, scrollOffset: Double) {
        val renderY = y - scrollOffset
        
        // 如果不在可视范围，跳过
        if (renderY + height < 0 || renderY > canvas.localBounds.height) return
        
        // 更新动画
        hoverAnimation.run(if (isHovered || isListening) 30 else 0)
        val hoverAlpha = hoverAnimation.getValueInt()
        
        // 组件位置
        val componentX = x.toFloat()
        val componentY = renderY.toFloat()
        
        // 背景
        Render2DUtils.drawRoundedRect(
            canvas,
            componentX, componentY,
            width.toFloat(), height.toFloat(),
            4.0f,
            Color(30, 33, 40)
        )
        
        // 悬停效果
        if (hoverAlpha > 0) {
            Render2DUtils.drawRoundedRect(
                canvas,
                componentX, componentY,
                width.toFloat(), height.toFloat(),
                4.0f,
                Color(255, 255, 255, hoverAlpha)
            )
        }
        
        // 配置名称
        Render2DUtils.drawText(
            canvas,
            keyBindValue.name,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 按键显示
        val keyName = if (isListening) {
            "Press a key..."
        } else {
            getKeyName(keyBindValue.get())
        }
        
        val keyX = componentX + width.toFloat() - 80
        val keyWidth = 70.0f
        
        // 按键背景
        Render2DUtils.drawRoundedRect(
            canvas,
            keyX, componentY + 2,
            keyWidth, 16.0f,
            4.0f,
            if (isListening) Color(100, 100, 255) else Color(40, 43, 50)
        )
        
        // 按键文字
        Render2DUtils.drawText(
            canvas,
            keyName,
            keyX + (keyWidth - Render2DUtils.getTextWidth(keyName, 12.0f)) / 2,
            componentY + 5,
            12.0f,
            if (isListening) Color.WHITE.rgb else Color(150, 150, 150).rgb
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        if (!isMouseOver(mouseX, mouseY, scrollOffset)) return false
        
        if (button == 0) {
            isListening = !isListening
            return true
        } else if (button == 1 && !isListening) {
            // 右键清除绑定
            keyBindValue.set(-1)
            return true
        }
        
        return false
    }
    
    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        isDragging = false
    }
    
    override fun mouseMoved(mouseX: Double, mouseY: Double, scrollOffset: Double) {
        isHovered = isMouseOver(mouseX, mouseY, scrollOffset)
    }
    
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (!isListening) return false
        
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            // ESC 取消
            isListening = false
        } else {
            // 设置按键
            keyBindValue.set(keyCode)
            isListening = false
        }
        
        return true
    }
    
    override fun charTyped(codePoint: Char, modifiers: Int): Boolean = false
    
    /**
     * 获取按键名称
     */
    private fun getKeyName(keyCode: Int): String {
        return when (keyCode) {
            -1 -> "None"
            GLFW.GLFW_KEY_SPACE -> "Space"
            GLFW.GLFW_KEY_APOSTROPHE -> "'"
            GLFW.GLFW_KEY_COMMA -> ","
            GLFW.GLFW_KEY_MINUS -> "-"
            GLFW.GLFW_KEY_PERIOD -> "."
            GLFW.GLFW_KEY_SLASH -> "/"
            GLFW.GLFW_KEY_0 -> "0"
            GLFW.GLFW_KEY_1 -> "1"
            GLFW.GLFW_KEY_2 -> "2"
            GLFW.GLFW_KEY_3 -> "3"
            GLFW.GLFW_KEY_4 -> "4"
            GLFW.GLFW_KEY_5 -> "5"
            GLFW.GLFW_KEY_6 -> "6"
            GLFW.GLFW_KEY_7 -> "7"
            GLFW.GLFW_KEY_8 -> "8"
            GLFW.GLFW_KEY_9 -> "9"
            GLFW.GLFW_KEY_SEMICOLON -> ";"
            GLFW.GLFW_KEY_EQUAL -> "="
            GLFW.GLFW_KEY_A -> "A"
            GLFW.GLFW_KEY_B -> "B"
            GLFW.GLFW_KEY_C -> "C"
            GLFW.GLFW_KEY_D -> "D"
            GLFW.GLFW_KEY_E -> "E"
            GLFW.GLFW_KEY_F -> "F"
            GLFW.GLFW_KEY_G -> "G"
            GLFW.GLFW_KEY_H -> "H"
            GLFW.GLFW_KEY_I -> "I"
            GLFW.GLFW_KEY_J -> "J"
            GLFW.GLFW_KEY_K -> "K"
            GLFW.GLFW_KEY_L -> "L"
            GLFW.GLFW_KEY_M -> "M"
            GLFW.GLFW_KEY_N -> "N"
            GLFW.GLFW_KEY_O -> "O"
            GLFW.GLFW_KEY_P -> "P"
            GLFW.GLFW_KEY_Q -> "Q"
            GLFW.GLFW_KEY_R -> "R"
            GLFW.GLFW_KEY_S -> "S"
            GLFW.GLFW_KEY_T -> "T"
            GLFW.GLFW_KEY_U -> "U"
            GLFW.GLFW_KEY_V -> "V"
            GLFW.GLFW_KEY_W -> "W"
            GLFW.GLFW_KEY_X -> "X"
            GLFW.GLFW_KEY_Y -> "Y"
            GLFW.GLFW_KEY_Z -> "Z"
            GLFW.GLFW_KEY_LEFT_BRACKET -> "["
            GLFW.GLFW_KEY_BACKSLASH -> "\\"
            GLFW.GLFW_KEY_RIGHT_BRACKET -> "]"
            GLFW.GLFW_KEY_GRAVE_ACCENT -> "`"
            GLFW.GLFW_KEY_ESCAPE -> "ESC"
            GLFW.GLFW_KEY_ENTER -> "Enter"
            GLFW.GLFW_KEY_TAB -> "Tab"
            GLFW.GLFW_KEY_BACKSPACE -> "Backspace"
            GLFW.GLFW_KEY_INSERT -> "Insert"
            GLFW.GLFW_KEY_DELETE -> "Delete"
            GLFW.GLFW_KEY_RIGHT -> "Right"
            GLFW.GLFW_KEY_LEFT -> "Left"
            GLFW.GLFW_KEY_DOWN -> "Down"
            GLFW.GLFW_KEY_UP -> "Up"
            GLFW.GLFW_KEY_PAGE_UP -> "Page Up"
            GLFW.GLFW_KEY_PAGE_DOWN -> "Page Down"
            GLFW.GLFW_KEY_HOME -> "Home"
            GLFW.GLFW_KEY_END -> "End"
            GLFW.GLFW_KEY_CAPS_LOCK -> "Caps Lock"
            GLFW.GLFW_KEY_SCROLL_LOCK -> "Scroll Lock"
            GLFW.GLFW_KEY_NUM_LOCK -> "Num Lock"
            GLFW.GLFW_KEY_PRINT_SCREEN -> "Print Screen"
            GLFW.GLFW_KEY_PAUSE -> "Pause"
            GLFW.GLFW_KEY_F1 -> "F1"
            GLFW.GLFW_KEY_F2 -> "F2"
            GLFW.GLFW_KEY_F3 -> "F3"
            GLFW.GLFW_KEY_F4 -> "F4"
            GLFW.GLFW_KEY_F5 -> "F5"
            GLFW.GLFW_KEY_F6 -> "F6"
            GLFW.GLFW_KEY_F7 -> "F7"
            GLFW.GLFW_KEY_F8 -> "F8"
            GLFW.GLFW_KEY_F9 -> "F9"
            GLFW.GLFW_KEY_F10 -> "F10"
            GLFW.GLFW_KEY_F11 -> "F11"
            GLFW.GLFW_KEY_F12 -> "F12"
            GLFW.GLFW_KEY_LEFT_SHIFT -> "L-Shift"
            GLFW.GLFW_KEY_RIGHT_SHIFT -> "R-Shift"
            GLFW.GLFW_KEY_LEFT_CONTROL -> "L-Ctrl"
            GLFW.GLFW_KEY_RIGHT_CONTROL -> "R-Ctrl"
            GLFW.GLFW_KEY_LEFT_ALT -> "L-Alt"
            GLFW.GLFW_KEY_RIGHT_ALT -> "R-Alt"
            else -> "Key $keyCode"
        }
    }
}
