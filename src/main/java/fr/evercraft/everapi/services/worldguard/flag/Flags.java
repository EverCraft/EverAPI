package fr.evercraft.everapi.services.worldguard.flag;

public class Flags {
	
	public static final FlagBoolean BUILD = new FlagBoolean("build", false);
	public static final FlagBoolean BLOCK_BREAK = new FlagBoolean("block-break", false);
	public static final FlagBoolean BLOCK_PLACE = new FlagBoolean("block-place", false);
	
	public static final FlagBoolean USE = new FlagBoolean("use", false);
	public static final FlagBoolean INTERACT = new FlagBoolean("interact", false);
	
	public static final FlagBoolean DAMAGE_ANIMALS = new FlagBoolean("damage-animals", false);
	public static final FlagBoolean PVP = new FlagBoolean("pvp", false);
	public static final FlagBoolean SLEEP = new FlagBoolean("sleep", false);
	public static final FlagBoolean TNT = new FlagBoolean("tnt", false);
	public static final FlagBoolean CHEST_ACCESS = new FlagBoolean("chest-acces", false);
	
	public static final FlagBoolean PLACE_VEHICLE = new FlagBoolean("place_vehicle", false);
	public static final FlagBoolean DESTROY_VEHICLE = new FlagBoolean("destroy-vehicle", false);
	
	public static final FlagBoolean LIGHTER = new FlagBoolean("lighter", false);
	public static final FlagBoolean RIDE = new FlagBoolean("ride", false);
	public static final FlagBoolean POTION_SPLASH = new FlagBoolean("potion-splash", false);
	
	public static final FlagBoolean ITEM_PICKUP = new FlagBoolean("item-pickup", true);
	public static final FlagBoolean ITEM_DROP = new FlagBoolean("item-drop", true);
	public static final FlagBoolean EXP_DROPS = new FlagBoolean("exp-drops", true);
	
	public static final FlagBoolean MOB_DAMAGE = new FlagBoolean("mob-damage", true);
    public static final FlagBoolean MOB_SPAWNING = new FlagBoolean("mob-spawning", true);
    public static final FlagBoolean CREEPER_EXPLOSION = new FlagBoolean("creeper-explosion", true);
    public static final FlagBoolean ENDERDRAGON_BLOCK_DAMAGE = new FlagBoolean("enderdragon-block-damage", true);
    public static final FlagBoolean GHAST_FIREBALL = new FlagBoolean("ghast-fireball", true);
    public static final FlagBoolean OTHER_EXPLOSION = new FlagBoolean("other-explosion", true);
    public static final FlagBoolean FIRE_SPREAD = new FlagBoolean("fire-spread", true);
    public static final FlagBoolean LAVA_FIRE = new FlagBoolean("lava-fire", true);
    public static final FlagBoolean LIGHTNING = new FlagBoolean("lightning", true);
    public static final FlagBoolean WATER_FLOW = new FlagBoolean("water-flow", true);
    public static final FlagBoolean LAVA_FLOW = new FlagBoolean("lava-flow", true);
    public static final FlagBoolean PISTONS = new FlagBoolean("pistons", true);
    public static final FlagBoolean SNOW_FALL = new FlagBoolean("snow-fall", true);
    public static final FlagBoolean SNOW_MELT = new FlagBoolean("snow-melt", true);
    public static final FlagBoolean ICE_FORM = new FlagBoolean("ice-form", true);
    public static final FlagBoolean ICE_MELT = new FlagBoolean("ice-melt", true);
    public static final FlagBoolean MUSHROOMS = new FlagBoolean("mushroom-growth", true);
    public static final FlagBoolean LEAF_DECAY = new FlagBoolean("leaf-decay", true);
    public static final FlagBoolean GRASS_SPREAD = new FlagBoolean("grass-growth", true);
    public static final FlagBoolean MYCELIUM_SPREAD = new FlagBoolean("mycelium-spread", true);
    public static final FlagBoolean VINE_GROWTH = new FlagBoolean("vine-growth", true);
    public static final FlagBoolean SOIL_DRY = new FlagBoolean("soil-dry", true);
    public static final FlagBoolean ENDER_BUILD = new FlagBoolean("enderman-grief", true);
    public static final FlagBoolean INVINCIBILITY = new FlagBoolean("invincible", false);
    public static final FlagBoolean SEND_CHAT = new FlagBoolean("send-chat", true);
    public static final FlagBoolean RECEIVE_CHAT = new FlagBoolean("receive-chat", true);
    public static final FlagBoolean ENTRY = new FlagBoolean("entry", true);
    public static final FlagBoolean EXIT = new FlagBoolean("exit", true);
    public static final FlagBoolean ENDERPEARL = new FlagBoolean("enderpearl", true);
    public static final FlagBoolean CHORUS_TELEPORT = new FlagBoolean("chorus-fruit-teleport", true);
    public static final FlagBoolean ENTITY_PAINTING_DESTROY = new FlagBoolean("entity-painting-destroy", true);
    public static final FlagBoolean ENTITY_ITEM_FRAME_DESTROY = new FlagBoolean("entity-item-frame-destroy", true);
    public static final FlagBoolean FALL_DAMAGE = new FlagBoolean("fall-damage", true);

    public static final FlagLocation SPAWN_LOC = new FlagLocation("spawn");
    
    public static final FlagSetString BLOCKED_CMDS = new FlagSetString("blocked-cmds");
    public static final FlagSetString ALLOWED_CMDS = new FlagSetString("allowed-cmds");
 
}
