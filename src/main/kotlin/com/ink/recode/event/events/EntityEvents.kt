package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.entity.Entity
import net.minecraft.network.packet.Packet

/**
 * 实体事件
 */

/**
 * 攻击事件
 */
class AttackEvent(val enemy: Entity) : Event()

/**
 * 实体边缘事件
 */
class EntityMarginEvent(
    val entity: Entity,
    var margin: Float
) : Event()

/**
 * 网络事件
 */

/**
 * 管道事件
 */
class PipelineEvent(val channelPipeline: Any) : Event()

/**
 * 包事件
 */
class PacketEvent(
    val origin: TransferOrigin,
    val packet: Packet<*>,
    val original: Boolean = true
) : Event() {
    var cancelled = false
}

/**
 * 传输方向
 */
enum class TransferOrigin {
    SEND,
    RECEIVE
}
