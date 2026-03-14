package com.ink.recode.value

import java.awt.Color

/**
 * 创建 BooleanValue
 */
fun boolean(name: String, default: Boolean = false, description: String = ""): BooleanValue {
    return BooleanValue(name, default, description)
}

/**
 * 创建 NumberValue
 */
fun float(name: String, default: Float, range: ClosedFloatingPointRange<Float> = 0.0f..1.0f, description: String = ""): NumberValue {
    return NumberValue(name, default, range.start, range.endInclusive, 0.01f, description)
}

/**
 * 创建 IntValue
 */
fun int(name: String, default: Int, range: IntRange = 0..100, step: Int = 1, description: String = ""): IntValue {
    return IntValue(name, default, range.first, range.last, step, description)
}

/**
 * 创建 TextValue
 */
fun text(name: String, default: String = "", description: String = ""): TextValue {
    return TextValue(name, default, description)
}

/**
 * 创建 ModeValue
 */
fun mode(name: String, options: List<String>, default: String = options.first(), description: String = ""): ModeValue {
    val index = options.indexOf(default)
    return ModeValue(name, options, if (index >= 0) index else 0, description)
}

/**
 * 创建 ColorValue
 */
fun color(name: String, default: Color, description: String = ""): ColorValue {
    return ColorValue(name, default, description)
}

/**
 * 创建 KeyBindValue
 */
fun key(name: String, default: Int = -1, description: String = ""): KeyBindValue {
    return KeyBindValue(name, default, description)
}

/**
 * 创建 ChoiceValue
 */
fun <T : Choice> choice(name: String, choices: Array<T>, default: T, description: String = ""): ChoiceValue {
    return ChoiceValue(name, choices, default, description)
}
