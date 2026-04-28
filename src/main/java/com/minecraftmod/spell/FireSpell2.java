package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class FireSpell2  implements Spell {

    private static final int DAMAGE = 10;
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
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos target = surfacePos.offset(x, 0, z);
                if (level.getBlockState(target).canBeReplaced()) {
                    level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
                }
            }
        }
    }
}
