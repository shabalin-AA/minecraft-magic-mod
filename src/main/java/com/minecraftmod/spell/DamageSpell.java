package com.minecraftmod.spell;

import com.minecraftmod.entity.SpellProjectile;
import com.minecraftmod.entity.WaterProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class DamageSpell implements Spell {

    private static final int DAMAGE = 10;

    @Override
    public void castOnBlock(LivingEntity caster, Level level, BlockHitResult block) {
        //
    }

    @Override
    public void castOnEntity(LivingEntity caster, Level level, EntityHitResult entity) {
        entity.getEntity().hurt(entity.getEntity().damageSources().magic(), DAMAGE);
    }

    @Override
    public SpellProjectile createProjectile(EntityType<WaterProjectile> type, LivingEntity player, Level level) {
        return new WaterProjectile(type, player, level, this);
    }
}
