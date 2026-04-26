package com.minecraftmod.entity;


import com.minecraftmod.spell.Spell;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class WaterProjectile extends SpellProjectile implements ItemSupplier {

    public WaterProjectile(EntityType<WaterProjectile> type, Level level) {
        super(type, level);
    }

    public WaterProjectile(EntityType<WaterProjectile> type, Level level, Spell spell) {
        super(type, level, spell);
    }

    @Override
    public ItemStack getItem() {
        return Items.SNOWBALL.getDefaultInstance();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {

    }
}