package com.ink.recode.mixin;

import com.ink.recode.event.EventBus;
import com.ink.recode.event.events.Render3DEvent;
import com.ink.recode.event.events.Render2DEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class RenderMixin {
    
    @Inject(at = @At("HEAD"), method = "render")
    private void onRenderHead(CallbackInfo ci) {
        // 3D 渲染事件在世界渲染前触发
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world != null && mc.getRenderManager() != null) {
            RenderTickCounter tickCounter = mc.getRenderTickCounter();
            EventBus.INSTANCE.post(new Render3DEvent(null, tickCounter.getTickDelta(true)));
        }
    }
    
    @Inject(at = @At("TAIL"), method = "render")
    private void onRenderTail(CallbackInfo ci) {
        // 2D 渲染事件在 GUI 渲染后触发
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.currentScreen != null || true) { // 总是触发 2D 事件
            int width = mc.getWindow().getScaledWidth();
            int height = mc.getWindow().getScaledHeight();
            RenderTickCounter tickCounter = mc.getRenderTickCounter();
            
            // 创建 DrawContext（需要当前屏幕）
            DrawContext drawContext = null;
            if (mc.currentScreen != null) {
                // 通过屏幕获取 DrawContext（这个需要特殊处理）
                // 暂时使用 null，在 EventManager 中处理
            }
            
            EventBus.INSTANCE.post(new Render2DEvent(null, width, height, tickCounter.getTickDelta(true)));
        }
    }
}
