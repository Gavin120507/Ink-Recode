package com.ink.recode.modules.impl.movement

import com.ink.recode.Category
import com.ink.recode.Module
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import org.lwjgl.glfw.GLFW

// 全局MC实例引用（简化代码）
val mc = MinecraftClient.getInstance()

object Sprint : Module("Sprint", "Auto sprint only when possible", Category.MOVEMENT) {

    init {
        this.enabled = true
        this.key = GLFW.GLFW_KEY_I
    }

    override fun onTick() {
        // 1. 空安全检查：确保玩家和世界存在
        val player = mc.player ?: return

        // 2. 判断是否满足原生疾跑条件（和游戏内置逻辑一致）
        if (canSprint(player)) {
            player.isSprinting = true
        }
    }

    /**
     * 判断玩家是否满足疾跑条件（复刻游戏原生逻辑）
     */
    private fun canSprint(player: PlayerEntity): Boolean {
        return !player.isSprinting &&          // 还没在疾跑
                !player.isUsingItem &&         // 没有在使用物品（比如吃东西/喝药水）
                !player.isBlocking &&          // 没有举盾
                !player.isFallFlying &&        // 不是鞘翅飞行
                !player.isDead &&              // 没有死亡
                player.hungerManager.foodLevel > 6 &&  // 饱食度>6（游戏最低疾跑要求）
                player.forwardSpeed > 0.0f &&  // 向前移动按键被按下
                !player.isSneaking &&          // 没有潜行
                !player.isTouchingWater &&     // 没有在水里（可选：根据需求保留/删除）
                !player.isInLava              // 没有在岩浆里（可选：根据需求保留/删除）
    }
}