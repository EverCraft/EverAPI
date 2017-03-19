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
package fr.evercraft.everapi.java;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;

public class UtilsJson {
	
    public static Vector3i parseVector3i(JsonArray vector) {
    	Preconditions.checkNotNull(vector, "vector");
    	
		if (vector.size() != 3) throw new IllegalStateException();
		
    	int x = vector.get(0).getAsInt();
    	int y = vector.get(1).getAsInt();
    	int z = vector.get(2).getAsInt();
    	
    	return Vector3i.from(x, y, z);
    }
    
    public static Vector3d parseVector3d(JsonArray vector) {
    	Preconditions.checkNotNull(vector, "vector");
    	
		if (vector.size() != 3) throw new IllegalStateException();
		
    	double x = vector.get(0).getAsDouble();
    	double y = vector.get(1).getAsDouble();
    	double z = vector.get(2).getAsDouble();
    	
    	return Vector3d.from(x, y, z);
    }
}
