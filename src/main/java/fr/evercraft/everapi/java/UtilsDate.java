package fr.evercraft.everapi.java;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UtilsDate {
	/**
	 * Retourne le Timestamp de l'heure du serveur
	 * @return Le Timestamp de l'heure du serveur
	 */
	public static Timestamp getTimestamp(){
   	 	return new Timestamp(System.currentTimeMillis());
    }
	
	/**
	 * Retourne la date actuellement dans un certain format
	 * @param format Le format
	 * @return La date actuellement dans un certain format
	 */
	public static String getString(String format){
		return (new SimpleDateFormat(format)).format(UtilsDate.getTimestamp());
	}
	
	/**
	 * Retourne la date actuellement dans un certain format
	 * @param format Le format
	 * @param format L'heure
	 * @return La date actuellement dans un certain format
	 */
	public static String getString(String format, long time){
		return (new SimpleDateFormat(format)).format(new Timestamp(time));
	}
}
