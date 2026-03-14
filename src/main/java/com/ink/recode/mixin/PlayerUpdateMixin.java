package com.ink.recode.mixin;

import com.ink.recode.event.EventBus;
import com.ink.recode.event.events.UpdateEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class PlayerUpdateMixin {
    
    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo ci) {
        EventBus.INSTANCE.post(new UpdateEvent());
    }
}
