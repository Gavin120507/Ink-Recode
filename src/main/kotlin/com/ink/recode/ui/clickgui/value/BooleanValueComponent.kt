package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.BooleanValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.render.FontRenderer
import com.ink.recode.ui.utils.GUIUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * 布尔值配置组件
 */
class BooleanValueComponent(
    value: BooleanValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<BooleanValue>(value, x, y, width) {
    
    private val booleanValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    private val toggleAnimation = Animation(Easing::easeOutExpo, 150)
    
    override fun draw(matrices: MatrixStack, scrollOffset: Double) {
        val renderY = y - scrollOffset
        
        // 如果不在可视范围，跳过
        if (renderY + height < 0 || renderY > canvas.localBounds.height) return
        
        // 更新动画
        hoverAnimation.run(if (isHovered) 30 else 0)
        toggleAnimation.run(if (booleanValue.get()) 1.0 else 0.0)
        
        val hoverAlpha = hoverAnimation.getValueInt()
        val toggleProgress = toggleAnimation.getValue()
        
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
            booleanValue.name,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 开关按钮
        val switchX = componentX + width.toFloat() - 40
        val switchY = componentY + 2
        val switchWidth = 32.0f
        val switchHeight = 16.0f
        
        // 开关背景
        val switchBgColor = Color(
            50 + (50 * toggleProgress).toInt(),
            53 + (100 * toggleProgress).toInt(),
            60 + (100 * toggleProgress).toInt(),
            200
        )
        
        Render2DUtils.drawRoundedRect(
            canvas,
            switchX, switchY,
            switchWidth, switchHeight,
            8.0f,
            switchBgColor
        )
        
        // 开关滑块
        val sliderX = switchX + 2 + (switchWidth - 16) * toggleProgress.toFloat()
        val sliderColor = if (booleanValue.get()) {
            Color(78, 163, 255)
        } else {
            Color(100, 100, 100)
        }
        
        Render2DUtils.drawRoundedRect(
            canvas,
            sliderX, switchY,
            16.0f, 16.0f,
            8.0f,
            sliderColor
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        if (button == 0 && isMouseOver(mouseX, mouseY, scrollOffset)) {
            booleanValue.set(!booleanValue.get())
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
