package com.minecraftmod.spell;

import com.minecraftmod.entity.SpellProjectile;
import com.minecraftmod.entity.WaterProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class AirSpell2 implements Spell {

    private static final double LAUNCH_POWER = 1.5;
    private static final double HORIZONTAL_KNOCKBACK = 3;
    private static final double RADIUS = 3.0;
    private static final int DAMAGE = 5;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
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
            cast(caster, target);
        }
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        if (level.isClientSide()) return;
        var target = entity.getEntity();
        level.levelEvent(LevelEvent.PARTICLES_ELECTRIC_SPARK, target.blockPosition(), 0);
        target.hurt(target.damageSources().magic(), DAMAGE);
        cast(caster, target);
    }

    @Override
    public SpellProjectile createProjectile(EntityType<WaterProjectile> type, LivingEntity player, Level level) {
        return new WaterProjectile(type, player, level, this);
    }

    private void cast(LivingEntity caster, Entity target) {
        var direction = target.position().subtract(caster.position()).normalize();
        target.push(
                direction.x * HORIZONTAL_KNOCKBACK,
                LAUNCH_POWER,
               direction.z * HORIZONTAL_KNOCKBACK
        );
    }
}
