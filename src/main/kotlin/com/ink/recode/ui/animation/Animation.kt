package com.ink.recode.ui.animation

/**
 * 动画类
 * 管理动画时间和进度，支持多种缓动函数
 */
class Animation(
    private val easing: (Double) -> Double = Easing::easeOutExpo,
    private val duration: Long = 300L // 毫秒
) {
    private var startTime: Long = -1L
    private var currentValue: Double = 0.0
    private var targetValue: Double = 0.0
    private var isRunning: Boolean = false

    /**
     * 获取当前动画值
     */
    fun getValue(): Double = currentValue

    /**
     * 获取当前动画值（Float）
     */
    fun getValueFloat(): Float = currentValue.toFloat()

    /**
     * 获取当前动画值（Int）
     */
    fun getValueInt(): Int = currentValue.toInt()

    /**
     * 运行动画
     * @param target 目标值（0.0-1.0）
     */
    fun run(target: Double) {
        if (targetValue != target) {
            if (!isRunning || Math.abs(targetValue - target) > 0.001) {
                currentValue = targetValue
                targetValue = target
                startTime = System.currentTimeMillis()
                isRunning = true
            }
        } else {
            isRunning = false
        }

        if (isRunning) {
            val elapsed = System.currentTimeMillis() - startTime
            val progress = (elapsed.toDouble() / duration).coerceIn(0.0, 1.0)
            val easedProgress = easing(progress)
            
            currentValue = currentValue + (targetValue - currentValue) * easedProgress
            
            if (progress >= 1.0) {
                currentValue = targetValue
                isRunning = false
            }
        }
    }

    /**
     * 重置动画
     */
    fun reset() {
        startTime = -1L
        currentValue = 0.0
        targetValue = 0.0
        isRunning = false
    }

    /**
     * 立即设置值（无动画）
     */
    fun setValue(value: Double) {
        currentValue = value
        targetValue = value
        isRunning = false
    }

    /**
     * 检查动画是否完成
     */
    fun isComplete(): Boolean = !isRunning

    /**
     * 获取动画进度（0.0-1.0）
     */
    fun getProgress(): Double {
        return if (isRunning) {
            val elapsed = System.currentTimeMillis() - startTime
            (elapsed.toDouble() / duration).coerceIn(0.0, 1.0)
        } else {
            if (targetValue > currentValue) 1.0 else 0.0
        }
    }

    companion object {
        /**
         * 快速创建线性动画
         */
        fun linear(duration: Long = 300L): Animation = Animation(Easing::linear, duration)

        /**
         * 快速创建指数退出动画（最常用）
         */
        fun expoOut(duration: Long = 300L): Animation = Animation(Easing::easeOutExpo, duration)

        /**
         * 快速创建弹性动画
         */
        fun elastic(duration: Long = 500L): Animation = Animation(Easing::easeOutElastic, duration)

        /**
         * 快速创建正弦动画
         */
        fun sine(duration: Long = 300L): Animation = Animation(Easing::easeOutSine, duration)
    }
}

/**
 *  stopwatch 工具类 - 用于计算经过时间
 */
class Stopwatch {
    private var startTime: Long = System.currentTimeMillis()
    private var pausedTime: Long = 0L
    private var isPaused: Boolean = false

    /**
     * 获取经过的时间（毫秒）
     */
    fun getElapsedTime(): Long {
        return if (isPaused) {
            pausedTime
        } else {
            System.currentTimeMillis() - startTime
        }
    }

    /**
     * 获取经过的时间（秒）
     */
    fun getElapsedTimeSeconds(): Double = getElapsedTime() / 1000.0

    /**
     * 重置计时器
     */
    fun reset() {
        startTime = System.currentTimeMillis()
        pausedTime = 0L
        isPaused = false
    }

    /**
     * 暂停计时器
     */
    fun pause() {
        if (!isPaused) {
            pausedTime = System.currentTimeMillis() - startTime
            isPaused = true
        }
    }

    /**
     * 继续计时器
     */
    fun resume() {
        if (isPaused) {
            startTime = System.currentTimeMillis() - pausedTime
            isPaused = false
        }
    }
}
