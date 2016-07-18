package fr.evercraft.everapi.services.essentials;

public class TeleportResquest {
	public enum Type {
		TPA,
		TPAHERE;
	}
	
	private final long time;
	private final boolean expire;
	
	public TeleportResquest(long time, boolean expire) {
		super();
		this.time = time;
		this.expire = expire;
	}

	public long getTime() {
		return time;
	}

	public boolean isExpire() {
		return expire;
	}
	
}
