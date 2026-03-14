package com.ink.recode.utils

import kotlin.math.*

object MathUtils {
    
    /**
     * 计算两点之间的距离
     */
    fun distance(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double): Double {
        val dx = x2 - x1
        val dy = y2 - y1
        val dz = z2 - z1
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
    
    /**
     * 计算水平距离
     */
    fun horizontalDistance(x1: Double, z1: Double, x2: Double, z2: Double): Double {
        val dx = x2 - x1
        val dz = z2 - z1
        return sqrt(dx * dx + dz * dz)
    }
    
    /**
     * 角度标准化到 -180 到 180
     */
    fun normalizeAngle(angle: Float): Float {
        var normalized = angle % 360f
        if (normalized > 180f) normalized -= 360f
        if (normalized < -180f) normalized += 360f
        return normalized
    }
    
    /**
     * 计算角度差
     */
    fun angleDifference(a: Float, b: Float): Float {
        return normalizeAngle(a - b)
    }
    
    /**
     * 线性插值
     */
    fun lerp(start: Float, end: Float, t: Float): Float {
        return start + (end - start) * t
    }
    
    /**
     * 双线性插值
     */
    fun lerp(start: Double, end: Double, t: Double): Double {
        return start + (end - start) * t
    }
    
    /**
     * 将值限制在范围内
     */
    fun clamp(value: Float, min: Float, max: Float): Float {
        return value.coerceIn(min, max)
    }
    
    /**
     * 生成高斯随机数
     */
    fun gaussianRandom(mean: Double = 0.0, stdDev: Double = 1.0): Double {
        val u1 = Random.nextDouble()
        val u2 = Random.nextDouble()
        val z0 = sqrt(-2.0 * ln(u1)) * cos(2.0 * PI * u2)
        return z0 * stdDev + mean
    }
    
    /**
     * 生成范围内的高斯随机整数
     */
    fun gaussianRandomIntInRange(min: Int, max: Int): Int {
        val mean = (min + max) / 2.0
        val stdDev = (max - min) / 6.0
        return gaussianRandom(mean, stdDev).toInt().coerceIn(min, max)
    }
    
    /**
     * 弧度转角度
     */
    fun toDegrees(radians: Double): Float {
        return Math.toDegrees(radians).toFloat()
    }
    
    /**
     * 角度转弧度
     */
    fun toRadians(degrees: Float): Double {
        return Math.toRadians(degrees.toDouble())
    }
    
    /**
     * 计算向量的模
     */
    fun magnitude(x: Double, y: Double, z: Double): Double {
        return sqrt(x * x + y * y + z * z)
    }
    
    /**
     * 归一化向量
     */
    fun normalize(x: Double, y: Double, z: Double): Triple<Double, Double, Double> {
        val mag = magnitude(x, y, z)
        return Triple(x / mag, y / mag, z / mag)
    }
}
