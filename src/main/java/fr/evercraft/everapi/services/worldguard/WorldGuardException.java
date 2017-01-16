package fr.evercraft.everapi.services.worldguard;

/**
 * Parent for all WorldEdit exceptions.
 */
public abstract class WorldGuardException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Create a new exception with a message.
	 *
	 * @param message the message
	 */
	protected WorldGuardException(String message) {
		super(message);
	}
}
