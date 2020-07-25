package net.vortetty.pulaskisandshaxes.items;

import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.vortetty.pulaskisandshaxes.mixin.AxeItemAccessor;
import net.vortetty.pulaskisandshaxes.mixin.HoeItemAccessor;

import java.util.Arrays;
import java.util.Map;

public class PulaskiItem extends MiningToolItem {
	public static final Map<Block, Block> STRIPPED_BLOCKS;
	public static final Map<Block, BlockState> TILLED_BLOCKS;
	
	ToolMaterial material;
	
	public PulaskiItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(attackDamage, attackSpeed, material, AxeItemAccessor.getEffectiveBlocks(), settings);
		this.material = material;
	}
	
	public float getMiningSpeed(ItemStack stack, BlockState state) {
		Material material = state.getMaterial();
		return this.miningSpeed;
	}
	
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		Block block = (Block)STRIPPED_BLOCKS.get(blockState.getBlock());
		if (block != null) {
			PlayerEntity playerEntity = context.getPlayer();
			world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			//stack.getOrCreateTag().putFloat("HOEING", 0f);
			if (!world.isClient) {
				world.setBlockState(blockPos, (BlockState)block.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)), 11);
				if (playerEntity != null) {
					context.getStack().damage(1, playerEntity, (p) -> {
						p.sendToolBreakStatus(context.getHand());
					});
				}
			}
			return ActionResult.SUCCESS;
			
		} else if (context.getSide() != Direction.DOWN && (world.getBlockState(blockPos.up()).getMaterial()==Material.AIR||world.getBlockState(blockPos.up()).getMaterial()==Material.BAMBOO||world.getBlockState(blockPos.up()).getMaterial()==Material.BAMBOO_SAPLING||world.getBlockState(blockPos.up()).getMaterial()==Material.PLANT)) {
			BlockState blockState1 = (BlockState)TILLED_BLOCKS.get(world.getBlockState(blockPos).getBlock());
			if (blockState1 != null) {
				PlayerEntity playerEntity = context.getPlayer();
				world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				//stack.getOrCreateTag().putFloat("HOEING", 1f);
				if (!world.isClient) {
					world.setBlockState(blockPos, blockState1, 11);
					if (playerEntity != null) {
						context.getStack().damage(1, playerEntity, (p) -> {
							p.sendToolBreakStatus(context.getHand());
						});
					}
				}
				
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}
	
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.damage(2, attacker, (e) -> {
			e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
		});
		return true;
	}
	
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
			stack.damage(1, miner, (e) -> {
				e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			});
		}
		//stack.getOrCreateTag().putFloat("HOEING", 0f);
		return true;
	}
	
	static {
		AxeItemAccessor.getEffectiveBlocks().addAll(Arrays.asList(Blocks.STRIPPED_ACACIA_LOG,Blocks.STRIPPED_ACACIA_WOOD,Blocks.STRIPPED_BIRCH_WOOD,Blocks.STRIPPED_BIRCH_LOG,Blocks.STRIPPED_CRIMSON_HYPHAE,Blocks.STRIPPED_CRIMSON_STEM,Blocks.STRIPPED_DARK_OAK_LOG,Blocks.STRIPPED_DARK_OAK_WOOD,Blocks.STRIPPED_JUNGLE_LOG,Blocks.STRIPPED_JUNGLE_WOOD,Blocks.STRIPPED_OAK_LOG,Blocks.STRIPPED_OAK_WOOD,Blocks.STRIPPED_SPRUCE_LOG,Blocks.STRIPPED_SPRUCE_WOOD,Blocks.STRIPPED_WARPED_HYPHAE,Blocks.STRIPPED_WARPED_STEM));
		STRIPPED_BLOCKS = AxeItemAccessor.getStrippedBlocks();
		TILLED_BLOCKS = HoeItemAccessor.getTilledBlocks();
	}
}