package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level.ExplosionInteraction;

public class FireSpell3 implements Spell {

    private static final int DAMAGE = 10;
    private static final int FIRE_TICKS = 8;
    private static final int EXPLOSION_RADIUS = 3;

    @Override
    public void castOnBlock(Level level, BlockHitResult block) {
        cast(level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        entity.getEntity().setRemainingFireTicks(FIRE_TICKS);
        cast(level, entity.getEntity().getOnPos());
    }

    private void cast(Level level, BlockPos hitPos) {
        if (level.isClientSide()) return;
        double x = hitPos.getX() + 0.5;
        double y = hitPos.getY() + 0.5;
        double z = hitPos.getZ() + 0.5;
        level.explode(
                null,
                x, y, z,
                EXPLOSION_RADIUS,
                true,
                ExplosionInteraction.MOB
        );
    }

}

