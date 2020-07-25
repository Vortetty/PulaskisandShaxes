package net.vortetty.pulaskisandshaxes.gens;

import net.devtech.rrp.api.RuntimeResourcePack;
import net.devtech.rrp.util.recipies.RecipeChoice;
import net.minecraft.util.Identifier;

public class PistonGenerator {
	public PistonGenerator(Identifier result, Identifier stickyresult, Identifier bar){
		RuntimeResourcePack.INSTANCE.addShaped9x9CraftingRecipe(new Identifier(result+"_main"),
				result, 1,
				new RecipeChoice.Tag(new Identifier("minecraft", "planks")), new RecipeChoice.Tag(new Identifier("minecraft", "planks")),new RecipeChoice.Tag(new Identifier("minecraft", "planks")),
				new RecipeChoice.Item(new Identifier("minecraft", "cobblestone")), new RecipeChoice.Item(bar), new RecipeChoice.Item(new Identifier("minecraft", "cobblestone")),
				new RecipeChoice.Item(new Identifier("minecraft", "cobblestone")), new RecipeChoice.Item(new Identifier("minecraft", "redstone")), new RecipeChoice.Item(new Identifier("minecraft", "cobblestone")));
		RuntimeResourcePack.INSTANCE.addShapelessRecipe(new Identifier(result+"_sticky"), stickyresult, 1, new RecipeChoice.Item(new Identifier("minecraft", "slime_ball")),new RecipeChoice.Item(result));
		return;
	}
}
