package com.minecraftmod;

import com.minecraftmod.util.Scheduler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicMod implements ModInitializer {
	public static final String MOD_ID = "magic-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ModEntityTypes.initialize();
		ModBlocks.initialize();
		ServerTickEvents.END_SERVER_TICK.register(Scheduler::serverTick);
	}
}