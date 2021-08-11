package net.vortetty.pulaskisandshaxes.items

import com.google.common.collect.BiMap
import com.mojang.datafixers.util.Pair
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.*
import net.minecraft.entity.LivingEntity
import net.minecraft.item.*
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.util.ActionResult
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldEvents
import net.vortetty.pulaskisandshaxes.mixin.AxeItemAccessor
import net.vortetty.pulaskisandshaxes.mixin.HoeItemAccessor
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

open class PulaskiItem(material: ToolMaterial?, attackDamage: Float, attackSpeed: Float, settings: Settings?) : MiningToolItem(attackDamage, attackSpeed, material, BlockTags.AXE_MINEABLE, settings) {
    private var STRIPPED_BLOCKS: Map<Block, Block> = AxeItemAccessor.getStrippedBlocks()
    private var TILLED_BLOCKS: Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> = HoeItemAccessor.getTilledBlocks()

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val world = context.world
        val blockPos = context.blockPos
        val playerEntity = context.player
        val blockState = world.getBlockState(blockPos)
        val optional = getStrippedState(blockState)
        val optional2 = Oxidizable.getDecreasedOxidationState(blockState)
        val optional3 = Optional.ofNullable((HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get() as BiMap<*, *>)[blockState.block] as Block?).map { block: Block -> block.getStateWithProperties(blockState) }
        val itemStack = context.stack
        var optional4 = Optional.empty<BlockState>()
        val pair: Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>? = TILLED_BLOCKS[world.getBlockState(blockPos).block]
        if (optional.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f)
            optional4 = optional
        } else if (optional2.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world.syncWorldEvent(playerEntity, WorldEvents.BLOCK_SCRAPED, blockPos, 0)
            optional4 = optional2
        } else if (optional3.isPresent) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0f, 1.0f)
            world.syncWorldEvent(playerEntity, WorldEvents.WAX_REMOVED, blockPos, 0)
            optional4 = optional3
        }
        return if (optional4.isPresent) {
            if (playerEntity is ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(playerEntity as ServerPlayerEntity?, blockPos, itemStack)
            }
            world.setBlockState(blockPos, optional4.get(), Block.NOTIFY_ALL or Block.REDRAW_ON_MAIN_THREAD)
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity as LivingEntity) { p: LivingEntity -> p.sendToolBreakStatus(context.hand) }
            }
            ActionResult.success(world.isClient)
        } else {
            return if (pair == null) {
                ActionResult.PASS
            } else {
                val predicate: Predicate<ItemUsageContext> = pair.first as Predicate<ItemUsageContext>
                val consumer: Consumer<ItemUsageContext> = pair.second as Consumer<ItemUsageContext>
                if (predicate.test(context)) {
                    world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f)
                    if (!world.isClient) {
                        consumer.accept(context)
                        if (playerEntity != null) {
                            context.stack.damage(1, playerEntity as LivingEntity) { p: LivingEntity -> p.sendToolBreakStatus(context.hand) }
                        }
                    }
                    ActionResult.success(world.isClient)
                } else {
                    ActionResult.PASS
                }
            }
        }
    }

    private fun getStrippedState(state: BlockState): Optional<BlockState> {
        return Optional.ofNullable(STRIPPED_BLOCKS[state.block]).map { block: Block -> block.defaultState.with(PillarBlock.AXIS, state.get(PillarBlock.AXIS) as Direction.Axis) as BlockState }
    }

}
