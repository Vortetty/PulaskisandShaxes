package net.vortetty.pulaskisandshaxes.items

import net.minecraft.block.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.MiningToolItem
import net.minecraft.item.ToolMaterial
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.util.ActionResult
import net.minecraft.util.math.Direction
import net.vortetty.pulaskisandshaxes.mixin.ShovelItemAccessor

class ShaxeItem(material: ToolMaterial, attackDamage: Int, attackSpeed: Float, settings: Settings?) : MiningToolItem(attackDamage.toFloat(), attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings) {
    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val blockPos = context.blockPos
        val blockState = world.getBlockState(blockPos)
        return if (context.side == Direction.DOWN) {
            ActionResult.PASS
        } else {
            val playerEntity = context.player
            val blockState2 = PATH_BLOCKSTATES!![blockState.block]
            var blockState3: BlockState? = null
            if (blockState2 != null && world.getBlockState(blockPos.up()).isAir) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f)
                blockState3 = blockState2
            } else if (blockState.block is CampfireBlock && blockState.get(CampfireBlock.LIT) as Boolean) {
                if (!world.isClient()) {
                    world.syncWorldEvent(null as PlayerEntity?, 1009, blockPos, 0)
                }
                blockState3 = blockState.with(CampfireBlock.LIT, false) as BlockState
            }
            if (blockState3 != null) {
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState3, 11)
                    if (playerEntity != null) {
                        context.stack.damage(1, playerEntity) { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) }
                    }
                }
                ActionResult.SUCCESS
            } else {
                ActionResult.PASS
            }
        }
    }

    override fun getMiningSpeedMultiplier(stack: ItemStack, state: BlockState): Float {
        val material = state.material
        return if (material != Material.METAL && material != Material.STONE) super.getMiningSpeedMultiplier(stack, state) else miningSpeed
    }

    companion object {
        var PATH_BLOCKSTATES: Map<Block, BlockState>? = null

        init {
            PATH_BLOCKSTATES = ShovelItemAccessor.getEffectiveBlocks()
        }
    }
}