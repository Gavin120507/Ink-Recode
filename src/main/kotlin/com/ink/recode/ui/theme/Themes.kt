package com.ink.recode.ui.theme

import java.awt.Color

/**
 * Rise 6.0 风格的主题系统
 * 支持 30 种预设主题和动态渐变效果
 */
enum class Themes(
    val themeName: String,
    private val firstColor: Color,
    private val secondColor: Color,
    private val thirdColor: Color? = null,
    private val triColor: Boolean = false
) {
    AUBERGINE(
        "Aubergine",
        Color(142, 52, 255),
        Color(75, 25, 180)
    ),
    AQUA(
        "Aqua",
        Color(0, 190, 255),
        Color(0, 120, 160)
    ),
    BANANA(
        "Banana",
        Color(255, 220, 0),
        Color(200, 170, 0)
    ),
    BLEND(
        "Blend",
        Color(255, 100, 100),
        Color(100, 150, 255),
        Color(150, 100, 255),
        true
    ),
    BLOSSOM(
        "Blossom",
        Color(255, 150, 200),
        Color(200, 100, 150)
    ),
    BUBBLEGUM(
        "Bubblegum",
        Color(255, 100, 180),
        Color(200, 50, 130)
    ),
    CANDY_CANE(
        "Candy Cane",
        Color(255, 80, 80),
        Color(255, 255, 255),
        Color(200, 50, 50),
        true
    ),
    CHERRY(
        "Cherry",
        Color(255, 50, 100),
        Color(180, 20, 60)
    ),
    CHRISTMAS(
        "Christmas",
        Color(0, 180, 50),
        Color(200, 30, 30),
        Color(255, 255, 255),
        true
    ),
    CORAL(
        "Coral",
        Color(255, 120, 100),
        Color(200, 60, 50)
    ),
    DIGITAL_HORIZON(
        "Digital Horizon",
        Color(0, 150, 200),
        Color(50, 100, 150),
        Color(100, 50, 100),
        true
    ),
    EXPRESS(
        "Express",
        Color(255, 150, 0),
        Color(200, 100, 0)
    ),
    LIME_WATER(
        "Lime Water",
        Color(180, 255, 100),
        Color(100, 180, 50)
    ),
    LUSH(
        "Lush",
        Color(50, 200, 100),
        Color(20, 150, 60)
    ),
    HALOGEN(
        "Halogen",
        Color(255, 255, 255),
        Color(200, 200, 200),
        Color(150, 150, 150),
        true
    ),
    HYPER(
        "Hyper",
        Color(255, 50, 150),
        Color(200, 30, 100),
        Color(150, 20, 80),
        true
    ),
    MAGIC(
        "Magic",
        Color(150, 50, 255),
        Color(100, 30, 180)
    ),
    MAY(
        "May",
        Color(255, 200, 100),
        Color(200, 150, 50)
    ),
    ORANGE_JUICE(
        "Orange Juice",
        Color(255, 180, 50),
        Color(200, 130, 20)
    ),
    PASTEL(
        "Pastel",
        Color(180, 200, 255),
        Color(130, 150, 200)
    ),
    PUMPKIN(
        "Pumpkin",
        Color(255, 150, 50),
        Color(200, 100, 20)
    ),
    SATIN(
        "Satin",
        Color(200, 100, 200),
        Color(150, 50, 150)
    ),
    SNOWY_SKY(
        "Snowy Sky",
        Color(200, 220, 255),
        Color(150, 180, 220),
        Color(100, 140, 180),
        true
    ),
    STEEL_FADE(
        "Steel Fade",
        Color(100, 120, 140),
        Color(60, 80, 100)
    ),
    SUNDAE(
        "Sundae",
        Color(255, 180, 150),
        Color(200, 120, 100)
    ),
    SUNKIST(
        "Sunkist",
        Color(255, 200, 100),
        Color(200, 150, 50)
    ),
    WATER(
        "Water",
        Color(50, 150, 255),
        Color(20, 100, 180)
    ),
    WINTER(
        "Winter",
        Color(180, 200, 220),
        Color(120, 150, 180)
    ),
    WOOD(
        "Wood",
        Color(180, 130, 80),
        Color(130, 90, 50)
    ),
    INK_DEFAULT(
        "Ink Default",
        Color(78, 163, 255),
        Color(52, 119, 204),
        Color(102, 187, 255),
        true
    );

    /**
     * 获取混合因子（基于时间和位置的正弦波动画）
     */
    fun getBlendFactor(screenX: Double, screenY: Double): Double {
        return Math.sin(
            System.currentTimeMillis() / 600.0 +
            screenX * 0.005 +
            screenY * 0.06
        ) * 0.5 + 0.5
    }

    /**
     * 获取强调色（支持动态渐变）
     */
    fun getAccentColor(screenX: Double, screenY: Double): Color {
        val blendFactor = getBlendFactor(screenX, screenY)
        
        return if (triColor && thirdColor != null) {
            if (blendFactor <= 0.5) {
                mixColors(secondColor, firstColor, blendFactor * 2.0)
            } else {
                mixColors(thirdColor, secondColor, (blendFactor - 0.5) * 2.0)
            }
        } else {
            mixColors(firstColor, secondColor, blendFactor)
        }
    }

    /**
     * 获取第一主色
     */
    fun getFirstColor(): Color = firstColor

    /**
     * 获取第二主色
     */
    fun getSecondColor(): Color = secondColor

    /**
     * 获取第三主色（如果有）
     */
    fun getThirdColor(): Color = thirdColor ?: secondColor

    companion object {
        /**
         * 颜色混合算法
         */
        fun mixColors(color1: Color, color2: Color, percent: Double): Color {
            val inversePercent = 1.0 - percent
            val redPart = (color1.red * percent + color2.red * inversePercent).toInt()
            val greenPart = (color1.green * percent + color2.green * inversePercent).toInt()
            val bluePart = (color1.blue * percent + color2.blue * inversePercent).toInt()
            return Color(redPart, greenPart, bluePart)
        }

        /**
         * 带透明度的颜色
         */
        fun withAlpha(color: Color, alpha: Int): Color {
            val clampedAlpha = alpha.coerceIn(0, 255)
            return Color(color.red, color.green, color.blue, clampedAlpha)
        }

        /**
         * 从名称获取主题
         */
        fun fromName(name: String): Themes {
            return values().find { it.themeName.equals(name, ignoreCase = true) } ?: INK_DEFAULT
        }

        /**
         * 所有主题名称列表
         */
        fun getThemeNames(): Array<String> {
            return values().map { it.themeName }.toTypedArray()
        }
    }
}
