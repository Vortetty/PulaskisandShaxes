package net.vortetty.pulaskisandshaxes.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.class)
public interface abstractBlockAccessor {
    @Accessor
    Identifier getLootTableId();

    @Accessor
    void setLootTableId(Identifier lootTableId);
}
