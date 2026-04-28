package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class FireSpell1 implements Spell {

    private static final int DAMAGE = 5;
    private static final int FIRE_TICKS = 8;

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
        var surfacePos = hitPos.above();
        if (level.getBlockState(surfacePos).canBeReplaced()) {
            level.setBlock(surfacePos, Blocks.FIRE.defaultBlockState(), 3);
        }
    }
}
