package fr.evercraft.everapi.services.essentials;

public class TeleportRequest {
	public enum Type {
		TPA,
		TPAHERE;
	}
	
	private final Type type;
	private final long time;
	private boolean expire;
	
	public TeleportRequest(final Type type, final long time) {
		this.type = type;
		this.time = time;
		this.expire = false;
	}

	public Type getType() {
		return this.type;
	}
	
	public long getTime() {
		return this.time;
	}

	public boolean isExpire() {
		return this.expire;
	}
	
	public void setExpire(boolean expire) {
		this.expire = expire;
	}
}
