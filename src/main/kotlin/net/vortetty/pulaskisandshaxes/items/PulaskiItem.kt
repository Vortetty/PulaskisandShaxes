package net.vortetty.pulaskisandshaxes.items

import net.minecraft.block.*
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.MiningToolItem
import net.minecraft.item.ToolMaterial
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import net.vortetty.pulaskisandshaxes.mixin.AxeItemAccessor
import net.vortetty.pulaskisandshaxes.mixin.HoeItemAccessor
import java.util.*

class PulaskiItem(material: ToolMaterial, attackDamage: Float, attackSpeed: Float, settings: Settings?) : MiningToolItem(attackDamage, attackSpeed, material, AxeItemAccessor.getEffectiveBlocks(), settings) {
    fun getMiningSpeed(stack: ItemStack?, state: BlockState): Float {
        val material = state.material
        return miningSpeed
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val blockPos = context.blockPos
        val blockState = world.getBlockState(blockPos)
        val block = STRIPPED_BLOCKS!![blockState.block]
        if (block != null) {
            val playerEntity = context.player
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
            //stack.getOrCreateTag().putFloat("HOEING", 0f);
            if (!world.isClient) {
                world.setBlockState(blockPos, block.defaultState.with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)) as BlockState, 11)
                if (playerEntity != null) {
                    context.stack.damage(1, playerEntity, { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) })
                }
            }
            return ActionResult.SUCCESS
        } else if (context.side != Direction.DOWN && (world.getBlockState(blockPos.up()).material == Material.AIR || world.getBlockState(blockPos.up()).material == Material.BAMBOO || world.getBlockState(blockPos.up()).material == Material.BAMBOO_SAPLING || world.getBlockState(blockPos.up()).material == Material.PLANT)) {
            val blockState1 = TILLED_BLOCKS!![world.getBlockState(blockPos).block]
            if (blockState1 != null) {
                val playerEntity = context.player
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
                //stack.getOrCreateTag().putFloat("HOEING", 1f);
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState1, 11)
                    if (playerEntity != null) {
                        context.stack.damage(1, playerEntity, { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) })
                    }
                }
                return ActionResult.SUCCESS
            }
        }
        return ActionResult.PASS
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        stack.damage(2, attacker, { e: LivingEntity -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) })
        return true
    }

    override fun postMine(stack: ItemStack, world: World, state: BlockState, pos: BlockPos, miner: LivingEntity): Boolean {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            stack.damage(1, miner, { e: LivingEntity -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) })
        }
        //stack.getOrCreateTag().putFloat("HOEING", 0f);
        return true
    }

    companion object {
        var STRIPPED_BLOCKS: Map<Block, Block>? = null
        var TILLED_BLOCKS: Map<Block, BlockState>? = null

        init {
            AxeItemAccessor.getEffectiveBlocks().addAll(Arrays.asList(Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_STEM, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_WARPED_HYPHAE, Blocks.STRIPPED_WARPED_STEM))
            STRIPPED_BLOCKS = AxeItemAccessor.getStrippedBlocks()
            TILLED_BLOCKS = HoeItemAccessor.getTilledBlocks()
        }
    }

}