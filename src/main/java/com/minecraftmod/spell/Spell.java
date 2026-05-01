package com.minecraftmod.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public interface Spell {
    void castOnBlock(
            LivingEntity caster,
            Level level,
            BlockHitResult block
    );

    void castOnEntity(
            LivingEntity caster,
            Level level,
            EntityHitResult entity
    );
}
