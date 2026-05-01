package com.minecraftmod;

import com.minecraftmod.block.TemporaryFireBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static Block TEMPORARY_FIRE_BLOCK = register(
            "temporary_fire_block",
            props -> new TemporaryFireBlock(props, 300),
            BlockBehaviour.Properties.of()
    );

    public static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MagicMod.MOD_ID, name));
        T item = blockFactory.apply(settings.setId(key));
        Registry.register(BuiltInRegistries.BLOCK, key, item);
        return item;
    }

    public static void initialize() {
    }
}
