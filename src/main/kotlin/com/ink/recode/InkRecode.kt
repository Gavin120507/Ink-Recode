package com.ink.recode

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import com.ink.recode.event.*
import com.ink.recode.modules.impl.*
import com.ink.recode.modules.impl.movement.Sprint

object InkRecode : ModInitializer {
    private val logger = LoggerFactory.getLogger("ink-recode")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		EventManager.init()
		ModuleManager.register(Sprint)
	}
}