package net.vortetty.pulaskisandshaxes.block

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import net.minecraft.block.*
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.block.enums.PistonType
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
import net.minecraft.entity.mob.PiglinBrain
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.tag.BlockTags
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.ItemScatterer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.vortetty.pulaskisandshaxes.block.handlers.ConfigHandler
import net.minecraft.block.Blocks as Blocks

class ConfigPiston(sticky: Boolean, settings: Settings?, limit: Int) : FacingBlock(settings) {
    private val sticky: Boolean
    private val limit: Int
    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        val be = world.getBlockEntity(pos)
        if (!world.isClient && !player.isCreative) {
            ItemScatterer.spawn(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), ItemStack(asItem()))
        }
        if (isIn(BlockTags.GUARDED_BY_PIGLINS)) {
            PiglinBrain.onGuardedBlockBroken(player, false)
        }
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return if (state.get(EXTENDED) as Boolean) {
            when (state.get(FACING) as Direction) {
                Direction.DOWN -> EXTENDED_DOWN_SHAPE!!
                Direction.UP -> EXTENDED_UP_SHAPE!!
                Direction.NORTH -> EXTENDED_NORTH_SHAPE!!
                Direction.SOUTH -> EXTENDED_SOUTH_SHAPE!!
                Direction.WEST -> EXTENDED_WEST_SHAPE!!
                Direction.EAST -> EXTENDED_EAST_SHAPE!!
                else -> EXTENDED_UP_SHAPE!!
            }
        } else {
            VoxelShapes.fullCube()
        }
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        if (!world.isClient) {
            tryMove(world, pos, state)
        }
    }

    override fun neighborUpdate(state: BlockState, world: World, pos: BlockPos, block: Block, fromPos: BlockPos, notify: Boolean) {
        if (!world.isClient) {
            tryMove(world, pos, state)
        }
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, notify: Boolean) {
        if (!oldState.isOf(state.block)) {
            if (!world.isClient && world.getBlockEntity(pos) == null) {
                tryMove(world, pos, state)
            }
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return (defaultState.with(FACING, ctx.playerLookDirection.opposite) as BlockState).with(EXTENDED, false) as BlockState
    }

    private fun tryMove(world: World, pos: BlockPos, state: BlockState) {
        val direction = state.get(FACING) as Direction
        val bl = shouldExtend(world, pos, direction)
        if (bl && !state.get(EXTENDED)) {
            if (ConfigHandler(world, pos, direction, true, limit).calculatePush()) {
                world.addSyncedBlockEvent(pos, this, 0, direction.id)
            }
        } else if (!bl && state.get(EXTENDED) as Boolean) {
            val blockPos = pos.offset(direction, 2)
            val blockState = world.getBlockState(blockPos)
            var i = 1
            if (blockState.isOf(Blocks.MOVING_PISTON) && blockState.get(FACING) == direction) {
                val blockEntity = world.getBlockEntity(blockPos)
                if (blockEntity is PistonBlockEntity) {
                    val pistonBlockEntity = blockEntity
                    if (pistonBlockEntity.isExtending && (pistonBlockEntity.getProgress(0.0f) < 0.5f || world.time == pistonBlockEntity.savedWorldTime || (world as ServerWorld).isInBlockTick)) {
                        i = 2
                    }
                }
            }
            world.addSyncedBlockEvent(pos, this, i, direction.id)
        }
    }

    private fun shouldExtend(world: World, pos: BlockPos, pistonFace: Direction): Boolean {
        val var4 = Direction.values()
        val var5 = var4.size
        var var6: Int
        var6 = 0
        while (var6 < var5) {
            val direction = var4[var6]
            if (direction != pistonFace && world.isEmittingRedstonePower(pos.offset(direction), direction)) {
                return true
            }
            ++var6
        }
        return if (world.isEmittingRedstonePower(pos, Direction.DOWN)) {
            true
        } else {
            val blockPos = pos.up()
            val var10 = Direction.values()
            var6 = var10.size
            for (var11 in 0 until var6) {
                val direction2 = var10[var11]
                if (direction2 != Direction.DOWN && world.isEmittingRedstonePower(blockPos.offset(direction2), direction2)) {
                    return true
                }
            }
            false
        }
    }

    override fun onSyncedBlockEvent(state: BlockState, world: World, pos: BlockPos, type: Int, data: Int): Boolean {
        val direction = state.get(FACING) as Direction
        if (!world.isClient) {
            val bl = shouldExtend(world, pos, direction)
            if (bl && (type == 1 || type == 2)) {
                world.setBlockState(pos, state.with(EXTENDED, true) as BlockState, 2)
                return false
            }
            if (!bl && type == 0) {
                return false
            }
        }
        if (type == 0) {
            if (!move(world, pos, direction, true)) {
                return false
            }
            world.setBlockState(pos, state.with(EXTENDED, true) as BlockState, 67)
            world.playSound(null as PlayerEntity?, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.25f + 0.6f)
        } else if (type == 1 || type == 2) {
            val blockEntity = world.getBlockEntity(pos.offset(direction))
            if (blockEntity is PistonBlockEntity) {
                blockEntity.finish()
            }
            val blockState = (Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, direction) as BlockState).with(PistonExtensionBlock.TYPE, if (sticky) PistonType.STICKY else PistonType.DEFAULT) as BlockState
            world.setBlockState(pos, blockState, 20)
            world.setBlockEntity(pos, PistonExtensionBlock.createBlockEntityPiston(defaultState.with(FACING, Direction.byId(data and 7)) as BlockState, direction, false, true))
            world.updateNeighbors(pos, blockState.block)
            blockState.method_30101(world, pos, 2)
            if (sticky) {
                val blockPos = pos.add(direction.offsetX * 2, direction.offsetY * 2, direction.offsetZ * 2)
                val blockState2 = world.getBlockState(blockPos)
                var bl2 = false
                if (blockState2.isOf(Blocks.MOVING_PISTON)) {
                    val blockEntity2 = world.getBlockEntity(blockPos)
                    if (blockEntity2 is PistonBlockEntity) {
                        val pistonBlockEntity = blockEntity2
                        if (pistonBlockEntity.facing == direction && pistonBlockEntity.isExtending) {
                            pistonBlockEntity.finish()
                            bl2 = true
                        }
                    }
                }
                if (!bl2) {
                    if (type != 1 || blockState2.isAir || !isMovable(blockState2, world, blockPos, direction.opposite, false, direction) || blockState2.pistonBehavior != PistonBehavior.NORMAL && !blockState2.isOf(Blocks.PISTON) && !blockState2.isOf(Blocks.STICKY_PISTON)) {
                        world.removeBlock(pos.offset(direction), false)
                    } else {
                        move(world, pos, direction, false)
                    }
                }
            } else {
                world.removeBlock(pos.offset(direction), false)
            }
            world.playSound(null as PlayerEntity?, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.15f + 0.6f)
        }
        return true
    }

    private fun move(world: World, pos: BlockPos, dir: Direction, retract: Boolean): Boolean {
        val blockPos = pos.offset(dir)
        if (!retract && world.getBlockState(blockPos).isOf(Blocks.PISTON_HEAD)) {
            world.setBlockState(blockPos, Blocks.AIR.defaultState, 20)
        }
        val pistonHandler = ConfigHandler(world, pos, dir, retract, limit)
        return if (!pistonHandler.calculatePush()) {
            false
        } else {
            val map: MutableMap<BlockPos, BlockState> = Maps.newHashMap()
            val list: List<BlockPos> = pistonHandler.getMovedBlocks()
            val list2: MutableList<BlockState> = Lists.newArrayList()
            for (i in list.indices) {
                val blockPos2 = list[i]
                val blockState = world.getBlockState(blockPos2)
                list2.add(blockState)
                map[blockPos2] = blockState
            }
            val list3: List<BlockPos> = pistonHandler.getBrokenBlocks() as List<BlockPos>
            val blockStates = arrayOfNulls<BlockState>(list.size + list3.size)
            val direction = if (retract) dir else dir.opposite
            var j = 0
            var l: Int
            var blockPos4: BlockPos
            var blockState8: BlockState?
            l = list3.size - 1
            while (l >= 0) {
                blockPos4 = list3[l]
                blockState8 = world.getBlockState(blockPos4)
                val blockEntity = if (blockState8.block.hasBlockEntity()) world.getBlockEntity(blockPos4) else null
                Block.dropStacks(blockState8, world, blockPos4, blockEntity)
                world.setBlockState(blockPos4, Blocks.AIR.defaultState, 18)
                blockStates[j++] = blockState8
                --l
            }
            l = list.size - 1
            while (l >= 0) {
                blockPos4 = list[l]
                blockState8 = world.getBlockState(blockPos4)
                blockPos4 = blockPos4.offset(direction)
                map.remove(blockPos4)
                world.setBlockState(blockPos4, Blocks.MOVING_PISTON.defaultState.with(FACING, dir) as BlockState, 68)
                world.setBlockEntity(blockPos4, PistonExtensionBlock.createBlockEntityPiston(list2[l], dir, retract, false))
                blockStates[j++] = blockState8
                --l
            }
            if (retract) {
                val pistonType = if (sticky) PistonType.STICKY else PistonType.DEFAULT
                val blockState4 = (Blocks.PISTON_HEAD.defaultState.with(PistonHeadBlock.FACING, dir) as BlockState).with(PistonHeadBlock.TYPE, pistonType) as BlockState
                blockState8 = (Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, dir) as BlockState).with(PistonExtensionBlock.TYPE, if (sticky) PistonType.STICKY else PistonType.DEFAULT) as BlockState
                map.remove(blockPos)
                world.setBlockState(blockPos, blockState8, 68)
                world.setBlockEntity(blockPos, PistonExtensionBlock.createBlockEntityPiston(blockState4, dir, true, true))
            }
            val blockState6 = Blocks.AIR.defaultState
            var var25: Iterator<*> = map.keys.iterator()
            while (var25.hasNext()) {
                val blockPos5 = var25.next() as BlockPos
                world.setBlockState(blockPos5, blockState6, 82)
            }
            var25 = map.entries.iterator()
            var blockPos7: BlockPos
            while (var25.hasNext()) {
                val entry: Map.Entry<BlockPos, BlockState> = var25.next()
                blockPos7 = entry.key
                entry.value.method_30102(world, blockPos7, 2)
                blockState6.method_30101(world, blockPos7, 2)
                blockState6.method_30102(world, blockPos7, 2)
            }
            j = 0
            var n: Int
            n = list3.size - 1
            while (n >= 0) {
                blockState8 = blockStates[j++]
                blockPos7 = list3[n]
                blockState8!!.method_30102(world, blockPos7, 2)
                world.updateNeighborsAlways(blockPos7, blockState8.block)
                --n
            }
            n = list.size - 1
            while (n >= 0) {
                world.updateNeighborsAlways(list[n], blockStates[j++]!!.block)
                --n
            }
            if (retract) {
                world.updateNeighborsAlways(blockPos, Blocks.PISTON_HEAD)
            }
            true
        }
    }

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState {
        return state.with(FACING, rotation.rotate(state.get(FACING) as Direction)) as BlockState
    }

    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState {
        return state.rotate(mirror.getRotation(state.get(FACING) as Direction))
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(*arrayOf(FACING, EXTENDED))
    }

    override fun hasSidedTransparency(state: BlockState): Boolean {
        return state.get(EXTENDED) as Boolean
    }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType): Boolean {
        return false
    }

    companion object {
        var EXTENDED: BooleanProperty? = null
        protected var EXTENDED_EAST_SHAPE: VoxelShape? = null
        protected var EXTENDED_WEST_SHAPE: VoxelShape? = null
        protected var EXTENDED_SOUTH_SHAPE: VoxelShape? = null
        protected var EXTENDED_NORTH_SHAPE: VoxelShape? = null
        protected var EXTENDED_UP_SHAPE: VoxelShape? = null
        protected var EXTENDED_DOWN_SHAPE: VoxelShape? = null
        fun isMovable(state: BlockState, world: World, pos: BlockPos, motionDir: Direction, canBreak: Boolean, pistonDir: Direction): Boolean {
            return if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR)) {
                if (!world.worldBorder.contains(pos)) {
                    false
                } else if (pos.y < 0 || motionDir == Direction.DOWN && pos.y == 0) {
                    false
                } else if (pos.y > world.height - 1 || motionDir == Direction.UP && pos.y == world.height - 1) {
                    false
                } else {
                    if (!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON)) {
                        if (state.getHardness(world, pos) == -1.0f) {
                            return false
                        }
                        when (state.pistonBehavior) {
                            PistonBehavior.BLOCK -> return false
                            PistonBehavior.DESTROY -> return canBreak
                            PistonBehavior.PUSH_ONLY -> return motionDir == pistonDir
                        }
                    } else if (state.get(EXTENDED) as Boolean) {
                        return false
                    }
                    !state.block.hasBlockEntity()
                }
            } else {
                false
            }
        }

        init {
            EXTENDED = Properties.EXTENDED
            EXTENDED_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0)
            EXTENDED_WEST_SHAPE = Block.createCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0)
            EXTENDED_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0)
            EXTENDED_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0)
            EXTENDED_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0)
            EXTENDED_DOWN_SHAPE = Block.createCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0)
        }
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.NORTH) as BlockState).with(EXTENDED, false) as BlockState
        this.sticky = sticky
        this.limit = limit
    }
}