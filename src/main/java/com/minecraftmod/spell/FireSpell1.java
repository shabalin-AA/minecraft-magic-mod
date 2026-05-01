package com.minecraftmod.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;

public class FireSpell1 implements Spell {

    private static final int DAMAGE = 5;
    private static final int FIRE_TICKS = 8;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
        cast(level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        entity.getEntity().setRemainingFireTicks(FIRE_TICKS);
        cast(level, entity.getEntity().getOnPos());
    }

    private void cast(Level level, BlockPos hitPos) {
        for (Direction direction : Direction.values()) {
            var pos = hitPos.relative(direction);
            if (level.getBlockState(pos).canBeReplaced()) {
                level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
            }
        }
    }
}
