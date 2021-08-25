package net.vortetty.pulaskisandshaxes.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemUsageContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(Item.Settings.class)
public interface itemSettingsAccessor {
	@Accessor("group")
	ItemGroup getGroup();
}
