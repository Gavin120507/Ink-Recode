package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.render.Camera
import net.minecraft.client.util.math.MatrixStack

/**
 * 渲染事件
 */

/**
 * 游戏渲染事件
 */
class GameRenderEvent : Event()

/**
 * 屏幕渲染事件
 */
class ScreenRenderEvent : Event()

/**
 * 世界渲染事件
 */
class WorldRenderEvent(
    val matrixStack: MatrixStack,
    val camera: Camera,
    val partialTicks: Float
) : Event()

/**
 * 绘制轮廓事件
 */
class DrawOutlinesEvent(
    val matrixStack: MatrixStack,
    val camera: Camera,
    val partialTicks: Float,
    val type: OutlineType
) : Event() {
    var dirtyFlag: Boolean = false
        private set
    
    enum class OutlineType {
        INBUILT_OUTLINE,
        MINECRAFT_GLOW
    }
    
    fun markDirty() {
        this.dirtyFlag = true
    }
}

/**
 * 覆盖渲染事件（HUD 渲染）
 */
class OverlayRenderEvent(
    val context: DrawContext,
    val tickDelta: Float
) : Event()
