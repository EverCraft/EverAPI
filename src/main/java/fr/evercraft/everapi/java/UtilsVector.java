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

import java.util.Optional;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;

public class UtilsVector {
	private static final String PATTERN_SPLIT = "[,\\s]+";	
	
    public static Optional<Vector3i> parseVector3i(String vector) {
    	try {
    		Preconditions.checkNotNull(vector, "vector");
    		
    		String[] split = vector.split(PATTERN_SPLIT);
    		if (split.length == 3) {
		    	int x = Integer.parseInt(split[0]);
		    	int y = Integer.parseInt(split[1]);
		    	int z = Integer.parseInt(split[2]);
		    	
		    	return Optional.of(Vector3i.from(x, y, z));
    		}
    	} catch (Exception e) {}
    	
    	return Optional.empty();
    }
    
    public static Optional<Vector3d> parseVector3d(String vector) {
    	try {
    		Preconditions.checkNotNull(vector, "vector");
    		
    		String[] split = vector.split(PATTERN_SPLIT);
    		if (split.length == 3) {
		    	double x = Double.parseDouble(split[0]);
		    	double y = Double.parseDouble(split[1]);
		    	double z = Double.parseDouble(split[2]);
		    	
		    	return Optional.of(Vector3d.from(x, y, z));
    		}
    	} catch (Exception e) {}
    	
    	return Optional.empty();
    }
}
