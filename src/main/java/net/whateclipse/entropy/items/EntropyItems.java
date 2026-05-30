package net.whateclipse.entropy.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.tiers.EntropyToolTiers;

public class EntropyItems {
    public static  final  DeferredRegister.Items ENTROPY_ITEMS = DeferredRegister.createItems(Entropy.MODID);

    public static final DeferredItem<ScytheItem> SCYTHE = ENTROPY_ITEMS.register("scythe", ()-> new ScytheItem(Tiers.NETHERITE, 3F, -3F, new Item.Properties()));

    public static void register (IEventBus eventBus){
        ENTROPY_ITEMS.register(eventBus);
    }

}
