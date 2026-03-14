package com.ink.recode.mixin;

import com.ink.recode.event.EventBus;
import com.ink.recode.event.events.PacketEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 网络包 Mixin - 拦截客户端网络包
 */
@Mixin(ClientPlayNetworkHandler.class)
public class PacketMixin {
    
    /**
     * 拦截发送到服务端的包
     */
    @Inject(at = @At("HEAD"), method = "sendPacket(Lnet/minecraft/network/packet/Packet;)V", cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = new PacketEvent(packet, PacketEvent.PacketType.SEND);
        EventBus.INSTANCE.post(event);
        
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    /**
     * 拦截从服务端接收的包
     */
    @Inject(at = @At("HEAD"), method = "onPacket", cancellable = true)
    private void onReceivePacket(Packet<?> packet, CallbackInfo ci) {
        PacketEvent event = new PacketEvent(packet, PacketEvent.PacketType.RECEIVE);
        EventBus.INSTANCE.post(event);
        
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
