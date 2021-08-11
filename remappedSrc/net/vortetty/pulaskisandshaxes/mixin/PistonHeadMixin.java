package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonHeadBlock.class)
public class PistonHeadMixin extends FacingBlock {
	protected PistonHeadMixin(Settings settings) {
		super(settings);
	}
	
	@Inject(method = "canPlaceAt",at = @At("RETURN"),cancellable = true)
	private void mixin(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState block = world.getBlockState(pos.offset(((Direction)state.get(FACING)).getOpposite()));
		cir.setReturnValue(block.getMaterial()==Material.PISTON);
	}
}
