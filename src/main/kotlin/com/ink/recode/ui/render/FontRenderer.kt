package com.ink.recode.ui.render

import net.minecraft.client.Minecraft
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.util.math.MatrixStack
import org.jetbrains.skija.Canvas

/**
 * 字体渲染工具类
 * 使用 Minecraft 的 TextRenderer 进行文字渲染
 */
object FontRenderer {
    private val mc = Minecraft.getInstance()
    
    /**
     * 获取 TextRenderer
     */
    fun getTextRenderer(): TextRenderer? {
        return mc.textRenderer
    }
    
    /**
     * 绘制文字
     */
    fun drawText(matrices: MatrixStack, text: String, x: Float, y: Float, color: Int, scale: Float = 1.0f) {
        val textRenderer = getTextRenderer() ?: return
        
        matrices.push()
        matrices.scale(scale, scale, scale)
        
        textRenderer.draw(matrices, text, x / scale, y / scale, color)
        
        matrices.pop()
    }
    
    /**
     * 绘制带阴影的文字
     */
    fun drawTextWithShadow(matrices: MatrixStack, text: String, x: Float, y: Float, color: Int, scale: Float = 1.0f) {
        val textRenderer = getTextRenderer() ?: return
        
        matrices.push()
        matrices.scale(scale, scale, scale)
        
        textRenderer.drawWithShadow(matrices, text, x / scale, y / scale, color)
        
        matrices.pop()
    }
    
    /**
     * 获取文字宽度
     */
    fun getTextWidth(text: String, scale: Float = 1.0f): Float {
        val textRenderer = getTextRenderer() ?: return 0f
        return textRenderer.getWidth(text) * scale
    }
    
    /**
     * 获取文字高度
     */
    fun getTextHeight(scale: Float = 1.0f): Float {
        val textRenderer = getTextRenderer() ?: return 0f
        return textRenderer.fontHeight * scale
    }
    
    /**
     * 截断文字到指定宽度
     */
    fun trimToWidth(text: String, maxWidth: Int): String {
        val textRenderer = getTextRenderer() ?: return text
        return textRenderer.trimToWidth(text, maxWidth)
    }
}
