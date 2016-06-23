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
