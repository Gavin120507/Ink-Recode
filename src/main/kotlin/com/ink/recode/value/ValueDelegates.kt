package com.ink.recode.value

import kotlin.reflect.KProperty

/**
 * BooleanValue 委托属性
 */
operator fun BooleanValue.getValue(thisRef: Any?, property: KProperty<*>): Boolean {
    return get()
}

operator fun BooleanValue.setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
    set(value)
}

/**
 * NumberValue 委托属性
 */
operator fun NumberValue.getValue(thisRef: Any?, property: KProperty<*>): Float {
    return get()
}

operator fun NumberValue.setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
    set(value)
}

/**
 * IntValue 委托属性
 */
operator fun IntValue.getValue(thisRef: Any?, property: KProperty<*>): Int {
    return get()
}

operator fun IntValue.setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
    set(value)
}

/**
 * TextValue 委托属性
 */
operator fun TextValue.getValue(thisRef: Any?, property: KProperty<*>): String {
    return get()
}

operator fun TextValue.setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    set(value)
}

/**
 * ModeValue 委托属性
 */
operator fun ModeValue.getValue(thisRef: Any?, property: KProperty<*>): Int {
    return get()
}

operator fun ModeValue.setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
    set(value)
}

/**
 * ColorValue 委托属性
 */
operator fun ColorValue.getValue(thisRef: Any?, property: KProperty<*>): java.awt.Color {
    return get()
}

operator fun ColorValue.setValue(thisRef: Any?, property: KProperty<*>, value: java.awt.Color) {
    set(value)
}

/**
 * KeyBindValue 委托属性
 */
operator fun KeyBindValue.getValue(thisRef: Any?, property: KProperty<*>): Int {
    return get()
}

operator fun KeyBindValue.setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
    set(value)
}
