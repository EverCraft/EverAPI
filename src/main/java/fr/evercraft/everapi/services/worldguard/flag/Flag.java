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

import java.util.regex.Pattern;

public abstract class Flag<T> {

    private static final Pattern VALID_NAME = Pattern.compile("^[:A-Za-z0-9\\-]{1,40}$");
    private final String name;
    private final TypeToken<T> token;
    
    protected Flag(String name, TypeToken<T> token) {
    	Preconditions.checkArgument(name != null && !Flag.isValidName(name), "Invalid flag name used");
    	Preconditions.checkNotNull(token, "token");
    	
        this.name = name;
        this.token = token;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public TypeToken<T> getToken() {
        return this.token;
    }
    
    public static boolean isValidName(String name) {
    	Preconditions.checkNotNull(name, "name");
    	
        return VALID_NAME.matcher(name).matches();
    }

}
