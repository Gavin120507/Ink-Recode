package com.ink.recode.utils

import net.minecraft.client.Minecraft
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.*
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.Enchantments

object InventoryUtils {
    
    private val mc = Minecraft.getInstance()
    
    /**
     * 检查物品是否是神器（附魔保护 IV）
     */
    fun isGodItem(stack: ItemStack): Boolean {
        if (stack.isEmpty()) return false
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val protLevel = enchantments[Enchantments.ALL_DAMAGE_PROTECTION] ?: 0
        return protLevel >= 4
    }
    
    /**
     * 检查剑是否有锋利 V
     */
    fun isSharpnessAxe(stack: ItemStack): Boolean {
        if (stack.item !is AxeItem && stack.item !is SwordItem) return false
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val sharpLevel = enchantments[Enchantments.SHARPNESS] ?: 0
        return sharpLevel >= 5
    }
    
    /**
     * 获取物品的保护值
     */
    fun getProtection(stack: ItemStack): Float {
        if (stack.isEmpty()) return 0f
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        var prot = 0f
        
        enchantments.forEach { (enchantment, level) ->
            when (enchantment) {
                Enchantments.ALL_DAMAGE_PROTECTION -> prot += level * 1.5f
                Enchantments.FIRE_PROTECTION -> prot += level * 0.75f
                Enchantments.BLAST_PROTECTION -> prot += level * 0.75f
                Enchantments.PROJECTILE_PROTECTION -> prot += level * 0.75f
            }
        }
        
        return prot
    }
    
    /**
     * 获取最佳防具分数
     */
    fun getBestArmorScore(slot: EquipmentSlot): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item is ArmorItem) {
                val armorItem = stack.item as ArmorItem
                if (armorItem.slot == slot) {
                    val score = getProtection(stack)
                    if (score > bestScore) bestScore = score
                }
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取剑的伤害值
     */
    fun getSwordDamage(stack: ItemStack): Float {
        if (stack.item !is SwordItem) return 0f
        
        val sword = stack.item as SwordItem
        var damage = sword.attackDamageBonus.toFloat()
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val sharpLevel = enchantments[Enchantments.SHARPNESS] ?: 0
        damage += sharpLevel * 1.25f
        
        return damage
    }
    
    /**
     * 获取最佳剑伤害
     */
    fun getBestSwordDamage(): Float {
        val player = mc.player ?: return 0f
        
        var bestDamage = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            val damage = getSwordDamage(stack)
            if (damage > bestDamage) bestDamage = damage
        }
        
        return bestDamage
    }
    
    /**
     * 获取工具分数
     */
    fun getToolScore(stack: ItemStack): Float {
        if (stack.isEmpty()) return 0f
        
        var score = 0f
        
        // 耐久度分数
        val maxDamage = stack.maxDamage
        val currentDamage = stack.damageValue
        score += (maxDamage - currentDamage) / maxDamage.toFloat() * 50f
        
        // 效率附魔
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val effiLevel = enchantments[Enchantments.BLOCK_EFFICIENCY] ?: 0
        score += effiLevel * 10f
        
        // 时运附魔
        val fortuneLevel = enchantments[Enchantments.BLOCK_FORTUNE] ?: 0
        score += fortuneLevel * 15f
        
        return score
    }
    
    /**
     * 获取最佳镐子分数
     */
    fun getBestPickaxeScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item is PickaxeItem) {
                val score = getToolScore(stack)
                if (score > bestScore) bestScore = score
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取最佳斧头分数
     */
    fun getBestAxeScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item is AxeItem) {
                val score = getToolScore(stack)
                if (score > bestScore) bestScore = score
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取最佳铲子分数
     */
    fun getBestShovelScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item is ShovelItem) {
                val score = getToolScore(stack)
                if (score > bestScore) bestScore = score
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取弩的分数
     */
    fun getCrossbowScore(stack: ItemStack): Float {
        if (stack.item !is CrossbowItem) return 0f
        
        var score = 0f
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val quickCharge = enchantments[Enchantments.QUICK_CHARGE_CROSSBOW] ?: 0
        val multishot = enchantments[Enchantments.MULTISHOT] ?: 0
        val piercing = enchantments[Enchantments.PIERCING_CROSSBOW] ?: 0
        
        score += quickCharge * 20f
        score += multishot * 30f
        score += piercing * 15f
        
        return score
    }
    
    /**
     * 获取最佳弩分数
     */
    fun getBestCrossbowScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            val score = getCrossbowScore(stack)
            if (score > bestScore) bestScore = score
        }
        
        return bestScore
    }
    
    /**
     * 检查弓是否有力量 V
     */
    fun isPowerBow(stack: ItemStack): Boolean {
        if (stack.item !is BowItem) return false
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val powerLevel = enchantments[Enchantments.POWER_ARROWS] ?: 0
        return powerLevel >= 5
    }
    
    /**
     * 检查弓是否有冲击 II
     */
    fun isPunchBow(stack: ItemStack): Boolean {
        if (stack.item !is BowItem) return false
        
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        val punchLevel = enchantments[Enchantments.PUNCH_ARROWS] ?: 0
        return punchLevel >= 2
    }
    
    /**
     * 获取力量弓分数
     */
    fun getPowerBowScore(stack: ItemStack): Float {
        if (!isPowerBow(stack)) return 0f
        
        var score = 0f
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        
        val powerLevel = enchantments[Enchantments.POWER_ARROWS] ?: 0
        val punchLevel = enchantments[Enchantments.PUNCH_ARROWS] ?: 0
        val flameLevel = enchantments[Enchantments.FLAMING_ARROWS] ?: 0
        val infinityLevel = enchantments[Enchantments.INFINITY_ARROWS] ?: 0
        
        score += powerLevel * 20f
        score += punchLevel * 15f
        score += flameLevel * 10f
        score += infinityLevel * 25f
        
        return score
    }
    
    /**
     * 获取最佳力量弓分数
     */
    fun getBestPowerBowScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (isPowerBow(stack)) {
                val score = getPowerBowScore(stack)
                if (score > bestScore) bestScore = score
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取冲击弓分数
     */
    fun getPunchBowScore(stack: ItemStack): Float {
        if (!isPunchBow(stack)) return 0f
        
        var score = 0f
        val enchantments = EnchantmentHelper.getEnchantments(stack)
        
        val punchLevel = enchantments[Enchantments.PUNCH_ARROWS] ?: 0
        val powerLevel = enchantments[Enchantments.POWER_ARROWS] ?: 0
        
        score += punchLevel * 25f
        score += powerLevel * 15f
        
        return score
    }
    
    /**
     * 获取最佳冲击弓分数
     */
    fun getBestPunchBowScore(): Float {
        val player = mc.player ?: return 0f
        
        var bestScore = 0f
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (isPunchBow(stack)) {
                val score = getPunchBowScore(stack)
                if (score > bestScore) bestScore = score
            }
        }
        
        return bestScore
    }
    
    /**
     * 获取物品数量
     */
    fun getItemCount(item: Item): Int {
        val player = mc.player ?: return 0
        
        var count = 0
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item == item) {
                count += stack.count
            }
        }
        
        return count
    }
    
    /**
     * 检查物品是否有用
     */
    fun isCommonItemUseful(stack: ItemStack): Boolean {
        if (stack.isEmpty()) return false
        
        return when (stack.item) {
            Items.TOTEM_OF_UNDYING,
            Items.ENDER_PEARL,
            Items.GOLDEN_APPLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.OBSIDIAN,
            Items.CRYING_OBSIDIAN,
            Items.ANCIENT_DEBRIS,
            Items.NETHERITE_INGOT,
            Items.NETHERITE_SCRAP,
            Items.DIAMOND,
            Items.EMERALD,
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.ENDER_CHEST -> true
            else -> false
        }
    }
    
    /**
     * 获取背包中方块的数量
     */
    fun getBlockCountInInventory(): Int {
        val player = mc.player ?: return 0
        
        var count = 0
        for (i in 0 until player.inventory.containerSize) {
            val stack = player.inventory.getItem(i)
            if (stack.item is BlockItem) {
                count += stack.count
            }
        }
        
        return count
    }
}
