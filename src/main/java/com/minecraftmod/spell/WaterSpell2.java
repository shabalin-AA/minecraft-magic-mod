package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WaterSpell2 implements Spell {

    private static final int DAMAGE = 10;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
        cast(level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        cast(level, entity.getEntity().getOnPos());
    }

    private void cast(Level level, BlockPos hitPos) {
        var surfacePos = hitPos.above();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos target = surfacePos.offset(x, 0, z);
                if (level.getBlockState(target).canBeReplaced()) {
                    level.setBlock(target, Fluids.WATER.defaultFluidState().createLegacyBlock(), 3);
                }
            }
        }
    }

}
