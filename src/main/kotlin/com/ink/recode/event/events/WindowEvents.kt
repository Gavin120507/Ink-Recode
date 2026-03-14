package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.client.option.KeyBinding

/**
 * 窗口事件
 */

/**
 * 窗口大小变化事件
 */
class WindowResizeEvent(
    val width: Int,
    val height: Int
) : Event()

/**
 * 鼠标按键事件
 */
class MouseButtonEvent(
    val button: Int,
    val action: Int,
    val mods: Int
) : Event()

/**
 * 鼠标滚动事件
 */
class MouseScrollEvent(
    val horizontal: Double,
    val vertical: Double
) : Event()

/**
 * 鼠标光标事件
 */
class MouseCursorEvent(
    val x: Double,
    val y: Double
) : Event()

/**
 * 键盘按键事件
 */
class KeyboardKeyEvent(
    val keyCode: Int,
    val scanCode: Int,
    val action: Int,
    val mods: Int
) : Event()

/**
 * 键盘字符事件
 */
class KeyboardCharEvent(
    val codePoint: Int,
    val modifiers: Int
) : Event()

/**
 * 输入事件
 */

/**
 * 输入处理事件
 */
class InputHandleEvent : Event()

/**
 * 移动输入事件
 */
class MovementInputEvent(
    var directionalInput: DirectionalInput,
    var jumping: Boolean,
    var sneaking: Boolean
) : Event()

/**
 * 方向输入
 */
data class DirectionalInput(
    val forward: Int,
    val sideways: Int
)

/**
 * 鼠标旋转事件
 */
class MouseRotationEvent(
    var cursorDeltaX: Double,
    var cursorDeltaY: Double
) : Event() {
    var cancelled = false
}

/**
 * 按键绑定变化事件
 */
class KeybindChangeEvent : Event()

/**
 * 使用冷却事件
 */
class UseCooldownEvent(var cooldown: Int) : Event()

/**
 * 取消方块破坏事件
 */
class CancelBlockBreakingEvent : Event() {
    var cancelled = false
}

/**
 * 飞溅覆盖事件（启动画面）
 */
class SplashOverlayEvent(val showingSplash: Boolean) : Event()

/**
 * 飞溅进度事件
 */
class SplashProgressEvent(
    val progress: Float,
    val isComplete: Boolean
) : Event()
