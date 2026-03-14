package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.ModeValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.utils.GUIUtil
import org.jetbrains.skija.Canvas
import java.awt.Color

/**
 * 模式选择配置组件
 */
class ModeValueComponent(
    value: ModeValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<ModeValue>(value, x, y, width) {
    
    private val modeValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    
    override val height: Double = 20.0
    
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
        
        // 配置名称
        Render2DUtils.drawText(
            canvas,
            modeValue.name,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 当前模式值
        val modeName = modeValue.get()
        val modeX = componentX + width.toFloat() - 80
        val modeWidth = 70.0f
        
        // 模式背景
        Render2DUtils.drawRoundedRect(
            canvas,
            modeX, componentY + 2,
            modeWidth, 16.0f,
            4.0f,
            Color(40, 43, 50)
        )
        
        // 模式文字
        Render2DUtils.drawText(
            canvas,
            modeName,
            modeX + (modeWidth - Render2DUtils.getTextWidth(modeName, 12.0f)) / 2,
            componentY + 5,
            12.0f,
            Color(100, 200, 255).rgb
        )
        
        // 左右箭头
        Render2DUtils.drawText(
            canvas,
            "◀",
            modeX - 15,
            componentY + 5,
            12.0f,
            Color(150, 150, 150).rgb
        )
        
        Render2DUtils.drawText(
            canvas,
            "▶",
            modeX + modeWidth + 3,
            componentY + 5,
            12.0f,
            Color(150, 150, 150).rgb
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        if (!isMouseOver(mouseX, mouseY, scrollOffset)) return false
        
        if (button == 0) {
            // 左键 - 下一个模式
            val currentIndex = modeValue.modes.indexOf(modeValue.get())
            val nextIndex = (currentIndex + 1) % modeValue.modes.size
            modeValue.set(modeValue.modes[nextIndex])
            return true
        } else if (button == 1) {
            // 右键 - 上一个模式
            val currentIndex = modeValue.modes.indexOf(modeValue.get())
            val prevIndex = if (currentIndex <= 0) modeValue.modes.size - 1 else currentIndex - 1
            modeValue.set(modeValue.modes[prevIndex])
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
}
