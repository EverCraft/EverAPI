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
import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.server.player.EPlayer;

import java.util.regex.Pattern;

import org.spongepowered.api.text.Text;

public abstract class EFlag<T> implements Flag<T> {

    private static final Pattern VALID_NAME = Pattern.compile("^[:A-Za-z0-9\\-]{1,40}$");
    
    private final String id;
    private final String name;
    private final TypeToken<T> token;
    
    protected EFlag(String name, TypeToken<T> token) {
    	Preconditions.checkArgument(name != null && !EFlag.isValidName(name), "Invalid flag name used");
    	Preconditions.checkNotNull(token, "token");
    	
        this.id = name.toLowerCase();
        this.name = name.toUpperCase();
        this.token = token;
    }
    
    public String getIdentifier() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public TypeToken<T> getToken() {
        return this.token;
    }

	public Text getNameFormat() {
		return EAMessages.FLAG_DESCRIPTION.getFormat()
					.toText("<description>", this.getDescription());
	}
	
	@Override
	public T deserialize(EPlayer player, String value) {
		return this.deserialize(value);
	}

	public static boolean isValidName(String name) {
    	Preconditions.checkNotNull(name, "name");
    	
        return VALID_NAME.matcher(name).matches();
    }
}
