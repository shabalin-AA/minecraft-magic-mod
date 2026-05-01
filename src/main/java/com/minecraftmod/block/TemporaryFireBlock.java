package com.minecraftmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TemporaryFireBlock extends FireBlock {
    private final int lifetimeTicks;

    public TemporaryFireBlock(BlockBehaviour.Properties settings, int lifetimeTicks) {
        super(settings);
        this.lifetimeTicks = lifetimeTicks;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.isClientSide()) return;
        level.scheduleTick(pos, this, this.lifetimeTicks);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // natural fire spreading disabled
        //super.tick(state, level, pos, random);
        if (level.getBlockState(pos).is(this)) {
            level.removeBlock(pos, false);
        }
    }

}
