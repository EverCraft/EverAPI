package fr.evercraft.everapi.sponge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;

public class UtilsLocation {

	private final Set<String> MATERIALS_EMPTY = new HashSet<String>();
	private final Set<String> MATERIALS_DAMAGE = new HashSet<String>();
	private final Set<String> MATERIALS_SAFE = new HashSet<String>();
	private final EverAPI plugin;
	
	private Integer X_min;
	private Integer X_max;
	
	private Integer Y_min;
	private Integer Y_max;
	
	private Integer Z_min;
	private Integer Z_max;
	
	public UtilsLocation(final EverAPI plugin) {
		this.plugin = plugin;
		
		this.load();
		this.reload();
	}
	
	
	public void load() {
		// Autre
		MATERIALS_EMPTY.add(BlockTypes.AIR.getId());
		MATERIALS_EMPTY.add(BlockTypes.CARPET.getId());
		MATERIALS_EMPTY.add(BlockTypes.LADDER.getId());
		MATERIALS_EMPTY.add(BlockTypes.SNOW_LAYER.getId());
		MATERIALS_EMPTY.add(BlockTypes.STANDING_BANNER.getId());
		MATERIALS_EMPTY.add(BlockTypes.STANDING_SIGN.getId());
		MATERIALS_EMPTY.add(BlockTypes.TORCH.getId());
		MATERIALS_EMPTY.add(BlockTypes.WALL_BANNER.getId());
		MATERIALS_EMPTY.add(BlockTypes.WALL_SIGN.getId());
		
		// Nature
		MATERIALS_EMPTY.add(BlockTypes.BROWN_MUSHROOM.getId());
		MATERIALS_EMPTY.add(BlockTypes.CARROTS.getId());
		MATERIALS_EMPTY.add(BlockTypes.DEADBUSH.getId());
		MATERIALS_EMPTY.add(BlockTypes.DOUBLE_PLANT.getId());
		MATERIALS_EMPTY.add(BlockTypes.FARMLAND.getId());
		MATERIALS_EMPTY.add(BlockTypes.MELON_STEM.getId());
		MATERIALS_EMPTY.add(BlockTypes.NETHER_WART.getId());
		MATERIALS_EMPTY.add(BlockTypes.POTATOES.getId());
		MATERIALS_EMPTY.add(BlockTypes.PUMPKIN_STEM.getId());
		MATERIALS_EMPTY.add(BlockTypes.RED_FLOWER.getId());
		MATERIALS_EMPTY.add(BlockTypes.RED_MUSHROOM.getId());
		MATERIALS_EMPTY.add(BlockTypes.REEDS.getId());
		MATERIALS_EMPTY.add(BlockTypes.SAPLING.getId());
		MATERIALS_EMPTY.add(BlockTypes.TALLGRASS.getId());
		MATERIALS_EMPTY.add(BlockTypes.VINE.getId());
		MATERIALS_EMPTY.add(BlockTypes.WATERLILY.getId());
		MATERIALS_EMPTY.add(BlockTypes.WEB.getId());
		MATERIALS_EMPTY.add(BlockTypes.WHEAT.getId());
		MATERIALS_EMPTY.add(BlockTypes.YELLOW_FLOWER.getId());
		
		// Porte
		MATERIALS_EMPTY.add(BlockTypes.ACACIA_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.BIRCH_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.DARK_OAK_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.IRON_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.JUNGLE_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.SPRUCE_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.WOODEN_DOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.IRON_TRAPDOOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.TRAPDOOR.getId());
		
		// Rail
		MATERIALS_EMPTY.add(BlockTypes.DETECTOR_RAIL.getId());
		MATERIALS_EMPTY.add(BlockTypes.GOLDEN_RAIL.getId());
		MATERIALS_EMPTY.add(BlockTypes.RAIL.getId());
		
		// RedStone
		MATERIALS_EMPTY.add(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE.getId());
		MATERIALS_EMPTY.add(BlockTypes.LEVER.getId());
		MATERIALS_EMPTY.add(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE.getId());
		MATERIALS_EMPTY.add(BlockTypes.LIT_REDSTONE_LAMP.getId());
		MATERIALS_EMPTY.add(BlockTypes.POWERED_COMPARATOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.POWERED_REPEATER.getId());
		MATERIALS_EMPTY.add(BlockTypes.REDSTONE_LAMP.getId());
		MATERIALS_EMPTY.add(BlockTypes.REDSTONE_TORCH.getId());
		MATERIALS_EMPTY.add(BlockTypes.STONE_BUTTON.getId());
		MATERIALS_EMPTY.add(BlockTypes.STONE_PRESSURE_PLATE.getId());
		MATERIALS_EMPTY.add(BlockTypes.REDSTONE_TORCH.getId());
		MATERIALS_EMPTY.add(BlockTypes.TRIPWIRE.getId());
		MATERIALS_EMPTY.add(BlockTypes.TRIPWIRE_HOOK.getId());
		MATERIALS_EMPTY.add(BlockTypes.UNLIT_REDSTONE_TORCH.getId());
		MATERIALS_EMPTY.add(BlockTypes.UNPOWERED_COMPARATOR.getId());
		MATERIALS_EMPTY.add(BlockTypes.UNPOWERED_REPEATER.getId());
		MATERIALS_EMPTY.add(BlockTypes.WOODEN_BUTTON.getId());
		MATERIALS_EMPTY.add(BlockTypes.WOODEN_PRESSURE_PLATE.getId());

		// MATERIALS_SAFE
		MATERIALS_SAFE.add(BlockTypes.WATER.getId());
		MATERIALS_SAFE.add(BlockTypes.FLOWING_WATER.getId());
		
		// MATERIALS_DAMAGE
		MATERIALS_DAMAGE.add(BlockTypes.CACTUS.getId());
		MATERIALS_DAMAGE.add(BlockTypes.LAVA.getId());
		MATERIALS_DAMAGE.add(BlockTypes.FLOWING_LAVA.getId());
		MATERIALS_DAMAGE.add(BlockTypes.FIRE.getId());
		MATERIALS_DAMAGE.add(BlockTypes.BED.getId());
	}
	
	public void reload() {
		this.X_min = this.plugin.getConfigs().get("location.minX").getInt(-30000);
		this.X_max = this.plugin.getConfigs().get("location.maxX").getInt(30000);
		this.Y_min = this.plugin.getConfigs().get("location.minY").getInt(0);
		this.Y_max = this.plugin.getConfigs().get("location.maxY").getInt(255);
		this.Z_min = this.plugin.getConfigs().get("location.minZ").getInt(-30000);
		this.Z_max = this.plugin.getConfigs().get("location.maxZ").getInt(30000);
	}
	
	/**
	 * Vérifie les 2 blocks du corps
	 * @param world
	 * @param vector
	 * @return
	 */
	private boolean isBlockAir(final World world, final Vector3i vector) {
		if (vector.getY() > world.getBlockMax().getY()) {
			return true;
		} else if (vector.getY() == world.getBlockMax().getY()) {
			return MATERIALS_EMPTY.contains(world.getBlock(vector).getType().getId());
		} else {
			return MATERIALS_EMPTY.contains(world.getBlock(vector).getType().getId()) && MATERIALS_EMPTY.contains(world.getBlock(vector.add(0, 1, 0)).getType().getId());
		}
	}
	
	/**
	 * Vérifie le block sous les pieds
	 * @param world
	 * @param vector
	 * @return
	 */
	private boolean blockDownSafe(final World world, final Vector3i vector) {
		if (MATERIALS_DAMAGE.contains(world.getBlock(vector.sub(0, 1, 0)).getType().getId())) {
			return false;
		}
		return !MATERIALS_EMPTY.contains(world.getBlock(vector.sub(0, 1, 0)).getType().getId());
	}
	
	private boolean blockDownNoEmpty(final World world, final Vector3i vector) {
		return MATERIALS_DAMAGE.contains(world.getBlock(vector.sub(0, 1, 0)).getType().getId()) || !MATERIALS_EMPTY.contains(world.getBlock(vector.sub(0, 1, 0)).getType().getId());
	}

	public boolean isBlock(final World world, final Vector3i vector, final boolean safe) {
		if(safe) {
			return blockDownSafe(world, vector) && isBlockAir(world, vector);
		}
		return blockDownNoEmpty(world, vector) && isBlockAir(world, vector);
	}
	
	public Optional<Transform<World>> getMaxBlock(final Transform<World> location, final boolean safe) {
		Vector3i destination = new Vector3i(location.getLocation().getBlockX(), location.getExtent().getBlockMax().getY() + 1, location.getLocation().getBlockZ());

		while (!isBlock(location.getExtent(), destination, safe) && destination.getY() > 1) {
			destination = destination.sub(0, 1, 0);
		}
		if(destination.getY() > 1){
			return Optional.of(location.setPosition(destination.toDouble().add(0.5, 0, 0.5)));
		}
		return Optional.empty();
	}
	
	public Optional<Transform<World>> getMaxBlock(final Transform<World> location) {
		return getMaxBlock(location, false);
	}
	
	public Optional<Transform<World>> getMaxBlockSafe(final Transform<World> location) {
		return getMaxBlock(location, true);
	}
	
	public Optional<Transform<World>> getBlock(final Transform<World> location, final boolean safe) {
		int max = location.getExtent().getBlockMax().getY() + 1;
		Vector3i destinationMax = new Vector3i(location.getLocation().getBlockX(), location.getLocation().getBlockY(), location.getLocation().getBlockZ());
		Vector3i destinationMin = new Vector3i(location.getLocation().getBlockX(), location.getLocation().getBlockY() - 1, location.getLocation().getBlockZ());
		Vector3i destination = null;
		while (destination == null && (destinationMin.getY() >= 0 || destinationMax.getY() <= max)) {
			if(destinationMax.getY() <= max && isBlock(location.getExtent(), destinationMax, safe)) {
				destination = destinationMax;
			} else if(destinationMin.getY() > 0 && isBlock(location.getExtent(), destinationMin, safe)) {
				destination = destinationMin;
			}
			if(destination == null) {
				if(destinationMax.getY() <= max) {
					destinationMax = destinationMax.add(0, 1, 0);
				}
				if(destinationMin.getY() >= 0) {
					destinationMin = destinationMin.sub(0, 1, 0);
				}
			}
		}
		if(destination != null){
			return Optional.of(location.setPosition(destination.toDouble().add(0.5, 0, 0.5)));
		}
		return Optional.empty();
	}
	
	public Optional<Transform<World>> getBlock(final Transform<World> location) {
		return getBlock(location, false);
	}
	
	public Optional<Transform<World>> getBlockSafe(final Transform<World> location) {
		return getBlock(location, true);
	}
	
	public  Optional<Vector3i> getLocation(final CommandSource player, final String pos_x, final String pos_y, final String pos_z){
		try {
			int x = Integer.parseInt(pos_x);
			if(x >= this.X_min && x <= this.X_max) {
				try {
					int y = Integer.parseInt(pos_y);
					if(y >= this.Y_min && y <= this.Y_max) {
						try {
							int z = Integer.parseInt(pos_z);
							if(z >= this.Z_min && z <= this.Z_max) {
								return Optional.of(new Vector3i(x, y, z));
							} else {
								player.sendMessage(EChat.of(EAMessages.LOCATION_ERROR_NUMBER.get()
										.replaceAll("<name>", "Z")
										.replaceAll("<min>", this.Z_min.toString())
										.replaceAll("<max>", this.Z_max.toString())));
							}
						} catch (NumberFormatException e) {
							player.sendMessage(EChat.of(EAMessages.IS_NOT_NUMBER.get()
									.replaceAll("<number>", pos_x)));
						}
					} else {
						player.sendMessage(EChat.of(EAMessages.LOCATION_ERROR_NUMBER.get()
								.replaceAll("<name>", "Y")
								.replaceAll("<min>", this.Y_min.toString())
								.replaceAll("<max>", this.Y_max.toString())));
					}
				} catch (NumberFormatException e) {
					player.sendMessage(EChat.of(EAMessages.IS_NOT_NUMBER.get()
							.replaceAll("<number>", pos_x)));
				}
			} else {
				player.sendMessage(EChat.of(EAMessages.LOCATION_ERROR_NUMBER.get()
						.replaceAll("<name>", "X")
						.replaceAll("<min>", this.X_min.toString())
						.replaceAll("<max>", this.X_max.toString())));
			}
		} catch (NumberFormatException e) {
			player.sendMessage(EChat.of(EAMessages.IS_NOT_NUMBER.get()
					.replaceAll("<number>", pos_x)));
		}
		return Optional.empty();
	}
}
