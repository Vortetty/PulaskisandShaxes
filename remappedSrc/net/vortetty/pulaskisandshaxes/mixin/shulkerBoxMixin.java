package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.item.BlockItem;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class shulkerBoxMixin {
    @Inject(method = "canBeNested", at = @At("RETURN"), cancellable = true)
    void canBeNested(CallbackInfoReturnable<Boolean> cir) {
        if (pulaskisandshaxes.Companion.getConfig().getGeneral_config().getBoolean("allowNestedShulkerBoxes", false)) {
            cir.setReturnValue(true);
        }
    }
}
