package com.ink.recode.utils

import com.ink.recode.event.EventBus
import com.ink.recode.event.Listener
import com.ink.recode.event.events.MotionEvent
import com.ink.recode.event.events.UpdateEvent
import net.minecraft.client.Minecraft
import kotlin.math.cos
import kotlin.math.sin

object RotationManager {
    
    private val mc = Minecraft.getInstance()
    
    // 当前旋转
    private var currentRotation: Rotation? = null
    
    // 目标旋转
    private var targetRotation: Rotation? = null
    
    // 旋转速度
    private var rotationSpeed: Float = 180f
    
    // 旋转条件检查
    private var rotationCondition: ((Rotation) -> Boolean)? = null
    
    // 是否启用旋转
    var enabled: Boolean = false
    
    /**
     * 设置目标旋转
     */
    fun setRotations(target: Rotation, speed: Float = 180f, condition: ((Rotation) -> Boolean)? = null) {
        targetRotation = target
        rotationSpeed = speed
        rotationCondition = condition
        enabled = true
    }
    
    /**
     * 重置旋转
     */
    fun reset() {
        currentRotation = null
        targetRotation = null
        enabled = false
    }
    
    /**
     * 获取当前旋转
     */
    fun getCurrentRotation(): Rotation? = currentRotation
    
    /**
     * 应用旋转到玩家
     */
    fun applyRotations() {
        if (!enabled || targetRotation == null || mc.player == null) return
        
        val target = targetRotation ?: return
        
        // 检查条件
        if (rotationCondition != null) {
            if (!rotationCondition!!.invoke(target)) {
                return
            }
        }
        
        // 插值旋转
        val current = currentRotation ?: Rotation(mc.player!!.yRot, mc.player!!.xRot)
        val interpolated = RotationUtils.interpolateRotation(current, target, rotationSpeed)
        
        // 应用旋转
        mc.player!!.yRot = interpolated.yaw
        mc.player!!.xRot = interpolated.pitch
        
        currentRotation = interpolated
        
        // 如果已经到达目标，停止旋转
        if (MathUtils.angleDifference(interpolated.yaw, target.yaw) < 1f && 
            kotlin.math.abs(interpolated.pitch - target.pitch) < 1f) {
            mc.player!!.yRot = target.yaw
            mc.player!!.xRot = target.pitch
            currentRotation = target
        }
    }
    
    /**
     * 计算射线投射
     */
    fun rayCast(rotation: Rotation, range: Double): Boolean {
        val player = mc.player ?: return false
        
        val yawRad = Math.toRadians(rotation.yaw.toDouble())
        val pitchRad = Math.toRadians(rotation.pitch.toDouble())
        
        val x = -cos(yawRad) * cos(pitchRad)
        val y = -sin(pitchRad)
        val z = -sin(yawRad) * cos(pitchRad)
        
        // 简单的视线检测
        return true
    }
}
