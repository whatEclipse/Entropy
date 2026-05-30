package net.whateclipse.entropy.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.whateclipse.entropy.enchantments.EntropyEnchantments;
import net.whateclipse.entropy.entities.EntropyBloodProjectileEntity;

public class ScytheItem extends SwordItem {

    public ScytheItem(Tier tier, float damage, float attackSpeed, Properties properties) {
        super(tier, new Item.Properties()
                .attributes(SwordItem.createAttributes(tier, (int) damage, attackSpeed)));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_HOE)) {
            return getTier().getSpeed();
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(
                    level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                            .getOrThrow(EntropyEnchantments.SPEWING), itemStack);

            if (enchantLevel > 0) {
                if (player.getCooldowns().isOnCooldown(itemStack.getItem())){
                    return InteractionResultHolder.pass(itemStack);
                }

                EntropyBloodProjectileEntity projectile = new EntropyBloodProjectileEntity(
                        (ServerLevel) level, player, new ItemStack(Items.ARROW));


                player.getCooldowns().addCooldown(itemStack.getItem(), 20);
                level.addFreshEntity(projectile);
                player.hurt(level.damageSources().magic(), 4.0F);
                return InteractionResultHolder.success(itemStack);
            }
        }

        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        // Only till if clicking the top face of a block
        if (context.getClickedFace() == Direction.DOWN) {
            return InteractionResult.PASS;
        }

        // Determine what this block tills into
        BlockState tilledState = null;
        if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)
                || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.ROOTED_DIRT)
                || state.is(Blocks.DIRT_PATH)) {
            tilledState = Blocks.FARMLAND.defaultBlockState();
        }

        if (tilledState == null) return InteractionResult.PASS;

        // Can't till if something is sitting on top
        if (!level.getBlockState(pos.above()).isAir()) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide && player != null) {
            level.setBlock(pos, tilledState, Block.UPDATE_ALL_IMMEDIATE);
            level.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);

            // Damage the item — correct 1.21.1 NeoForge signature
            context.getItemInHand().hurtAndBreak(
                    1,
                    (ServerLevel) level,
                    player,
                    item -> {}
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}