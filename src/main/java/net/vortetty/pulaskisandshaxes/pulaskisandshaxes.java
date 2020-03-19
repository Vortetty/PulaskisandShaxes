package net.vortetty.pulaskisandshaxes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vortetty.pulaskisandshaxes.items.PulaskiItem;
import net.vortetty.pulaskisandshaxes.items.ShaxeItem;

public class pulaskisandshaxes implements ModInitializer {
	
	public static final PulaskiItem WOODEN_PULASKI = new PulaskiItem(ToolMaterials.WOOD, 8, -3.2f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(64));
	public static final ShaxeItem WOODEN_SHAXE = new ShaxeItem(ToolMaterials.WOOD, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(64));
	
	public static final PulaskiItem STONE_PULASKI = new PulaskiItem(ToolMaterials.STONE, 9, -3.2f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(131));
	public static final ShaxeItem STONE_SHAXE = new ShaxeItem(ToolMaterials.STONE, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(131));
	
	public static final PulaskiItem IRON_PULASKI = new PulaskiItem(ToolMaterials.IRON, 8, -3.1f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(250));
	public static final ShaxeItem IRON_SHAXE = new ShaxeItem(ToolMaterials.IRON, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(250));
	
	public static final PulaskiItem GOLD_PULASKI = new PulaskiItem(ToolMaterials.GOLD, 8, -3, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(32));
	public static final ShaxeItem GOLD_SHAXE = new ShaxeItem(ToolMaterials.GOLD, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(32));
	
	public static final PulaskiItem DIAMOND_PULASKI = new PulaskiItem(ToolMaterials.DIAMOND, 7, -3, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(1561));
	public static final ShaxeItem DIAMOND_SHAXE = new ShaxeItem(ToolMaterials.DIAMOND, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(1561));
	
	public static final PulaskiItem NETHERITE_PULASKI = new PulaskiItem(ToolMaterials.NETHERITE, 7, -3, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(2031));
	public static final ShaxeItem NETHERITE_SHAXE = new ShaxeItem(ToolMaterials.NETHERITE, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(2031));
	
	@Override
	public void onInitialize() {
		System.out.println("\n\n\n\n\n\n\n\n\n\npulaskisandshaxes initializing\n\n\n\n\n");
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "wooden_pulaski"), WOODEN_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "wooden_shaxe"), WOODEN_SHAXE);
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "stone_pulaski"), STONE_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "stone_shaxe"), STONE_SHAXE);
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "iron_pulaski"), IRON_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "iron_shaxe"), IRON_SHAXE);
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "gold_pulaski"), GOLD_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "gold_shaxe"), GOLD_SHAXE);
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "diamond_pulaski"), DIAMOND_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "diamond_shaxe"), DIAMOND_SHAXE);
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "netherite_pulaski"), NETHERITE_PULASKI);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "netherite_shaxe"), NETHERITE_SHAXE);
		
		System.out.println("\n\n\n\n\npulaskisandshaxes initialized\n\n\n\n\n\n\n\n\n\n");
	}
}
