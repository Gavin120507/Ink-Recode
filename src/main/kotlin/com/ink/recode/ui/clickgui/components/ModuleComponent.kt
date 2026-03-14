package com.ink.recode.ui.clickgui

import com.ink.recode.Module
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.render.FontRenderer
import com.ink.recode.ui.utils.GUIUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * 模块组件
 * 显示单个模块的开关状态和名称
 */
class ModuleComponent(
    private val module: Module,
    private val x: Double,
    private val y: Double,
    private val width: Double,
    private val height: Double
) {
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    private val enableAnimation = Animation(Easing::easeOutExpo, 150)
    
    // 状态
    private var isHovered = false
    private var isMouseDown = false
    
    // 配置界面
    private var settingsPanel: SettingsPanel? = null
    private var isSettingsOpen = false
    
    /**
     * 绘制模块组件
     */
    fun draw(matrices: MatrixStack, accentColor: Color, scrollOffset: Double = 0.0) {
        val renderY = y - scrollOffset
        
        // 如果不在可视范围内，跳过
        if (renderY + height < 0 || renderY > 1000) return // 简化高度检测
        
        // 更新动画
        hoverAnimation.run(if (isHovered) if (isMouseDown) 50 else 30 else 0)
        enableAnimation.run(if (module.enabled) 1.0 else 0.0)
        
        val hoverAlpha = hoverAnimation.getValueInt()
        
        // 模块位置
        val moduleX = x.toFloat()
        val moduleY = renderY.toFloat()
        val moduleWidth = width.toFloat()
        val moduleHeight = height.toFloat()
        
        // 背景
        val bgColor = if (module.enabled) {
            Color(40, 43, 50, 200)
        } else {
            Color(30, 33, 40, 180)
        }
        
        Render2DUtils.drawRoundedRect(
            matrices,
            moduleX, moduleY,
            moduleWidth, moduleHeight,
            6.0f,
            bgColor
        )
        
        // 悬停覆盖层
        if (hoverAlpha > 0) {
            Render2DUtils.drawRoundedRect(
                matrices,
                moduleX, moduleY,
                moduleWidth, moduleHeight,
                6.0f,
                Color(0, 0, 0, hoverAlpha)
            )
        }
        
        // 启用指示器（左侧条）
        if (module.enabled) {
            val indicatorWidth = 3.0f
            val indicatorHeight = (moduleHeight * 0.6).toFloat()
            val indicatorY = moduleY + (moduleHeight - indicatorHeight) / 2
            
            Render2DUtils.drawRoundedRect(
                matrices,
                moduleX, indicatorY,
                indicatorWidth, indicatorHeight,
                1.5f,
                accentColor
            )
        }
        
        // 模块名称
        val moduleName = module.getTranslatedName()
        val nameColor = if (module.enabled) {
            accentColor.rgb
        } else {
            Color(180, 180, 180).rgb
        }
        
        FontRenderer.drawText(
            matrices,
            moduleName,
            moduleX + 12,
            moduleY + 12,
            nameColor,
            1.0f
        )
        
        // 模块描述（如果悬停）
        if (isHovered && !module.description.isNullOrEmpty()) {
            drawTooltip(matrices, module.description, moduleX, moduleY + moduleHeight + 5)
        }
        
        // 设置按钮
        drawSettingsButton(matrices, moduleX + moduleWidth - 25, moduleY + 10)
        
        // 如果设置了打开，绘制设置面板
        if (isSettingsOpen && settingsPanel != null) {
            settingsPanel?.draw(matrices)
        }
    }
    
    /**
     * 绘制提示框
     */
    private fun drawTooltip(matrices: MatrixStack, text: String, x: Double, y: Double) {
        val padding = 5
        val textWidth = FontRenderer.getTextWidth(text, 0.9f)
        val tooltipWidth = textWidth + padding * 2
        val tooltipHeight = 20
        
        // 背景
        Render2DUtils.drawRoundedRect(
            matrices,
            x.toFloat(), y.toFloat(),
            tooltipWidth.toFloat(), tooltipHeight.toFloat(),
            4.0f,
            Color(20, 20, 20, 230)
        )
        
        // 文字
        FontRenderer.drawText(
            matrices,
            text,
            x.toFloat() + padding,
            y.toFloat() + 5,
            Color(200, 200, 200).rgb,
            0.9f
        )
    }
    
    /**
     * 绘制设置按钮
     */
    private fun drawSettingsButton(matrices: MatrixStack, x: Float, y: Float) {
        val buttonSize = 18.0f
        
        // 背景
        Render2DUtils.drawRoundedRect(
            matrices,
            x, y,
            buttonSize, buttonSize,
            4.0f,
            Color(50, 50, 50, 150)
        )
        
        // 齿轮图标（简化为十字）
        Render2DUtils.drawLine(
            matrices,
            x + 5, y + 9,
            x + 13, y + 9,
            Color(150, 150, 150),
            2.0f
        )
        Render2DUtils.drawLine(
            matrices,
            x + 9, y + 5,
            x + 9, y + 13,
            Color(150, 150, 150),
            2.0f
        )
    }
    
    /**
     * 鼠标点击事件
     */
    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (!GUIUtil.mouseOver(x, y, width, height, mouseX, mouseY)) {
            // 检测设置按钮点击
            val settingsX = x + width - 25
            val settingsY = y + 10
            val settingsSize = 18.0
            
            if (GUIUtil.mouseOver(settingsX, settingsY, settingsSize, settingsSize, mouseX, mouseY)) {
                if (button == 0) { // 左键
                    toggleSettings()
                    return true
                }
            }
            return false
        }
        
        if (button == 0) { // 左键
            module.toggle()
            isMouseDown = true
            return true
        } else if (button == 1) { // 右键
            toggleSettings()
            return true
        }
        
        return false
    }
    
    /**
     * 鼠标释放事件
     */
    fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
        if (button == 0) {
            isMouseDown = false
        }
    }
    
    /**
     * 鼠标移动事件
     */
    fun mouseMoved(mouseX: Double, mouseY: Double, scrollOffset: Double = 0.0) {
        val renderY = y - scrollOffset
        isHovered = GUIUtil.mouseOver(x, renderY, width, height, mouseX, mouseY)
        
        // 检测设置按钮悬停
        val settingsX = x + width - 25
        val settingsY = y + 10
        val settingsSize = 18.0
        
        if (GUIUtil.mouseOver(settingsX, settingsY, settingsSize, settingsSize, mouseX, mouseY)) {
            // 设置按钮悬停
        }
    }
    
    /**
     * 切换设置面板
     */
    private fun toggleSettings() {
        isSettingsOpen = !isSettingsOpen
        
        if (isSettingsOpen && settingsPanel == null) {
            settingsPanel = SettingsPanel(module, x + width + 5, y, 200.0, 300.0)
        }
    }
    
    /**
     * 获取模块
     */
    fun getModule(): Module = module
}
