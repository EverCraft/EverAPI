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

import java.util.ArrayList;
import java.util.List;

public class UtilsList {
	/**
	 * Mettre une liste en minuscule
	 * @param strings La liste
	 * @return La liste en minuscule
	 */
	public static List<String> toLowerCase(final List<String> strings){
		List<String> tempo = new ArrayList<String>(); 
	    for(String string : strings){
	    	tempo.add(string.toLowerCase());
	    }
	    return tempo;
	}
	
	/**
	 * Mettre une liste en majuscule
	 * @param strings La liste
	 * @return La liste en majuscule
	 */
	public static List<String> toUpperCase(final List<String> strings){
		List<String> tempo = new ArrayList<String>(); 
	    for(String string : strings){
	    	tempo.add(string.toUpperCase());
	    }
	    return tempo;
	}
}
