package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Block.class)
public class blockDropMixin {
	@Overwrite()
	public static void dropStack(World world, BlockPos pos, ItemStack stack) {
		if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
			float f = 0.5F;
			double d = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			double e = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			double g = (double)(world.random.nextFloat() * 0.5F) + 0.25D;
			if(pulaskisandshaxes.Companion.getConfig().getDoRandomDrops()) {
				Item item = Registry.ITEM.getRandom(world.random);
				int count = world.random.nextInt(3) + 8;
				stack = new ItemStack(item, count);
			}
			ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + g, stack);
			itemEntity.setToDefaultPickupDelay();
			world.spawnEntity(itemEntity);
		}
	}
}
