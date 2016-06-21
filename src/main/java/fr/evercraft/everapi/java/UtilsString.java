package fr.evercraft.everapi.java;

import java.text.Normalizer;
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
		if(messages != null){
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
		for(char caractere : message.toCharArray()) {
			if(caractere == '"') {
				if(arg.endsWith(String.valueOf('\\'))) {
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
		if(!arg.isEmpty()) {
			args.add(arg);
		}
		return args;
	}
}
