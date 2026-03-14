package com.ink.recode.ui.clickgui

import com.ink.recode.Module
import com.ink.recode.value.*
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.render.FontRenderer
import com.ink.recode.ui.utils.GUIUtil
import com.ink.recode.ui.clickgui.value.*
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * 模块设置面板
 * 显示模块的所有配置值
 */
class SettingsPanel(
    private val module: Module,
    private val x: Double,
    private val y: Double,
    private val width: Double,
    private val height: Double
) {
    // 动画
    private val openAnimation = Animation(Easing::easeOutExpo, 200)
    
    // 配置组件
    private val valueComponents = mutableListOf<com.ink.recode.ui.clickgui.value.ValueComponent<*>>()
    
    // 滚动
    private var scrollOffset = 0.0
    private var maxScroll = 0.0
    
    // 状态
    private var isOpen = false
    
    init {
        initializeComponents()
        openAnimation.setValue(0.0)
        isOpen = true
        openAnimation.run(1.0)
    }
    
    /**
     * 初始化配置组件
     */
    private fun initializeComponents() {
        valueComponents.clear()
        
        var componentY = 20.0
        for (value in module.getValues()) {
            val component = when (value) {
                is BooleanValue -> BooleanValueComponent(value, x + 10, y + componentY, width - 20)
                is FloatValue -> FloatValueComponent(value, x + 10, y + componentY, width - 20)
                is IntValue -> IntValueComponent(value, x + 10, y + componentY, width - 20)
                is TextValue -> TextValueComponent(value, x + 10, y + componentY, width - 20)
                is ModeValue -> ModeValueComponent(value, x + 10, y + componentY, width - 20)
                is KeyBindValue -> KeyBindValueComponent(value, x + 10, y + componentY, width - 20)
                is ColorValue -> ColorValueComponent(value, x + 10, y + componentY, width - 20)
                else -> null
            }
            
            component?.let {
                valueComponents.add(it)
                componentY += it.height + 5
            }
        }
        
        maxScroll = (componentY - height).coerceAtLeast(0.0)
    }
    
    /**
     * 绘制设置面板
     */
    fun draw(matrices: MatrixStack) {
        // 更新动画
        openAnimation.run(if (isOpen) 1.0 else 0.0)
        val scale = openAnimation.getValue()
        
        if (scale < 0.01) return
        
        val panelX = x.toFloat()
        val panelY = y.toFloat()
        val panelWidth = width.toFloat()
        val panelHeight = height.toFloat()
        
        // 背景
        Render2DUtils.drawRoundedRectWithShadow(
            canvas,
            panelX, panelY,
            panelWidth, panelHeight,
            8.0f,
            Color(25, 28, 35, 240),
            10.0f
        )
        
        // 标题栏
        Render2DUtils.drawRoundedRect(
            canvas,
            panelX, panelY,
            panelWidth, 30.0f,
            8.0f,
            Color(35, 38, 45)
        )
        
        // 标题
        val moduleName = module.getTranslatedName()
        Render2DUtils.drawText(
            canvas,
            "$moduleName Settings",
            panelX + 10,
            panelY + 8,
            16.0f,
            Color.WHITE.rgb
        )
        
        // 关闭按钮
        drawCloseButton(canvas, panelX + panelWidth - 25, panelY + 7)
        
        // 应用裁剪
        GUIUtil.scissor(panelX.toDouble(), panelY.toDouble() + 30, panelWidth.toDouble(), panelHeight.toDouble() - 30)
        
        // 绘制配置组件
        for (component in valueComponents) {
            component.draw(canvas, scrollOffset)
        }
        
        // 恢复裁剪
        GUIUtil.stopScissor()
        
        // 滚动条
        drawScrollBar(canvas)
    }
    
    /**
     * 绘制关闭按钮
     */
    private fun drawCloseButton(canvas: Canvas, x: Float, y: Float) {
        Render2DUtils.drawRoundedRect(
            canvas,
            x, y,
            18.0f, 18.0f,
            4.0f,
            Color(200, 50, 50)
        )
        
        // X 符号
        Render2DUtils.drawLine(
            canvas,
            x + 5, y + 5,
            x + 13, y + 13,
            Color.WHITE,
            2.0f
        )
        Render2DUtils.drawLine(
            canvas,
            x + 13, y + 5,
            x + 5, y + 13,
            Color.WHITE,
            2.0f
        )
    }
    
    /**
     * 绘制滚动条
     */
    private fun drawScrollBar(canvas: Canvas) {
        if (maxScroll <= 0) return
        
        val scrollProgress = scrollOffset / maxScroll
        val barHeight = 50.0f
        val barY = (y + 35 + scrollProgress * (height - 40 - barHeight)).toFloat()
        
        Render2DUtils.drawRoundedRect(
            canvas,
            (x + width - 8).toFloat(), barY,
            4.0f, barHeight,
            2.0f,
            Color(80, 80, 80, 150)
        )
    }
    
    /**
     * 鼠标点击事件
     */
    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        // 检测关闭按钮
        val closeX = x + width - 25
        val closeY = y + 7
        
        if (GUIUtil.mouseOver(closeX, closeY, 18.0, 18.0, mouseX, mouseY) && button == 0) {
            close()
            return true
        }
        
        // 检测配置组件点击
        for (component in valueComponents) {
            if (component.mouseClicked(mouseX, mouseY, button, scrollOffset)) {
                return true
            }
        }
        
        return false
    }
    
    /**
     * 鼠标释放事件
     */
    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        for (component in valueComponents) {
            component.mouseReleased(mouseX, mouseY, button)
        }
    }
    
    /**
     * 鼠标移动事件
     */
    fun mouseMoved(mouseX: Double, mouseY: Double) {
        for (component in valueComponents) {
            component.mouseMoved(mouseX, mouseY, scrollOffset)
        }
    }
    
    /**
     * 键盘事件
     */
    fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        for (component in valueComponents) {
            if (component.keyPressed(keyCode, scanCode, modifiers)) {
                return true
            }
        }
        return false
    }
    
    /**
     * 字符输入事件
     */
    fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        for (component in valueComponents) {
            if (component.charTyped(codePoint, modifiers)) {
                return true
            }
        }
        return false
    }
    
    /**
     * 鼠标滚轮事件
     */
    fun mouseScrolled(amount: Double) {
        scrollOffset -= amount * 10
        scrollOffset = scrollOffset.coerceIn(0.0, maxScroll)
    }
    
    /**
     * 关闭面板
     */
    fun close() {
        isOpen = false
    }
    
    /**
     * 检查是否完成关闭动画
     */
    fun isClosed(): Boolean = !isOpen && openAnimation.isComplete()
}
