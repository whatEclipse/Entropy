package net.whateclipse.entropy.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;
import net.whateclipse.entropy.entities.EntropyBloodProjectileEntity;

@EventBusSubscriber(modid = Entropy.MODID)
public class EntropyEventHandler {

    @SubscribeEvent
    public static void OnCriticalHit(CriticalHitEvent criticalHitEvent) {
        if (criticalHitEvent.getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == EntropyItems.SCYTHE.get()){
                boolean isCrit = criticalHitEvent.isVanillaCritical();
                if (isCrit){
                    criticalHitEvent.getTarget().addTag("entropy_inverted_knockback");
                }
            }

        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() != EntropyItems.SCYTHE.get()) return;
        if (player.level().isClientSide()) return;

        int level = stack.getEnchantmentLevel(
                player.level().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(EntropyEnchantments.SPEWING)
        );

        if (level > 0) {
            ServerLevel serverLevel = (ServerLevel) player.level();
            EntropyBloodProjectileEntity projectile = new EntropyBloodProjectileEntity(
                    serverLevel, player, new ItemStack(Items.ARROW)
            );
            serverLevel.addFreshEntity(projectile);
        }
    }

    @SubscribeEvent
    public static void onLivingKnockBackEvent(LivingKnockBackEvent event) {

        LivingEntity entity = event.getEntity();
        if (entity.getTags().contains("entropy_inverted_knockback")){

            event.setRatioX(-event.getRatioX());
            event.setRatioZ(-event.getRatioZ());

            entity.hasImpulse = true;
            var currentMovement = entity.getDeltaMovement();

            entity.setDeltaMovement(currentMovement.x, 0.0D, currentMovement.z);

            entity.removeTag("entropy_inverted_knockback");
        }
    }
}

