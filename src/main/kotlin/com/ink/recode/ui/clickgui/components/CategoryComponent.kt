package com.ink.recode.ui.clickgui

import com.ink.recode.Category
import com.ink.recode.ui.animation.Animation
import com.ink.recode.ui.animation.Easing
import com.ink.recode.ui.render.Render2DUtils
import com.ink.recode.ui.render.FontRenderer
import com.ink.recode.ui.utils.GUIUtil
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * 分类组件
 * 显示分类按钮
 */
class CategoryComponent(
    val category: Category,
    private val x: Double,
    private val y: Double,
    private val width: Double,
    private val height: Double
) {
    // 动画
    private val hoverAnimation = Animation(Easing::easeOutExpo, 50)
    private val selectAnimation = Animation(Easing::easeOutExpo, 100)
    
    // 状态
    private var isHovered = false
    private var isSelected = false
    
    /**
     * 绘制分类组件
     */
    fun draw(matrices: MatrixStack, clickGUI: RiseClickGUI, accentColor: Color) {
        // 更新动画
        hoverAnimation.run(if (isHovered) 30 else 0)
        selectAnimation.run(if (isSelected) 1.0 else 0.0)
        
        val hoverAlpha = hoverAnimation.getValueInt()
        val selectProgress = selectAnimation.getValue()
        
        // 组件位置
        val componentX = x.toFloat()
        val componentY = y.toFloat()
        val componentWidth = width.toFloat()
        val componentHeight = height.toFloat()
        
        // 背景
        val bgColor = if (isSelected) {
            Color(50, 53, 60, 200)
        } else {
            Color(30, 33, 40, 150)
        }
        
        Render2DUtils.drawRoundedRect(
            matrices,
            componentX, componentY,
            componentWidth, componentHeight,
            6.0f,
            bgColor
        )
        
        // 悬停覆盖层
        if (hoverAlpha > 0) {
            Render2DUtils.drawRoundedRect(
                matrices,
                componentX, componentY,
                componentWidth, componentHeight,
                6.0f,
                Color(255, 255, 255, hoverAlpha)
            )
        }
        
        // 选中指示器（左侧条）
        if (isSelected) {
            val indicatorWidth = 3.0f
            val indicatorHeight = (componentHeight * 0.7).toFloat()
            val indicatorY = componentY + (componentHeight - indicatorHeight) / 2
            
            Render2DUtils.drawRoundedRect(
                matrices,
                componentX, indicatorY,
                indicatorWidth, indicatorHeight,
                1.5f,
                accentColor
            )
        }
        
        // 分类图标
        val icon = getCategoryIcon(category)
        if (!icon.isNullOrEmpty()) {
            FontRenderer.drawText(
                matrices,
                icon,
                componentX + 8,
                componentY + 10,
                Color(200, 200, 200).rgb,
                0.9f
            )
        }
        
        // 分类名称
        val categoryName = getCategoryName(category)
        val nameColor = if (isSelected) {
            accentColor.rgb
        } else {
            Color(180, 180, 180).rgb
        }
        
        FontRenderer.drawText(
            matrices,
            categoryName,
            componentX + 28,
            componentY + 10,
            nameColor,
            1.0f
        )
    }
    
    /**
     * 获取分类图标
     */
    private fun getCategoryIcon(category: Category): String? {
        return when (category) {
            Category.COMBAT -> "⚔️"
            Category.MOVEMENT -> "🏃"
            Category.RENDER -> "🎨"
            Category.PLAYER -> "👤"
        }
    }
    
    /**
     * 获取分类名称
     */
    private fun getCategoryName(category: Category): String {
        return when (category) {
            Category.COMBAT -> "Combat"
            Category.MOVEMENT -> "Movement"
            Category.RENDER -> "Render"
            Category.PLAYER -> "Player"
        }
    }
    
    /**
     * 鼠标点击事件
     */
    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (GUIUtil.mouseOver(x, y, width, height, mouseX, mouseY) && button == 0) {
            isSelected = true
            return true
        }
        return false
    }
    
    /**
     * 鼠标移动事件
     */
    fun mouseMoved(mouseX: Double, mouseY: Double) {
        isHovered = GUIUtil.mouseOver(x, y, width, height, mouseX, mouseY)
    }
    
    /**
     * 设置选中状态
     */
    fun setSelected(selected: Boolean) {
        isSelected = selected
    }
}
