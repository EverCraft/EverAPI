package fr.evercraft.everapi.services.title;

import java.util.UUID;

import org.spongepowered.api.text.title.Title;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.sponge.UtilsTick;

public class TitleMessage {
	
	private final UUID uuid;
	
	private final long time;
	private final String identifier;
	private final Title title;
	
	public TitleMessage(final UUID uuid, final String identifier, final Title title) {
		this.uuid = uuid;
		this.identifier = identifier;
		this.time = System.currentTimeMillis() + ((title.getStay().orElse(60) * 1000) / UtilsTick.TICK_SECONDS);
		this.title = title;
	}

	public UUID getPlayer() {
		return this.uuid;
	}

	public long getTime() {
		return this.time;
	}

	public Title getTitle() {
		return this.title;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
	
	public boolean send(EPlayer player) {
		player.sendTitle(this.title);
		return true;
	}

	@Override
	public String toString() {
		return "TitleMessage [uuid=" + uuid + ", time=" + time
				+ ", identifier=" + identifier + ", title=" + title + "]";
	}	
}
