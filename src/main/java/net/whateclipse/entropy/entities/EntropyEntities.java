package net.whateclipse.entropy.entities;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.whateclipse.entropy.Entropy;

public class EntropyEntities {

    @SuppressWarnings("null")
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Entropy.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntropyBloodProjectileEntity>> BLOOD_PROJECTILE = ENTITIES.register("blood_projectile",
            () -> EntityType.Builder.<EntropyBloodProjectileEntity>of(EntropyBloodProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(64)
                    .updateInterval(1)
                    .build("blood_projectile"));



    public static void register(@SuppressWarnings("null") IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
