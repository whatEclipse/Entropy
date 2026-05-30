package net.whateclipse.entropy.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;

public class EntropyItemModelProvider extends ItemModelProvider {

    public EntropyItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Entropy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
