package net.whateclipse.entropy.creative;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;
import net.whateclipse.entropy.items.ScytheItem;

public class EntropyCreativeTab {
    public static final DeferredRegister<CreativeModeTab> ENTROPY_CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Entropy.MODID);

    static final DeferredHolder<CreativeModeTab, CreativeModeTab> ENTROPY_TAB = ENTROPY_CREATIVE_TAB.register("entropy_tab", () -> CreativeModeTab.builder()
            .title(net.minecraft.network.chat.Component.translatable("creativeta.entropy.entropy_tab"))
            .withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> ScytheItem.SCYTHE.get().getDefaultInstance()).displayItems((parameters, output) ->{
                output.accept(ScytheItem.SCYTHE.get());
            }).build());

    public static void register(IEventBus bus) {
        ENTROPY_CREATIVE_TAB.register(bus);
    }
}