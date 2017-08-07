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

import org.spongepowered.api.world.gamerule.DefaultGameRules;

import fr.evercraft.everapi.EverAPI;

public class UtilsGameRule <T> {
	
	public static final GameRule<Boolean> COMMAND_BLOCK_OUTPUT = UtilsGameRule.get(DefaultGameRules.COMMAND_BLOCK_OUTPUT, Boolean.class);
	public static final GameRule<Boolean> DO_DAYLIGHT_CYCLE = UtilsGameRule.get(DefaultGameRules.DO_DAYLIGHT_CYCLE, Boolean.class);
	public static final GameRule<Boolean> DO_ENTITY_DROPS = UtilsGameRule.get(DefaultGameRules.DO_ENTITY_DROPS, Boolean.class);
	public static final GameRule<Boolean> DO_FIRE_TICK = UtilsGameRule.get(DefaultGameRules.DO_FIRE_TICK, Boolean.class);
	public static final GameRule<Boolean> DO_MOB_LOOT = UtilsGameRule.get(DefaultGameRules.DO_MOB_LOOT, Boolean.class);
	public static final GameRule<Boolean> DO_MOB_SPAWNING = UtilsGameRule.get(DefaultGameRules.DO_MOB_SPAWNING, Boolean.class);
	public static final GameRule<Boolean> DO_TILE_DROPS = UtilsGameRule.get(DefaultGameRules.DO_TILE_DROPS, Boolean.class);
	public static final GameRule<Boolean> KEEP_INVENTORY = UtilsGameRule.get(DefaultGameRules.KEEP_INVENTORY, Boolean.class);
	public static final GameRule<Boolean> LOG_ADMIN_COMMANDS = UtilsGameRule.get(DefaultGameRules.LOG_ADMIN_COMMANDS, Boolean.class);
	public static final GameRule<Boolean> MOB_GRIEFING = UtilsGameRule.get(DefaultGameRules.MOB_GRIEFING, Boolean.class);
	public static final GameRule<Boolean> NATURAL_REGENERATION = UtilsGameRule.get(DefaultGameRules.NATURAL_REGENERATION, Boolean.class);
	public static final GameRule<Boolean> RANDOM_TICK_SPEED = UtilsGameRule.get(DefaultGameRules.RANDOM_TICK_SPEED, Boolean.class);
	public static final GameRule<Boolean> REDUCED_DEBUG_INFO = UtilsGameRule.get(DefaultGameRules.REDUCED_DEBUG_INFO, Boolean.class);
	public static final GameRule<Boolean> SEND_COMMAND_FEEDBACK = UtilsGameRule.get(DefaultGameRules.SEND_COMMAND_FEEDBACK, Boolean.class);
	public static final GameRule<Boolean> SHOW_DEATH_MESSAGES = UtilsGameRule.get(DefaultGameRules.SHOW_DEATH_MESSAGES, Boolean.class);
	
	@SuppressWarnings("unused")
	private final EverAPI plugin;
	
	public UtilsGameRule(final EverAPI plugin) {
		this.plugin = plugin;
	}
	
	public static <T> GameRule<T> get(final String name, final Class<T> type) {
		return new GameRule<T>(name, type);
	}

}
