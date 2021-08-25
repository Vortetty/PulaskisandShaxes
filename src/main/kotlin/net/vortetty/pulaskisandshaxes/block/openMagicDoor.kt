package net.vortetty.pulaskisandshaxes.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.BlockWithEntity.checkType
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.MAGIC_DOOR_BLOCK_ENTITY


class openMagicDoor(settings: Settings) : BlockWithEntity(settings) {
    private var ticks = 0

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return openMagicDoorBlockEntity(pos, state)
    }

    override fun getRenderType(state: BlockState?): BlockRenderType? {
        // With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.INVISIBLE
    }

    override fun getOutlineShape(state: BlockState?, view: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape? {
        return net.vortetty.pulaskisandshaxes.createCuboidShape(6.0, 6.0, 6.0, 4.0, 4.0, 4.0)
    }

    override fun <T : BlockEntity?> getTicker(world: World, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return checkType(type, MAGIC_DOOR_BLOCK_ENTITY) { world1: World, pos: BlockPos, state1: BlockState, be: openMagicDoorBlockEntity -> (openMagicDoorBlockEntity::tick)(be, world1, pos, state1, be) }
    }
}