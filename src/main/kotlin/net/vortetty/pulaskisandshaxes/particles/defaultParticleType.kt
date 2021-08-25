package net.vortetty.pulaskisandshaxes.particles

import com.mojang.brigadier.StringReader
import net.minecraft.particle.ParticleType
import net.minecraft.particle.ParticleEffect
import com.mojang.serialization.Codec
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.registry.Registry

class defaultParticleType(alwaysShow: Boolean) : ParticleType<defaultParticleType>(alwaysShow, PARAMETER_FACTORY), ParticleEffect {
    private val codec = Codec.unit { this.type }
    override fun getType(): defaultParticleType {
        return this
    }

    override fun getCodec(): Codec<defaultParticleType> {
        return codec
    }

    override fun write(buf: PacketByteBuf) {}

    override fun asString(): String {
        return Registry.PARTICLE_TYPE.getId(this).toString()
    }

    companion object {
        private val PARAMETER_FACTORY: ParticleEffect.Factory<defaultParticleType> = object : ParticleEffect.Factory<defaultParticleType> {
            override fun read(particleType: ParticleType<defaultParticleType>, stringReader: StringReader): defaultParticleType {
                return particleType as defaultParticleType
            }

            override fun read(particleType: ParticleType<defaultParticleType>, packetByteBuf: PacketByteBuf): defaultParticleType {
                return particleType as defaultParticleType
            }
        }
    }
}