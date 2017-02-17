package fr.evercraft.everapi.services.selection;

/*
 * Erreur : Il faut d'abord selectionner la premiere position
 */
public class SelectorSecondaryException extends Exception {

	private static final long serialVersionUID = -2975193019359714465L;

	public SelectorSecondaryException(String msg) {
        super(msg);
    }
}
