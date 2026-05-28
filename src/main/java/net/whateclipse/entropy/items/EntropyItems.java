package net.whateclipse.entropy.items;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;

public class EntropyItems {
    public static  final  DeferredRegister.Items ENTROPY_ITEMS = DeferredRegister.createItems(Entropy.MODID);

    public static final DeferredItem<Item> CUPRENITE_INGOT = ENTROPY_ITEMS.registerSimpleItem("cuprenite_ingot");


    public static void register (IEventBus eventBus){
        ENTROPY_ITEMS.register(eventBus);
    }
}
