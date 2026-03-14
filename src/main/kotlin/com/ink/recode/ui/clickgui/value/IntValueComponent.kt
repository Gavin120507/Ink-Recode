package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.IntValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.utils.GUIUtil
import org.jetbrains.skija.Canvas
import java.awt.Color

/**
 * 整数值配置组件
 */
class IntValueComponent(
    value: IntValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<IntValue>(value, x, y, width) {
    
    private val intValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    
    override val height: Double = 25.0
    
    override fun draw(canvas: Canvas, scrollOffset: Double) {
        val renderY = y - scrollOffset
        
        // 如果不在可视范围，跳过
        if (renderY + height < 0 || renderY > canvas.localBounds.height) return
        
        // 更新动画
        hoverAnimation.run(if (isHovered) 30 else 0)
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
        
        // 配置名称和值
        val valueText = intValue.get().toString()
        val displayText = "${intValue.name}: §7$valueText"
        
        Render2DUtils.drawText(
            canvas,
            displayText,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 滑块条
        val sliderY = componentY + 20
        val sliderHeight = 3.0f
        val sliderX = componentX + 8
        val sliderWidth = width.toFloat() - 16
        
        // 背景条
        Render2DUtils.drawRoundedRect(
            canvas,
            sliderX, sliderY,
            sliderWidth, sliderHeight,
            1.5f,
            Color(50, 53, 60)
        )
        
        // 进度条
        val range = intValue.range
        val progress = (intValue.get() - range.start) / (range.endInclusive - range.start)
        val filledWidth = sliderWidth * progress
        
        Render2DUtils.drawRoundedRect(
            canvas,
            sliderX, sliderY,
            filledWidth, sliderHeight,
            1.5f,
            Color(78, 163, 255)
        )
        
        // 滑块手柄
        val grabberX = sliderX + filledWidth - 5
        val grabberSize = 10.0f
        
        Render2DUtils.drawRoundedRect(
            canvas,
            grabberX, sliderY - 3.5f,
            grabberSize, grabberSize,
            5.0f,
            Color(78, 163, 255)
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        if (button == 0 && isMouseOver(mouseX, mouseY, scrollOffset)) {
            isDragging = true
            updateValueFromMouse(mouseX, scrollOffset)
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
    
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean = false
    
    override fun charTyped(codePoint: Char, modifiers: Int): Boolean = false
    
    /**
     * 从鼠标位置更新值
     */
    private fun updateValueFromMouse(mouseX: Double, scrollOffset: Double) {
        val sliderX = x + 8
        val sliderWidth = width - 16
        val range = intValue.range
        
        val relativeX = (mouseX - sliderX).coerceIn(0.0, sliderWidth)
        val progress = relativeX / sliderWidth
        
        val newValue = (range.start + (range.endInclusive - range.start) * progress).toInt()
        intValue.set(newValue.coerceIn(range.start, range.endInclusive))
    }
}
