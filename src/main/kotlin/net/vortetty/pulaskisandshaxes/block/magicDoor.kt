package net.vortetty.pulaskisandshaxes.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.LeavesBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.DustParticleEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.world.World
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.MAGIC_DOOR_PARTICLE
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.OPEN_MAGIC_DOOR
import java.util.*


class magicDoor(settings: Settings) : Block(settings) {
    fun randDouble(rand: Random, min: Double, max: Double): Double {
        return min + rand.nextDouble() * (max - min)
    }

    fun <T: ParticleEffect> spawnParticles(world: World, particle: T, pos: Vec3d, offset: Vec3d, speed: Double, count: Int) {
        if (!world.isClient()) {
            (world as ServerWorld).spawnParticles(particle, pos.x, pos.y, pos.z, count, offset.x, offset.y, offset.z, speed)
        }
    }

    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        world.playSound(pos.x+0.5, pos.y+0.5, pos.z+0.5, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1F, 1F, true)
        spawnParticles(
            world,
            MAGIC_DOOR_PARTICLE,
            Vec3d(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()),
            Vec3d(0.0, 0.0, 0.0),
            .0001,
            50
        )
        world.setBlockState(pos, OPEN_MAGIC_DOOR.defaultState)
        return ActionResult.SUCCESS
    }
}