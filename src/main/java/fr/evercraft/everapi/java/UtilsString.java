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

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

public class UtilsString {

	/**
	 * Supprimé les accents d'un texte
	 * @param text Le texte
	 * @return Le texte sans accent
	 */
	public static String removeAccents(String text) {
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		return text.replaceAll("\\p{M}", "");
	}
	
	/**
	 * Remplacé un élément dans une liste de string
	 * @param messages La liste
	 * @param replace L'élement a remplacé
	 * @param value La valeur
	 * @return La liste de string avec les éléments remplacés
	 */
	public static List<String> replace(List<String> messages, String replace, String value){
		List<String> resultat = new ArrayList<String>();
		if (messages != null){
			for (String line : messages) {
				resultat.add(line.replaceAll(replace, value));
			}
		}
		return resultat;
	}
	
	/**
	 * Split un string et remplace les \" en "
	 * @param message Le message
	 * @return Liste de message
	 */
	public static List<String> splitGuillemets(String message) {
		String arg = "";
		List<String> args = new ArrayList<String>();
		for (char caractere : message.toCharArray()) {
			if (caractere == '"') {
				if (arg.endsWith(String.valueOf('\\'))) {
					arg = arg.substring(1, arg.length()-1);
					arg += caractere;
				} else {
					args.add(arg);
					arg = "";
				}
			} else {
				arg += caractere;
			}
		}
		args.add(arg);
		return args;
	}
	
	/**
     * Normalize a string according to the rules of this class.
     *
     * @param name an string
     * @return the normalized string
     */
    public static String normalize(String name) {
        return Normalizer.normalize(name.toLowerCase(), Form.NFC);
    }
}
