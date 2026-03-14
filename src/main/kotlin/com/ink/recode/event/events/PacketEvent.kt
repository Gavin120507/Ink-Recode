package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.network.packet.Packet

/**
 * 网络包事件
 */
class PacketEvent(
    val packet: Packet<*>,
    val type: PacketType
) : Event() {
    
    enum class PacketType {
        SEND,      // 客户端发送到服务端
        RECEIVE    // 服务端接收到客户端
    }
}
