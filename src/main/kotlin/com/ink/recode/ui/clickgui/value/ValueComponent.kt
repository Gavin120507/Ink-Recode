package com.ink.recode.ui.clickgui.value

import com.ink.recode.value.Value
import org.jetbrains.skija.Canvas

/**
 * Value 配置组件基类
 */
abstract class ValueComponent<T : Value<*>>(
    val value: T,
    val x: Double,
    val y: Double,
    val width: Double
) {
    // 组件高度
    open val height: Double = 20.0
    
    // 状态
    protected var isHovered = false
    protected var isDragging = false
    
    /**
     * 绘制组件
     */
    abstract fun draw(canvas: Canvas, scrollOffset: Double)
    
    /**
     * 鼠标点击事件
     */
    abstract fun mouseClicked(mouseX: Double, mouseY: Double, button: Int, scrollOffset: Double): Boolean
    
    /**
     * 鼠标释放事件
     */
    abstract fun mouseReleased(mouseX: Double, mouseY: Double, button: Int)
    
    /**
     * 鼠标移动事件
     */
    abstract fun mouseMoved(mouseX: Double, mouseY: Double, scrollOffset: Double)
    
    /**
     * 键盘按键事件
     */
    abstract fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean
    
    /**
     * 字符输入事件
     */
    abstract fun charTyped(codePoint: Char, modifiers: Int): Boolean
    
    /**
     * 检查鼠标是否悬停
     */
    protected fun isMouseOver(mouseX: Double, mouseY: Double, scrollOffset: Double): Boolean {
        val renderY = y - scrollOffset
        return mouseX > x && mouseX < x + width &&
               mouseY > renderY && mouseY < renderY + height
    }
}
