package com.ink.recode.utils

import net.minecraft.client.Minecraft

class TickTimeHelper {
    
    private var lastTime = 0L
    private var ticks = 0
    
    companion object {
        private val mc = Minecraft.getInstance()
        
        /**
         * 延迟指定的游戏刻
         */
        fun delay(ticks: Int): Boolean {
            val currentTime = System.currentTimeMillis()
            val gameTime = mc.player?.tickCount?.toLong() ?: currentTime / 50
            
            return gameTime - ticks >= ticks
        }
    }
    
    /**
     * 重置计时器
     */
    fun reset() {
        lastTime = System.currentTimeMillis()
        ticks = 0
    }
    
    /**
     * 检查是否已经延迟了指定的游戏刻
     */
    fun delay(delayTicks: Int): Boolean {
        val currentTime = System.currentTimeMillis()
        val gameTime = mc.player?.tickCount?.toLong() ?: currentTime / 50
        
        if (gameTime - ticks >= delayTicks) {
            ticks = gameTime.toInt()
            return true
        }
        
        return false
    }
    
    /**
     * 获取经过的刻数
     */
    fun getElapsedTicks(): Int {
        val currentTime = System.currentTimeMillis()
        val gameTime = mc.player?.tickCount?.toLong() ?: currentTime / 50
        return (gameTime - ticks).toInt()
    }
}
