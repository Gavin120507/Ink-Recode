package com.ink.recode.utils

import net.minecraft.client.Minecraft
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object RotationUtils {
    
    private val mc = Minecraft.getInstance()
    
    /**
     * 计算到实体的旋转角度
     */
    fun calculateRotation(entity: Entity): Pair<Float, Float> {
        val player = mc.player ?: return Pair(0f, 0f)
        
        val dx = entity.x - player.x
        val dy = entity.y + entity.eyeHeight - (player.y + player.eyeHeight)
        val dz = entity.z - player.z
        
        val yaw = (Math.toDegrees(atan2(dz, dx)).toFloat() - 90f)
        val distance = sqrt(dx * dx + dz * dz)
        val pitch = -(Math.toDegrees(atan2(dy, distance)).toFloat())
        
        return Pair(yaw, pitch)
    }
    
    /**
     * 计算到实体位置的旋转角度（可指定预测）
     */
    fun calculate(entity: Entity, predict: Boolean = false): Rotation {
        val player = mc.player ?: return Rotation(0f, 0f)
        
        val posX = if (predict) entity.x + entity.deltaMovement.x else entity.x
        val posY = if (predict) entity.y + entity.deltaMovement.y else entity.y
        val posZ = if (predict) entity.z + entity.deltaMovement.z else entity.z
        
        val dx = posX - player.x
        val dy = (posY + entity.eyeHeight * 0.5) - (player.y + player.eyeHeight)
        val dz = posZ - player.z
        
        val yaw = (Math.toDegrees(atan2(dz, dx)).toFloat() - 90f)
        val distance = sqrt(dx * dx + dz * dz)
        val pitch = -(Math.toDegrees(atan2(dy, distance)).toFloat())
        
        return Rotation(yaw, pitch)
    }
    
    /**
     * RayCast 检测
     */
    fun rayCast(rotation: Rotation, range: Double): Entity? {
        val player = mc.player ?: return null
        val world = mc.level ?: return null
        
        val yawRad = Math.toRadians(rotation.yaw.toDouble())
        val pitchRad = Math.toRadians(rotation.pitch.toDouble())
        
        val x = -cos(yawRad) * cos(pitchRad)
        val y = -sin(pitchRad)
        val z = -sin(yawRad) * cos(pitchRad)
        
        val startPos = Vec3(player.x, player.y + player.eyeHeight, player.z)
        val endPos = startPos.add(x * range, y * range, z * range)
        
        val hitResult = world.clip(startPos, endPos)
        
        // 简单的实体检测
        var closestEntity: Entity? = null
        var closestDist = range
        
        for (entity in world.entities) {
            if (entity == player || entity !is LivingEntity) continue
            
            val dist = player.distanceTo(entity)
            if (dist < closestDist) {
                closestDist = dist
                closestEntity = entity
            }
        }
        
        return closestEntity
    }
    
    /**
     * 旋转插值
     */
    fun interpolateRotation(current: Rotation, target: Rotation, speed: Float): Rotation {
        val yawDiff = MathUtils.angleDifference(target.yaw, current.yaw)
        val pitchDiff = target.pitch - current.pitch
        
        val newYaw = current.yaw + yawDiff * (speed / 180f)
        val newPitch = (current.pitch + pitchDiff * (speed / 180f)).coerceIn(-90f, 90f)
        
        return Rotation(newYaw, newPitch)
    }
}

data class Rotation(
    var yaw: Float,
    var pitch: Float
)
