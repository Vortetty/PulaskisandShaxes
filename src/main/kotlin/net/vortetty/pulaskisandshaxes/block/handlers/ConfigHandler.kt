package net.vortetty.pulaskisandshaxes.block.handlers

import com.google.common.collect.Lists
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.PistonBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class ConfigHandler(private val world: World, private val posFrom: BlockPos, private val pistonDirection: Direction, private val retracted: Boolean, limit: Int) {
    private var posTo: BlockPos? = null
    private var motionDirection: Direction? = null
    private val movedBlocks: MutableList<BlockPos> = Lists.newArrayList()
    private val brokenBlocks: MutableList<BlockPos?> = Lists.newArrayList()
    private val limit: Int
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
                if (isBlockSticky(world.getBlockState(blockPos).block) && !canMoveAdjacentBlock(blockPos)) {
                    return false
                }
            }
            true
        }
    }

    private fun tryMove(pos: BlockPos?, dir: Direction?): Boolean {
        var blockState = world.getBlockState(pos)
        var block = blockState.block
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
                while (isBlockSticky(block)) {
                    val blockPos = pos!!.offset(motionDirection!!.opposite, i)
                    val block2 = block
                    blockState = world.getBlockState(blockPos)
                    block = blockState.block
                    if (blockState.isAir || !isAdjacentBlockStuck(block2, block) || !PistonBlock.isMovable(blockState, world, blockPos, motionDirection, false, motionDirection!!.opposite) || blockPos == posFrom) {
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
                            if (isBlockSticky(world.getBlockState(blockPos3).block) && !canMoveAdjacentBlock(blockPos3)) {
                                return false
                            }
                        }
                        return true
                    }
                    blockState = world.getBlockState(blockPos2)
                    if (blockState.isAir) {
                        return true
                    }
                    if (!PistonBlock.isMovable(blockState, world, blockPos2, motionDirection, true, motionDirection) || blockPos2 == posFrom) {
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

    private fun canMoveAdjacentBlock(pos: BlockPos): Boolean {
        val blockState = world.getBlockState(pos)
        val var3 = Direction.values()
        val var4 = var3.size
        for (var5 in 0 until var4) {
            val direction = var3[var5]
            if (direction.axis !== motionDirection!!.axis) {
                val blockPos = pos.offset(direction)
                val blockState2 = world.getBlockState(blockPos)
                if (isAdjacentBlockStuck(blockState2.block, blockState.block) && !tryMove(blockPos, direction)) {
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
        private fun isBlockSticky(block: Block): Boolean {
            return block === Blocks.SLIME_BLOCK || block === Blocks.HONEY_BLOCK
        }

        private fun isAdjacentBlockStuck(block: Block, block2: Block): Boolean {
            return if (block === Blocks.HONEY_BLOCK && block2 === Blocks.SLIME_BLOCK) {
                false
            } else if (block === Blocks.SLIME_BLOCK && block2 === Blocks.HONEY_BLOCK) {
                false
            } else {
                isBlockSticky(block) || isBlockSticky(block2)
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