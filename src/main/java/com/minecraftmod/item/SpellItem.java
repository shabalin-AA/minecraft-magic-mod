package com.minecraftmod.item;

import com.minecraftmod.spell.Spell;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SpellItem extends Item {

    protected final Spell spell;

    public SpellItem(Properties properties, Spell spell) {
        super(properties);
        this.spell = spell;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        int damage = stack.getDamageValue();
        if (damage > 0) {
            stack.setDamageValue(Math.max(0, damage - 1));
        }
    }

    public boolean isReady(ItemStack stack) {
        return stack.getDamageValue() == 0;
    }

    public void applyCooldown(ItemStack stack) {
        stack.setDamageValue(stack.getMaxDamage());
    }
}
