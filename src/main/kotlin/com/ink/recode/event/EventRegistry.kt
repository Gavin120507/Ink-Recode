package com.ink.recode.event

/**
 * 事件注册表 - 注册所有可用事件
 */
object EventRegistry {
    /**
     * 所有已注册的事件类列表（90+ 个事件）
     */
    val ALL_EVENT_CLASSES = listOf(
        // 客户端事件 (25 个)
        com.ink.recode.event.events.ClientStartEvent::class,
        com.ink.recode.event.events.ClientShutdownEvent::class,
        com.ink.recode.event.events.SessionEvent::class,
        com.ink.recode.event.events.ScreenEvent::class,
        com.ink.recode.event.events.ChatSendEvent::class,
        com.ink.recode.event.events.ChatReceiveEvent::class,
        com.ink.recode.event.events.ServerConnectEvent::class,
        com.ink.recode.event.events.DisconnectEvent::class,
        com.ink.recode.event.events.OverlayMessageEvent::class,
        com.ink.recode.event.events.ResourceReloadEvent::class,
        com.ink.recode.event.events.ScaleFactorChangeEvent::class,
        com.ink.recode.event.events.FpsChangeEvent::class,
        com.ink.recode.event.events.GameModeChangeEvent::class,
        com.ink.recode.event.events.ServerPingedEvent::class,
        com.ink.recode.event.events.VirtualScreenEvent::class,
        com.ink.recode.event.events.RefreshArrayListEvent::class,
        com.ink.recode.event.events.ValueChangedEvent::class,
        com.ink.recode.event.events.ToggleModuleEvent::class,
        com.ink.recode.event.events.AccountManagerMessageEvent::class,
        com.ink.recode.event.events.AccountManagerLoginResultEvent::class,
        com.ink.recode.event.events.AccountManagerAdditionResultEvent::class,
        com.ink.recode.event.events.ProxyAdditionResultEvent::class,
        com.ink.recode.event.events.ProxyCheckResultEvent::class,
        com.ink.recode.event.events.BrowserReadyEvent::class,
        com.ink.recode.event.events.ComponentsUpdateEvent::class,
        com.ink.recode.event.events.SimulatedTickEvent::class,
        com.ink.recode.event.events.ClickGuiScaleChangeEvent::class,
        com.ink.recode.event.events.SpaceSeparatedNamesChangeEvent::class,
        com.ink.recode.event.events.TargetChangeEvent::class,
        com.ink.recode.event.events.ClientChatStateChangeEvent::class,
        com.ink.recode.event.events.ClientChatMessageEvent::class,
        com.ink.recode.event.events.ClientChatErrorEvent::class,
        com.ink.recode.event.events.ClientChatJwtTokenEvent::class,
        com.ink.recode.event.events.ScheduleInventoryActionEvent::class,
        
        // 玩家事件 (20 个)
        com.ink.recode.event.events.HealthUpdateEvent::class,
        com.ink.recode.event.events.DeathEvent::class,
        com.ink.recode.event.events.PlayerTickEvent::class,
        com.ink.recode.event.events.PlayerPostTickEvent::class,
        com.ink.recode.event.events.PlayerMovementTickEvent::class,
        com.ink.recode.event.events.PlayerNetworkMovementTickEvent::class,
        com.ink.recode.event.events.PlayerPushOutEvent::class,
        com.ink.recode.event.events.PlayerMoveEvent::class,
        com.ink.recode.event.events.RotatedMovementInputEvent::class,
        com.ink.recode.event.events.PlayerJumpEvent::class,
        com.ink.recode.event.events.PlayerAfterJumpEvent::class,
        com.ink.recode.event.events.PlayerUseMultiplier::class,
        com.ink.recode.event.events.PlayerInteractedItem::class,
        com.ink.recode.event.events.PlayerVelocityStrafe::class,
        com.ink.recode.event.events.PlayerStrideEvent::class,
        com.ink.recode.event.events.PlayerSafeWalkEvent::class,
        com.ink.recode.event.events.PlayerStepEvent::class,
        com.ink.recode.event.events.PlayerStepSuccessEvent::class,
        
        // 世界事件 (10 个)
        com.ink.recode.event.events.WorldChangeEvent::class,
        com.ink.recode.event.events.ChunkUnloadEvent::class,
        com.ink.recode.event.events.ChunkLoadEvent::class,
        com.ink.recode.event.events.ChunkDeltaUpdateEvent::class,
        com.ink.recode.event.events.BlockChangeEvent::class,
        com.ink.recode.event.events.BlockShapeEvent::class,
        com.ink.recode.event.events.BlockBreakingProgressEvent::class,
        com.ink.recode.event.events.BlockVelocityMultiplierEvent::class,
        com.ink.recode.event.events.BlockSlipperinessMultiplierEvent::class,
        com.ink.recode.event.events.FluidPushEvent::class,
        
        // 实体事件 (2 个)
        com.ink.recode.event.events.AttackEvent::class,
        com.ink.recode.event.events.EntityMarginEvent::class,
        
        // 网络事件 (2 个)
        com.ink.recode.event.events.PipelineEvent::class,
        com.ink.recode.event.events.PacketEvent::class,
        
        // 渲染事件 (5 个)
        com.ink.recode.event.events.GameRenderEvent::class,
        com.ink.recode.event.events.ScreenRenderEvent::class,
        com.ink.recode.event.events.WorldRenderEvent::class,
        com.ink.recode.event.events.DrawOutlinesEvent::class,
        com.ink.recode.event.events.OverlayRenderEvent::class,
        
        // 窗口事件 (6 个)
        com.ink.recode.event.events.WindowResizeEvent::class,
        com.ink.recode.event.events.MouseButtonEvent::class,
        com.ink.recode.event.events.MouseScrollEvent::class,
        com.ink.recode.event.events.MouseCursorEvent::class,
        com.ink.recode.event.events.KeyboardKeyEvent::class,
        com.ink.recode.event.events.KeyboardCharEvent::class,
        
        // 输入事件 (7 个)
        com.ink.recode.event.events.InputHandleEvent::class,
        com.ink.recode.event.events.MovementInputEvent::class,
        com.ink.recode.event.events.MouseRotationEvent::class,
        com.ink.recode.event.events.KeybindChangeEvent::class,
        com.ink.recode.event.events.UseCooldownEvent::class,
        com.ink.recode.event.events.CancelBlockBreakingEvent::class,
        com.ink.recode.event.events.SplashOverlayEvent::class,
        com.ink.recode.event.events.SplashProgressEvent::class,
        
        // 基础事件 (6 个)
        com.ink.recode.event.events.TickEvent::class,
        com.ink.recode.event.events.KeyboardEvent::class,
        com.ink.recode.event.events.MotionEvent::class,
        com.ink.recode.event.events.UpdateEvent::class,
        com.ink.recode.event.events.Render2DEvent::class,
        com.ink.recode.event.events.Render3DEvent::class
    )
    
    /**
     * 获取事件总数
     */
    fun getEventCount(): Int {
        return ALL_EVENT_CLASSES.size
    }
    
    /**
     * 打印事件列表
     */
    fun printEventList() {
        println("=== Ink-Recode Event Registry ===")
        println("Total events: ${getEventCount()}")
        println()
        
        var count = 0
        ALL_EVENT_CLASSES.forEach { eventClass ->
            count++
            println("$count. ${eventClass.simpleName}")
        }
    }
}
