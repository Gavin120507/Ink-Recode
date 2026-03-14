package com.ink.recode.config.adapter

import com.google.gson.*
import java.lang.reflect.Type

/**
 * 颜色序列化器
 */
class ColorSerializer : JsonSerializer<java.awt.Color>, JsonDeserializer<java.awt.Color> {
    override fun serialize(src: java.awt.Color, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        json.addProperty("red", src.red)
        json.addProperty("green", src.green)
        json.addProperty("blue", src.blue)
        json.addProperty("alpha", src.alpha)
        return json
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): java.awt.Color {
        val obj = json.asJsonObject
        return java.awt.Color(
            obj.get("red")?.asInt ?: 255,
            obj.get("green")?.asInt ?: 255,
            obj.get("blue")?.asInt ?: 255,
            obj.get("alpha")?.asInt ?: 255
        )
    }
}

/**
 * 整数范围序列化器
 */
class IntRangeSerializer : JsonSerializer<IntRange>, JsonDeserializer<IntRange> {
    override fun serialize(src: IntRange, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        json.addProperty("min", src.first)
        json.addProperty("max", src.last)
        return json
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): IntRange {
        val obj = json.asJsonObject
        val min = obj.get("min")?.asInt ?: 0
        val max = obj.get("max")?.asInt ?: 0
        return min..max
    }
}

/**
 * 浮点范围序列化器
 */
class FloatRangeSerializer : JsonSerializer<ClosedFloatingPointRange<Float>>, JsonDeserializer<ClosedFloatingPointRange<Float>> {
    override fun serialize(src: ClosedFloatingPointRange<Float>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        json.addProperty("min", src.start)
        json.addProperty("max", src.endInclusive)
        return json
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ClosedFloatingPointRange<Float> {
        val obj = json.asJsonObject
        val min = obj.get("min")?.asFloat ?: 0f
        val max = obj.get("max")?.asFloat ?: 0f
        return min..max
    }
}

/**
 * 注册通用适配器
 */
fun GsonBuilder.registerCommonTypeAdapters(): GsonBuilder {
    return this
        .registerTypeHierarchyAdapter(java.awt.Color::class.java, ColorSerializer())
        .registerTypeHierarchyAdapter(IntRange::class.java, IntRangeSerializer())
        .registerTypeHierarchyAdapter(ClosedFloatingPointRange::class.java, FloatRangeSerializer())
}
