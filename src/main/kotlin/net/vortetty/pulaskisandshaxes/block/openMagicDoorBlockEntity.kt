package net.vortetty.pulaskisandshaxes.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.Packet
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket
import net.minecraft.particle.DustParticleEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.world.World
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.MAGIC_DOOR
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.MAGIC_DOOR_BLOCK_ENTITY
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes.Companion.MAGIC_DOOR_PARTICLE
import java.util.*

class openMagicDoorBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(MAGIC_DOOR_BLOCK_ENTITY, pos, state) {
    private var ticks = 0

    override fun writeNbt(tag: NbtCompound): NbtCompound? {
        super.writeNbt(tag)

        // Save the current value of the number to the tag
        tag.putInt("ticks", ticks)
        return tag
    }

    override fun readNbt(tag: NbtCompound) {
        super.readNbt(tag)
        ticks = tag.getInt("ticks")
    }

    fun randDouble(rand: Random, min: Double, max: Double): Double {
        return min + rand.nextDouble() * (max - min)
    }

    fun <T: ParticleEffect> spawnParticles(world: World, particle: T, pos: Vec3d, offset: Vec3d, speed: Double, count: Int) {
        if (!world.isClient()) {
            (world as ServerWorld).spawnParticles(particle, pos.x, pos.y, pos.z, count, offset.x, offset.y, offset.z, speed)
        }
    }

    fun tryOpenDoorBlock(world: World, pos: BlockPos) {
        if (world.getBlockState(pos) == MAGIC_DOOR.defaultState) {
            world.setBlockState(pos, pulaskisandshaxes.OPEN_MAGIC_DOOR.defaultState)
            world.playSound(pos.x+0.5, pos.y+0.5, pos.z+0.5, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1F, 1F, true)
            //for (i in 0..15) {
            //    world.addParticle(DustParticleEffect(Vec3f(Vec3d.unpackRgb(0x555555)), 1.0F), pos.x+0.5, pos.y+0.5, pos.z+0.5, 0.0, 0.0, 0.0)
            //}
            spawnParticles(
                world,
                MAGIC_DOOR_PARTICLE,
                Vec3d(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()),
                Vec3d(0.0, 0.0, 0.0),
                .0001,
                50
            )
        }
    }

    fun tick(world: World, pos: BlockPos, state: BlockState, be: openMagicDoorBlockEntity) {
        ticks++
        if (ticks > 60) {
            world.playSound(pos.x+0.5, pos.y+0.5, pos.z+0.5, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1F, 1F, true)
            //spawnParticles(
            //    world,
            //    MAGIC_DOOR_PARTICLE,
            //    Vec3d(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()),
            //    Vec3d(0.0, 0.0, 0.0),
            //    .0001,
            //    50
            //)
            world.setBlockState(pos, MAGIC_DOOR.defaultState)
        } else if (ticks == 4) {
            tryOpenDoorBlock(world, pos.up())
            tryOpenDoorBlock(world, pos.down())
            tryOpenDoorBlock(world, pos.north())
            tryOpenDoorBlock(world, pos.south())
            tryOpenDoorBlock(world, pos.east())
            tryOpenDoorBlock(world, pos.west())
        }
    }
}