package net.whateclipse.entropy.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.dedicated.Settings;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.whateclipse.entropy.enchantments.EntropyEnchantmentRegister;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;
import net.whateclipse.entropy.entities.EntropyBloodProjectileEntity;

public class ScytheItem extends SwordItem {

    public ScytheItem(Tier tier, float damage, float attackSpeed, Properties properties) {
        super(tier, new Item.Properties()
                .attributes(SwordItem.createAttributes(tier, (int) damage, attackSpeed)));
    }

    public static final DeferredHolder<Item, ScytheItem> SCYTHE = EntropyItems.ENTROPY_ITEMS.register("scythe",
            ()-> new ScytheItem(Tiers.NETHERITE, 3.0F, -3.0F, new Item.Properties()));

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

            if (!level.isClientSide) {

                int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(EntropyEnchantments.SPEWING), itemStack);
                if (enchantLevel > 0) {
                    EntropyBloodProjectileEntity projectile = new EntropyBloodProjectileEntity((ServerLevel) level, player, new ItemStack(Items.ARROW));

                    level.addFreshEntity(projectile);
                    player.hurt(level.damageSources().magic(), 4.0F);
                }
            }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
