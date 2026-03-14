package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity

/**
 * 攻击事件
 */
class AttackEvent(
    val entity: Entity
) : Event()

/**
 * 死亡事件
 */
class DeathEvent(
    val entity: LivingEntity
) : Event()

/**
 * 生命更新事件
 */
class HealthUpdateEvent(
    val entity: LivingEntity,
    val health: Float
) : Event()

/**
 * 世界变更事件
 */
class WorldChangeEvent(
    val oldWorld: net.minecraft.client.world.ClientWorld?,
    val newWorld: net.minecraft.client.world.ClientWorld?
) : Event()

/**
 * 方块变更事件
 */
class BlockChangeEvent(
    val x: Int,
    val y: Int,
    val z: Int,
    val oldBlock: net.minecraft.block.BlockState,
    val newBlock: net.minecraft.block.BlockState
) : Event()

/**
 * 聊天接收事件
 */
class ChatReceiveEvent(
    var message: String
) : Event()

/**
 * 聊天发送事件
 */
class ChatSendEvent(
    var message: String
) : Event()

/**
 * 断开连接事件
 */
class DisconnectEvent : Event()

/**
 * 屏幕事件
 */
class ScreenEvent(
    val screen: net.minecraft.client.gui.screen.Screen?
) : Event()

/**
 * 玩家 Tick 事件
 */
class PlayerTickEvent : Event()

/**
 * 玩家移动事件
 */
class PlayerMoveEvent(
    var x: Double,
    var y: Double,
    var z: Double
) : Event()

/**
 * 玩家跳跃事件
 */
class PlayerJumpEvent : Event()

/**
 * 值变更事件
 */
class ValueChangedEvent(
    val valueName: String,
    val oldValue: Any?,
    val newValue: Any?
) : Event()

/**
 * 模块切换事件
 */
class ToggleModuleEvent(
    val moduleName: String,
    val enabled: Boolean
) : Event()
