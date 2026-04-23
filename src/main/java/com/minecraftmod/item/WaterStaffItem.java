package com.minecraftmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WaterStaffItem extends Item {
    public WaterStaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        BlockPos placePos = pos.relative(face);
        ItemStack stack = context.getItemInHand();
        InteractionResult result = InteractionResult.PASS;
        if (player != null && !world.isClientSide()) {
            if (player.mayUseItemAt(placePos, face, stack)) {
                BlockState waterState = Blocks.WATER.defaultBlockState();
                boolean placed = world.setBlock(placePos, waterState, 3);
                if (placed) {
                    world.playSound(null, placePos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    stack.hurtAndBreak(1, player, player.getUsedItemHand());
                    result = InteractionResult.SUCCESS;
                }
            }
        }
        return result;
    }
}
