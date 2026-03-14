package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.client.gui.GuiGraphics

class Render3DEvent(
    val poseStack: GuiGraphics,
    val partialTicks: Float
) : Event()
