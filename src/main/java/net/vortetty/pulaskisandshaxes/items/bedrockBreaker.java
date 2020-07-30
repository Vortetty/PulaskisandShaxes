package net.vortetty.pulaskisandshaxes.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.vortetty.pulaskisandshaxes.config.bedrockBreakerConfig;

import java.util.Random;

import static net.vortetty.pulaskisandshaxes.pulaskisandshaxes.BROKEN_BEDROCK_BREAKER;

public class bedrockBreaker extends ToolItem {
	public int uses;
	private int maxDamage;
	private bedrockBreakerConfig config;
	public Random rand = new Random();
	
	@Environment(EnvType.CLIENT)
	public Text getName() {
		return new TranslatableText(this.getTranslationKey());
	}
	
	public bedrockBreaker(ToolMaterial material, Settings settings, bedrockBreakerConfig config) {
		super(material, settings);
		this.config = config;
		this.uses = config.bedrockBreakerUses;
		this.maxDamage = config.bedrockBreakerUses * 10;
	}
	
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return 0F;
	}
	
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}
	
	public boolean hasGlint(ItemStack stack) {
		return true;
	}
	
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		if (!context.getWorld().isClient) {
			
			this.uses = config.bedrockBreakerUses;
			this.maxDamage = config.bedrockBreakerUses * 10;
			
			ItemStack stack = context.getStack();
			PlayerEntity playerEntity = context.getPlayer();
			if(world.getBlockState(pos) == Blocks.BEDROCK.getDefaultState()){
				world.breakBlock(pos, false, context.getPlayer());
				world.setBlockState(pos, Blocks.BEDROCK.getDefaultState());
				world.breakBlock(pos, false, context.getPlayer());
				world.setBlockState(pos, Blocks.CAVE_AIR.getDefaultState());
				world.createExplosion(null, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 0, Explosion.DestructionType.NONE);
				world.addParticle(ParticleTypes.EXPLOSION, pos.getX()+0.5+(rand.nextFloat()*0.25), pos.getY()+0.5+(rand.nextFloat()*0.25), pos.getZ()+0.5+(rand.nextFloat()*0.25), 0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.EXPLOSION, pos.getX()+0.5+(rand.nextFloat()*0.25), pos.getY()+0.5+(rand.nextFloat()*0.25), pos.getZ()+0.5+(rand.nextFloat()*0.25), 0D, 0.0D, 0.0D);
				world.addParticle(ParticleTypes.EXPLOSION, pos.getX()+0.5+(rand.nextFloat()*0.25), pos.getY()+0.5+(rand.nextFloat()*0.25), pos.getZ()+0.5+(rand.nextFloat()*0.25), 0D, 0.0D, 0.0D);
				if(stack.getDamage() >= this.getMaxDamage() - this.getMaxDamage() / this.uses && playerEntity != null && !playerEntity.isCreative()) {
					playerEntity.setStackInHand(Hand.MAIN_HAND, new ItemStack(BROKEN_BEDROCK_BREAKER));
				}
				if (playerEntity != null && !playerEntity.isCreative()) {
					playerEntity.getStackInHand(Hand.MAIN_HAND).setDamage(this.getMaxDamage() / this.uses + playerEntity.getStackInHand(Hand.MAIN_HAND).getDamage());
				}
			}
		}
		if(world.getBlockState(pos) == Blocks.BEDROCK.getDefaultState()){
			return ActionResult.success(true);
		}
		return ActionResult.success(false);
	}
}
