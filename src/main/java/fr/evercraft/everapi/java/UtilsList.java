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
