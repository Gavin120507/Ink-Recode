package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.ColorValue
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.utils.GUIUtil
import org.jetbrains.skija.Canvas
import java.awt.Color

/**
 * 颜色选择配置组件
 */
class ColorValueComponent(
    value: ColorValue,
    x: Double,
    y: Double,
    width: Double
) : ValueComponent<ColorValue>(value, x, y, width) {
    
    private val colorValue = value
    
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    
    // 状态
    private var isPickerOpen = false
    private var pickerX = 0.0
    private var pickerY = 0.0
    
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
            colorValue.name,
            componentX + 8,
            componentY + 5,
            14.0f,
            Color(200, 200, 200).rgb
        )
        
        // 颜色预览
        val currentColor = colorValue.get()
        val colorX = componentX + width.toFloat() - 40
        val colorSize = 16.0f
        
        Render2DUtils.drawRoundedRect(
            canvas,
            colorX, componentY + 2,
            colorSize, colorSize,
            4.0f,
            currentColor
        )
        
        // 边框
        Render2DUtils.drawRoundedOutline(
            canvas,
            colorX, componentY + 2,
            colorSize, colorSize,
            4.0f,
            1.5f,
            Color(80, 80, 80)
        )
        
        // 如果颜色选择器打开，绘制选择器
        if (isPickerOpen) {
            drawColorPicker(canvas)
        }
    }
    
    /**
     * 绘制颜色选择器
     */
    private fun drawColorPicker(canvas: Canvas) {
        val pickerWidth = 200.0f
        val pickerHeight = 150.0f
        
        // 选择器背景
        Render2DUtils.drawRoundedRectWithShadow(
            canvas,
            pickerX.toFloat(), pickerY.toFloat(),
            pickerWidth, pickerHeight,
            8.0f,
            Color(25, 28, 35, 240),
            10.0f
        )
        
        // 颜色渐变区域
        drawColorGradient(canvas, pickerX.toFloat() + 10, pickerY.toFloat() + 10, 150.0f, 100.0f)
        
        // 透明度滑块
        drawAlphaSlider(canvas, pickerX.toFloat() + 10, pickerY.toFloat() + 120, 150.0f)
        
        // RGB 滑块
        drawRGBSliders(canvas, pickerX.toFloat() + 10, pickerY.toFloat() + 145)
        
        // 当前颜色预览
        val previewSize = 30.0f
        Render2DUtils.drawRoundedRect(
            canvas,
            pickerX.toFloat() + pickerWidth - 50,
            pickerY.toFloat() + 10,
            previewSize, previewSize,
            5.0f,
            colorValue.get()
        )
        
        // 关闭按钮
        drawCloseButton(canvas, pickerX.toFloat() + pickerWidth - 20, pickerY.toFloat() + 5)
    }
    
    /**
     * 绘制颜色渐变区域
     */
    private fun drawColorGradient(canvas: Canvas, x: Float, y: Float, width: Float, height: Float) {
        // 简化实现：绘制白色到当前颜色的渐变
        val baseColor = colorValue.get()
        
        Render2DUtils.drawRoundedGradientRect(
            canvas,
            x, y, width, height,
            4.0f,
            Color.WHITE,
            baseColor,
            true
        )
        
        // 黑色透明度渐变
        Render2DUtils.drawRoundedGradientRect(
            canvas,
            x, y, width, height,
            4.0f,
            Color(0, 0, 0, 0),
            Color(0, 0, 0, 255),
            false
        )
    }
    
    /**
     * 绘制透明度滑块
     */
    private fun drawAlphaSlider(canvas: Canvas, x: Float, y: Float, width: Float) {
        val baseColor = colorValue.get()
        
        // 背景
        Render2DUtils.drawRoundedRect(
            canvas,
            x, y, width, 10.0f,
            5.0f,
            Color(40, 43, 50)
        )
        
        // 渐变
        Render2DUtils.drawRoundedGradientRect(
            canvas,
            x + 2, y + 2, width - 4, 6.0f,
            3.0f,
            Color(baseColor.red, baseColor.green, baseColor.blue, 0),
            baseColor,
            false
        )
    }
    
    /**
     * 绘制 RGB 滑块
     */
    private fun drawRGBSliders(canvas: Canvas, x: Float, y: Float) {
        val baseColor = colorValue.get()
        val sliderWidth = 120.0f
        
        // R 滑块
        drawSingleSlider(canvas, x, y, sliderWidth, Color.RED, baseColor.red, "R")
        
        // G 滑块
        drawSingleSlider(canvas, x + sliderWidth + 10, y, sliderWidth, Color.GREEN, baseColor.green, "G")
        
        // B 滑块
        drawSingleSlider(canvas, x + sliderWidth * 2 + 20, y, sliderWidth, Color.BLUE, baseColor.blue, "B")
    }
    
    /**
     * 绘制单个滑块
     */
    private fun drawSingleSlider(canvas: Canvas, x: Float, y: Float, width: Float, color: Color, value: Int, label: String) {
        // 标签
        Render2DUtils.drawText(
            canvas,
            label,
            x, y - 8,
            10.0f,
            Color(150, 150, 150).rgb
        )
        
        // 背景
        Render2DUtils.drawRoundedRect(
            canvas,
            x, y, width, 8.0f,
            4.0f,
            Color(40, 43, 50)
        )
        
        // 进度
        val progress = value / 255.0f
        Render2DUtils.drawRoundedRect(
            canvas,
            x, y, width * progress, 8.0f,
            4.0f,
            color
        )
    }
    
    /**
     * 绘制关闭按钮
     */
    private fun drawCloseButton(canvas: Canvas, x: Float, y: Float) {
        Render2DUtils.drawRoundedRect(
            canvas,
            x, y,
            15.0f, 15.0f,
            4.0f,
            Color(200, 50, 50)
        )
        
        Render2DUtils.drawLine(
            canvas,
            x + 4, y + 4,
            x + 11, y + 11,
            Color.WHITE,
            2.0f
        )
        Render2DUtils.drawLine(
            canvas,
            x + 11, y + 4,
            x + 4, y + 11,
            Color.WHITE,
            2.0f
        )
    }
    
    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean {
        if (!isMouseOver(mouseX, mouseY, scrollOffset)) {
            // 检测颜色选择器内的点击
            if (isPickerOpen && GUIUtil.mouseOver(pickerX, pickerY, 200.0, 150.0, mouseX, mouseY)) {
                return true
            }
            return false
        }
        
        if (button == 0) {
            // 打开颜色选择器
            isPickerOpen = !isPickerOpen
            if (isPickerOpen) {
                pickerX = x + width - 210
                pickerY = y - scrollOffset + 25
            }
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
