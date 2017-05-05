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
package fr.evercraft.everapi.services.worldguard.flag;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion.Group;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public abstract class EFlag<T> implements Flag<T> {

    private static final Pattern VALID_NAME = Pattern.compile("^[:A-Za-z0-9\\-_]{1,40}$");
    
    private final String id;
    private final String name;
    
    protected EFlag(String name) {
    	Preconditions.checkArgument(name != null && EFlag.isValidName(name), "Invalid flag name used");
    	
        this.id = name.toLowerCase();
        this.name = name.toUpperCase();
    }
    
    public String getIdentifier() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
	public Text getNameFormat() {
		return Text.builder(this.getName())
				.onHover(TextActions.showText(EAMessages.FLAG_DESCRIPTION.getFormat()
						.toText("<description>", this.getDescription())))
				.build();
	}
	
	@Override
	public Set<Group> getGroups() {
		return ImmutableSet.copyOf(Group.values());
	}
	
	@Override
	public T getDefault(ProtectedRegion region) {
		return this.getDefault();
	}
	
	@Override
	public T parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) throws IllegalArgumentException {
		if (values.isEmpty()) {
			return this.deserialize("");
		}
		return this.deserialize(values.get(0));
	}
	
	@Override
	public Optional<T> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) {
		if (!values.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return Optional.empty();
	}
	
	@Override
	public Collection<String> getSuggestRemove(CommandSource source, List<String> args) {
		return Arrays.asList();
	}

	public static boolean isValidName(String name) {
    	Preconditions.checkNotNull(name, "name");
    	
        return VALID_NAME.matcher(name).matches();
    }
}
