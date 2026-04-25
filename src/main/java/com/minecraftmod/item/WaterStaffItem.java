package com.minecraftmod.item;

import com.minecraftmod.entity.WaterProjectile;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.minecraftmod.ModEntityTypes.WATER_PROJECTILE;

public class WaterStaffItem extends Item {
    public WaterStaffItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            WaterProjectile projectile = new WaterProjectile(
                    WATER_PROJECTILE, level
            );
            projectile.setPos(
                    player.getX() + player.getLookAngle().x * 1.2,
                    player.getEyeY() - 0.1,
                    player.getZ() + player.getLookAngle().z * 1.2
            );
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }

        level.playSound(
                null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        return InteractionResult.SUCCESS;
    }
}
