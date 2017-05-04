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
package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandSource;

import fr.evercraft.everapi.services.worldguard.flag.EFlag;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;

public abstract class BlockTypeFlag extends EFlag<EntryFlagValue<BlockType>> {
	
	protected static final String PATTERN_SPLIT = "[,\\s]+";

	public BlockTypeFlag(String name) {
		super(name);
	}
	
	/*
	 * Suggest
	 */
	
	@Override
	public Collection<String> getSuggestRemove(CommandSource source, List<String> args) {
		return this.getSuggestAdd(source, args);
	}
	
	/*
	 * Parse
	 */
	
	@Override
	public EntryFlagValue<BlockType> parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		EntryFlagValue<BlockType> newFlag = null;
		if (args.isEmpty()) {
			newFlag = this.deserialize("");
		} else {
			newFlag = this.deserialize(String.join(", ", args));
		}
		
		Optional<EntryFlagValue<BlockType>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			return newFlag.addAll(flag.get());
		} else {
			return newFlag;
		}
	}
	
	@Override
	public Optional<EntryFlagValue<BlockType>> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> args) {
		if (args.isEmpty()) Optional.empty();
		
		EntryFlagValue<BlockType> newFlag = this.deserialize(String.join(", ", args));
		if (newFlag.getKeys().isEmpty()) Optional.empty();
		
		Optional<EntryFlagValue<BlockType>> flag = region.getFlag(this).get(group);
		if (flag.isPresent()) {
			newFlag = flag.get().removeAll(newFlag);
			if (!newFlag.getKeys().isEmpty()) {
				return Optional.of(flag.get().removeAll(newFlag));
			}
			return Optional.empty();
		} else {
			return Optional.of(this.getDefault().removeAll(newFlag));
		}
	}
}
