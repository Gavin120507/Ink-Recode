package com.ink.recode.notification

import com.ink.recode.event.Listener
import com.ink.recode.event.events.Render2DEvent
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import java.awt.Color

/**
 * 通知管理器
 */
object NotificationManager {
    private val mc = MinecraftClient.getInstance()
    private val notifications = mutableListOf<Notification>()
    
    /**
     * 通知严重性
     */
    enum class Severity(val color: Color) {
        INFO(Color(0x3498db)),           // 蓝色
        SUCCESS(Color(0x2ecc71)),         // 绿色
        WARNING(Color(0xf1c40f)),         // 黄色
        ERROR(Color(0xe74c3c)),           // 红色
        ENABLED(Color(0x9b59b6)),         // 紫色
        DISABLED(Color(0x95a5a6))         // 灰色
    }
    
    /**
     * 通知数据类
     */
    data class Notification(
        val title: String,
        val message: String,
        val severity: Severity,
        val createTime: Long = System.currentTimeMillis(),
        var fadeOut: Boolean = false
    ) {
        val displayTime: Long
            get() = System.currentTimeMillis() - createTime
    }
    
    /**
     * 发送通知
     */
    fun notification(title: String, message: String, severity: Severity) {
        val notification = Notification(title, message, severity)
        notifications.add(notification)
        println("[Notification] $title: $message ($severity)")
    }
    
    /**
     * 发送信息通知
     */
    fun info(title: String, message: String) {
        notification(title, message, Severity.INFO)
    }
    
    /**
     * 发送成功通知
     */
    fun success(title: String, message: String) {
        notification(title, message, Severity.SUCCESS)
    }
    
    /**
     * 发送警告通知
     */
    fun warning(title: String, message: String) {
        notification(title, message, Severity.WARNING)
    }
    
    /**
     * 发送错误通知
     */
    fun error(title: String, message: String) {
        notification(title, message, Severity.ERROR)
    }
    
    /**
     * 发送启用通知
     */
    fun enabled(moduleName: String) {
        notification(moduleName, "Module enabled", Severity.ENABLED)
    }
    
    /**
     * 发送禁用通知
     */
    fun disabled(moduleName: String) {
        notification(moduleName, "Module disabled", Severity.DISABLED)
    }
    
    /**
     * 渲染所有通知（在 Render2DEvent 中调用）
     */
    fun renderNotifications(event: Render2DEvent) {
        val textRenderer = mc.textRenderer
        val currentTime = System.currentTimeMillis()
        
        // 移除超过 5 秒的通知
        notifications.removeAll { 
            it.displayTime > 5000 || (it.fadeOut && it.displayTime > 6000)
        }
        
        // 渲染通知
        notifications.forEachIndexed { index, notification ->
            val x = 10
            val y = event.height - 20 - (index * 25)
            
            // 计算淡入淡出效果
            val alpha = when {
                notification.displayTime < 500 -> (notification.displayTime / 500.0f).coerceIn(0f, 1f)
                notification.displayTime > 4500 -> ((5000 - notification.displayTime) / 500.0f).coerceIn(0f, 1f)
                else -> 1f
            }
            
            if (notification.displayTime > 4500) {
                notification.fadeOut = true
            }
            
            // 绘制背景
            val backgroundColor = withAlpha(notification.severity.color, alpha * 0.8f)
            event.guiGraphics?.fill(x - 5, y - 5, x + 200, y + 15, backgroundColor.rgb)
            
            // 绘制标题
            val titleColor = withAlpha(Color.WHITE, alpha)
            event.guiGraphics?.drawText(
                textRenderer,
                notification.title,
                x,
                y,
                titleColor.rgb
            )
            
            // 绘制消息
            val messageColor = withAlpha(Color(200, 200, 200), alpha * 0.8f)
            event.guiGraphics?.drawText(
                textRenderer,
                notification.message,
                x,
                y + 10,
                messageColor.rgb
            )
        }
    }
    
    /**
     * 为颜色添加透明度
     */
    private fun withAlpha(color: Color, alpha: Float): Color {
        return Color(color.red, color.green, color.blue, (alpha * 255).toInt())
    }
}
