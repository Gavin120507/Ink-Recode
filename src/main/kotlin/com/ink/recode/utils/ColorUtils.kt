package com.ink.recode.utils

import java.awt.Color

object ColorUtils {
    
    /**
     * 生成彩虹颜色
     * @param index 索引值，用于偏移色相
     * @param saturation 饱和度 (0.0-1.0)
     * @param brightness 亮度 (0.0-1.0)
     * @param alpha 透明度 (0-255)
     */
    fun rainbow(
        index: Int = 0,
        saturation: Float = 1.0f,
        brightness: Float = 1.0f,
        alpha: Int = 255
    ): Int {
        val hue = (System.currentTimeMillis() % 10000) / 10000f + (index / 50f)
        val color = Color.getHSBColor(hue % 1f, saturation, brightness)
        return Color(color.red, color.green, color.blue, alpha).rgb
    }
    
    /**
     * 从 HSB 转换到 Color
     */
    fun fromHSB(hue: Float, saturation: Float, brightness: Float, alpha: Int = 255): Color {
        return Color.getHSBColor(hue, saturation, brightness).let {
            Color(it.red, it.green, it.blue, alpha)
        }
    }
    
    /**
     * 颜色插值
     */
    fun interpolate(color1: Color, color2: Color, ratio: Float): Color {
        val r = (color1.red + (color2.red - color1.red) * ratio).toInt()
        val g = (color1.green + (color2.green - color1.green) * ratio).toInt()
        val b = (color1.blue + (color2.blue - color1.blue) * ratio).toInt()
        val a = (color1.alpha + (color2.alpha - color1.alpha) * ratio).toInt()
        return Color(r, g, b, a)
    }
    
    /**
     * 从整数 RGB 获取 Color
     */
    fun fromRGB(rgb: Int): Color {
        return Color(rgb)
    }
    
    /**
     * 从整数 RGB 和 Alpha 获取 Color
     */
    fun fromRGBA(rgb: Int, alpha: Int): Color {
        return Color(rgb and 0xFF0000 shr 16, rgb and 0xFF00 shr 8, rgb and 0xFF, alpha)
    }
    
    /**
     * 获取主题颜色（可以根据需要修改）
     */
    fun getThemeColor(): Color {
        // 默认返回蓝色主题，可以根据模组配置修改
        return Color(0, 150, 255)
    }
    
    /**
     * 颜色变亮
     */
    fun brighter(color: Color, factor: Float = 0.1f): Color {
        return Color(
            (color.red * (1 + factor)).toInt().coerceIn(0, 255),
            (color.green * (1 + factor)).toInt().coerceIn(0, 255),
            (color.blue * (1 + factor)).toInt().coerceIn(0, 255),
            color.alpha
        )
    }
    
    /**
     * 颜色变暗
     */
    fun darker(color: Color, factor: Float = 0.1f): Color {
        return Color(
            (color.red * (1 - factor)).toInt().coerceIn(0, 255),
            (color.green * (1 - factor)).toInt().coerceIn(0, 255),
            (color.blue * (1 - factor)).toInt().coerceIn(0, 255),
            color.alpha
        )
    }
}
