package com.minecraftmod.entity;

import com.minecraftmod.spell.DamageSpell;
import com.minecraftmod.spell.Spell;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public abstract class SpellProjectile extends ThrowableProjectile {
    protected final Spell spell;
    protected final LivingEntity caster;
    private static final DamageSpell DEFAULT_SPELL = new DamageSpell();

    protected SpellProjectile(EntityType<? extends SpellProjectile> type, Level level) {
        var defaultPlayer = level.getNearestPlayer(0, 0, 0, 0, false);
        this(type, defaultPlayer, level, DEFAULT_SPELL);
    }

    protected SpellProjectile(EntityType<? extends SpellProjectile> type, LivingEntity player, Level level, Spell spell) {
        super(type, level);
        this.spell = spell;
        this.caster = player;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            spell.castOnBlock(this.caster, this.level(), result);
        }
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide()) {
            spell.castOnEntity(this.caster, this.level(), result);
        }
        this.discard();
    }

}
