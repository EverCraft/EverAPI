package fr.evercraft.everapi.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
	
	/**
	 * Trié par ordre croissant les valeurs
	 * @param hashmap La HashMap
	 * @returnLa HashMap trié par ordre croissant les valeurs
	 */
	public static <K, V > TreeMap<K, V> split(final TreeMap<K, V> hashmap, int size) {
		if(hashmap.size() > size) {
			TreeMap<K, V> tempo = new TreeMap<K, V>();
			Iterator<Entry<K, V>> iterator = hashmap.entrySet().iterator();
			int cpt = 0;
			while (cpt < size && iterator.hasNext()) {
				Entry<K, V> element = iterator.next();
				tempo.put(element.getKey(), element.getValue());
				cpt++;
			}
			return tempo;
		}
		return hashmap;
	}
}