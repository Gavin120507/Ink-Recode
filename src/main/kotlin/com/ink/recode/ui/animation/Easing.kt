package com.ink.recode.ui.animation

/**
 * Easing 缓动函数库
 * 基于 Rise 6.0 的缓动函数实现
 */
object Easing {
    /**
     * 线性插值
     */
    fun linear(t: Double): Double = t

    /**
     * 二次方缓动 - 进入
     */
    fun easeInQuad(t: Double): Double = t * t

    /**
     * 二次方缓动 - 退出
     */
    fun easeOutQuad(t: Double): Double = t * (2 - t)

    /**
     * 二次方缓动 - 进入退出
     */
    fun easeInOutQuad(t: Double): Double = if (t < 0.5) 2 * t * t else -1 + (4 - 2 * t) * t

    /**
     * 三次方缓动 - 进入
     */
    fun easeInCubic(t: Double): Double = t * t * t

    /**
     * 三次方缓动 - 退出
     */
    fun easeOutCubic(t: Double): Double = 1 + (--t) * t * t

    /**
     * 三次方缓动 - 进入退出
     */
    fun easeInOutCubic(t: Double): Double = if (t < 0.5) 4 * t * t * t else (t - 1) * (2 * t - 2) * (2 * t - 2) + 1

    /**
     * 指数缓动 - 进入
     */
    fun easeInExpo(t: Double): Double = if (t == 0.0) 0.0 else Math.pow(2.0, 10.0 * (t - 1))

    /**
     * 指数缓动 - 退出（最常用）
     */
    fun easeOutExpo(t: Double): Double = if (t == 1.0) 1.0 else 1 - Math.pow(2.0, -10.0 * t)

    /**
     * 指数缓动 - 进入退出
     */
    fun easeInOutExpo(t: Double): Double {
        if (t == 0.0) return 0.0
        if (t == 1.0) return 1.0
        return if (t < 0.5) Math.pow(2.0, 20.0 * t - 10) / 2 else (2 - Math.pow(2.0, -20.0 * t + 10)) / 2
    }

    /**
     * 正弦缓动 - 进入
     */
    fun easeInSine(t: Double): Double = 1 - Math.cos(t * Math.PI / 2)

    /**
     * 正弦缓动 - 退出
     */
    fun easeOutSine(t: Double): Double = Math.sin(t * Math.PI / 2)

    /**
     * 正弦缓动 - 进入退出
     */
    fun easeInOutSine(t: Double): Double = -(Math.cos(Math.PI * t) - 1) / 2

    /**
     * 弹性缓动 - 退出
     */
    fun easeOutElastic(t: Double): Double {
        val p = 0.3
        return Math.pow(2.0, -10.0 * t) * Math.sin((t - p / 4) * (2 * Math.PI) / p) + 1
    }

    /**
     * 回弹缓动 - 进入
     */
    fun easeInBack(t: Double): Double {
        val s = 1.70158
        return t * t * ((s + 1) * t - s)
    }

    /**
     * 回弹缓动 - 退出
     */
    fun easeOutBack(t: Double): Double {
        val s = 1.70158
        val t2 = t - 1
        return t2 * t2 * ((s + 1) * t2 + s) + 1
    }

    /**
     * 回弹缓动 - 进入退出
     */
    fun easeInOutBack(t: Double): Double {
        val s = 1.70158 * 1.525
        return if (t < 0.5) {
            val t2 = t * 2
            (t2 * t2 * ((s + 1) * t2 - s)) / 2
        } else {
            val t2 = t * 2 - 2
            (t2 * t2 * ((s + 1) * t2 + s) + 2) / 2
        }
    }

    /**
     * 圆形缓动 - 进入
     */
    fun easeInCirc(t: Double): Double = -(Math.sqrt(1 - t * t) - 1)

    /**
     * 圆形缓动 - 退出
     */
    fun easeOutCirc(t: Double): Double = Math.sqrt(1 - (t - 1) * (t - 1))

    /**
     * 圆形缓动 - 进入退出
     */
    fun easeInOutCirc(t: Double): Double {
        return if (t < 0.5) {
            (1 - Math.sqrt(1 - 4 * t * t)) / 2
        } else {
            (Math.sqrt(1 - Math.pow(2 * t - 2, 2.0)) + 1) / 2
        }
    }

    /**
     * 四次方缓动 - 进入
     */
    fun easeInQuart(t: Double): Double = t * t * t * t

    /**
     * 四次方缓动 - 退出
     */
    fun easeOutQuart(t: Double): Double = 1 - (--t) * t * t * t

    /**
     * 五次方缓动 - 进入
     */
    fun easeInQuint(t: Double): Double = t * t * t * t * t

    /**
     * 五次方缓动 - 退出
     */
    fun easeOutQuint(t: Double): Double = 1 + (--t) * t * t * t * t
}
