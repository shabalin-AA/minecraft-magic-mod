package com.minecraftmod.spell;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public interface Spell {
    void castOnBlock(Level level, BlockHitResult block);
    void castOnEntity(Level level, EntityHitResult entity);
}
