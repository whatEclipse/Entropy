package net.whateclipse.entropy.sounds;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;

public class EntropySounds {
    @SuppressWarnings("null")
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Entropy.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOOD_PROJECTILE_HIT = registerSoundEvent("blood_hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_SPEWING_0 = registerSoundEvent("scythe_spewing_0");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_SPEWING_1 = registerSoundEvent("scythe_spewing_1");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_SPEWING_2 = registerSoundEvent("scythe_spewing_2");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
        @SuppressWarnings("null")
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Entropy.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(@SuppressWarnings("null") IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
