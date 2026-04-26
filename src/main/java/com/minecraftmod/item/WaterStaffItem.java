package com.minecraftmod.item;

import com.minecraftmod.ModEntityTypes;
import com.minecraftmod.entity.WaterProjectile;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class WaterStaffItem extends Item {

    public static final int FULL_CHARGE_TICKS = 20;
    public static final int MAX_USE_DURATION_TICKS = 72000;
    public static final float MIN_CHARGE_RATIO = 0.1F;
    public static final int MIN_REQUIRED_CHARGE_TICKS = (int) Math.ceil(FULL_CHARGE_TICKS * MIN_CHARGE_RATIO);

    public static final float MAX_PROJECTILE_POWER = 1.5F;
    public static final float SPAWN_OFFSET_XZ = 1.2F;
    public static final float SPAWN_OFFSET_Y = -0.1F;
    public static final float PROJECTILE_INACCURACY = 0.0F;
    public static final float PROJECTILE_DIVERGENCE = 1.0F;

    public static final float SOUND_VOLUME = 0.5F;
    public static final int CHARGE_SOUND_INTERVAL = 4;
    public static final float PITCH_DIVIDEND = 0.4F;
    public static final float PITCH_RANDOM_RANGE = 0.4F;
    public static final float PITCH_BASE_OFFSET = 0.8F;

    public static final int DURABILITY_COST_PER_SHOT = 1;

    public WaterStaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
        return MAX_USE_DURATION_TICKS;
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public void onUseTick(final Level level, final LivingEntity player, final ItemStack itemStack, final int ticksRemaining) {
        if (ticksRemaining % CHARGE_SOUND_INTERVAL == 0 && !level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.CROSSBOW_LOADING_MIDDLE, SoundSource.PLAYERS, SOUND_VOLUME, 1.0F);
        }
    }

    @Override
    public boolean releaseUsing(final ItemStack stack, final Level level, final LivingEntity player, final int remainingTime) {
        var charge = getCharge(stack, player, remainingTime);
        if (!charge.apply) {
            return false;
        }
        if (!level.isClientSide()) {
            createProjectile(player, level, charge);
        }
        playSound(level, player);
        stack.hurtAndBreak(DURABILITY_COST_PER_SHOT, player, player.getUsedItemHand());
        return true;
    }

    void playSound(Level level, LivingEntity player) {
        float pitch = PITCH_DIVIDEND / (level.getRandom().nextFloat() * PITCH_RANDOM_RANGE + PITCH_BASE_OFFSET);
        level.playSound(
                null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS,
                SOUND_VOLUME, pitch
        );
    }

    void createProjectile(LivingEntity player, Level level, Charge charge) {
        WaterProjectile projectile = new WaterProjectile(ModEntityTypes.WATER_PROJECTILE, level);
        projectile.setPos(
                player.getX() + player.getLookAngle().x * SPAWN_OFFSET_XZ,
                player.getEyeY() + SPAWN_OFFSET_Y,
                player.getZ() + player.getLookAngle().z * SPAWN_OFFSET_XZ
        );
        float power = charge.value * MAX_PROJECTILE_POWER;
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), PROJECTILE_INACCURACY, power, PROJECTILE_DIVERGENCE);
        level.addFreshEntity(projectile);
    }

    Charge getCharge(ItemStack stack, LivingEntity player, int remainingTime) {
        int chargeTicks = getUseDuration(stack, player) - remainingTime;
        float charge = Math.min(chargeTicks / (float) FULL_CHARGE_TICKS, 1.0F);
        return new Charge(chargeTicks, charge, chargeTicks >= MIN_REQUIRED_CHARGE_TICKS);
    }

    record Charge(int ticks, float value, boolean apply) {}
}
