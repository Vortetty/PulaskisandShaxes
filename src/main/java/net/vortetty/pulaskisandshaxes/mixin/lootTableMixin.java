package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.util.registry.Registry;
import net.vortetty.pulaskisandshaxes.pulaskisandshaxes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(LootTable.class)
public class lootTableMixin {
	private static Random rand = new Random();

	@Inject(method="processStacks", at=@At("RETURN"), cancellable=true)
	private static void processStacks(Consumer<ItemStack> lootConsumer, CallbackInfoReturnable cir) {
		if (pulaskisandshaxes.Companion.getConfig().getRandom_config().getBoolean("doAllRandomLoot", false)) {
			cir.setReturnValue((Consumer<ItemStack>)(stack) -> {
				Item item = Registry.ITEM.getRandom(rand);
				int count = rand.nextInt(3) + 8;
				ItemStack itemStack = new ItemStack(item, count);
				lootConsumer.accept(itemStack);
			});
		}
	}
}
