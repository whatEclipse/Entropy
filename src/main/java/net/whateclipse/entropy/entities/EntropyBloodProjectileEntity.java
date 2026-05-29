package net.whateclipse.entropy.entities;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.entropy.entities.EntropyEntities;
import net.whateclipse.entropy.sounds.EntropySounds;
import javax.annotation.Nonnull;

public class EntropyBloodProjectileEntity extends AbstractArrow {

    public EntropyBloodProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.setBaseDamage(17.33); // Velocity = 0.75, Damage = 13.0 (approx 17.33 * 0.75 = 12.9975)
        this.setNoGravity(true);
        if (!level.isClientSide) {
            int random_number = (int) (Math.random()*3);

            if (random_number == 0){
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_0.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
            if (random_number == 1){
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_1.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
            if (random_number == 2){
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_2.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
        }
    }

    public EntropyBloodProjectileEntity(Level level, LivingEntity shooter, @Nonnull ItemStack pickupItem) {
        super(EntropyEntities.BLOOD_PROJECTILE.get(), shooter, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
        // Automatically set the movement based on the looking direction of the shooter entity
        this.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, 0.75F, 0.0F);

        if (!level.isClientSide) {
            level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_0.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
        }
    }

    public EntropyBloodProjectileEntity(Level level, double x, double y, double z, @Nonnull ItemStack pickupItem) {
        super(EntropyEntities.BLOOD_PROJECTILE.get(), x, y, z, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);

        if (!level.isClientSide) {
            int random_number = (int) (Math.random() * 3);

            if (random_number == 0) {
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_0.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
            if (random_number == 1) {
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_1.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
            if (random_number == 2) {
                level.playSound(null, this.getX(), this.getY(), this.getZ(), EntropySounds.SCYTHE_SPEWING_2.get(), net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
            }
        }
    }

    @Override
    protected void onHitEntity(@Nonnull EntityHitResult result) {
        this.playSound(EntropySounds.BLOOD_PROJECTILE_HIT.get(), 3.0F, 1.0F);
        super.onHitEntity(result);
        this.discard();
    }

    @Override
    public void tick() {
        // Run on both sides to ensure smooth movement and avoid jitter/teleporting
        if (!this.inGround) {
            Vec3 vec3 = this.getDeltaMovement();
            // If it has no velocity (e.g. summoned), initialize it once
            if (vec3.lengthSqr() < 0.001) {
                Vec3 look = Vec3.directionFromRotation(this.getXRot(), this.getYRot());
                this.setDeltaMovement(look.scale(0.75));
            } else {
                // Force constant speed of 0.75 blocks/tick (15 blocks/s) on both sides
                // This overrides air drag in AbstractArrow.tick() and prevents divergence
                @SuppressWarnings("null")
                Vec3 normalized = vec3.normalize();
                this.setDeltaMovement(normalized.scale(0.75));
            }
        }
        super.tick();

        if (this.level().isClientSide && !this.inGround) {
            // Spawn bubbles randomly behind the trajectory of the projectile
            if (this.random.nextFloat() < 0.7F) { // Spawn rate
                Vec3 movement = this.getDeltaMovement();
                // We position the particle slightly behind the current position
                double px = this.getX() - movement.x;
                double py = this.getY() - movement.y + 0.15D; // Adjust center
                double pz = this.getZ() - movement.z;

                // Add tiny random offset to make it feel natural
                px += (this.random.nextDouble() - 0.5) * 0.3;
                py += (this.random.nextDouble() - 0.5) * 0.3;
                pz += (this.random.nextDouble() - 0.5) * 0.3;

                // Spawn the particle
                this.level().addParticle(net.whateclipse.entropy.particles.EntropyParticles.BLOOD_BUBBLE_PARTICLE.get(), px, py, pz, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitBlock(@Nonnull net.minecraft.world.phys.BlockHitResult result) {
        this.playSound(EntropySounds.BLOOD_PROJECTILE_HIT.get(), 3.0F, 1.0F);
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDefaultHitGroundSoundEvent() {
        return EntropySounds.BLOOD_PROJECTILE_HIT.get();
    }

    // Set constant speed if it somehow slows down due to air resistance
    @Override
    public void setDeltaMovement(@Nonnull Vec3 delta) {
        super.setDeltaMovement(delta);
    }
}
