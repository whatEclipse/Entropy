package net.whateclipse.entropy.enchantments;

import  net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;

import java.util.function.Supplier;

public class EntropyEnchantments {

    public static final ResourceKey<Enchantment> SPEWING = ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Entropy.MODID, "spewing"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var encahntments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        effectsRegister(context, SPEWING, Enchantment.enchantment(Enchantment.definition(
                HolderSet.direct(items.getOrThrow(EntropyItems.SCYTHE.getKey())),
                HolderSet.direct(items.getOrThrow(EntropyItems.SCYTHE.getKey())),
                5,
                1,
                Enchantment.dynamicCost(5, 7),
                Enchantment.dynamicCost(25, 7),
                2,
                EquipmentSlotGroup.MAINHAND)
        ));
    }

    private static void effectsRegister(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key,
                                        Enchantment.Builder builder){
        registry.register(key, builder.build(key.location()));
    }
}
