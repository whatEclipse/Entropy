package net.whateclipse.entropy.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;

import java.util.concurrent.CompletableFuture;

public class EntropyRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public EntropyRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

    }
}
