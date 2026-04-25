package com.minecraftmod.client;

import com.minecraftmod.ModEntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class MagicModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntityTypes.WATER_PROJECTILE, config -> new EntityRenderer(config) {
			@Override
			public EntityRenderState createRenderState() {
				return new EntityRenderState();
			}
		});
	}
}