package com.minecraftmod;

import com.minecraftmod.item.SpellItem;
import com.minecraftmod.item.WaterStaffItem;
import com.minecraftmod.spell.*;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.Set;
import java.util.function.Function;

public class ModItems {

    public static final Item SUSPICIOUS_SUBSTANCE = register("suspicious_substance", Item::new, new Item.Properties());
    public static final Item WATER_STAFF = register(
            "water_staff",
            WaterStaffItem::new,
            new Item.Properties()
                    .durability(10)
    );
    public static final Item WATER_SPELL_1 = register(
            "water_spell_1",
            (properties) -> new SpellItem(properties, new WaterSpell1()),
            new Item.Properties().durability(100)
    );
    public static final Item WATER_SPELL_2 = register(
            "water_spell_2",
            (properties) -> new SpellItem(properties, new WaterSpell2()),
            new Item.Properties()
    );
    public static final Item FIRE_SPELL_1 = register(
            "fire_spell_1",
            (properties) -> new SpellItem(properties, new FireSpell1()),
            new Item.Properties()
    );
    public static final Item FIRE_SPELL_2 = register(
            "fire_spell_2",
            (properties) -> new SpellItem(properties, new FireSpell2()),
            new Item.Properties()
    );
    public static final Item FIRE_SPELL_3 = register(
            "fire_spell_3",
            (properties) -> new SpellItem(properties, new FireSpell3()),
            new Item.Properties()
    );
    public static final Item AIR_SPELL_1 = register(
            "air_spell_1",
            (properties) -> new SpellItem(properties, new AirSpell1()),
            new Item.Properties()
    );
    public static final Item AIR_SPELL_2 = register(
            "air_spell_2",
            (properties) -> new SpellItem(properties, new AirSpell2()),
            new Item.Properties()
    );

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MagicMod.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void initialize() {
        var modItems = Set.of(
                Pair.of(CreativeModeTabs.INGREDIENTS, SUSPICIOUS_SUBSTANCE),
                Pair.of(CreativeModeTabs.COMBAT, WATER_STAFF),
                Pair.of(CreativeModeTabs.COMBAT, WATER_SPELL_1),
                Pair.of(CreativeModeTabs.COMBAT, WATER_SPELL_2),
                Pair.of(CreativeModeTabs.COMBAT, FIRE_SPELL_1),
                Pair.of(CreativeModeTabs.COMBAT, FIRE_SPELL_2),
                Pair.of(CreativeModeTabs.COMBAT, FIRE_SPELL_3),
                Pair.of(CreativeModeTabs.COMBAT, AIR_SPELL_1),
                Pair.of(CreativeModeTabs.COMBAT, AIR_SPELL_2)
        );
        modItems.forEach(pair -> {
            CreativeModeTabEvents.modifyOutputEvent(pair.left())
                    .register((creativeTab) -> creativeTab.accept(pair.right()));
        });
    }

}
