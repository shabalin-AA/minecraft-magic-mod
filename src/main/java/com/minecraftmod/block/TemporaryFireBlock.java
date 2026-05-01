package com.minecraftmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.ScheduledTick;

public class TemporaryFireBlock extends FireBlock {
    private final int lifetimeTicks;

    public TemporaryFireBlock(BlockBehaviour.Properties settings, int lifetimeTicks) {
        super(settings);
        this.lifetimeTicks = lifetimeTicks;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.isClientSide()) return;
        if (level instanceof ServerLevel serverLevel) {
            long triggerTick = serverLevel.getGameTime() + this.lifetimeTicks;
            ScheduledTick<Block> scheduled = new ScheduledTick<>(this.asBlock(), pos, triggerTick, 0L);
            serverLevel.getBlockTicks().schedule(scheduled);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos).is(this)) {
            level.removeBlock(pos, false);
        }
    }

}
