package net.vortetty.pulaskisandshaxes;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.vortetty.pulaskisandshaxes.block.ConfigPiston;
import net.vortetty.pulaskisandshaxes.items.PulaskiItem;
import net.vortetty.pulaskisandshaxes.items.ShaxeItem;

import java.util.Calendar;

public class pulaskisandshaxes implements ModInitializer {
	//  _______          _
	// |__   __|        | |
	//    | | ___   ___ | |___
	//    | |/ _ \ / _ \| / __|
	//    | | (_) | (_) | \__ \
	//    |_|\___/ \___/|_|___/
	//
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
	
	public static final PulaskiItem NETHERITE_PULASKI_TEST = new PulaskiItem(ToolMaterials.NETHERITE, 7, -3, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(2031));
	public static final ShaxeItem NETHERITE_SHAXE_TEST = new ShaxeItem(ToolMaterials.NETHERITE, 2, -2.8f, new Item.Settings().group(ItemGroup.TOOLS).maxDamage(2031));
	
	//  ____  _            _
	// |  _ \| |          | |
	// | |_) | | ___   ___| | _____
	// |  _ <| |/ _ \ / __| |/ / __|
	// | |_) | | (_) | (__|   <\__ \
	// |____/|_|\___/ \___|_|\_|___/
	//
	public static final ConfigPiston WOODENPISTON = new ConfigPiston(false, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "axes")), 0).collidable(true).hardness(1f).build(),6);
	public static final ConfigPiston GOLDPISTON = new ConfigPiston(false, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(1.5f).build(),24);
	public static final ConfigPiston DIAMONDPISTON = new ConfigPiston(false, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(2.5f).build(),48);
	public static final ConfigPiston NETHERITEPISTON = new ConfigPiston(false, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(5f).build(),96);
	public static final ConfigPiston SUPERPISTON = new ConfigPiston(false, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 2).collidable(true).hardness(10f).build(),864);
	
	public static final ConfigPiston STICKYWOODENPISTON = new ConfigPiston(true, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "axes")), 0).collidable(true).hardness(1f).build(),6);
	public static final ConfigPiston STICKYGOLDPISTON = new ConfigPiston(true, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(1.5f).build(),24);
	public static final ConfigPiston STICKYDIAMONDPISTON = new ConfigPiston(true, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(2.5f).build(),48);
	public static final ConfigPiston STICKYNETHERITEPISTON = new ConfigPiston(true, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 0).collidable(true).hardness(5f).build(),96);
	public static final ConfigPiston STICKYSUPERPISTON = new ConfigPiston(true, FabricBlockSettings.of(Material.PISTON).breakByHand(true).breakByTool(TagRegistry.item(new Identifier("fabric", "pickaxes")), 2).collidable(true).hardness(10f).build(),864);

	@Override
	public void onInitialize(){
		System.out.println("\n\n\n\npulaskisandshaxes initializing\n\n\n\n");
		
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
		
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "netherite_pulaski_test"), NETHERITE_PULASKI_TEST);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "netherite_shaxe_test"), NETHERITE_SHAXE_TEST);
		
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "wooden_piston"), WOODENPISTON);
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "sticky_wooden_piston"), STICKYWOODENPISTON);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "wooden_piston"), new BlockItem(WOODENPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "sticky_wooden_piston"), new BlockItem(STICKYWOODENPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "gold_piston"), GOLDPISTON);
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "sticky_gold_piston"), STICKYGOLDPISTON);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "gold_piston"), new BlockItem(GOLDPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "sticky_gold_piston"), new BlockItem(STICKYGOLDPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "diamond_piston"), DIAMONDPISTON);
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "sticky_diamond_piston"), STICKYDIAMONDPISTON);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "diamond_piston"), new BlockItem(DIAMONDPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "sticky_diamond_piston"), new BlockItem(STICKYDIAMONDPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "netherite_piston"), NETHERITEPISTON);
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "sticky_netherite_piston"), STICKYNETHERITEPISTON);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "netherite_piston"), new BlockItem(NETHERITEPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "sticky_netherite_piston"), new BlockItem(STICKYNETHERITEPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "super_piston"), SUPERPISTON);
		Registry.register(Registry.BLOCK, new Identifier("pulaskisandshaxes", "sticky_super_piston"), STICKYSUPERPISTON);
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "super_piston"), new BlockItem(SUPERPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("pulaskisandshaxes", "sticky_super_piston"), new BlockItem(STICKYSUPERPISTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		
		System.out.println("\n\n\n\npulaskisandshaxes initialized\n\n\n\n");
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		String date = String.valueOf(day)+"/"+String.valueOf(month);
		if(date.equals("1/4")){
			System.out.println("April Fools Mode Active, please view log in monospace font.");
			System.out.println("\n| |||||| |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n            |  ||||| |  |||   |||||||||lllllllllllllllllL|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n         |||| ||||||  | |||||||||||llllll$$@@@@$$$$$$$$$$$@@l||||||||||||||||||||||||||     | ||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n    |||||||||||||||   ||||||||||lll$$$@@$$@@@@@@$$$$$$$$$$$$@@@lL|||||||||||||||||||||| ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n       |||||||||||||  |||||||ll$$$$$$@@@@@@@$$$@@$$$$$$$$@@@@@$@@|||||||||||||||||||||||||| ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n           ||||  ||   |||||lll$$$l$$$$$$$@@@@@@@@$@@@@@@@@@@@@@$@l|||||||||||||||||           ||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n                 ||   ||||l$$$$$@@@@@@@@@@@@@@@@@@$$$$$$$$$@@@@@@@|||||||||||||||||          |||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n      ||      || ||     ||ll$$$$$@@@@@@@@@@@@@$$$$$$$$$$$$$@$@@@@@L|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||    ||| |  ||      |||||l$$$$$@@@@@@@$$$$$$$$llllllll$$$$$$$@@@@L||||||||||||||  ||| ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n    ||||||||||||||      ||||ll$$$$$$$$$$$$$$$$lllllllllll$$$$$$@$$@@||||||||||||||||  ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||     ||||ll$$$$$$$$$$$$$$$$lllllllllll$$$$$$$$@@ll||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n  |||||||||||||||||||||||||||l$$$$$$$$$$$$$$$$$lllllllllllll$$$$$$$@llll||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||l$$l$$$$$$$$$$$$$lllllllllllllll$$$$$@$l||l||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||jlll$$$$$$$$$$$$$$$$$$$$$$llllll$$$$lll$L|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||||llll$$$$$$$$$$$$$$$&$$$$$$llllll$$l$$llL|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||lll$$$$$$$$$$llll$$$$llllllllllllll$$l||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||| |||||||||||||||||||||||||||l$$$$$$$$$$$lllllll$lllllllllllllll|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||| ||||||||||||||||||||||||||||j$$$$$$$$$$llllllllllllllllllllll||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||||||||l$$$$$$$$$$lllll$$$lllllllllllll|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||||||||ll$$$$$$$$$$$$llllllllllllllllll|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||ll$$$$$$$$$$$llllllllllllllllll|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||||ll$$$$$$$$$$$@@@@$lllllllllllllllllll|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||||||l$$$$$$@@@@@$$$lllllllllllll$$$@@@@@@@L|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||||||||l$$$$$$$$$$$lllllllllllll$$$$$$$$$$$llllllll||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||||||||lll$$$$$$$$$$lllllllll$$ll$$$$$$$$$$llllllllllllll||||||||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||||||||||||||$$@Wl$$$$$$$$$$lllllllllll$$$$$$$$$$$$@@@@@@@@ggg@lll|l||||||||||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||||||||ll$$@@@l$$$$$$$$$$$$$$l$llll$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@|||||||||||||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||||||||||||||ll$$@@@@@@l$$$$$$$$$$$$$$$$$$ll$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@gg|||||||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||||lll$$@@@@@@@@@$l$$$$$$$$$$$$$$$$ll$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@L|||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||||||l$$@@@@@@@@@@@@@@llll$$$$$$$$$$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@L||||||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||||||||ll$$$@@@@@@@@@@@@@@@@@$llllM$$$$$$$$$$$$$$MTl$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@||||||||||||||||||||||||||||||||||||||||||\n|||||||||||||||||||||||l$$$@@@@@@@@@@@@@@@@@@@@$$$llllll$$$$$$$$$$$Tlll$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ll|||||||||||||||||||||||||||||||||||||||\n||||||||||||||||||||l$$$$@@@@@@@@@@@@@@@@@@@@@@$$$$lllllllll$$$$$$llllll$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Llll|||||||lll|||||||||||||||||||||||||||\n||||||||||||||||ll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$llllllllllllllllllllll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llll||||lllll|||||||||||||||||||||||||||\n||||||||||||ll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllll@@@@@@@@@@@@@$Mlll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllll||lllll|||||||||||||||||||||||||||\n||||||||||l$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@g$$$$$lll$$$$$$lllgllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllll||llllll||||||||||||||||||||||||||\n|||||||||l$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@$$$$MMMMllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllll||||||||||||||||||||||\n||||||||ll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$gggg@@@@g@@@@@@@@l$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllll|||||||||||||||||||||||||\n||||||llll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$&MMM$MTTTlll$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllll||||||||||||||||||||||||\n||||llllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Llllllllllllllll|l|||||||||||||||||||||\n|||||||ll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$TT||llllllll$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Lllllllllllll|||||||||||||||||||||||||\n|||||llll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllll||||||||||||||||||||||||\n||||lllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$lllllllgg$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllll|||||||||||||||||||||\nllllllllj$@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$%%$%%$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Llllllllllllllllllll||||||||||||||||\nllllllllj$@@@$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$@ggg@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllllll|||||||||||||||\nllllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$MMMMMM$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$Llllllllllllllllll|||||||||||||||||\n|lllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllll|||||||||||||||||||||\nllllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$MM$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$Lllllllllllllllllllllll|||||||||||\nllllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Lllllllllllllllllllllllll|||||||||\nllllllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$ll$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllllllllllll||||||||||\nllllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lLlllllllllllllllll||||||||||||||\n|lllllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$@@$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllll|||||||||||||\nlllllll%$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllllll|||||||||||\nlllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$llllllllllllllllllllll||||||||||\nlllllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$llllllllllllllllllllll||||||||||\nlllllll$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllllllllll||||||||\nllllll$$@@@@@@@@@@$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllllllll||||||\nllllll%$@@@@@@@$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllllllllllll||||\nllllll$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$lllllllllllllllllllllllllll\n|l|llll$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllllllllllllll\nllllll$$$$$$$lllll$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllllllll\nllll$@@$$$lllllll$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@llllllllllllllllllllll\nllll$$@$$$lll$llll$lllll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$@$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllllllllll\nlllllj$$$$llllllllllllll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$llllllllllllllllllll\nlllllll$$$lll$$$llllllll$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$llllllllllllllllll\nllll||l$$$$l$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@lllllllllllllll\nllll||l$$$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$lllllllll\nlll|||l$$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$@@@@@@@@$$$@@@@@@@@@@@@@@@@@@@@@@@@M$$%@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$llll\nlllll||l$$$$$$$@@@@$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@llllM$%@@@@@@@@@@@@@@@$$$$$$$$@@@@$$$$llll\nllllll|lll$$$@@@@@@@$$$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@@@llllllM$$@@@@@@@@@@@@$$$$$$$$$$$$$$$lllll\nlllllllllllllMMMMMMMMM$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@@@@llllllllllM$$@@@@@@@@@$$$$$$$$$$$$$$lllll\nllll|ll|||llllllllllll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@llllllllllllll$$@@@@@@@$$$$$$$$$$$$llllll\nllll||||||lllllllllllll$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@@@$llllllllllllll&$$@@$@@$$$$$$$$$$$lllllll\nllll|lllllllllllllllll$$$@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@$$$$$$$$$$$$$$$$$$$$$@@@@@@@@@@@@@@@@$llllllllllllllllj$$@@@@$$$$$$$llllllllll");
		}
	}
}
