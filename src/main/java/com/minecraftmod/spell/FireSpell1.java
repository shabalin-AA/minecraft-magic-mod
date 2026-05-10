package com.minecraftmod.spell;

import com.minecraftmod.entity.SpellProjectile;
import com.minecraftmod.entity.WaterProjectile;
import com.minecraftmod.util.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.block.Blocks;

public class FireSpell1 implements Spell {

    private static final int DAMAGE = 5;
    private static final int FIRE_TICKS = 8;
    private static final int REMOVE_DELAY = 20;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
        cast(caster, level, block.getBlockPos());
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
        entity.getEntity().setRemainingFireTicks(FIRE_TICKS);
        cast(caster, level, entity.getEntity().getOnPos());
    }

    @Override
    public SpellProjectile createProjectile(EntityType<WaterProjectile> type, LivingEntity player, Level level) {
        return new WaterProjectile(type, player, level, this);
    }

    private void cast(LivingEntity caster, Level level, BlockPos hitPos) {
        for (Direction direction : Direction.values()) {
            var pos = hitPos.relative(direction);
            spawnFire(pos, caster, level);
        }
    }

    private void spawnFire(BlockPos target, LivingEntity caster, Level level) {
        if (!level.getBlockState(target).canBeReplaced()) {
            return;
        }
        level.setBlock(target, Blocks.FIRE.defaultBlockState(), 3);
        Scheduler.schedule(REMOVE_DELAY, () -> {
            level.setBlock(target, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
        }, System.currentTimeMillis() + this.getClass().getName() + caster.getId() + target);
    }

}
