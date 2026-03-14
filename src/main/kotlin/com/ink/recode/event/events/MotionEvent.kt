package com.ink.recode.event.events

import com.ink.recode.event.Event

enum class EventType {
    PRE,
    POST
}

class MotionEvent(
    val type: EventType,
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var onGround: Boolean = false
) : Event()
