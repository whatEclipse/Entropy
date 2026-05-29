package net.whateclipse.entropy.enchantments;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.entropy.entities.EntropyBloodProjectileEntity;

public record SpewingEnchantment() implements EnchantmentEntityEffect {
    public static final MapCodec<SpewingEnchantment> CODEC = MapCodec.unit(SpewingEnchantment::new);

    @Override
    public void apply(ServerLevel serverLevel, int lvl, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
        if (lvl == 1){
            EntropyBloodProjectileEntity projectile = new EntropyBloodProjectileEntity(serverLevel, (LivingEntity) entity, new ItemStack(Items.ARROW));
            serverLevel.addFreshEntity(projectile);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
