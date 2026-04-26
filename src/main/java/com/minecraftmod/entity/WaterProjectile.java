package com.minecraftmod.entity;


import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.material.Fluids;

public class WaterProjectile extends ThrowableProjectile implements ItemSupplier {

    public WaterProjectile(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        spawnWater(result.getBlockPos());
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        BlockPos pos = result.getEntity().getOnPos();
        spawnWater(pos);
        this.discard();
    }

    private void spawnWater(BlockPos hitPos) {
        if (this.level().isClientSide()) return;

        var surfacePos = hitPos.above();
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos target = surfacePos.offset(x, 0, z);
                if (this.level().getBlockState(target).canBeReplaced()) {
                    this.level().setBlock(target, Fluids.WATER.defaultFluidState().createLegacyBlock(), 3);
                }
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {

    }

    @Override
    public ItemStack getItem() {
        return Items.SNOWBALL.getDefaultInstance();
    }
}