package net.vortetty.pulaskisandshaxes.items

import com.google.common.collect.ImmutableSet
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

class ShaxeItem(material: ToolMaterial, attackDamage: Int, attackSpeed: Float, settings: Settings?) : MiningToolItem(attackDamage.toFloat(), attackSpeed, material, EFFECTIVE_BLOCKS, settings) {
    override fun isEffectiveOn(state: BlockState): Boolean {
        val block = state.block
        val i = material.miningLevel
        return if (block !== Blocks.OBSIDIAN && block !== Blocks.CRYING_OBSIDIAN && block !== Blocks.NETHERITE_BLOCK && block !== Blocks.ANCIENT_DEBRIS) {
            if (block !== Blocks.DIAMOND_BLOCK && block !== Blocks.DIAMOND_ORE && block !== Blocks.EMERALD_ORE && block !== Blocks.EMERALD_BLOCK && block !== Blocks.GOLD_BLOCK && !block.isIn(BlockTags.GOLD_ORES) && block !== Blocks.REDSTONE_ORE) {
                if (block !== Blocks.IRON_BLOCK && block !== Blocks.IRON_ORE && block !== Blocks.LAPIS_BLOCK && block !== Blocks.LAPIS_ORE) {
                    val material = state.material
                    material == Material.STONE || material == Material.METAL || block === Blocks.SNOW || block === Blocks.SNOW_BLOCK
                } else {
                    i >= 1
                }
            } else {
                i >= 2
            }
        } else {
            i >= 3
        }
    }

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
                        context.stack.damage(1, playerEntity, { p: PlayerEntity -> p.sendToolBreakStatus(context.hand) })
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
        var EFFECTIVE_BLOCKS: Set<Block>? = null
        var PATH_BLOCKSTATES: Map<Block, BlockState>? = null

        init {
            EFFECTIVE_BLOCKS = ImmutableSet.of(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, *arrayOf(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL, Blocks.POWERED_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.NETHER_GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB, Blocks.SANDSTONE_SLAB, Blocks.PETRIFIED_OAK_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.BRICK_SLAB, Blocks.STONE_BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.QUARTZ_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.PURPUR_SLAB, Blocks.SMOOTH_QUARTZ, Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_STONE, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.POLISHED_GRANITE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_DIORITE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.GRANITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.RED_NETHER_BRICK_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.DIORITE_SLAB, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.PISTON_HEAD))
            PATH_BLOCKSTATES = ShovelItemAccessor.getEffectiveBlocks()
        }
    }
}