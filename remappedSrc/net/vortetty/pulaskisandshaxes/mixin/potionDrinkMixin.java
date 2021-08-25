package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static java.lang.Math.max;

@Mixin(PotionItem.class)
public class potionDrinkMixin {
    @Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
    private void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable cir) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (!world.isClient) {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);

            for (StatusEffectInstance statusEffectInstance : list) {
                StatusEffect tmptmp = Registry.STATUS_EFFECT.get(max(0, world.random.nextInt(max(1, Registry.STATUS_EFFECT.getEntries().size())) - 1));
                while (tmptmp == null) {
                    tmptmp = Registry.STATUS_EFFECT.get(max(0, world.random.nextInt(max(1, Registry.STATUS_EFFECT.getEntries().size())) - 1));
                }
                StatusEffectInstance tmp = pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doRandomPotions", false) ? new StatusEffectInstance(
                        tmptmp,
                        statusEffectInstance.getDuration(),
                        statusEffectInstance.getAmplifier(),
                        statusEffectInstance.isAmbient(),
                        statusEffectInstance.shouldShowParticles(),
                        statusEffectInstance.shouldShowIcon()
                ) : statusEffectInstance;
                if (tmp.getEffectType().isInstant()) {
                    tmp.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, tmp.getAmplifier(), 1.0D);
                } else {
                    user.addStatusEffect(tmp);
                }
            }
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.getAbilities().creativeMode) {
            if (stack.isEmpty()) {
                cir.setReturnValue(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (playerEntity != null) {
                playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        world.emitGameEvent(user, GameEvent.DRINKING_FINISH, user.getCameraBlockPos());
        cir.setReturnValue(stack);
        cir.cancel();
    }
}
