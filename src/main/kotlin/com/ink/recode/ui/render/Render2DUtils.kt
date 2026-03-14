package com.ink.recode.ui.render

import com.ink.recode.ui.theme.Themes
import org.jetbrains.skija.Canvas
import org.jetbrains.skija.Color
import org.jetbrains.skija.Paint
import org.jetbrains.skija.RRect
import org.jetbrains.skija.Rect
import java.awt.Color as AWTColor

/**
 * 2D 渲染工具类
 * 使用 Skija 进行高性能 2D 渲染
 */
object Render2DUtils {
    private val paint = Paint()
    private val rectPaint = Paint()

    /**
     * 绘制实心矩形
     */
    fun drawRect(canvas: Canvas, x: Float, y: Float, width: Float, height: Float, color: AWTColor) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        canvas.drawRect(Rect.makeXYWH(x, y, width, height), paint)
    }

    /**
     * 绘制圆角矩形
     */
    fun drawRoundedRect(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        color: AWTColor
    ) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        val rRect = RRect.makeRectXYWH(x, y, width, height, radius, radius)
        canvas.drawRRect(rRect, paint)
    }

    /**
     * 绘制圆角渐变矩形（垂直渐变）
     */
    fun drawRoundedGradientRect(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        color1: AWTColor,
        color2: AWTColor,
        vertical: Boolean = true
    ) {
        val rRect = RRect.makeRectXYWH(x, y, width, height, radius, radius)
        
        paint.shader = org.jetbrains.skija.Shader.makeLinearGradient(
            if (vertical) x + width / 2 else x,
            if (vertical) y else y + height / 2,
            if (vertical) x + width / 2 else x + width,
            if (vertical) y + height else y + height / 2,
            Color(color1.rgb and 0x00FFFFFF or (color1.alpha shl 24)),
            Color(color2.rgb and 0x00FFFFFF or (color2.alpha shl 24)),
            org.jetbrains.skija.TileMode.CLAMP
        )
        
        canvas.drawRRect(rRect, paint)
        paint.shader = null
    }

    /**
     * 绘制圆角渐变矩形（水平渐变）
     */
    fun drawRoundedGradientRectHorizontal(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        color1: AWTColor,
        color2: AWTColor
    ) {
        drawRoundedGradientRect(canvas, x, y, width, height, radius, color1, color2, false)
    }

    /**
     * 绘制圆角边框
     */
    fun drawRoundedOutline(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        strokeWidth: Float,
        color: AWTColor
    ) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        
        val rRect = RRect.makeRectXYWH(x + strokeWidth / 2, y + strokeWidth / 2, 
            width - strokeWidth, height - strokeWidth, radius)
        canvas.drawRRect(rRect, paint)
        
        paint.style = Paint.Style.FILL
    }

    /**
     * 绘制圆形
     */
    fun drawCircle(canvas: Canvas, centerX: Float, centerY: Float, radius: Float, color: AWTColor) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    /**
     * 绘制圆形边框
     */
    fun drawCircleOutline(
        canvas: Canvas,
        centerX: Float, centerY: Float,
        radius: Float,
        strokeWidth: Float,
        color: AWTColor
    ) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        canvas.drawCircle(centerX, centerY, radius, paint)
        paint.style = Paint.Style.FILL
    }

    /**
     * 绘制线条
     */
    fun drawLine(
        canvas: Canvas,
        x1: Float, y1: Float,
        x2: Float, y2: Float,
        color: AWTColor,
        strokeWidth: Float = 1.0f
    ) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        paint.strokeWidth = strokeWidth
        canvas.drawLine(x1, y1, x2, y2, paint)
    }

    /**
     * 绘制三角形
     */
    fun drawTriangle(
        canvas: Canvas,
        x1: Float, y1: Float,
        x2: Float, y2: Float,
        x3: Float, y3: Float,
        color: AWTColor
    ) {
        paint.color = Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
        canvas.drawPath(
            org.jetbrains.skija.Path().apply {
                moveTo(x1, y1)
                lineTo(x2, y2)
                lineTo(x3, y3)
                close()
            },
            paint
        )
    }

    /**
     * 绘制阴影效果（多层实现）
     */
    fun drawShadow(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        shadowSize: Float = 10.0f,
        opacity: Int = 100
    ) {
        val layers = 8
        for (i in 0 until layers) {
            val margin = (shadowSize / layers) * i
            val layerOpacity = (opacity * (1.0f - i.toFloat() / layers) * 0.3f).toInt()
            val shadowColor = AWTColor(0, 0, 0, layerOpacity.coerceIn(0, 255))
            drawRoundedRect(
                canvas,
                x - margin / 2, y - margin / 2,
                width + margin, height + margin,
                radius + margin / 2,
                shadowColor
            )
        }
    }

    /**
     * 绘制带阴影的圆角矩形
     */
    fun drawRoundedRectWithShadow(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        color: AWTColor,
        shadowSize: Float = 10.0f
    ) {
        drawShadow(canvas, x, y, width, height, radius, shadowSize)
        drawRoundedRect(canvas, x, y, width, height, radius, color)
    }

    /**
     * 绘制进度条
     */
    fun drawProgressBar(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        progress: Float,
        backgroundColor: AWTColor,
        progressColor: AWTColor
    ) {
        // 背景
        drawRoundedRect(canvas, x, y, width, height, radius, backgroundColor)
        
        // 进度
        val progressWidth = width * progress.coerceIn(0.0f, 1.0f)
        if (progressWidth > 0) {
            drawRoundedRect(canvas, x, y, progressWidth, height, radius, progressColor)
        }
    }

    /**
     * 绘制滑块
     */
    fun drawSlider(
        canvas: Canvas,
        x: Float, y: Float,
        width: Float, height: Float,
        radius: Float,
        progress: Float,
        backgroundColor: AWTColor,
        grabberColor: AWTColor,
        grabberSize: Float = height * 1.2f
    ) {
        // 背景条
        val barHeight = height * 0.3f
        val barY = y + (height - barHeight) / 2
        drawRoundedRect(canvas, x, barY, width, barHeight, barHeight / 2, backgroundColor)
        
        // 滑块
        val grabberX = x + (width - grabberSize) * progress.coerceIn(0.0f, 1.0f)
        drawRoundedRect(canvas, grabberX, y, grabberSize, grabberSize, grabberSize / 2, grabberColor)
    }

    /**
     * 绘制复选框
     */
    fun drawCheckBox(
        canvas: Canvas,
        x: Float, y: Float,
        size: Float,
        checked: Boolean,
        backgroundColor: AWTColor,
        checkColor: AWTColor,
        scale: Float = 1.0f
    ) {
        val scaledSize = size * scale
        val scaledX = x + (size - scaledSize) / 2
        val scaledY = y + (size - scaledSize) / 2
        
        // 背景
        drawRoundedRect(canvas, scaledX, scaledY, scaledSize, scaledSize, scaledSize / 2, backgroundColor)
        
        // 勾选标记
        if (checked) {
            val padding = scaledSize * 0.2f
            drawLine(
                canvas,
                scaledX + padding, scaledY + scaledSize / 2,
                scaledX + scaledSize * 0.4f, scaledY + scaledSize * 0.7f,
                checkColor, 2.0f
            )
            drawLine(
                canvas,
                scaledX + scaledSize * 0.4f, scaledY + scaledSize * 0.7f,
                scaledX + scaledSize - padding, scaledY + scaledSize * 0.3f,
                checkColor, 2.0f
            )
        }
    }

    /**
     * 将 AWT Color 转换为 Skija Color
     */
    fun toSkijaColor(color: AWTColor): Color {
        return Color(color.rgb and 0x00FFFFFF or (color.alpha shl 24))
    }
    
    /**
     * 绘制文字（使用 Minecraft 字体渲染器）
     */
    fun drawText(canvas: Canvas, text: String, x: Float, y: Float, size: Float, color: Int) {
        // 注意：Skija 不直接支持文字渲染，需要使用 Minecraft 的字体渲染器
        // 这里提供一个接口，实际实现需要在 ClickGUI 中调用 Minecraft.fontRenderer
        // 由于 Canvas 参数无法直接用于 Minecraft 字体渲染，这个方法需要在 ClickGUI.kt 中重写
    }
    
    /**
     * 获取文字宽度
     */
    fun getTextWidth(text: String, size: Float): Float {
        // 估算文字宽度（假设每个字符平均 8 像素）
        return text.length * size * 0.6f
    }
}
