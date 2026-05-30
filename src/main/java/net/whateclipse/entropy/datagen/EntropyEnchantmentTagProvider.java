package net.whateclipse.entropy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;

import java.util.concurrent.CompletableFuture;

public class EntropyEnchantmentTagProvider extends EnchantmentTagsProvider {

    public EntropyEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, Entropy.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Makes the enchantment appear in the enchanting table
        tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(EntropyEnchantments.SPEWING);

        // Marks it as a non-treasure enchantment (can appear normally, not just in loot)
        tag(EnchantmentTags.NON_TREASURE)
                .add(EntropyEnchantments.SPEWING);
    }

    @Override
    public String getName() {
        return "Entropy Enchantment Tags";
    }
}

