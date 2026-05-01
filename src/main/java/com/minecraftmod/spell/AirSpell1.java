package com.minecraftmod.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class AirSpell1 implements Spell {

    private static final double LAUNCH_POWER = 2.2;
    private static final double RADIUS = 3.0;
    private static final int DAMAGE = 5;

    @Override
    public void castOnBlock(Level level, BlockHitResult block) {
        if (level.isClientSide()) return;
        level.levelEvent(LevelEvent.PARTICLES_ELECTRIC_SPARK, block.getBlockPos().above(), 0);
        var pos = block.getBlockPos();
        double cx = pos.getX() + 0.5, cy = pos.getY() + 0.5, cz = pos.getZ() + 0.5;
        AABB searchBox = new AABB(
                cx - RADIUS, cy - RADIUS, cz - RADIUS,
                cx + RADIUS, cy + RADIUS, cz + RADIUS
        );
        var targets = level.getEntities(null, searchBox);
        for (var target : targets) {
            System.out.println(target);
            cast(target);
        }
    }

    @Override
    public void castOnEntity(Level level, EntityHitResult entity) {
        if (level.isClientSide()) return;
        var target = entity.getEntity();
        level.levelEvent(LevelEvent.PARTICLES_ELECTRIC_SPARK, target.blockPosition(), 0);
        target.hurt(target.damageSources().magic(), DAMAGE);
        cast(target);
    }

    private void cast(Entity target) {
        target.push(0.0, LAUNCH_POWER, 0.0);
    }
}
