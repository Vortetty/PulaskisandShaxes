package net.minecraft.block.piston

import com.google.common.collect.Lists
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.PistonBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ConfigHandler(
    private val world: World,
    private val posFrom: BlockPos,
    private val pistonDirection: Direction,
    private val retracted: Boolean,
    limit: Int
) {
    private var posTo: BlockPos? = null
    var motionDirection: Direction? = null
    private val movedBlocks: MutableList<BlockPos> = Lists.newArrayList()
    private val brokenBlocks: MutableList<BlockPos?> = Lists.newArrayList()
    private var limit: Int
    fun calculatePush(): Boolean {
        movedBlocks.clear()
        brokenBlocks.clear()
        val blockState = world.getBlockState(posTo)
        return if (!PistonBlock.isMovable(blockState, world, posTo, motionDirection, false, pistonDirection)) {
            if (retracted && blockState.pistonBehavior == PistonBehavior.DESTROY) {
                brokenBlocks.add(posTo)
                true
            } else {
                false
            }
        } else if (!tryMove(posTo, motionDirection)) {
            false
        } else {
            for (i in movedBlocks.indices) {
                val blockPos = movedBlocks[i]
                if (isBlockSticky(world.getBlockState(blockPos)) && !tryMoveAdjacentBlock(blockPos)) {
                    return false
                }
            }
            true
        }
    }

    private fun tryMove(pos: BlockPos?, dir: Direction?): Boolean {
        var blockState = world.getBlockState(pos)
        if (blockState.isAir) {
            return true
        } else if (!PistonBlock.isMovable(blockState, world, pos, motionDirection, false, dir)) {
            return true
        } else if (pos == posFrom) {
            return true
        } else if (movedBlocks.contains(pos)) {
            return true
        } else {
            var i = 1
            if (i + movedBlocks.size > limit) {
                return false
            } else {
                while (isBlockSticky(blockState)) {
                    val blockPos = pos!!.offset(motionDirection!!.opposite, i)
                    val blockState2 = blockState
                    blockState = world.getBlockState(blockPos)
                    if (blockState.isAir || !isAdjacentBlockStuck(blockState2, blockState) || !PistonBlock.isMovable(
                            blockState,
                            world, blockPos, motionDirection, false, motionDirection!!.opposite
                        ) || blockPos == posFrom
                    ) {
                        break
                    }
                    ++i
                    if (i + movedBlocks.size > limit) {
                        return false
                    }
                }
                var j = 0
                var l: Int
                l = i - 1
                while (l >= 0) {
                    movedBlocks.add(pos!!.offset(motionDirection!!.opposite, l))
                    ++j
                    --l
                }
                l = 1
                while (true) {
                    val blockPos2 = pos!!.offset(motionDirection, l)
                    val m = movedBlocks.indexOf(blockPos2)
                    if (m > -1) {
                        setMovedBlocks(j, m)
                        for (n in 0..m + j) {
                            val blockPos3 = movedBlocks[n]
                            if (isBlockSticky(world.getBlockState(blockPos3)) && !tryMoveAdjacentBlock(blockPos3)) {
                                return false
                            }
                        }
                        return true
                    }
                    blockState = world.getBlockState(blockPos2)
                    if (blockState.isAir) {
                        return true
                    }
                    if (!PistonBlock.isMovable(
                            blockState,
                            world,
                            blockPos2,
                            motionDirection,
                            true,
                            motionDirection
                        ) || blockPos2 == posFrom
                    ) {
                        return false
                    }
                    if (blockState.pistonBehavior == PistonBehavior.DESTROY) {
                        brokenBlocks.add(blockPos2)
                        return true
                    }
                    if (movedBlocks.size >= limit) {
                        return false
                    }
                    movedBlocks.add(blockPos2)
                    ++j
                    ++l
                }
            }
        }
    }

    private fun setMovedBlocks(from: Int, to: Int) {
        val list: MutableList<BlockPos> = Lists.newArrayList()
        val list2: MutableList<BlockPos> = Lists.newArrayList()
        val list3: MutableList<BlockPos> = Lists.newArrayList()
        list.addAll(movedBlocks.subList(0, to))
        list2.addAll(movedBlocks.subList(movedBlocks.size - from, movedBlocks.size))
        list3.addAll(movedBlocks.subList(to, movedBlocks.size - from))
        movedBlocks.clear()
        movedBlocks.addAll(list)
        movedBlocks.addAll(list2)
        movedBlocks.addAll(list3)
    }

    private fun tryMoveAdjacentBlock(pos: BlockPos): Boolean {
        val blockState = world.getBlockState(pos)
        val var3 = Direction.values()
        val var4 = var3.size
        for (var5 in 0 until var4) {
            val direction = var3[var5]
            if (direction.axis !== motionDirection!!.axis) {
                val blockPos = pos.offset(direction)
                val blockState2 = world.getBlockState(blockPos)
                if (isAdjacentBlockStuck(blockState2, blockState) && !tryMove(blockPos, direction)) {
                    return false
                }
            }
        }
        return true
    }

    fun getMovedBlocks(): List<BlockPos> {
        return movedBlocks
    }

    fun getBrokenBlocks(): List<BlockPos?> {
        return brokenBlocks
    }

    companion object {
        private fun isBlockSticky(state: BlockState): Boolean {
            return state.isOf(Blocks.SLIME_BLOCK) || state.isOf(Blocks.HONEY_BLOCK)
        }

        private fun isAdjacentBlockStuck(state: BlockState, adjacentState: BlockState): Boolean {
            return if (state.isOf(Blocks.HONEY_BLOCK) && adjacentState.isOf(Blocks.SLIME_BLOCK)) {
                false
            } else if (state.isOf(Blocks.SLIME_BLOCK) && adjacentState.isOf(Blocks.HONEY_BLOCK)) {
                false
            } else {
                isBlockSticky(state) || isBlockSticky(adjacentState)
            }
        }
    }

    init {
        if (retracted) {
            motionDirection = pistonDirection
            posTo = posFrom.offset(pistonDirection)
        } else {
            motionDirection = pistonDirection.opposite
            posTo = posFrom.offset(pistonDirection, 2)
        }

        this.limit = limit
    }
}