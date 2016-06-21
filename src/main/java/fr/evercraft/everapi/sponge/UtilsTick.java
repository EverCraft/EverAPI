package fr.evercraft.everapi.sponge;

public class UtilsTick {
	public static final int TICK_SECONDS = 20;
	
	public static int parseSeconds(final double seconds){
		return (int) (seconds * TICK_SECONDS);
	}
	
	public static long parseSeconds(final long seconds){
		return seconds * TICK_SECONDS;
	}
	
	public static long parseMinutes(final long minutes){
		return 60 * parseSeconds(minutes);
	}
}
