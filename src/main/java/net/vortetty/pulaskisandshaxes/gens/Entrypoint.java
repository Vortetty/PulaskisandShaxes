package net.vortetty.pulaskisandshaxes.gens;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.devtech.rrp.entrypoint.RRPPreGenEntrypoint;
import net.devtech.rrp.util.recipies.RecipeChoice;
import net.minecraft.util.Identifier;

public class Entrypoint implements RRPPreGenEntrypoint {
	@Override
	public void register() {
		new PulaskiGen("pulaskisandshaxes","netherite_pulaski_test","ffffff","00ffff","ffff00","ff00ff","ff0000","00ff00","0000ff","777777");
		new ShaxeGen("pulaskisandshaxes","netherite_shaxe_test","ffffff","00ffff","ffff00","ff00ff","ff0000","00ff00","0000ff","777777");
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "wooden_piston"), new Identifier("pulaskisandshaxes", "wooden_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "gold_piston"), new Identifier("pulaskisandshaxes", "gold_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "diamond_piston"), new Identifier("pulaskisandshaxes", "diamond_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "netherite_piston"), new Identifier("pulaskisandshaxes", "netherite_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "super_piston"), new Identifier("pulaskisandshaxes", "super_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "sticky_wooden_piston"), new Identifier("pulaskisandshaxes", "sticky_wooden_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "sticky_gold_piston"), new Identifier("pulaskisandshaxes", "sticky_gold_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "sticky_diamond_piston"), new Identifier("pulaskisandshaxes", "sticky_diamond_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "sticky_netherite_piston"), new Identifier("pulaskisandshaxes", "sticky_netherite_piston"));
		RuntimeResourcePack.INSTANCE.addDefaultBlockLootTable(new Identifier("pulaskisandshaxes", "sticky_super_piston"), new Identifier("pulaskisandshaxes", "sticky_super_piston"));
		
		RuntimeResourcePack.INSTANCE.addShaped9x9CraftingRecipe(new Identifier("woodpiston_main"),
				new Identifier("pulaskisandshaxes", "wooden_piston"), 1, new RecipeChoice.Tag(new Identifier("minecraft", "planks")), new RecipeChoice.Tag(new Identifier("minecraft", "planks")),new RecipeChoice.Tag(new Identifier("minecraft", "planks")),
				new RecipeChoice.Tag(new Identifier("minecraft", "planks")), new RecipeChoice.Tag(new Identifier("minecraft", "planks")), new RecipeChoice.Tag(new Identifier("minecraft", "planks")),
				new RecipeChoice.Tag(new Identifier("minecraft", "planks")), new RecipeChoice.Item(new Identifier("minecraft", "redstone")), new RecipeChoice.Tag(new Identifier("minecraft", "planks")));
		RuntimeResourcePack.INSTANCE.addShapelessRecipe(new Identifier("woodpiston_sticky"), new Identifier("pulaskisandshaxes", "sticky_wooden_piston"), 1, new RecipeChoice.Item(new Identifier("minecraft", "slime_ball")),new RecipeChoice.Item(new Identifier("pulaskisandshaxes", "wooden_piston")));
		
		new PistonGenerator(new Identifier("pulaskisandshaxes", "gold_piston"), new Identifier("pulaskisandshaxes", "sticky_gold_piston"), new Identifier("minecraft", "gold_ingot"));
		new PistonGenerator(new Identifier("pulaskisandshaxes", "diamond_piston"), new Identifier("pulaskisandshaxes", "sticky_diamond_piston"), new Identifier("minecraft", "diamond"));
		new PistonGenerator(new Identifier("pulaskisandshaxes", "netherite_piston"), new Identifier("pulaskisandshaxes", "sticky_netherite_piston"), new Identifier("minecraft", "netherite_ingot"));
		new PistonGenerator(new Identifier("pulaskisandshaxes", "super_piston"), new Identifier("pulaskisandshaxes", "sticky_super_piston"), new Identifier("minecraft", "netherite_block"));
	}
}
