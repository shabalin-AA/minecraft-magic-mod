package com.minecraftmod.client;

import com.minecraftmod.ModEntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class MagicModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(
				ModEntityTypes.WATER_PROJECTILE,
                (EntityRendererProvider.Context context) -> new ThrownItemRenderer<>(context)
		);
	}
}