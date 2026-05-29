package net.whateclipse.entropy.enchantments;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.whateclipse.entropy.Entropy;

import java.util.function.Supplier;

public class EntropyEnchantmentRegister {


    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>>
            ENTROPY_ENCHANTMENTS_REGISTER = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Entropy.MODID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> SPEWING =
            ENTROPY_ENCHANTMENTS_REGISTER.register("spewing", ()-> SpewingEnchantment.CODEC);

    public static void register(IEventBus bus) {
        ENTROPY_ENCHANTMENTS_REGISTER.register(bus);
    }

}
