package com.ink.recode.mixin;

import com.ink.recode.event.EventBus;
import com.ink.recode.event.events.MotionEvent;
import com.ink.recode.event.events.EventType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class PlayerMoveMixin {
    
    @Inject(at = @At("HEAD"), method = "sendMovementPackets", cancellable = true)
    private void onSendMovementPacketsPre(CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            MotionEvent event = new MotionEvent(
                EventType.PRE,
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYaw(),
                player.getPitch(),
                player.isOnGround()
            );
            EventBus.INSTANCE.post(event);
            
            if (event.isCancelled()) {
                ci.cancel();
            }
        }
    }
    
    @Inject(at = @At("TAIL"), method = "sendMovementPackets")
    private void onSendMovementPacketsPost(CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            MotionEvent event = new MotionEvent(
                EventType.POST,
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYaw(),
                player.getPitch(),
                player.isOnGround()
            );
            EventBus.INSTANCE.post(event);
        }
    }
}
