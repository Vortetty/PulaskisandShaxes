package net.vortetty.pulaskisandshaxes.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class uselessItem extends Item {
	public boolean glint;
	
	public uselessItem(Settings settings) {
		super(settings);
		this.glint = false;
	}
	
	public uselessItem(Settings settings, boolean glint) {
		super(settings);
		this.glint = glint;
	}
	
	@Override
	public boolean damage(DamageSource source) {
		return super.damage(source);
	}
	
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
	}
	
	public boolean postProcessTag(CompoundTag tag) {
		return false;
	}
	
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return true;
	}
	
	public Item asItem() {
		return this;
	}
	
	public ActionResult useOnBlock(ItemUsageContext context) {
		return ActionResult.PASS;
	}
	
	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		return 1.0F;
	}
	
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (this.isFood()) {
			ItemStack itemStack = user.getStackInHand(hand);
			if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
				user.setCurrentHand(hand);
				return TypedActionResult.consume(itemStack);
			} else {
				return TypedActionResult.fail(itemStack);
			}
		} else {
			return TypedActionResult.pass(user.getStackInHand(hand));
		}
	}
	
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		return this.isFood() ? user.eatFood(world, stack) : stack;
	}
	
	public boolean isDamageable() {
		return false;
	}
	
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return false;
	}
	
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		return false;
	}
	
	public boolean isEffectiveOn(BlockState state) {
		return false;
	}
	
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		return ActionResult.success(false);
	}
	
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}
	
	public boolean hasGlint(ItemStack stack) {
		return this.glint;
	}
	
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
}
