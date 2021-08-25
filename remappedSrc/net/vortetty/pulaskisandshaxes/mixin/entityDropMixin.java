package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.lang.Math.max;

@Mixin(Entity.class)
public class entityDropMixin {
    @Inject(method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"))
    private void dropStack(ItemStack stack, float yOffset, CallbackInfoReturnable<@Nullable ItemEntity> cir) {
        if (cir.getReturnValue() != null && pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doRandomEntityDrops", false)) {
            Item item = Registry.ITEM.getRandom(cir.getReturnValue().world.random);
            int count = cir.getReturnValue().world.random.nextInt(3) + 8;
            cir.getReturnValue().setStack(new ItemStack(item, count));
        }
    }
}
