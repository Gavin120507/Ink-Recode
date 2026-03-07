package com.ink.recode.mixin;

import com.ink.recode.event.EventBus;
import com.ink.recode.event.events.KeyboardEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.lwjgl.glfw.GLFW;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci)
    {
        if(action == GLFW.GLFW_PRESS)
        {
            EventBus.INSTANCE.post(new KeyboardEvent(key));
        }
    }
}