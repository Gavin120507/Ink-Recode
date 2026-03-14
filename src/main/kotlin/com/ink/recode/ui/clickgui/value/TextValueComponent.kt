package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.TextValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.utils.GUIUtil
import org.jetbrains.skija.Canvas
import java.awt.Color

/**
 * 文本输入配置组件
 */
class TextValueComponent(
    value: TextValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<TextValue>(value, x, y, width) {
    
    private val textValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    private val focusAnimation = Animation(Easing::easeOutExpo, 100)
    
    // 状态
    private var isFocused = false
    private var cursorBlink = false
    private var lastBlinkTime = 0L
    
    override val height: Double = 20.0
    
    override fun draw(canvas: Canvas, scrollOffset: Double) {
        val renderY = y - scrollOffset
        
        // 如果不在可视范围，跳过
        if (renderY + height < 0 || renderY > canvas.localBounds.height) return
        
        // 更新动画
        hoverAnimation.run(if (isHovered) 30 else 0)
        focusAnimation.run(if (isFocused) 1.0 else 0.0)
        
        val hoverAlpha = hoverAnimation.getValueInt()
        val focusProgress = focusAnimation.getValue()
        
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
        
        // 聚焦边框
        if (focusProgress > 0.01) {
            Render2DUtils.drawRoundedOutline(
                canvas,
                componentX, componentY,
                width.toFloat(), height.toFloat(),
                4.0f,
                2.0f,
                Color(78, 163, 255, (150 * focusProgress).toInt())
            )
        }
        
        // 配置名称
        Render2DUtils.drawText(
            canvas,
            textValue.name,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 输入框
        val inputX = componentX + width.toFloat() - 120
        val inputWidth = 110.0f
        
        Render2DUtils.drawRoundedRect(
            canvas,
            inputX, componentY + 2,
            inputWidth, 16.0f,
            4.0f,
            Color(40, 43, 50)
        )
        
        // 文本内容
        val displayText = if (isFocused && cursorBlink) {
            textValue.get() + "_"
        } else {
            textValue.get()
        }
        
        Render2DUtils.drawText(
            canvas,
            displayText,
            inputX + 5,
            componentY + 5,
            12.0f,
            Color(200, 200, 200).rgb
        )
        
        // 更新光标闪烁
        updateCursorBlink()
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        val inputX = x + width - 120
        
        if (GUIUtil.mouseOver(inputX, y, 110.0, height, mouseX, mouseY) && button == 0) {
            isFocused = true
            return true
        } else {
            isFocused = false
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
        if (!isFocused) return false
        
        // 处理退格键
        if (keyCode == 259) { // GLFW_KEY_BACKSPACE
            val currentText = textValue.get()
            if (currentText.isNotEmpty()) {
                textValue.set(currentText.dropLast(1))
            }
            return true
        }
        
        return false
    }
    
    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        if (!isFocused) return false
        
        // 添加字符
        val currentText = textValue.get()
        if (currentText.length < 50) { // 限制长度
            textValue.set(currentText + codePoint)
        }
        return true
    }
    
    /**
     * 更新光标闪烁
     */
    private fun updateCursorBlink() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBlinkTime > 500) {
            cursorBlink = !cursorBlink
            lastBlinkTime = currentTime
        }
    }
}
