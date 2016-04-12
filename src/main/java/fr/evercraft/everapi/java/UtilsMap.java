/**
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UtilsMap {
	
	/**
	 * Trié par ordre décroissant les valeurs
	 * @param hashmap La HashMap
	 * @return La HashMap trié par ordre décroissant les valeurs
	 */
	public static <K, V extends Comparable<V>> List<Entry<K, V>> valueDESC(final Map<K, V> hashmap) {
		List<Map.Entry<K, V>> sortedBalanceTop = new ArrayList<Map.Entry<K, V>>(hashmap.entrySet());
		Collections.sort(sortedBalanceTop, new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare(final Entry<K, V> entry1, final Entry<K, V> entry2){
				return entry2.getValue().compareTo(entry1.getValue());
			}
		});
		return sortedBalanceTop;
	}
	
	/**
	 * Trié par ordre croissant les valeurs
	 * @param hashmap La HashMap
	 * @returnLa HashMap trié par ordre croissant les valeurs
	 */
	public static <K, V extends Comparable<V>> List<Entry<K, V>> valueASC(final Map<K, V> hashmap) {
		List<Map.Entry<K, V>> sortedBalanceTop = new ArrayList<Map.Entry<K, V>>(hashmap.entrySet());
		Collections.sort(sortedBalanceTop, new Comparator<Map.Entry<K, V>>()
		{
			@Override
			public int compare(final Entry<K, V> entry1, final Entry<K, V> entry2){
				return entry1.getValue().compareTo(entry2.getValue());
			}
		});
		return sortedBalanceTop;
	}
}