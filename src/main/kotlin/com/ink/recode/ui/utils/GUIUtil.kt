package com.ink.recode.ui.utils

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.opengl.GL11

/**
 * GUI 工具类
 * 提供鼠标检测、裁剪等通用 GUI 功能
 */
object GUIUtil {
    private val mc = Minecraft.getMinecraft()

    /**
     * 检测鼠标是否在矩形区域内
     */
    fun mouseOver(
        posX: Double, posY: Double,
        width: Double, height: Double,
        mouseX: Double, mouseY: Double
    ): Boolean {
        return mouseX > posX && mouseX < posX + width &&
               mouseY > posY && mouseY < posY + height
    }

    /**
     * 检测鼠标是否在矩形区域内（Float 版本）
     */
    fun mouseOver(
        posX: Float, posY: Float,
        width: Float, height: Float,
        mouseX: Float, mouseY: Float
    ): Boolean {
        return mouseX > posX && mouseX < posX + width &&
               mouseY > posY && mouseY < posY + height
    }

    /**
     * 检测鼠标是否在圆角矩形内
     */
    fun mouseOverRounded(
        posX: Double, posY: Double,
        width: Double, height: Double,
        radius: Double,
        mouseX: Double, mouseY: Double
    ): Boolean {
        // 简单版本：先检测矩形，再优化圆角
        if (!mouseOver(posX, posY, width, height, mouseX, mouseY)) {
            return false
        }

        // 检测四个圆角
        val corners = listOf(
            Pair(posX + radius, posY + radius),
            Pair(posX + width - radius, posY + radius),
            Pair(posX + radius, posY + height - radius),
            Pair(posX + width - radius, posY + height - radius)
        )

        for ((cx, cy) in corners) {
            val dx = mouseX - cx
            val dy = mouseY - cy
            if (dx * dx + dy * dy <= radius * radius) {
                return true
            }
        }

        // 如果在矩形内但不在圆角外，则在内部
        return mouseX >= posX + radius && mouseX <= posX + width - radius &&
               mouseY >= posY + radius && mouseY <= posY + height - radius
    }

    /**
     * 设置 Scissor 裁剪区域
     */
    fun scissor(x: Double, y: Double, width: Double, height: Double) {
        val sr = ScaledResolution(mc)
        val scale = sr.scaleFactor
        val scaledHeight = sr.scaledHeight

        val scaledX = (x * scale).toInt()
        val scaledY = ((scaledHeight - y - height) * scale).toInt()
        val scaledWidth = (width * scale).toInt()
        val scaledHeight2 = (height * scale).toInt()

        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        GL11.glScissor(scaledX, scaledY, scaledWidth, scaledHeight2)
    }

    /**
     * 停止 Scissor 裁剪
     */
    fun stopScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

    /**
     * 将 DPI 转换为 Minecraft 坐标
     */
    fun toMinecraftX(screenX: Int): Double {
        val sr = ScaledResolution(mc)
        return screenX.toDouble() / sr.scaleFactor
    }

    /**
     * 将 DPI 转换为 Minecraft 坐标
     */
    fun toMinecraftY(screenY: Int): Double {
        val sr = ScaledResolution(mc)
        return screenY.toDouble() / sr.scaleFactor
    }

    /**
     * 限制值在范围内
     */
    fun clamp(value: Double, min: Double, max: Double): Double {
        return value.coerceIn(min, max)
    }

    /**
     * 限制值在范围内（Float 版本）
     */
    fun clamp(value: Float, min: Float, max: Float): Float {
        return value.coerceIn(min, max)
    }

    /**
     * 限制值在范围内（Int 版本）
     */
    fun clamp(value: Int, min: Int, max: Int): Int {
        return value.coerceIn(min, max)
    }

    /**
     * 线性插值
     */
    fun lerp(start: Double, end: Double, progress: Double): Double {
        return start + (end - start) * progress
    }

    /**
     * 线性插值（Float 版本）
     */
    fun lerp(start: Float, end: Float, progress: Float): Float {
        return start + (end - start) * progress
    }

    /**
     * 计算两个点之间的距离
     */
    fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(): Int {
        return mc.displayWidth
    }

    /**
     * 获取屏幕高度
     */
    fun getScreenHeight(): Int {
        return mc.displayHeight
    }

    /**
     * 获取缩放后的屏幕宽度
     */
    fun getScaledWidth(): Int {
        return ScaledResolution(mc).scaledWidth
    }

    /**
     * 获取缩放后的屏幕高度
     */
    fun getScaledHeight(): Int {
        return ScaledResolution(mc).scaledHeight
    }

    /**
     * 获取缩放因子
     */
    fun getScaleFactor(): Int {
        return ScaledResolution(mc).scaleFactor
    }
}
