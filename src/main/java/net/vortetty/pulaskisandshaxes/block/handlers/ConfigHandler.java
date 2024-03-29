//
// Sooooo
// For some reason writing this in java fixed a bug where slime/honey blocks wouldn't stick to anything except immediately adjacent blocks
//

package net.vortetty.pulaskisandshaxes.block.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.vortetty.pulaskisandshaxes.block.ConfigPiston;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ConfigHandler {
    public final int MAX_MOVABLE_BLOCKS;
    public final int limit;
    private final World world;
    private final BlockPos posFrom;
    private final boolean retracted;
    private final BlockPos posTo;
    private final Direction motionDirection;
    private final List<BlockPos> movedBlocks = Lists.newArrayList();
    private final List<BlockPos> brokenBlocks = Lists.newArrayList();
    private final Direction pistonDirection;

    public ConfigHandler(World world, BlockPos pos, Direction dir, boolean retracted, int limit) {
        this.world = world;
        this.posFrom = pos;
        this.pistonDirection = dir;
        this.retracted = retracted;
        if (retracted) {
            this.motionDirection = dir;
            this.posTo = pos.offset(dir);
        } else {
            this.motionDirection = dir.getOpposite();
            this.posTo = pos.offset((Direction)dir, 2);
        }
        this.MAX_MOVABLE_BLOCKS = limit;
        this.limit = limit;
    }
    
    private static Boolean isMovable(BlockState state, @NotNull World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir) {
        if (pos.getY() >= world.getBottomY() && pos.getY() <= world.getTopY() - 1 && world.getWorldBorder().contains(pos)) {
            if (state.isAir()) {
                return true;
            } else if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR)) {
                if (direction == Direction.DOWN && pos.getY() == world.getBottomY()) {
                    return false;
                } else if (direction == Direction.UP && pos.getY() == world.getTopY() - 1) {
                    return false;
                } else {
                    if (!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON)) {
                        if (state.getHardness(world, pos) == -1.0F) {
                            return false;
                        }

                        switch(state.getPistonBehavior()) {
                            case BLOCK:
                                return state.getMaterial() == Material.PISTON;
                            case DESTROY:
                                return canBreak;
                            case PUSH_ONLY:
                                return direction == pistonDir;
                        }
                    } else if (state.get(PistonBlock.EXTENDED)) {
                        return pulaskisandshaxes.Companion.getConfig().getConfig().get("general_config").asObject().getBoolean("allowPushExtendedPiston", false);
                    }

                    return !state.hasBlockEntity() || pulaskisandshaxes.Companion.getConfig().getConfig().get("general_config").asObject().getBoolean("allowPushBlockEntities", false);
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean calculatePush() {
        this.movedBlocks.clear();
        this.brokenBlocks.clear();
        BlockState blockState = this.world.getBlockState(this.posTo);
        if (!isMovable(blockState, this.world, this.posTo, this.motionDirection, false, this.pistonDirection)) {
            if (this.retracted && blockState.getPistonBehavior() == PistonBehavior.DESTROY) {
                this.brokenBlocks.add(this.posTo);
                return true;
            } else {
                return false;
            }
        } else if (!this.tryMove(this.posTo, this.motionDirection)) {
            return false;
        } else {
            for(int i = 0; i < this.movedBlocks.size(); ++i) {
                BlockPos blockPos = (BlockPos)this.movedBlocks.get(i);
                if (isBlockSticky(this.world.getBlockState(blockPos)) && !this.tryMoveAdjacentBlock(blockPos)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean isBlockSticky(BlockState state) {
        return state.isOf(Blocks.SLIME_BLOCK) || state.isOf(Blocks.HONEY_BLOCK);
    }

    private static boolean isAdjacentBlockStuck(BlockState state, BlockState adjacentState) {
        if (state.isOf(Blocks.HONEY_BLOCK) && adjacentState.isOf(Blocks.SLIME_BLOCK)) {
            return false;
        } else if (state.isOf(Blocks.SLIME_BLOCK) && adjacentState.isOf(Blocks.HONEY_BLOCK)) {
            return false;
        } else {
            return isBlockSticky(state) || isBlockSticky(adjacentState);
        }
    }

    private boolean tryMove(BlockPos pos, Direction dir) {
        BlockState blockState = this.world.getBlockState(pos);
        if (blockState.isAir()) {
            return true;
        } else if (!isMovable(blockState, this.world, pos, this.motionDirection, false, dir)) {
            return true;
        } else if (pos.equals(this.posFrom)) {
            return true;
        } else if (this.movedBlocks.contains(pos)) {
            return true;
        } else {
            int i = 1;
            if (i + this.movedBlocks.size() > limit) {
                return false;
            } else {
                while(isBlockSticky(blockState)) {
                    BlockPos blockPos = pos.offset(this.motionDirection.getOpposite(), i);
                    BlockState blockState2 = blockState;
                    blockState = this.world.getBlockState(blockPos);
                    if (blockState.isAir() || !isAdjacentBlockStuck(blockState2, blockState) || !isMovable(blockState, this.world, blockPos, this.motionDirection, false, this.motionDirection.getOpposite()) || blockPos.equals(this.posFrom)) {
                        break;
                    }

                    ++i;
                    if (i + this.movedBlocks.size() > limit) {
                        return false;
                    }
                }

                int j = 0;

                int l;
                for(l = i - 1; l >= 0; --l) {
                    this.movedBlocks.add(pos.offset(this.motionDirection.getOpposite(), l));
                    ++j;
                }

                l = 1;

                while(true) {
                    BlockPos blockPos2 = pos.offset(this.motionDirection, l);
                    int m = this.movedBlocks.indexOf(blockPos2);
                    if (m > -1) {
                        this.setMovedBlocks(j, m);

                        for(int n = 0; n <= m + j; ++n) {
                            BlockPos blockPos3 = (BlockPos)this.movedBlocks.get(n);
                            if (isBlockSticky(this.world.getBlockState(blockPos3)) && !this.tryMoveAdjacentBlock(blockPos3)) {
                                return false;
                            }
                        }

                        return true;
                    }

                    blockState = this.world.getBlockState(blockPos2);
                    if (blockState.isAir()) {
                        return true;
                    }

                    if (!isMovable(blockState, this.world, blockPos2, this.motionDirection, true, this.motionDirection) || blockPos2.equals(this.posFrom)) {
                        return false;
                    }

                    if (blockState.getPistonBehavior() == PistonBehavior.DESTROY) {
                        this.brokenBlocks.add(blockPos2);
                        return true;
                    }

                    if (this.movedBlocks.size() >= limit) {
                        return false;
                    }

                    this.movedBlocks.add(blockPos2);
                    ++j;
                    ++l;
                }
            }
        }
    }

    private void setMovedBlocks(int from, int to) {
        List<BlockPos> list = Lists.newArrayList();
        List<BlockPos> list2 = Lists.newArrayList();
        List<BlockPos> list3 = Lists.newArrayList();
        list.addAll(this.movedBlocks.subList(0, to));
        list2.addAll(this.movedBlocks.subList(this.movedBlocks.size() - from, this.movedBlocks.size()));
        list3.addAll(this.movedBlocks.subList(to, this.movedBlocks.size() - from));
        this.movedBlocks.clear();
        this.movedBlocks.addAll(list);
        this.movedBlocks.addAll(list2);
        this.movedBlocks.addAll(list3);
    }

    private boolean tryMoveAdjacentBlock(BlockPos pos) {
        BlockState blockState = this.world.getBlockState(pos);
        Direction[] var3 = Direction.values();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() != this.motionDirection.getAxis()) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState2 = this.world.getBlockState(blockPos);
                if (isAdjacentBlockStuck(blockState2, blockState) && !this.tryMove(blockPos, direction)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Direction getMotionDirection() {
        return this.motionDirection;
    }

    public List<BlockPos> getMovedBlocks() {
        return this.movedBlocks;
    }

    public List<BlockPos> getBrokenBlocks() {
        return this.brokenBlocks;
    }
}
