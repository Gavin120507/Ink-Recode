package com.ink.recode.event.events

import com.ink.recode.event.Event
import net.minecraft.client.gui.GuiGraphics

class Render2DEvent(
    val guiGraphics: GuiGraphics,
    val width: Int,
    val height: Int,
    val partialTicks: Float
) : Event()
