package net.whateclipse.entropy.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.whateclipse.entropy.particles.EntropyParticles;
import net.whateclipse.entropy.sounds.EntropySounds;
import javax.annotation.Nonnull;

public class EntropyBloodProjectileEntity extends AbstractArrow {

    private static final float SPEED = 1.5F;

    // Synced data so the client knows the random Y rotation
    private static final EntityDataAccessor<Float> RANDOM_Y_ROT =
            SynchedEntityData.defineId(EntropyBloodProjectileEntity.class, EntityDataSerializers.FLOAT);

    public EntropyBloodProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
        playRandomSpewingSound(level);
    }

    public EntropyBloodProjectileEntity(Level level, LivingEntity shooter, @Nonnull ItemStack pickupItem) {
        super(EntropyEntities.BLOOD_PROJECTILE.get(), shooter, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
        this.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot(), 0.0F, SPEED, 0.0F);
        // Assign a random Y rotation offset (0-360) on spawn
        this.entityData.set(RANDOM_Y_ROT, level.random.nextFloat() * 360.0F);
        playRandomSpewingSound(level);
    }

    public EntropyBloodProjectileEntity(Level level, double x, double y, double z, @Nonnull ItemStack pickupItem) {
        super(EntropyEntities.BLOOD_PROJECTILE.get(), x, y, z, level, pickupItem, null);
        this.setBaseDamage(17.33);
        this.setNoGravity(true);
        this.entityData.set(RANDOM_Y_ROT, level.random.nextFloat() * 360.0F);
        playRandomSpewingSound(level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(RANDOM_Y_ROT, 0.0F);
    }

    public float getRandomYRot() {
        return this.entityData.get(RANDOM_Y_ROT);
    }

    private void playRandomSpewingSound(Level level) {
        if (!level.isClientSide) {
            int random_number = level.random.nextInt(3);
            var sound = switch (random_number) {
                case 0 -> EntropySounds.SCYTHE_SPEWING_0.get();
                case 1 -> EntropySounds.SCYTHE_SPEWING_1.get();
                default -> EntropySounds.SCYTHE_SPEWING_2.get();
            };
            level.playSound(null, this.getX(), this.getY(), this.getZ(),
                    sound, net.minecraft.sounds.SoundSource.PLAYERS,
                    1.0F, 1.0F + (level.random.nextFloat() - 0.5f) * 0.2f);
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
        if (!this.inGround) {
            Vec3 vec3 = this.getDeltaMovement();
            if (vec3.lengthSqr() < 0.001) {
                Vec3 look = Vec3.directionFromRotation(this.getXRot(), this.getYRot());
                this.setDeltaMovement(look.scale(SPEED));
            } else {
                this.setDeltaMovement(vec3.normalize().scale(SPEED));
            }
        }

        super.tick();

        // Client-side trail particles spanning the full width/height of the entity
        if (this.level().isClientSide && !this.inGround) {
            Vec3 movement = this.getDeltaMovement();
            float halfW = this.getBbWidth() / 2.0F;
            float halfH = this.getBbHeight() / 2.0F;

            for (int i = 0; i < 5; i++) {
                double offsetX = (this.random.nextDouble() - 0.5) * halfW * 2;
                double offsetY = (this.random.nextDouble()) * halfH * 2;
                double offsetZ = (this.random.nextDouble() - 0.5) * halfW * 2;

                double px = this.getX() - movement.x + offsetX;
                double py = this.getY() - movement.y + offsetY;
                double pz = this.getZ() - movement.z + offsetZ;

                this.level().addParticle(
                        EntropyParticles.BLOOD_BUBBLE_PARTICLE.get(),
                        px, py, pz,
                        0.0D, 0.0D, 0.0D
                );
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
}