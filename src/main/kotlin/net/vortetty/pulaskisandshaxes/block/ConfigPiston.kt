package net.vortetty.pulaskisandshaxes.block

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import net.minecraft.block.*
import net.minecraft.block.entity.PistonBlockEntity
import net.minecraft.block.enums.PistonType
import net.minecraft.block.piston.ConfigHandler
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.pathing.NavigationType
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
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class ConfigPiston(sticky: Boolean, settings: Settings?, limit: Int) : FacingBlock(settings) {
    private var limit: Int
    private val sticky: Boolean

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
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

    override fun neighborUpdate(
        state: BlockState,
        world: World,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        notify: Boolean
    ) {
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
        return (defaultState.with(FACING, ctx.playerLookDirection.opposite) as BlockState).with(
            EXTENDED,
            false
        ) as BlockState
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
                if (direction2 != Direction.DOWN && world.isEmittingRedstonePower(
                        blockPos.offset(direction2),
                        direction2
                    )
                ) {
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
                world.setBlockState(pos, state.with(EXTENDED, true) as BlockState, NOTIFY_LISTENERS)
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
            world.setBlockState(pos, state.with(EXTENDED, true) as BlockState, NOTIFY_ALL or MOVED)
            world.playSound(
                null as PlayerEntity?,
                pos,
                SoundEvents.BLOCK_PISTON_EXTEND,
                SoundCategory.BLOCKS,
                0.5f,
                world.random.nextFloat() * 0.25f + 0.6f
            )
            world.emitGameEvent(GameEvent.PISTON_EXTEND, pos)
        } else if (type == 1 || type == 2) {
            val blockEntity = world.getBlockEntity(pos.offset(direction))
            if (blockEntity is PistonBlockEntity) {
                blockEntity.finish()
            }
            val blockState =
                (Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, direction) as BlockState).with(
                    PistonExtensionBlock.TYPE,
                    if (sticky) PistonType.STICKY else PistonType.DEFAULT
                ) as BlockState
            world.setBlockState(pos, blockState, NO_REDRAW or FORCE_STATE)
            world.addBlockEntity(
                PistonExtensionBlock.createBlockEntityPiston(
                    pos,
                    blockState,
                    defaultState.with(FACING, Direction.byId(data and 7)) as BlockState,
                    direction,
                    false,
                    true
                )
            )
            world.updateNeighbors(pos, blockState.block)
            blockState.updateNeighbors(world, pos, NOTIFY_LISTENERS)
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
                    if (type != 1 || blockState2.isAir || !isMovable(
                            blockState2,
                            world,
                            blockPos,
                            direction.opposite,
                            false,
                            direction
                        ) || blockState2.pistonBehavior != PistonBehavior.NORMAL && !blockState2.isOf(Blocks.PISTON) && !blockState2.isOf(
                            Blocks.STICKY_PISTON
                        )
                    ) {
                        world.removeBlock(pos.offset(direction), false)
                    } else {
                        move(world, pos, direction, false)
                    }
                }
            } else {
                world.removeBlock(pos.offset(direction), false)
            }
            world.playSound(
                null as PlayerEntity?,
                pos,
                SoundEvents.BLOCK_PISTON_CONTRACT,
                SoundCategory.BLOCKS,
                0.5f,
                world.random.nextFloat() * 0.15f + 0.6f
            )
            world.emitGameEvent(GameEvent.PISTON_CONTRACT, pos)
        }
        return true
    }

    private fun move(world: World, pos: BlockPos, dir: Direction, retract: Boolean): Boolean {
        val blockPos = pos.offset(dir)
        if (!retract && world.getBlockState(blockPos).isOf(Blocks.PISTON_HEAD)) {
            world.setBlockState(blockPos, Blocks.AIR.defaultState, NO_REDRAW or FORCE_STATE)
        }
        val ConfigHandler = ConfigHandler(world, pos, dir, retract, limit)
        return if (!ConfigHandler.calculatePush()) {
            false
        } else {
            val map: MutableMap<BlockPos, BlockState> = Maps.newHashMap()
            val list = ConfigHandler.getMovedBlocks()
            val list2: MutableList<BlockState> = Lists.newArrayList()
            for (i in list.indices) {
                val blockPos2 = list[i] as BlockPos
                val blockState = world.getBlockState(blockPos2)
                list2.add(blockState)
                map[blockPos2] = blockState
            }
            val list3 = ConfigHandler.getBrokenBlocks()
            val blockStates = arrayOfNulls<BlockState>(list.size + list3.size)
            val direction = if (retract) dir else dir.opposite
            var j = 0
            var l: Int
            var blockPos4: BlockPos
            var blockState9: BlockState?
            l = list3.size - 1
            while (l >= 0) {
                blockPos4 = list3[l] as BlockPos
                blockState9 = world.getBlockState(blockPos4)
                val blockEntity = if (blockState9.hasBlockEntity()) world.getBlockEntity(blockPos4) else null
                dropStacks(blockState9, world, blockPos4, blockEntity)
                world.setBlockState(blockPos4, Blocks.AIR.defaultState, NOTIFY_LISTENERS or FORCE_STATE)
                if (!blockState9.isIn(BlockTags.FIRE)) {
                    world.addBlockBreakParticles(blockPos4, blockState9)
                }
                blockStates[j++] = blockState9
                --l
            }
            l = list.size - 1
            while (l >= 0) {
                blockPos4 = list[l] as BlockPos
                blockState9 = world.getBlockState(blockPos4)
                blockPos4 = blockPos4.offset(direction)
                map.remove(blockPos4)
                val blockState4 = Blocks.MOVING_PISTON.defaultState.with(FACING, dir) as BlockState
                world.setBlockState(blockPos4, blockState4, NO_REDRAW or MOVED)
                world.addBlockEntity(
                    PistonExtensionBlock.createBlockEntityPiston(
                        blockPos4, blockState4,
                        list2[l], dir, retract, false
                    )
                )
                blockStates[j++] = blockState9
                --l
            }
            if (retract) {
                val pistonType = if (sticky) PistonType.STICKY else PistonType.DEFAULT
                val blockState5 = (Blocks.PISTON_HEAD.defaultState.with(FACING, dir) as BlockState).with(
                    PistonHeadBlock.TYPE,
                    pistonType
                ) as BlockState
                blockState9 =
                    (Blocks.MOVING_PISTON.defaultState.with(PistonExtensionBlock.FACING, dir) as BlockState).with(
                        PistonExtensionBlock.TYPE,
                        if (sticky) PistonType.STICKY else PistonType.DEFAULT
                    ) as BlockState
                map.remove(blockPos)
                world.setBlockState(blockPos, blockState9, NO_REDRAW or MOVED)
                world.addBlockEntity(
                    PistonExtensionBlock.createBlockEntityPiston(
                        blockPos,
                        blockState9,
                        blockState5,
                        dir,
                        true,
                        true
                    )
                )
            }
            val blockState7 = Blocks.AIR.defaultState
            var var25: Iterator<*> = map.keys.iterator()
            while (var25.hasNext()) {
                val blockPos5 = var25.next() as BlockPos
                world.setBlockState(blockPos5, blockState7, NOTIFY_LISTENERS or FORCE_STATE or MOVED)
            }
            var25 = map.entries.iterator()
            var blockPos7: BlockPos
            while (var25.hasNext()) {
                val (key, value) = var25.next()
                blockPos7 = key as BlockPos
                val blockState8 = value as BlockState
                blockState8.prepare(world, blockPos7, 2)
                blockState7.updateNeighbors(world, blockPos7, NOTIFY_LISTENERS)
                blockState7.prepare(world, blockPos7, 2)
            }
            j = 0
            var n: Int
            n = list3.size - 1
            while (n >= 0) {
                blockState9 = blockStates[j++]
                blockPos7 = list3[n] as BlockPos
                blockState9!!.prepare(world, blockPos7, 2)
                world.updateNeighborsAlways(blockPos7, blockState9.block)
                --n
            }
            n = list.size - 1
            while (n >= 0) {
                world.updateNeighborsAlways(list[n] as BlockPos, blockStates[j++]!!.block)
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
        builder.add(FACING, EXTENDED)
    }

    override fun hasSidedTransparency(state: BlockState): Boolean {
        return state.get(EXTENDED) as Boolean
    }

    override fun canPathfindThrough(state: BlockState, world: BlockView, pos: BlockPos, type: NavigationType): Boolean {
        return false
    }

    companion object {
        var EXTENDED: BooleanProperty? = null
        const val field_31373 = 0
        const val field_31374 = 1
        const val field_31375 = 2
        const val field_31376 = 4.0f
        protected var EXTENDED_EAST_SHAPE: VoxelShape? = null
        protected var EXTENDED_WEST_SHAPE: VoxelShape? = null
        protected var EXTENDED_SOUTH_SHAPE: VoxelShape? = null
        protected var EXTENDED_NORTH_SHAPE: VoxelShape? = null
        protected var EXTENDED_UP_SHAPE: VoxelShape? = null
        protected var EXTENDED_DOWN_SHAPE: VoxelShape? = null
        fun isMovable(
            state: BlockState,
            world: World,
            pos: BlockPos,
            direction: Direction,
            canBreak: Boolean,
            pistonDir: Direction
        ): Boolean {
            return if (pos.y >= world.bottomY && pos.y <= world.topY - 1 && world.worldBorder.contains(pos)) {
                if (state.isAir) {
                    true
                } else if (!state.isOf(Blocks.OBSIDIAN) && !state.isOf(Blocks.CRYING_OBSIDIAN) && !state.isOf(Blocks.RESPAWN_ANCHOR)) {
                    if (direction == Direction.DOWN && pos.y == world.bottomY) {
                        false
                    } else if (direction == Direction.UP && pos.y == world.topY - 1) {
                        false
                    } else {
                        if (!state.isOf(Blocks.PISTON) && !state.isOf(Blocks.STICKY_PISTON)) {
                            if (state.getHardness(world, pos) == -1.0f) {
                                return false
                            }
                            when (state.pistonBehavior) {
                                PistonBehavior.BLOCK -> return false
                                PistonBehavior.DESTROY -> return canBreak
                                PistonBehavior.PUSH_ONLY -> return direction == pistonDir
                            }
                        } else if (state.get(EXTENDED) as Boolean) {
                            return false
                        }
                        !state.hasBlockEntity()
                    }
                } else {
                    false
                }
            } else {
                false
            }
        }

        init {
            EXTENDED = Properties.EXTENDED
            EXTENDED_EAST_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0)
            EXTENDED_WEST_SHAPE = createCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0)
            EXTENDED_SOUTH_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0)
            EXTENDED_NORTH_SHAPE = createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0)
            EXTENDED_UP_SHAPE = createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0)
            EXTENDED_DOWN_SHAPE = createCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0)
        }
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.NORTH) as BlockState).with(
            EXTENDED, false
        ) as BlockState
        this.sticky = sticky
        this.limit = limit
    }
}
