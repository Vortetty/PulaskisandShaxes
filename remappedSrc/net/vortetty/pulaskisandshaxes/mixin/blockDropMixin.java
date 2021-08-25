package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;

import static java.lang.Math.max;

@Mixin(Block.class)
public class blockDropMixin {
	@Overwrite
	public static void dropStack(World world, BlockPos pos, ItemStack stack) {
		if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
			float f = 0.5F;
			double d = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			double e = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			double g = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			if(pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doRandomBlockDrops", false)) {
				Item item = Registry.ITEM.getRandom(world.random);
				int count = world.random.nextInt(3) + 8;
				stack = new ItemStack(item, count);
			}
			ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, stack);
			itemEntity.setToDefaultPickupDelay();
			world.spawnEntity(itemEntity);
		}
	}

	@Overwrite
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if(pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doRandomPlaces", false)) {
			Block tmp = Registry.BLOCK.getRandom(world.random);
			world.setBlockState(
					pos, tmp.getStateManager().getStates().get(max(0, world.random.nextInt(max(1, tmp.getStateManager().getStates().size())) - 1))
			);
		}
	}
}
