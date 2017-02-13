/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.sponge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.java.UtilsInteger;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.message.EMessageSender;

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
		if (safe) {
			return blockDownSafe(world, vector) && isBlockAir(world, vector);
		}
		return blockDownNoEmpty(world, vector) && isBlockAir(world, vector);
	}
	
	public boolean isPositionSafe(final Transform<World> location) {
		Vector3i position = location.getLocation().getBlockPosition().sub(0, 1, 0);
		
		if (MATERIALS_EMPTY.contains(location.getExtent().getBlock(position).getType().getId())) {
			position = position.sub(0, 1, 0);
		}
		
		return !MATERIALS_DAMAGE.contains(location.getExtent().getBlock(position).getType().getId()) && 
				!MATERIALS_EMPTY.contains(location.getExtent().getBlock(position).getType().getId()) &&
				isBlockAir(location.getExtent(), position.add(0, 1, 0));
	}
	
	/**
	 * Donne la position plus haute
	 * @param location
	 * @return
	 */
	public Optional<Transform<World>> getMaxBlock(final Transform<World> location) {
		return this.getMaxBlock(location, false);
	}
	
	/**
	 * Donne la position plus haute safe
	 * @param location
	 * @return
	 */
	public Optional<Transform<World>> getMaxBlockSafe(final Transform<World> location) {
		return this.getMaxBlock(location, true);
	}
	
	public Optional<Transform<World>> getMaxBlock(final Transform<World> location, final boolean safe) {
		Vector3i destination = new Vector3i(location.getLocation().getBlockX(), location.getExtent().getBlockMax().getY() + 1, location.getLocation().getBlockZ());

		while (!isBlock(location.getExtent(), destination, safe) && destination.getY() > 1) {
			destination = destination.sub(0, 1, 0);
		}
		if (destination.getY() > 1){
			return Optional.of(location.setPosition(destination.toDouble().add(0.5, 0, 0.5)));
		}
		return Optional.empty();
	}
	
	public Optional<Transform<World>> getBlockBottom(final Transform<World> location) {
		Vector3i destination = new Vector3i(location.getLocation().getBlockX(), location.getLocation().getBlockY() + 1, location.getLocation().getBlockZ());

		while (!isBlock(location.getExtent(), destination, false) && destination.getY() > 1) {
			destination = destination.sub(0, 1, 0);
		}
		if (destination.getY() > 1){
			return Optional.of(location.setPosition(destination.toDouble().add(0.5, 1, 0.5)));
		}
		return Optional.empty();
	}
	
	
	/**
	 * Donne une position possible
	 * @param location
	 * @return
	 */
	public Optional<Transform<World>> getBlock(final Transform<World> location) {
		return this.getBlock(location, false);
	}
	
	/**
	 * Donne une position safe
	 * @param location
	 * @return
	 */
	public Optional<Transform<World>> getBlockSafe(final Transform<World> location) {
		return this.getBlock(location, true);
	}
	
	public Optional<Transform<World>> getBlock(final Transform<World> location, final boolean safe) {
		int max = location.getExtent().getBlockMax().getY() + 1;
		Vector3i destinationMax = new Vector3i(location.getLocation().getBlockX(), location.getLocation().getBlockY(), location.getLocation().getBlockZ());
		Vector3i destinationMin = new Vector3i(location.getLocation().getBlockX(), location.getLocation().getBlockY() - 1, location.getLocation().getBlockZ());
		Vector3i destination = null;
		while (destination == null && (destinationMin.getY() >= 0 || destinationMax.getY() <= max)) {
			if (destinationMax.getY() <= max && isBlock(location.getExtent(), destinationMax, safe)) {
				destination = destinationMax;
			} else if (destinationMin.getY() > 0 && isBlock(location.getExtent(), destinationMin, safe)) {
				destination = destinationMin;
			}
			if (destination == null) {
				if (destinationMax.getY() <= max) {
					destinationMax = destinationMax.add(0, 1, 0);
				}
				if (destinationMin.getY() >= 0) {
					destinationMin = destinationMin.sub(0, 1, 0);
				}
			}
		}
		if (destination != null){
			return Optional.of(location.setPosition(destination.toDouble().add(0.5, 0, 0.5)));
		}
		return Optional.empty();
	}
	
	public LocationResult getLocation(final CommandSource player, final String pos_x, final String pos_y, final String pos_z) {
		Optional<Integer> x = UtilsInteger.parseInt(pos_x);
		if (!x.isPresent()) {
			return new LocationResult(EAMessages.IS_NOT_NUMBER.sender()
					.replace("<number>", pos_x));
		}
		
		Optional<Integer> y = UtilsInteger.parseInt(pos_y);
		if (!y.isPresent()) {
			return new LocationResult(EAMessages.IS_NOT_NUMBER.sender()
					.replace("<number>", pos_y));
		}
		
		Optional<Integer> z = UtilsInteger.parseInt(pos_z);
		if (!z.isPresent()) {
			return new LocationResult(EAMessages.IS_NOT_NUMBER.sender()
					.replace("<number>", pos_z));
		}
		
		if (x.get() < this.X_min || x.get() > this.X_max) {
			return new LocationResult(EAMessages.LOCATION_ERROR_NUMBER.sender()
					.replace("<name>", "X")
					.replace("<min>", this.X_min.toString())
					.replace("<max>", this.X_max.toString()));
		}
		
		if (y.get() < this.Y_min || y.get() > this.Y_max) {
			return new LocationResult(EAMessages.LOCATION_ERROR_NUMBER.sender()
					.replace("<name>", "Y")
					.replace("<min>", this.Y_min.toString())
					.replace("<max>", this.Y_max.toString()));
		}
		
		if (z.get() < this.Z_min || z.get() > this.Z_max) {
			return new LocationResult(EAMessages.LOCATION_ERROR_NUMBER.sender()
					.replace("<name>", "Z")
					.replace("<min>", this.Z_min.toString())
					.replace("<max>", this.Z_max.toString()));
		}
		
		return new LocationResult(new Vector3i(x.get(), y.get(), z.get()));
	}
	
	public class LocationResult {
		private Optional<Vector3i> location;
		private Optional<EMessageSender> error;
		
		public LocationResult(EMessageSender error) {
			Preconditions.checkNotNull(error);
			
			this.error = Optional.ofNullable(error);
			this.location = Optional.empty();
		}
		
		public LocationResult(Vector3i location) {
			Preconditions.checkNotNull(location);
			
			this.location = Optional.ofNullable(location);
			this.error = Optional.empty();
		}
		
		public Optional<Vector3i> getLocation() {
			return this.location;
		}
		
		public Optional<EMessageSender> getError() {
			return this.error;
		}
		
		public boolean isError() {
			return this.error.isPresent();
		}
	}
	
	public static boolean isDifferentBlock(Transform<World> transform1, Transform<World> transform2) {
		return UtilsLocation.isDifferentBlock(transform1.getLocation(), transform2.getLocation());
	}
	
	public static boolean isDifferentBlock(Location<World> location1, Location<World> location2) {
		return !location1.getExtent().equals(location2.getExtent()) || !location1.getBlockPosition().equals(location2.getBlockPosition());
	}
}
