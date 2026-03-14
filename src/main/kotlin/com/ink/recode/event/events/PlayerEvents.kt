package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.entity.MovementType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.Vec3d

/**
 * 玩家事件
 */

/**
 * 生命更新事件
 */
class HealthUpdateEvent(
    val health: Float,
    val food: Int,
    val saturation: Float,
    val previousHealth: Float
) : Event()

/**
 * 死亡事件
 */
class DeathEvent : Event()

/**
 * 玩家 Tick 事件
 */
class PlayerTickEvent : Event() {
    var cancelled = false
}

/**
 * 玩家后 Tick 事件
 */
class PlayerPostTickEvent : Event()

/**
 * 玩家移动 Tick 事件
 */
class PlayerMovementTickEvent : Event()

/**
 * 玩家网络移动 Tick 事件
 */
class PlayerNetworkMovementTickEvent(val state: EventState) : Event()

/**
 * 事件状态
 */
enum class EventState {
    PRE,
    POST
}

/**
 * 玩家推开事件
 */
class PlayerPushOutEvent : Event() {
    var cancelled = false
}

/**
 * 玩家移动事件
 */
class PlayerMoveEvent(
    val type: MovementType,
    val movement: Vec3d
) : Event()

/**
 * 旋转移动输入事件
 */
class RotatedMovementInputEvent(
    var forward: Float,
    var sideways: Float
) : Event()

/**
 * 玩家跳跃事件
 */
class PlayerJumpEvent(var motion: Float) : Event() {
    var cancelled = false
}

/**
 * 玩家跳跃后事件
 */
class PlayerAfterJumpEvent : Event()

/**
 * 玩家使用倍率事件
 */
class PlayerUseMultiplier(
    var forward: Float,
    var sideways: Float
) : Event()

/**
 * 玩家物品交互事件
 */
class PlayerInteractedItem(
    val player: PlayerEntity,
    val hand: Hand,
    val actionResult: ActionResult
) : Event()

/**
 * 玩家横移事件
 */
class PlayerVelocityStrafe(
    val movementInput: Vec3d,
    val speed: Float,
    val yaw: Float,
    var velocity: Vec3d
) : Event()

/**
 * 玩家跨步事件
 */
class PlayerStrideEvent(var strideForce: Float) : Event()

/**
 * 玩家安全行走事件
 */
class PlayerSafeWalkEvent(var isSafeWalk: Boolean = false) : Event()

/**
 * 玩家跨步事件
 */
class PlayerStepEvent(var height: Float) : Event()

/**
 * 玩家跨步成功事件
 */
class PlayerStepSuccessEvent(
    val movementVec: Vec3d,
    var adjustedVec: Vec3d
) : Event()
