package net.whateclipse.entropy.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;

public class EntropyItems {
    public static  final  DeferredRegister.Items ENTROPY_ITEMS = DeferredRegister.createItems(Entropy.MODID);

    public static void register (IEventBus eventBus){
        ENTROPY_ITEMS.register(eventBus);
    }

}
