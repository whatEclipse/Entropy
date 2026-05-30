package net.whateclipse.entropy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EntropyEnchantmentProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, EntropyEnchantments::bootstrap);

    public EntropyEnchantmentProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Entropy.MODID));
    }

    @Override
    public String getName() {
        return "Entropy Enchantments";
    }
}
