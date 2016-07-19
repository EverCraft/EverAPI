package fr.evercraft.everapi.sponge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

public class UtilsItemTypes {
	public static List<ItemType> items = Arrays.asList(
			ItemTypes.ACACIA_BOAT,
			ItemTypes.ACACIA_DOOR,
			ItemTypes.ACACIA_FENCE,
			ItemTypes.ACACIA_FENCE_GATE,
			ItemTypes.ACACIA_STAIRS,
			ItemTypes.ACTIVATOR_RAIL,
			ItemTypes.ANVIL,
			ItemTypes.APPLE,
			ItemTypes.ARMOR_STAND,
			ItemTypes.ARROW,
			ItemTypes.BAKED_POTATO,
			ItemTypes.BANNER,
			ItemTypes.BARRIER,
			ItemTypes.BEACON,
			ItemTypes.BED,
			ItemTypes.BEDROCK,
			ItemTypes.BEEF,
			ItemTypes.BEETROOT,
			ItemTypes.BEETROOT_SEEDS,
			ItemTypes.BEETROOT_SOUP,
			ItemTypes.BIRCH_BOAT,
			ItemTypes.BIRCH_DOOR,
			ItemTypes.BIRCH_FENCE,
			ItemTypes.BIRCH_FENCE_GATE,
			ItemTypes.BIRCH_STAIRS,
			ItemTypes.BLAZE_POWDER,
			ItemTypes.BLAZE_ROD,
			ItemTypes.BOAT,
			ItemTypes.BONE,
			ItemTypes.BONE_BLOCK,
			ItemTypes.BOOK,
			ItemTypes.BOOKSHELF,
			ItemTypes.BOW,
			ItemTypes.BOWL,
			ItemTypes.BREAD,
			ItemTypes.BREWING_STAND,
			ItemTypes.BRICK,
			ItemTypes.BRICK_BLOCK,
			ItemTypes.BRICK_STAIRS,
			ItemTypes.BROWN_MUSHROOM,
			ItemTypes.BROWN_MUSHROOM_BLOCK,
			ItemTypes.BUCKET,
			ItemTypes.CACTUS,
			ItemTypes.CAKE,
			ItemTypes.CARPET,
			ItemTypes.CARROT,
			ItemTypes.CARROT_ON_A_STICK,
			ItemTypes.CAULDRON,
			ItemTypes.CHAINMAIL_BOOTS,
			ItemTypes.CHAINMAIL_CHESTPLATE,
			ItemTypes.CHAINMAIL_HELMET,
			ItemTypes.CHAINMAIL_LEGGINGS,
			ItemTypes.CHAIN_COMMAND_BLOCK,
			ItemTypes.CHEST,
			ItemTypes.CHEST_MINECART,
			ItemTypes.CHICKEN,
			ItemTypes.CHORUS_FLOWER,
			ItemTypes.CHORUS_FRUIT,
			ItemTypes.CHORUS_FRUIT_POPPED,
			ItemTypes.CHORUS_PLANT,
			ItemTypes.CLAY,
			ItemTypes.CLAY_BALL,
			ItemTypes.CLOCK,
			ItemTypes.COAL,
			ItemTypes.COAL_BLOCK,
			ItemTypes.COAL_ORE,
			ItemTypes.COBBLESTONE,
			ItemTypes.COBBLESTONE_WALL,
			ItemTypes.COMMAND_BLOCK,
			ItemTypes.COMMAND_BLOCK_MINECART,
			ItemTypes.COMPARATOR,
			ItemTypes.COMPASS,
			ItemTypes.COOKED_BEEF,
			ItemTypes.COOKED_CHICKEN,
			ItemTypes.COOKED_FISH,
			ItemTypes.COOKED_MUTTON,
			ItemTypes.COOKED_PORKCHOP,
			ItemTypes.COOKED_RABBIT,
			ItemTypes.COOKIE,
			ItemTypes.CRAFTING_TABLE,
			ItemTypes.DARK_OAK_BOAT,
			ItemTypes.DARK_OAK_DOOR,
			ItemTypes.DARK_OAK_FENCE,
			ItemTypes.DARK_OAK_FENCE_GATE,
			ItemTypes.DARK_OAK_STAIRS,
			ItemTypes.DAYLIGHT_DETECTOR,
			ItemTypes.DEADBUSH,
			ItemTypes.DETECTOR_RAIL,
			ItemTypes.DIAMOND,
			ItemTypes.DIAMOND_AXE,
			ItemTypes.DIAMOND_BLOCK,
			ItemTypes.DIAMOND_BOOTS,
			ItemTypes.DIAMOND_CHESTPLATE,
			ItemTypes.DIAMOND_HELMET,
			ItemTypes.DIAMOND_HOE,
			ItemTypes.DIAMOND_HORSE_ARMOR,
			ItemTypes.DIAMOND_LEGGINGS,
			ItemTypes.DIAMOND_ORE,
			ItemTypes.DIAMOND_PICKAXE,
			ItemTypes.DIAMOND_SHOVEL,
			ItemTypes.DIAMOND_SWORD,
			ItemTypes.DIRT,
			ItemTypes.DISPENSER,
			ItemTypes.DOUBLE_PLANT,
			ItemTypes.DRAGON_BREATH,
			ItemTypes.DRAGON_EGG,
			ItemTypes.DROPPER,
			ItemTypes.DYE,
			ItemTypes.EGG,
			ItemTypes.ELYTRA,
			ItemTypes.EMERALD,
			ItemTypes.EMERALD_BLOCK,
			ItemTypes.EMERALD_ORE,
			ItemTypes.ENCHANTED_BOOK,
			ItemTypes.ENCHANTING_TABLE,
			ItemTypes.ENDER_CHEST,
			ItemTypes.ENDER_EYE,
			ItemTypes.ENDER_PEARL,
			ItemTypes.END_BRICKS,
			ItemTypes.END_CRYSTAL,
			ItemTypes.END_PORTAL_FRAME,
			ItemTypes.END_ROD,
			ItemTypes.END_STONE,
			ItemTypes.EXPERIENCE_BOTTLE,
			ItemTypes.FARMLAND,
			ItemTypes.FEATHER,
			ItemTypes.FENCE,
			ItemTypes.FENCE_GATE,
			ItemTypes.FERMENTED_SPIDER_EYE,
			ItemTypes.FILLED_MAP,
			ItemTypes.FIREWORKS,
			ItemTypes.FIREWORK_CHARGE,
			ItemTypes.FIRE_CHARGE,
			ItemTypes.FISH,
			ItemTypes.FISHING_ROD,
			ItemTypes.FLINT,
			ItemTypes.FLINT_AND_STEEL,
			ItemTypes.FLOWER_POT,
			ItemTypes.FURNACE,
			ItemTypes.FURNACE_MINECART,
			ItemTypes.GHAST_TEAR,
			ItemTypes.GLASS,
			ItemTypes.GLASS_BOTTLE,
			ItemTypes.GLASS_PANE,
			ItemTypes.GLOWSTONE,
			ItemTypes.GLOWSTONE_DUST,
			ItemTypes.GOLDEN_APPLE,
			ItemTypes.GOLDEN_AXE,
			ItemTypes.GOLDEN_BOOTS,
			ItemTypes.GOLDEN_CARROT,
			ItemTypes.GOLDEN_CHESTPLATE,
			ItemTypes.GOLDEN_HELMET,
			ItemTypes.GOLDEN_HOE,
			ItemTypes.GOLDEN_HORSE_ARMOR,
			ItemTypes.GOLDEN_LEGGINGS,
			ItemTypes.GOLDEN_PICKAXE,
			ItemTypes.GOLDEN_RAIL,
			ItemTypes.GOLDEN_SHOVEL,
			ItemTypes.GOLDEN_SWORD,
			ItemTypes.GOLD_BLOCK,
			ItemTypes.GOLD_INGOT,
			ItemTypes.GOLD_NUGGET,
			ItemTypes.GOLD_ORE,
			ItemTypes.GRASS,
			ItemTypes.GRASS_PATH,
			ItemTypes.GRAVEL,
			ItemTypes.GUNPOWDER,
			ItemTypes.HARDENED_CLAY,
			ItemTypes.HAY_BLOCK,
			ItemTypes.HEAVY_WEIGHTED_PRESSURE_PLATE,
			ItemTypes.HOPPER,
			ItemTypes.HOPPER_MINECART,
			ItemTypes.ICE,
			ItemTypes.IRON_AXE,
			ItemTypes.IRON_BARS,
			ItemTypes.IRON_BLOCK,
			ItemTypes.IRON_BOOTS,
			ItemTypes.IRON_CHESTPLATE,
			ItemTypes.IRON_DOOR,
			ItemTypes.IRON_HELMET,
			ItemTypes.IRON_HOE,
			ItemTypes.IRON_HORSE_ARMOR,
			ItemTypes.IRON_INGOT,
			ItemTypes.IRON_LEGGINGS,
			ItemTypes.IRON_ORE,
			ItemTypes.IRON_PICKAXE,
			ItemTypes.IRON_SHOVEL,
			ItemTypes.IRON_SWORD,
			ItemTypes.IRON_TRAPDOOR,
			ItemTypes.ITEM_FRAME,
			ItemTypes.JUKEBOX,
			ItemTypes.JUNGLE_BOAT,
			ItemTypes.JUNGLE_DOOR,
			ItemTypes.JUNGLE_FENCE,
			ItemTypes.JUNGLE_FENCE_GATE,
			ItemTypes.JUNGLE_STAIRS,
			ItemTypes.LADDER,
			ItemTypes.LAPIS_BLOCK,
			ItemTypes.LAPIS_ORE,
			ItemTypes.LAVA_BUCKET,
			ItemTypes.LEAD,
			ItemTypes.LEATHER,
			ItemTypes.LEATHER_BOOTS,
			ItemTypes.LEATHER_CHESTPLATE,
			ItemTypes.LEATHER_HELMET,
			ItemTypes.LEATHER_LEGGINGS,
			ItemTypes.LEAVES,
			ItemTypes.LEAVES2,
			ItemTypes.LEVER,
			ItemTypes.LIGHT_WEIGHTED_PRESSURE_PLATE,
			ItemTypes.LINGERING_POTION,
			ItemTypes.LIT_PUMPKIN,
			ItemTypes.LOG,
			ItemTypes.LOG2,
			ItemTypes.MAGMA,
			ItemTypes.MAGMA_CREAM,
			ItemTypes.MAP,
			ItemTypes.MELON,
			ItemTypes.MELON_BLOCK,
			ItemTypes.MELON_SEEDS,
			ItemTypes.MILK_BUCKET,
			ItemTypes.MINECART,
			ItemTypes.MOB_SPAWNER,
			ItemTypes.MONSTER_EGG,
			ItemTypes.MOSSY_COBBLESTONE,
			ItemTypes.MUSHROOM_STEW,
			ItemTypes.MUTTON,
			ItemTypes.MYCELIUM,
			ItemTypes.NAME_TAG,
			ItemTypes.NETHERBRICK,
			ItemTypes.NETHERRACK,
			ItemTypes.NETHER_BRICK,
			ItemTypes.NETHER_BRICK_FENCE,
			ItemTypes.NETHER_BRICK_STAIRS,
			ItemTypes.NETHER_STAR,
			ItemTypes.NETHER_WART,
			ItemTypes.NETHER_WART_BLOCK,
			ItemTypes.NONE,
			ItemTypes.NOTEBLOCK,
			ItemTypes.OAK_STAIRS,
			ItemTypes.OBSIDIAN,
			ItemTypes.PACKED_ICE,
			ItemTypes.PAINTING,
			ItemTypes.PAPER,
			ItemTypes.PISTON,
			ItemTypes.PLANKS,
			ItemTypes.POISONOUS_POTATO,
			ItemTypes.PORKCHOP,
			ItemTypes.POTATO,
			ItemTypes.POTION,
			ItemTypes.PRISMARINE,
			ItemTypes.PRISMARINE_CRYSTALS,
			ItemTypes.PRISMARINE_SHARD,
			ItemTypes.PUMPKIN,
			ItemTypes.PUMPKIN_PIE,
			ItemTypes.PUMPKIN_SEEDS,
			ItemTypes.PURPUR_BLOCK,
			ItemTypes.PURPUR_PILLAR,
			ItemTypes.PURPUR_SLAB,
			ItemTypes.PURPUR_STAIRS,
			ItemTypes.QUARTZ,
			ItemTypes.QUARTZ_BLOCK,
			ItemTypes.QUARTZ_ORE,
			ItemTypes.QUARTZ_STAIRS,
			ItemTypes.RABBIT,
			ItemTypes.RABBIT_FOOT,
			ItemTypes.RABBIT_HIDE,
			ItemTypes.RABBIT_STEW,
			ItemTypes.RAIL,
			ItemTypes.RECORD_11,
			ItemTypes.RECORD_13,
			ItemTypes.RECORD_BLOCKS,
			ItemTypes.RECORD_CAT,
			ItemTypes.RECORD_CHIRP,
			ItemTypes.RECORD_FAR,
			ItemTypes.RECORD_MALL,
			ItemTypes.RECORD_MELLOHI,
			ItemTypes.RECORD_STAL,
			ItemTypes.RECORD_STRAD,
			ItemTypes.RECORD_WAIT,
			ItemTypes.RECORD_WARD,
			ItemTypes.REDSTONE,
			ItemTypes.REDSTONE_BLOCK,
			ItemTypes.REDSTONE_LAMP,
			ItemTypes.REDSTONE_ORE,
			ItemTypes.REDSTONE_TORCH,
			ItemTypes.RED_FLOWER,
			ItemTypes.RED_MUSHROOM,
			ItemTypes.RED_MUSHROOM_BLOCK,
			ItemTypes.RED_NETHER_BRICK,
			ItemTypes.RED_SANDSTONE,
			ItemTypes.RED_SANDSTONE_STAIRS,
			ItemTypes.REEDS,
			ItemTypes.REPEATER,
			ItemTypes.REPEATING_COMMAND_BLOCK,
			ItemTypes.ROTTEN_FLESH,
			ItemTypes.SADDLE,
			ItemTypes.SAND,
			ItemTypes.SANDSTONE,
			ItemTypes.SANDSTONE_STAIRS,
			ItemTypes.SAPLING,
			ItemTypes.SEA_LANTERN,
			ItemTypes.SHEARS,
			ItemTypes.SHIELD,
			ItemTypes.SIGN,
			ItemTypes.SKULL,
			ItemTypes.SLIME,
			ItemTypes.SLIME_BALL,
			ItemTypes.SNOW,
			ItemTypes.SNOWBALL,
			ItemTypes.SNOW_LAYER,
			ItemTypes.SOUL_SAND,
			ItemTypes.SPAWN_EGG,
			ItemTypes.SPECKLED_MELON,
			ItemTypes.SPECTRAL_ARROW,
			ItemTypes.SPIDER_EYE,
			ItemTypes.SPLASH_POTION,
			ItemTypes.SPONGE,
			ItemTypes.SPRUCE_BOAT,
			ItemTypes.SPRUCE_DOOR,
			ItemTypes.SPRUCE_FENCE,
			ItemTypes.SPRUCE_FENCE_GATE,
			ItemTypes.SPRUCE_STAIRS,
			ItemTypes.STAINED_GLASS,
			ItemTypes.STAINED_GLASS_PANE,
			ItemTypes.STAINED_HARDENED_CLAY,
			ItemTypes.STICK,
			ItemTypes.STICKY_PISTON,
			ItemTypes.STONE,
			ItemTypes.STONEBRICK,
			ItemTypes.STONE_AXE,
			ItemTypes.STONE_BRICK_STAIRS,
			ItemTypes.STONE_BUTTON,
			ItemTypes.STONE_HOE,
			ItemTypes.STONE_PICKAXE,
			ItemTypes.STONE_PRESSURE_PLATE,
			ItemTypes.STONE_SHOVEL,
			ItemTypes.STONE_SLAB,
			ItemTypes.STONE_SLAB2,
			ItemTypes.STONE_STAIRS,
			ItemTypes.STONE_SWORD,
			ItemTypes.STRING,
			ItemTypes.STRUCTURE_BLOCK,
			ItemTypes.STRUCTURE_VOID,
			ItemTypes.SUGAR,
			ItemTypes.TALLGRASS,
			ItemTypes.TIPPED_ARROW,
			ItemTypes.TNT,
			ItemTypes.TNT_MINECART,
			ItemTypes.TORCH,
			ItemTypes.TRAPDOOR,
			ItemTypes.TRAPPED_CHEST,
			ItemTypes.TRIPWIRE_HOOK,
			ItemTypes.VINE,
			ItemTypes.WATERLILY,
			ItemTypes.WATER_BUCKET,
			ItemTypes.WEB,
			ItemTypes.WHEAT,
			ItemTypes.WHEAT_SEEDS,
			ItemTypes.WOODEN_AXE,
			ItemTypes.WOODEN_BUTTON,
			ItemTypes.WOODEN_DOOR,
			ItemTypes.WOODEN_HOE,
			ItemTypes.WOODEN_PICKAXE,
			ItemTypes.WOODEN_PRESSURE_PLATE,
			ItemTypes.WOODEN_SHOVEL,
			ItemTypes.WOODEN_SLAB,
			ItemTypes.WOODEN_SWORD,
			ItemTypes.WOOL,
			ItemTypes.WRITABLE_BOOK,
			ItemTypes.WRITTEN_BOOK,
			ItemTypes.YELLOW_FLOWER);
	
	public static List<ItemType> getItems(){
		return items;
	}
	
	public static Optional<ItemType> getItemType(String name){
		ItemType item = null;
		boolean test =  false;
		int cpt = 0;
		if(!name.contains("minecraft:")){
			name = "minecraft:" + name;
		}
		while(cpt < items.size() && test == false){
			if(name.equalsIgnoreCase(items.get(cpt).getName())){
				item = items.get(cpt);
				test = true;
			}
			cpt++;
		}
		return Optional.ofNullable(item);
	}			
}
