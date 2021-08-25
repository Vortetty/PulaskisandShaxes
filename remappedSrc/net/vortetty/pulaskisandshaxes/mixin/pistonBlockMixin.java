package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class pistonBlockMixin extends FacingBlock {
    protected pistonBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "isMovable", at = @At("RETURN"))
    private static void isMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        if (pos.getY() >= world.getBottomY() && pos.getY() <= world.getTopY() - 1 && world.getWorldBorder().contains(pos)) {
            if (state.isAir()) {
                cir.setReturnValue(true);
            } else if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR)) {
                if (direction == Direction.DOWN && pos.getY() == world.getBottomY()) {
                    cir.setReturnValue(false);
                } else if (direction == Direction.UP && pos.getY() == world.getTopY() - 1) {
                    cir.setReturnValue(false);
                } else {
                    if (!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON)) {
                        if (state.getHardness(world, pos) == -1.0F) {
                            cir.setReturnValue(false);
                        }

                        switch(state.getPistonBehavior()) {
                            case BLOCK:
                                cir.setReturnValue(state.getMaterial() == Material.PISTON);
                            case DESTROY:
                                cir.setReturnValue(canBreak);
                            case PUSH_ONLY:
                                cir.setReturnValue(direction == pistonDir);
                        }
                    } else if (state.get(PistonBlock.EXTENDED)) {
                        cir.setReturnValue(pulaskisandshaxes.Companion.getConfig().getConfig().get("general_config").asObject().getBoolean("allowPushExtendedPiston", false));
                    }

                    cir.setReturnValue(!state.hasBlockEntity() || pulaskisandshaxes.Companion.getConfig().getConfig().get("general_config").asObject().getBoolean("allowPushBlockEntities", false));
                }
            } else {
                cir.setReturnValue(false);
            }
        } else {
            cir.setReturnValue(false);
        }
    }
}
