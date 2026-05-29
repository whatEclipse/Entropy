package net.whateclipse.entropy.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.whateclipse.entropy.Entropy;
import net.whateclipse.entropy.items.EntropyItems;
import net.whateclipse.entropy.items.ScytheItem;

@EventBusSubscriber(modid = Entropy.MODID)
public class EntropyEventHandler {

    @SubscribeEvent
    public static void OnCriticalHit(CriticalHitEvent criticalHitEvent) {
        if (criticalHitEvent.getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == ScytheItem.SCYTHE.get()){
                boolean isCrit = criticalHitEvent.isVanillaCritical();
                if (isCrit){
                    criticalHitEvent.getTarget().addTag("entropy_inverted_knockback");
                }
            }

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

