package net.whateclipse.entropy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.whateclipse.entropy.Entropy;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Entropy.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EntropyDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Item models
        generator.addProvider(event.includeClient(), new EntropyItemModelProvider(output, event.getExistingFileHelper()));

        // Lang
        generator.addProvider(event.includeClient(), new EntropyLangProvider(output));

        // Enchantments
        generator.addProvider(event.includeServer(), new EntropyEnchantmentProvider(output, lookupProvider));

        // Recipes
        generator.addProvider(event.includeServer(), new EntropyRecipeProvider(output, lookupProvider));

        generator.addProvider(event.includeServer(), new EntropyEnchantmentTagProvider(output, lookupProvider, event.getExistingFileHelper()));
    }
}
