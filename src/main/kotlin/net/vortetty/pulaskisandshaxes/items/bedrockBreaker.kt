package net.vortetty.pulaskisandshaxes.items

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.ToolItem
import net.minecraft.item.ToolMaterial
import net.minecraft.particle.ParticleTypes
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion
import net.vortetty.pulaskisandshaxes.config.bedrockBreakerConfig
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes
import java.util.*

class bedrockBreaker(material: ToolMaterial?, settings: Settings?, config: bedrockBreakerConfig) : ToolItem(material, settings) {
    var uses: Int
    private var maxDamage = 0
        @JvmName("getMaxDamage0") get
    private val config: bedrockBreakerConfig
    var rand = Random()

    @Environment(EnvType.CLIENT)
    override fun getName(): Text {
        return TranslatableText(translationKey)
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        return 0f
    }

    override fun canMine(state: BlockState, world: World, pos: BlockPos, miner: PlayerEntity): Boolean {
        return false
    }

    override fun hasGlint(stack: ItemStack): Boolean {
        return true
    }

    override fun isEnchantable(stack: ItemStack): Boolean {
        return false
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val pos = context.blockPos
        if (!context.world.isClient) {
            val stack = context.stack
            val playerEntity = context.player
            if (world.getBlockState(pos) === Blocks.BEDROCK.defaultState) {
                world.breakBlock(pos, false, context.player)
                world.setBlockState(pos, Blocks.BEDROCK.defaultState)
                world.breakBlock(pos, false, context.player)
                world.setBlockState(pos, Blocks.CAVE_AIR.defaultState)
                world.createExplosion(null, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, 0f, Explosion.DestructionType.NONE)
                world.addParticle(ParticleTypes.EXPLOSION, pos.x + 0.5 + rand.nextFloat() * 0.25, pos.y + 0.5 + rand.nextFloat() * 0.25, pos.z + 0.5 + rand.nextFloat() * 0.25, 0.0, 0.0, 0.0)
                world.addParticle(ParticleTypes.EXPLOSION, pos.x + 0.5 + rand.nextFloat() * 0.25, pos.y + 0.5 + rand.nextFloat() * 0.25, pos.z + 0.5 + rand.nextFloat() * 0.25, 0.0, 0.0, 0.0)
                world.addParticle(ParticleTypes.EXPLOSION, pos.x + 0.5 + rand.nextFloat() * 0.25, pos.y + 0.5 + rand.nextFloat() * 0.25, pos.z + 0.5 + rand.nextFloat() * 0.25, 0.0, 0.0, 0.0)
                if (stack.damage >= this.maxDamage - this.maxDamage / uses && playerEntity != null && !playerEntity.isCreative) {
                    playerEntity.setStackInHand(Hand.MAIN_HAND, ItemStack(pulaskisandshaxes.BROKEN_BEDROCK_BREAKER))
                }
                if (playerEntity != null && !playerEntity.isCreative) {
                    playerEntity.getStackInHand(Hand.MAIN_HAND).damage = this.maxDamage / uses + playerEntity.getStackInHand(Hand.MAIN_HAND).damage
                }
            }
        }
        return if (world.getBlockState(pos) === Blocks.BEDROCK.defaultState) {
            ActionResult.success(true)
        } else ActionResult.success(false)
    }

    init {
        this.config = config
        uses = config.bedrockBreakerUses
        this.maxDamage = config.bedrockBreakerUses * 10
    }
}