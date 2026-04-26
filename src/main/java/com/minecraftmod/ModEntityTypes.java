package com.minecraftmod;

import com.minecraftmod.entity.WaterProjectile;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

public class ModEntityTypes {

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MagicMod.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void registerModEntityTypes() {
        MagicMod.LOGGER.info("Registering EntityTypes for " + MagicMod.MOD_ID);
    }

    public static void registerAttributes() {
    }

    public static final EntityType<WaterProjectile> WATER_PROJECTILE = register(
            "water_projectile",
            EntityType.Builder.of(
                    (EntityType<WaterProjectile> type, Level level) -> new WaterProjectile(type, level),
                    MobCategory.MISC
            ).sized(0.25f, 0.25f)
    );

    public static void initialize() {
        //
    }
}
