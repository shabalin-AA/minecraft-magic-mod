package com.minecraftmod.spell;

import com.minecraftmod.entity.SpellProjectile;
import com.minecraftmod.entity.WaterProjectile;
import com.minecraftmod.util.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class WaterSpell2 implements Spell {

    private static final int DAMAGE = 10;
    public static final int REMOVE_DELAY = 20;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
        cast(caster, level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        cast(caster, level, entity.getEntity().getOnPos());
    }

    @Override
    public SpellProjectile createProjectile(EntityType<WaterProjectile> type, LivingEntity player, Level level) {
        return new WaterProjectile(type, player, level, this);
    }


    private void cast(LivingEntity caster, Level level, BlockPos hitPos) {
        var surfacePos = hitPos.above();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos target = surfacePos.offset(x, 0, z);
                spawnWater(target, caster, level);
            }
        }
    }

    private void spawnWater(BlockPos target, LivingEntity caster, Level level) {
        if (!level.getBlockState(target).canBeReplaced()) {
            return;
        }
        level.setBlock(target, Fluids.WATER.defaultFluidState().createLegacyBlock(), 3);
        Scheduler.schedule(REMOVE_DELAY, () -> {
            level.setBlock(target, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }, System.currentTimeMillis() + this.getClass().getName() + caster.getId() + target);
    }

}
