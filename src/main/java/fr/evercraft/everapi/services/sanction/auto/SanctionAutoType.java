package fr.evercraft.everapi.services.sanction.auto;

public enum SanctionAutoType {
	BAN_PROFILE(true, false, false, false),
	BAN_IP(false, true, false, false),
	MUTE(false, false, true, false),
	JAIL(false, false, false, true),
	BAN_PROFILE_AND_BAN_IP(true, true, false, false),
	MUTE_AND_JAIL(false, false, true, true);
	
	private final boolean ban;
	private final boolean ban_ip;
	private final boolean mute;
	private final boolean jail;
	
	SanctionAutoType(final boolean ban, final boolean ban_ip, final boolean mute, final boolean jail) {
		this.ban = ban;
		this.ban_ip = ban_ip;
		this.mute = mute;
		this.jail = jail;
	}
	
	public boolean isBan() {
		return this.ban;
	}
	
	public boolean isBanIP() {
		return this.ban_ip;
	}
	
	public boolean isMute() {
		return this.mute;
	}
	
	public boolean isJail() {
		return this.jail;
	}
}
