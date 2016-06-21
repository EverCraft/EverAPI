/*
 * EverAPI
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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilsDouble {
	
	/**
	 * Choisir le nombre de chiffre après la virgule
	 * @param value La valeur
	 * @param places Le nombre de chiffre après la virgule
	 * @return Le chiffre avec l'arrondi
	 */
	public static Double round(final double value, final int places) {
		if (places < 0){
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static Double asDouble(final Object value){
    	if ((value instanceof Number)) {
    		return ((Number)value).doubleValue();
    	} else {
    		try {
    			return Double.valueOf(value.toString()).doubleValue();
    		} catch (NumberFormatException localNumberFormatException){}
    	}
    	return 0.0D;
    }
}
