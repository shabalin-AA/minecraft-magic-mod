package com.minecraftmod;

import com.minecraftmod.item.WaterSpellItem;
import com.minecraftmod.item.WaterStaffItem;
import com.minecraftmod.spell.WaterSpell;
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
    public static final Item WATER_SPELL = register(
            "water_spell",
            (properties) -> new WaterSpellItem(properties, new WaterSpell()),
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
                Pair.of(CreativeModeTabs.COMBAT, WATER_SPELL)
        );
        modItems.forEach(pair -> {
            CreativeModeTabEvents.modifyOutputEvent(pair.left())
                    .register((creativeTab) -> creativeTab.accept(pair.right()));
        });
    }

}
