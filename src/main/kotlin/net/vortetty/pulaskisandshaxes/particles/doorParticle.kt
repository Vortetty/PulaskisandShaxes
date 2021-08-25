package net.minecraft.client.particle

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.world.ClientWorld
import net.minecraft.particle.DefaultParticleType

@Environment(EnvType.CLIENT)
class doorParticle : SpriteBillboardParticle {
    internal constructor(world: ClientWorld?, spriteProvider: SpriteProvider?, x: Double, y: Double, z: Double) : super(world, x, y - 0.125, z) {
        setBoundingBoxSpacing(0.01f, 0.01f)
        this.setSprite(spriteProvider)
        scale *= random.nextFloat() * 0.6f + 0.2f
        maxAge = (16.0 / (Math.random() * 0.8 + 0.2)).toInt()
        collidesWithWorld = false
        field_28786 = 1.0f
        gravityStrength = 0.0f
    }

    internal constructor(world: ClientWorld?, spriteProvider: SpriteProvider?, x: Double, y: Double, z: Double, velocityX: Double, velocityY: Double, velocityZ: Double) : super(world, x, y - 0.125, z, velocityX, velocityY, velocityZ) {
        setBoundingBoxSpacing(0.01f, 0.01f)
        this.setSprite(spriteProvider)
        this.velocityX *= 0.10000000149011612
        this.velocityY *= 0.10000000149011612
        this.velocityZ *= 0.10000000149011612
        scale *= random.nextFloat() * 0.6f + 0.6f
        maxAge = (10.0 / (Math.random() * 0.8 + 0.2)).toInt()
        collidesWithWorld = true
        field_28786 = 1.0f
        gravityStrength = 0.0f
    }

    fun lerp(a: Float, b: Float, progress: Float): Float {
        return a + progress * (b - a)
    }

    override fun getSize(tickDelta: Float): Float {
        return lerp(this.scale, 0f, (age.toFloat())/(maxAge.toFloat()))
    }

    override fun getType(): ParticleTextureSheet {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE
    }

    @Environment(EnvType.CLIENT)
    class doorParticleFactory(private val spriteProvider: SpriteProvider) : ParticleFactory<DefaultParticleType?> {
        override fun createParticle(defaultParticleType: DefaultParticleType?, clientWorld: ClientWorld, x: Double, y: Double, z: Double, velocityX: Double, velocityY: Double, velocityZ: Double): Particle? {
            val j = clientWorld.random.nextFloat().toDouble() * -1.9 * clientWorld.random.nextFloat().toDouble() * 0.1 * (if (clientWorld.random.nextBoolean()) 1 else -1)
            val particle = doorParticle(clientWorld, spriteProvider, x+clientWorld.random.nextFloat(), y+clientWorld.random.nextFloat(), z+clientWorld.random.nextFloat(), velocityX, velocityY, velocityZ)
            particle.setColor(1f, 1f, 1f)
            particle.setBoundingBoxSpacing(0.001f, 0.001f)
            particle.collidesWithWorld = true
            return particle
        }
    }
}