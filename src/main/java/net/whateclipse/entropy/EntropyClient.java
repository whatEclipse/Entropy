package net.whateclipse.entropy;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.whateclipse.entropy.client.renderer.EntropyBloodProjectileRenderer;
import net.whateclipse.entropy.entities.EntropyEntities;

@EventBusSubscriber(modid = Entropy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntropyClient {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntropyEntities.BLOOD_PROJECTILE.get(), EntropyBloodProjectileRenderer::new);
    }
}
