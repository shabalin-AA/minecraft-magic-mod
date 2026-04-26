package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WaterSpell1 implements Spell {

    private static final int DAMAGE = 5;

    @Override
    public void castOnBlock(Level level, BlockHitResult block) {
        cast(level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        cast(level, entity.getEntity().getOnPos());
    }

    private void cast(Level level, BlockPos hitPos) {
        var surfacePos = hitPos.above();
        if (level.getBlockState(surfacePos).canBeReplaced()) {
            level.setBlock(surfacePos, Fluids.WATER.defaultFluidState().createLegacyBlock(), 3);
        }
    }

}
