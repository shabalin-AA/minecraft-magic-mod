package com.minecraftmod.item;

import com.minecraftmod.spell.Spell;
import net.minecraft.world.item.Item;

public abstract class SpellItem extends Item {
    protected final Spell spell;

    public SpellItem(Properties properties, Spell spell) {
        super(properties);
        this.spell = spell;
    }
}
