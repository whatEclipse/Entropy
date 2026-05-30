package net.whateclipse.entropy.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;
import net.whateclipse.entropy.items.EntropyItems;

public class EntropyLangProvider extends LanguageProvider {

    public EntropyLangProvider(PackOutput output) {
        super(output, Entropy.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Items
        add(EntropyItems.SCYTHE.get(), "Scythe");

        // Enchantments
        add("enchantment.entropy.spewing", "Spewing");

        // Creative tab (if you have one)
        add("itemGroup.entropy.entropy_tab", "Entropy");
    }
}
