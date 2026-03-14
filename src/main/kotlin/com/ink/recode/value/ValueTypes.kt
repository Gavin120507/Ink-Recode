package com.ink.recode.value

import net.minecraft.block.Block
import net.minecraft.item.Item
import java.awt.Color

/**
 * 文本值
 */
class TextValue(
    name: String,
    default: String,
    description: String = ""
) : Value<String>(name, description) {
    
    private var value: String = default
    
    override fun get(): String = value
    override fun set(v: String) { value = v }
    
    fun setByString(string: String) {
        value = string
    }
}

/**
 * 整数
 */
class IntValue(
    name: String,
    default: Int,
    val min: Int,
    val max: Int,
    val step: Int = 1,
    description: String = ""
) : Value<Int>(name, description) {
    
    private var value: Int = default.coerceIn(min, max)
    
    override fun get(): Int = value
    override fun set(v: Int) {
        value = v.coerceIn(min, max)
    }
    
    fun add() { value = (value + step).coerceIn(min, max) }
    fun sub() { value = (value - step).coerceIn(min, max) }
    
    fun setByString(string: String) {
        string.toIntOrNull()?.let { set(it) }
    }
}

/**
 * 整数范围
 */
class IntRangeValue(
    name: String,
    defaultMin: Int,
    defaultMax: Int,
    val min: Int,
    val max: Int,
    description: String = ""
) : Value<IntRange>(name, description) {
    
    private var minVal = defaultMin.coerceIn(min, max)
    private var maxVal = defaultMax.coerceIn(min, max)
    
    override fun get(): IntRange = minVal..maxVal
    
    override fun set(v: IntRange) {
        minVal = v.first.coerceIn(min, max)
        maxVal = v.last.coerceIn(min, max)
        if (minVal > maxVal) {
            minVal = maxVal
        }
    }
    
    fun setMin(v: Int) {
        minVal = v.coerceIn(min, maxVal)
    }
    
    fun setMax(v: Int) {
        maxVal = v.coerceIn(minVal, max)
    }
}

/**
 * 浮点数范围
 */
class FloatRangeValue(
    name: String,
    defaultMin: Float,
    defaultMax: Float,
    val min: Float,
    val max: Float,
    description: String = ""
) : Value<ClosedFloatingPointRange<Float>>(name, description) {
    
    private var minVal = defaultMin.coerceIn(min, max)
    private var maxVal = defaultMax.coerceIn(min, max)
    
    override fun get(): ClosedFloatingPointRange<Float> = minVal..maxVal
    
    override fun set(v: ClosedFloatingPointRange<Float>) {
        minVal = v.start.coerceIn(min, max)
        maxVal = v.endInclusive.coerceIn(min, max)
        if (minVal > maxVal) {
            minVal = maxVal
        }
    }
}

/**
 * 文本数组
 */
class TextArrayValue(
    name: String,
    default: List<String> = emptyList(),
    description: String = ""
) : Value<List<String>>(name, description) {
    
    private var value: List<String> = default
    
    override fun get(): List<String> = value
    override fun set(v: List<String>) { value = v }
    
    fun add(str: String) {
        value = value + str
    }
    
    fun remove(str: String) {
        value = value - str
    }
}

/**
 * 颜色值
 */
class ColorValue(
    name: String,
    default: Color,
    description: String = ""
) : Value<Color>(name, description) {
    
    private var value: Color = default
    
    override fun get(): Color = value
    override fun set(v: Color) { value = v }
    
    fun setByString(string: String) {
        try {
            val parts = string.split(",").map { it.trim().toInt() }
            if (parts.size == 3) {
                value = Color(parts[0], parts[1], parts[2])
            } else if (parts.size == 4) {
                value = Color(parts[0], parts[1], parts[2], parts[3])
            }
        } catch (e: Exception) {
            // 忽略解析错误
        }
    }
}

/**
 * 方块值
 */
class BlockValue(
    name: String,
    default: Block,
    description: String = ""
) : Value<Block>(name, description) {
    
    private var value: Block = default
    
    override fun get(): Block = value
    override fun set(v: Block) { value = v }
    
    fun setByString(string: String) {
        // 通过方块 ID 或名称设置
        Block.REGISTRY.getOrEmpty(string).ifPresent { value = it }
    }
}

/**
 * 方块列表
 */
class BlocksValue(
    name: String,
    default: List<Block> = emptyList(),
    description: String = ""
) : Value<List<Block>>(name, description) {
    
    private var value: List<Block> = default
    
    override fun get(): List<Block> = value
    override fun set(v: List<Block>) { value = v }
    
    fun add(block: Block) {
        value = value + block
    }
    
    fun remove(block: Block) {
        value = value - block
    }
}

/**
 * 物品值
 */
class ItemValue(
    name: String,
    default: Item,
    description: String = ""
) : Value<Item>(name, description) {
    
    private var value: Item = default
    
    override fun get(): Item = value
    override fun set(v: Item) { value = v }
    
    fun setByString(string: String) {
        Item.REGISTRY.getOrEmpty(string).ifPresent { value = it }
    }
}

/**
 * 物品列表
 */
class ItemsValue(
    name: String,
    default: List<Item> = emptyList(),
    description: String = ""
) : Value<List<Item>>(name, description) {
    
    private var value: List<Item> = default
    
    override fun get(): List<Item> = value
    override fun set(v: List<Item>) { value = v }
    
    fun add(item: Item) {
        value = value + item
    }
    
    fun remove(item: Item) {
        value = value - item
    }
}

/**
 * 按键绑定值
 */
class KeyBindValue(
    name: String,
    default: Int,
    description: String = ""
) : Value<Int>(name, description) {
    
    private var value: Int = default
    
    override fun get(): Int = value
    override fun set(v: Int) { value = v }
    
    fun setByString(string: String) {
        string.toIntOrNull()?.let { value = it }
    }
}

/**
 * 选择接口
 */
interface Choice {
    val name: String
    val description: String
}

/**
 * 命名选择
 */
enum class NamedChoice(override val name: String, override val description: String = "") : Choice {
    NORMAL("Normal", "Normal mode"),
    SILENT("Silent", "Silent mode"),
    SMART("Smart", "Smart mode")
}

/**
 * 选择值
 */
class ChoiceValue(
    name: String,
    val choices: Array<Choice>,
    default: Choice,
    description: String = ""
) : Value<Choice>(name, description) {
    
    private var value: Choice = default
    
    override fun get(): Choice = value
    override fun set(v: Choice) { value = v }
    
    val current: Choice get() = value
    
    fun cycle() {
        val index = choices.indexOf(value)
        value = choices[(index + 1) % choices.size]
    }
    
    fun setByString(string: String) {
        choices.find { it.name.equals(string, ignoreCase = true) }?.let { value = it }
    }
}

/**
 * 代理值
 */
class ProxyValue(
    name: String,
    description: String = ""
) : Value<Proxy>(name, description) {
    
    private var value: Proxy? = null
    
    override fun get(): Proxy? = value
    override fun set(v: Proxy?) { value = v }
    
    data class Proxy(
        val host: String,
        val port: Int,
        val username: String? = null,
        val password: String? = null
    )
}
