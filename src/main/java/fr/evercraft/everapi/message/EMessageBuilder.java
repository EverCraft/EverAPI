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
 * aInteger with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.message;

import java.util.Optional;

import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.text.Text;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.format.EFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.type.EMessageActionBar;
import fr.evercraft.everapi.message.type.EMessageBossBar;
import fr.evercraft.everapi.message.type.EMessageChat;
import fr.evercraft.everapi.message.type.EMessageTitle;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public final class EMessageBuilder {
	private EnumMessage prefix;
	
	//EMessageChat
	private EFormat chat_message;
	private Boolean chat_prefix;
	
	// EMessageActionBar
	private EFormat actionbar_message;
	private Boolean actionbar_prefix;
	private Integer actionbar_stay;
	private String actionbar_priority;
	
	// EMessageTitle
	private EFormat title_message;
	private Boolean title_prefix;
	private EFormat title_submessage;
	private Boolean title_subprefix;
	private Integer title_stay;
	private Integer title_fadeIn;
	private Integer title_fadeOut;
	private String title_priority;
	
	// EMessageBossBar
	private EFormat bossbar_message;
	private Boolean bossbar_prefix;
	private Integer bossbar_stay;
	private BossBarColor bossbar_color;
	private Boolean bossbar_createFog;
	private Boolean bossbar_darkenSky;
	private BossBarOverlay bossbar_overlay;
	private Float bossbar_percent;
	private Boolean bossbar_playEndBossMusic;
	private String bossbar_priority;
	
	public EMessageBuilder() {
		this.clear();
	}
	
	/*
	 * Chat
	 */
	
	public EMessageBuilder chat(final EFormat message, final Boolean prefix) {
		this.chatMessage(message);
		this.chatPrefix(prefix);
		return this;
	}
	
	public EMessageBuilder chatMessage(EFormat message) {
		this.chat_message = message;
		return this;
	}
	
	public EMessageBuilder chatPrefix(Boolean prefix) {
		this.chat_prefix = prefix;
		return this;
	}

	/*
	 * ActionBar
	 */
	
	public EMessageBuilder actionbar(final EFormat message, final Integer stay, final String priority, final Boolean prefix) {
		this.actionbarMessage(message);
		this.actionbarStay(stay);
		this.actionbarPriority(priority);
		this.actionbarPrefix(prefix);
		return this;
	}
	
	public EMessageBuilder actionbarMessage(String message) {
		this.actionbar_message = EFormatString.of(message);
		return this;
	}
	
	public EMessageBuilder actionbarMessage(EFormat message) {
		this.actionbar_message = message;
		return this;
	}
	
	public EMessageBuilder actionbarStay(Integer stay) {
		this.actionbar_stay = stay;
		return this;
	}
	
	public EMessageBuilder actionbarPriority(String priority) {
		this.actionbar_priority = priority;
		return this;
	}
	
	public EMessageBuilder actionbarPrefix(Boolean prefix) {
		this.actionbar_prefix = prefix;
		return this;
	}
	
	/*
	 * Title
	 */
	
	public EMessageBuilder title(final EFormat message, final Boolean prefix, 
			final EFormat sub_message, final Boolean sub_prefix,
			final Integer stay, final Integer fadeIn, final Integer fadeOut, final String priority) {
		this.titleMessage(message);
		this.titleSubMessage(sub_message);
		this.titleStay(stay);
		this.titleFadeIn(fadeIn);
		this.titleFadeOut(fadeOut);
		this.titlePriority(priority);
		this.titlePrefix(prefix);
		this.titleSubPrefix(sub_prefix);
		return this;
	}
	
	public EMessageBuilder titleMessage(EFormat message) {
		this.title_message = message;
		return this;
	}
	
	public EMessageBuilder titleSubMessage(EFormat sub_message) {
		this.title_submessage = sub_message;
		return this;
	}
	
	public EMessageBuilder titleStay(Integer stay) {
		this.title_stay = stay;
		return this;
	}
	
	public EMessageBuilder titleFadeIn(Integer fadeIn) {
		this.title_fadeIn = fadeIn;
		return this;
	}
	
	public EMessageBuilder titleFadeOut(Integer fadeOut) {
		this.title_fadeOut = fadeOut;
		return this;
	}
	
	public EMessageBuilder titlePriority(String priority) {
		this.title_priority = priority;
		return this;
	}
	
	public EMessageBuilder titlePrefix(Boolean prefix) {
		this.title_prefix = prefix;
		return this;
	}
	
	public EMessageBuilder titleSubPrefix(Boolean sub_prefix) {
		this.title_subprefix = sub_prefix;
		return this;
	}
	
	/*
	 * BossBar
	 */
	
	public EMessageBuilder bossbar(final EFormat message, final Integer stay, final BossBarColor color, final Boolean createFog, 
			final Boolean darkenSky, final BossBarOverlay overlay, final Float percent, final Boolean playEndBossMusic,
			final String priority, final Boolean prefix) {
		this.bossbarMessage(message);
		this.bossbarStay(stay);
		this.bossbarColor(color);
		this.bossbarCreateFog(createFog);
		this.bossbarDarkenSky(darkenSky);
		this.bossbarOverlay(overlay);
		this.bossbarPercent(percent);
		this.bossbarPlayEndBossMusic(playEndBossMusic);
		this.bossbarPriority(priority);
		this.bossbarPrefix(prefix);
		return this;
	}
	
	public EMessageBuilder bossbarMessage(EFormat message) {
		this.bossbar_message = message;
		return this;
	}
	
	public EMessageBuilder bossbarStay(Integer stay) {
		this.bossbar_stay = stay;
		return this;
	}
	
	public EMessageBuilder bossbarColor(BossBarColor color) {
		this.bossbar_color = color;
		return this;
	}
	
	public EMessageBuilder bossbarCreateFog(Boolean createFog) {
		this.bossbar_createFog = createFog;
		return this;
	}
	
	public EMessageBuilder bossbarDarkenSky(Boolean darkenSky) {
		this.bossbar_darkenSky = darkenSky;
		return this;
	}
	
	public EMessageBuilder bossbarOverlay(BossBarOverlay overlay) {
		this.bossbar_overlay = overlay;
		return this;
	}
	
	public EMessageBuilder bossbarPercent(Float percent) {
		this.bossbar_percent = percent;
		return this;
	}
	
	public EMessageBuilder bossbarPlayEndBossMusic(Boolean playEndBossMusic) {
		this.bossbar_playEndBossMusic = playEndBossMusic;
		return this;
	}
	
	public EMessageBuilder bossbarPriority(String priority) {
		this.bossbar_priority = priority;
		return this;
	}
	
	public EMessageBuilder bossbarPrefix(Boolean prefix) {
		this.bossbar_prefix = prefix;
		return this;
	}
	
	/*
	 * Autre
	 */
	
	public EMessageBuilder prefix(final EnumMessage prefix) {
		Preconditions.checkNotNull(prefix, "prefix");
		
		this.prefix = prefix;
		return this;
	}
	
	public EMessageBuilder clear() {
		this.prefix = null;
		
		this.chat_message = null;
		this.chat_prefix = null;
		
		this.actionbar_message = null;
		this.actionbar_prefix = null;
		this.actionbar_stay = null;
		this.actionbar_priority = null;
		
		this.title_message = null;
		this.title_prefix = null;
		this.title_submessage = null;
		this.title_subprefix = null;
		this.title_stay = null;
		this.title_fadeIn = null;
		this.title_fadeOut = null;
		this.title_priority = null;
		
		// EMessageBossBar
		this.bossbar_message = null;
		this.bossbar_prefix = null;
		this.bossbar_stay = null;
		this.bossbar_createFog = null;
		this.bossbar_darkenSky = null;
		this.bossbar_overlay = null;
		this.bossbar_percent = null;
		this.bossbar_playEndBossMusic = null;
		this.bossbar_priority = null;
		return this;
	}
	
	public EMessageFormat build() {
		Optional<EMessageChat> chat = Optional.empty();
		Optional<EMessageActionBar> actionbar = Optional.empty();
		Optional<EMessageTitle> title = Optional.empty();
		Optional<EMessageBossBar> bossbar = Optional.empty();
		
		if (this.chat_message != null && !this.chat_message.isEmpty()) {
			chat = Optional.of(new EMessageChat(
					this.chat_message, 
					this.chat_prefix != null ? this.chat_prefix : true));
		}
		
		if (this.actionbar_message != null && !this.actionbar_message.isEmpty()) {			
			actionbar = Optional.of(new EMessageActionBar(
					this.actionbar_message, 
					this.actionbar_stay != null ? this.actionbar_stay : 5, 
					this.actionbar_priority != null ? this.actionbar_priority : "message", 
					this.actionbar_prefix != null ? this.actionbar_prefix : false));
		}
		
		if ((this.title_message != null && !this.title_message.isEmpty()) || 
				(this.title_submessage != null && !this.title_submessage.isEmpty())) {
			
			title = Optional.of(new EMessageTitle(
					this.title_message != null ? this.title_message : EFormatString.EMPTY, 
					this.title_prefix != null ? this.title_prefix : false,
					this.title_submessage != null ? this.title_submessage : EFormatString.EMPTY, 
					this.title_subprefix != null ? this.title_prefix : false,
					this.title_stay != null ? this.title_stay : 5, 
					this.title_fadeIn != null ? this.title_fadeIn : 1, 
					this.title_fadeOut != null ? this.title_fadeOut : 1, 
					this.title_priority != null ? this.title_priority : "message"));
		}

		if (this.bossbar_message != null) {			
			bossbar = Optional.of(new EMessageBossBar(
					this.bossbar_message, 
					this.bossbar_stay != null ? this.title_stay : 5, 
					ServerBossBar.builder()
							.name(Text.EMPTY)
							.color(this.bossbar_color != null ? this.bossbar_color : BossBarColors.WHITE)
							.createFog(this.bossbar_createFog != null ? this.bossbar_createFog : false)
							.darkenSky(this.bossbar_darkenSky != null ? this.bossbar_darkenSky : false)
							.overlay(this.bossbar_overlay != null ? this.bossbar_overlay : BossBarOverlays.PROGRESS)
							.percent(this.bossbar_percent != null ? this.bossbar_percent : 1)
							.playEndBossMusic(this.bossbar_playEndBossMusic != null ? this.bossbar_playEndBossMusic : false)
							.build(), 
					this.title_priority != null ? this.title_priority : "message",
					this.bossbar_prefix != null ? this.bossbar_prefix : false));
		}
		
		return new EMessageFormat(Optional.ofNullable(this.prefix), chat, actionbar, title, bossbar);
	}

	public EnumMessage getPrefix() {
		return prefix;
	}

	public EFormat getChatMessage() {
		return chat_message;
	}

	public Boolean getChatPrefix() {
		return chat_prefix;
	}

	public EFormat getActionbarMessage() {
		return actionbar_message;
	}

	public Boolean getActionbarPrefix() {
		return actionbar_prefix;
	}

	public Integer getActionbarStay() {
		return actionbar_stay;
	}

	public String getActionbarPriority() {
		return actionbar_priority;
	}

	public EFormat getTitleMessage() {
		return title_message;
	}

	public Boolean getTitlePrefix() {
		return title_prefix;
	}

	public EFormat getTitleSubMessage() {
		return title_submessage;
	}

	public Boolean getTitleSubPrefix() {
		return title_subprefix;
	}

	public Integer getTitleStay() {
		return title_stay;
	}

	public Integer getTitleFadeIn() {
		return title_fadeIn;
	}

	public Integer getTitleFadeOut() {
		return title_fadeOut;
	}

	public String getTitlePriority() {
		return title_priority;
	}

	public EFormat getBossbarMessage() {
		return bossbar_message;
	}

	public Boolean getBossbarPrefix() {
		return bossbar_prefix;
	}

	public Integer getBossbarStay() {
		return bossbar_stay;
	}

	public BossBarColor getBossbarColor() {
		return bossbar_color;
	}

	public Boolean getBossbarCreateFog() {
		return bossbar_createFog;
	}

	public Boolean getBossbarDarkenSky() {
		return bossbar_darkenSky;
	}

	public BossBarOverlay getBossbarOverlay() {
		return bossbar_overlay;
	}

	public Float getBossbarPercent() {
		return bossbar_percent;
	}

	public Boolean getBossbarPlayEndBossMusic() {
		return bossbar_playEndBossMusic;
	}

	public String getBossbarPriority() {
		return bossbar_priority;
	}
	
	
}
