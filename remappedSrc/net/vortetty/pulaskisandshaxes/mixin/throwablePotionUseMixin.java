package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.max;

@Mixin(ThrowablePotionItem.class)
public class throwablePotionUseMixin {
    @Inject(method="use", at=@At("HEAD"), cancellable=true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        ItemStack itemStack1 = user.getStackInHand(hand);
        if (!world.isClient) {
            Potion pot = PotionUtil.getPotion(itemStack);
            List<StatusEffectInstance> list = pot.getEffects();
            ArrayList<StatusEffectInstance> newlist = new ArrayList();
            Iterator potIterator = list.iterator();

            int i = 0;
            while (potIterator.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance) potIterator.next();
                StatusEffect tmptmp = Registry.STATUS_EFFECT.get(max(0, world.random.nextInt(max(0, Registry.STATUS_EFFECT.getEntries().size())) - 1));
                while (tmptmp == null) {
                    tmptmp = Registry.STATUS_EFFECT.get(max(0, world.random.nextInt(max(0, Registry.STATUS_EFFECT.getEntries().size())) - 1));
                }
                newlist.add(i, pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doRandomPotions", false) ? new StatusEffectInstance(
                        tmptmp,
                        statusEffectInstance.getDuration(),
                        statusEffectInstance.getAmplifier(),
                        statusEffectInstance.isAmbient(),
                        statusEffectInstance.shouldShowParticles(),
                        statusEffectInstance.shouldShowIcon()
                ) : statusEffectInstance);
                i++;
            }

            itemStack = PotionUtil.setCustomPotionEffects(itemStack, newlist);

            PotionEntity potionEntity = new PotionEntity(world, user);
            potionEntity.setItem(itemStack);
            potionEntity.setProperties(user, user.getPitch(), user.getYaw(), -20.0F, 0.5F, 1.0F);
            world.spawnEntity(potionEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(user.getStackInHand(hand).getItem()));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        cir.setReturnValue(TypedActionResult.success(itemStack1, world.isClient()));
        cir.cancel();
    }
}
