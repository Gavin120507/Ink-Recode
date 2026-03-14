package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape

/**
 * 世界事件
 */

/**
 * 世界变化事件
 */
class WorldChangeEvent(val world: ClientWorld?) : Event()

/**
 * 区块卸载事件
 */
class ChunkUnloadEvent(val x: Int, val z: Int) : Event()

/**
 * 区块加载事件
 */
class ChunkLoadEvent(val x: Int, val z: Int) : Event()

/**
 * 区块更新事件
 */
class ChunkDeltaUpdateEvent(val x: Int, val z: Int) : Event()

/**
 * 方块变化事件
 */
class BlockChangeEvent(
    val blockPos: BlockPos,
    val newState: BlockState
) : Event()

/**
 * 方块形状事件
 */
class BlockShapeEvent(
    val state: BlockState,
    val pos: BlockPos,
    var shape: VoxelShape
) : Event()

/**
 * 方块破坏进度事件
 */
class BlockBreakingProgressEvent(val pos: BlockPos) : Event()

/**
 * 方块速度倍率事件
 */
class BlockVelocityMultiplierEvent(
    val block: Block,
    var multiplier: Float
) : Event()

/**
 * 方块滑度倍率事件
 */
class BlockSlipperinessMultiplierEvent(
    val block: Block,
    var slipperiness: Float
) : Event()

/**
 * 流体推动事件
 */
class FluidPushEvent : Event() {
    var cancelled = false
}
