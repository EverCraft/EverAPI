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
package fr.evercraft.everapi.services.worldguard.flag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColor;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.config.EMessageBuilderSerializer;
import fr.evercraft.everapi.java.UtilsBoolean;
import fr.evercraft.everapi.java.UtilsFloat;
import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.command.Args;
import fr.evercraft.everapi.services.worldguard.region.ProtectedRegion;
import fr.evercraft.everapi.sponge.UtilsBossBar;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public abstract class MessageFlag extends EFlag<EMessageBuilder> {
	
	public static final String MARKER_CHAT_MESSAGE = "-chatMessage";
	public static final String MARKER_CHAT_PREFIX = "-chatPrefix";
	
	public static final String MARKER_ACTIONBAR_MESSAGE = "-actionbarMessage";
	public static final String MARKER_ACTIONBAR_PREFIX = "-actionbarPrefix";
	public static final String MARKER_ACTIONBAR_STAY = "-actionbarStay";
	public static final String MARKER_ACTIONBAR_PRIORITY = "-actionbarPriority";
	
	public static final String MARKER_TITLE_MESSAGE = "-titleMessage";
	public static final String MARKER_TITLE_PREFIX = "-titlePrefix";
	public static final String MARKER_TITLE_SUBMESSAGE = "-titleSubMessage";
	public static final String MARKER_TITLE_SUBPREFIX = "-titleSubPrefix";
	public static final String MARKER_TITLE_STAY = "-titleStay";
	public static final String MARKER_TITLE_FADEIN = "-titleFadeIn";
	public static final String MARKER_TITLE_FADEOUT = "-titleFadeOut";
	public static final String MARKER_TITLE_PRIORITY = "-titlePriority";
	
	public static final String MARKER_BOSSBAR_MESSAGE = "-bossbarMessage";
	public static final String MARKER_BOSSBAR_PREFIX = "-bossbarPrefix";
	public static final String MARKER_BOSSBAR_STAY = "-bossbarStay";
	public static final String MARKER_BOSSBAR_COLOR = "-bossbarColor";
	public static final String MARKER_BOSSBAR_CREATEFOG = "-bossbarCreateFog";
	public static final String MARKER_BOSSBAR_DARKENSKY = "-bossbarDarkenSky";
	public static final String MARKER_BOSSBAR_OVERLAY = "-bossbarOverlay";
	public static final String MARKER_BOSSBAR_MUSIC = "-bossbarMusic";
	public static final String MARKER_BOSSBAR_PERCENT = "-bossbarPercent";
	public static final String MARKER_BOSSBAR_PRIORITY = "-bossbarPriority";
	
	private final Args.Builder patternAdd;
	private final Args.Builder patternRemove;

	public MessageFlag(String name) {
		super(name);
		
		this.patternAdd = Args.builder()
			.value(MARKER_CHAT_MESSAGE, (source, args) -> Arrays.asList("\"\""))
			.value(MARKER_CHAT_PREFIX, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			
			.value(MARKER_ACTIONBAR_MESSAGE, (source, args) -> Arrays.asList("\"\""))
			.value(MARKER_ACTIONBAR_PREFIX, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_ACTIONBAR_STAY, (source, args) -> Arrays.asList("5", "10", "20"))
			.value(MARKER_ACTIONBAR_PRIORITY, (source, args) -> Arrays.asList("worldguard.flag.message"))
			
			.value(MARKER_TITLE_MESSAGE, (source, args) -> Arrays.asList("\"\""))
			.value(MARKER_TITLE_PREFIX, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_TITLE_SUBMESSAGE, (source, args) -> Arrays.asList("\"\""))
			.value(MARKER_TITLE_SUBPREFIX, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_TITLE_STAY, (source, args) -> Arrays.asList("5", "10", "20"))
			.value(MARKER_TITLE_FADEIN, (source, args) -> Arrays.asList("0", "1", "2"))
			.value(MARKER_TITLE_FADEOUT, (source, args) -> Arrays.asList("0", "1", "2"))
			.value(MARKER_TITLE_PRIORITY, (source, args) -> Arrays.asList("worldguard.flag.message"))
			
			.value(MARKER_BOSSBAR_MESSAGE, (source, args) -> Arrays.asList("\"\""))
			.value(MARKER_BOSSBAR_PREFIX, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_BOSSBAR_STAY, (source, args) -> Arrays.asList("5", "10", "20"))
			.value(MARKER_BOSSBAR_CREATEFOG, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_BOSSBAR_DARKENSKY, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_BOSSBAR_MUSIC, (source, args) -> Arrays.asList("TRUE", "FALSE"))
			.value(MARKER_BOSSBAR_COLOR, (source, args) -> 
					Sponge.getRegistry().getAllOf(BossBarColor.class).stream()
						.map(color -> color.getName())
						.collect(Collectors.toSet()))
			.value(MARKER_BOSSBAR_OVERLAY, (source, args) -> 
					Sponge.getRegistry().getAllOf(BossBarOverlay.class).stream()
						.map(overlay -> overlay.getId())
						.collect(Collectors.toSet()))
			.value(MARKER_BOSSBAR_PERCENT, (source, args) -> Arrays.asList("0", "0.1", "0.5", "1"))
			.value(MARKER_BOSSBAR_PRIORITY, (source, args) -> Arrays.asList("worldguard.flag.message"));
		
		this.patternRemove = Args.builder()
				.empty(MARKER_CHAT_MESSAGE)
				.empty(MARKER_CHAT_PREFIX)
				.empty(MARKER_ACTIONBAR_MESSAGE)
				.empty(MARKER_ACTIONBAR_PREFIX)
				.empty(MARKER_ACTIONBAR_STAY)
				.empty(MARKER_ACTIONBAR_PRIORITY)
				.empty(MARKER_TITLE_MESSAGE)
				.empty(MARKER_TITLE_PREFIX)
				.empty(MARKER_TITLE_SUBMESSAGE)
				.empty(MARKER_TITLE_SUBPREFIX)
				.empty(MARKER_TITLE_STAY)
				.empty(MARKER_TITLE_FADEIN)
				.empty(MARKER_TITLE_FADEOUT)
				.empty(MARKER_TITLE_PRIORITY)
				.empty(MARKER_BOSSBAR_MESSAGE)
				.empty(MARKER_BOSSBAR_PREFIX)
				.empty(MARKER_BOSSBAR_STAY)
				.empty(MARKER_BOSSBAR_CREATEFOG)
				.empty(MARKER_BOSSBAR_DARKENSKY)
				.empty(MARKER_BOSSBAR_MUSIC)
				.empty(MARKER_BOSSBAR_COLOR)
				.empty(MARKER_BOSSBAR_OVERLAY)
				.empty(MARKER_BOSSBAR_PERCENT)
				.empty(MARKER_BOSSBAR_PRIORITY);
	}
	
	@Override
	public Collection<String> getSuggestAdd(CommandSource source, final List<String> args) {
		return this.patternAdd.suggest(source, args);
	}
	
	@Override
	public Collection<String> getSuggestRemove(CommandSource source, final List<String> args) {
		return this.patternRemove.suggest(source, args);
	}
	
	@Override
	public EMessageBuilder parseAdd(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) throws IllegalArgumentException {
		Args args = this.patternAdd.build(values);
		
		if (args.getArgs().size() > 1 || (args.getArgs().size() == 0 && args.countValues() == 0) || 
				(args.getArgs().size() == 1 && args.getValue(MARKER_CHAT_MESSAGE).isPresent())) {
			throw new IllegalArgumentException();
		}
		List<String> args_string = args.getArgs();
		EMessageBuilder message = region.getFlag(this).get(group).orElseGet(() -> new EMessageBuilder());
		
		// Chat
		if (args_string.size() == 1) {
			message.chatMessage(EFormatString.of(args_string.get(0)));
		} else {
			args.getValue(MARKER_CHAT_MESSAGE).ifPresent(arg -> {
				message.chatMessage(EFormatString.of(arg));
			});
		}
		args.getValue(MARKER_CHAT_PREFIX).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.chatPrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		
		// ActionBar
		args.getValue(MARKER_ACTIONBAR_MESSAGE).ifPresent(arg -> {
			message.actionbarMessage(EFormatString.of(arg));
		});
		args.getValue(MARKER_ACTIONBAR_PREFIX).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.actionbarPrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_ACTIONBAR_STAY).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.actionbarStay(Math.round(value.get() * 1000));
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_ACTIONBAR_PRIORITY).ifPresent(arg -> {
			message.actionbarPriority(arg);
		});
		
		// Title
		args.getValue(MARKER_TITLE_MESSAGE).ifPresent(arg -> {
			message.titleMessage(EFormatString.of(arg));
		});
		args.getValue(MARKER_TITLE_PREFIX).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.titlePrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_TITLE_SUBMESSAGE).ifPresent(arg -> {
			message.titleSubMessage(EFormatString.of(arg));
		});
		args.getValue(MARKER_TITLE_SUBPREFIX).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.titleSubPrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_TITLE_STAY).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.titleStay(Math.round(value.get() * 1000));
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_TITLE_FADEIN).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.titleFadeIn(Math.round(value.get() * 1000));
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_TITLE_FADEOUT).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.titleFadeOut(Math.round(value.get() * 1000));
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_TITLE_PRIORITY).ifPresent(arg -> {
			message.titlePriority(arg);
		});
		
		// BossBar
		args.getValue(MARKER_BOSSBAR_MESSAGE).ifPresent(arg -> {
			message.bossbarMessage(EFormatString.of(arg));
		});
		args.getValue(MARKER_BOSSBAR_PREFIX).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.bossbarPrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_STAY).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.bossbarStay(Math.round(value.get() * 1000));
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_PERCENT).ifPresent(arg -> {
			Optional<Float> value = UtilsFloat.parseFloat(arg);
			if (value.isPresent()) {
				message.bossbarPercent(value.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_NUMBER.getFormat().toString("<number>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_CREATEFOG).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.bossbarCreateFog(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_DARKENSKY).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.bossbarPrefix(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_MUSIC).ifPresent(arg -> {
			Optional<Boolean> bool = UtilsBoolean.parseBoolean(arg);
			if (bool.isPresent()) {
				message.bossbarPlayEndBossMusic(bool.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_BOOLEAN.getFormat().toString("<boolean>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_COLOR).ifPresent(arg -> {
			Optional<BossBarColor> color = UtilsBossBar.getColor(arg);
			if (color.isPresent()) {
				message.bossbarColor(color.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_COLOR.getFormat().toString("<color>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_OVERLAY).ifPresent(arg -> {
			Optional<BossBarOverlay> overlay = UtilsBossBar.getOverlay(arg);
			if (overlay.isPresent()) {
				message.bossbarOverlay(overlay.get());
			} else {
				throw new IllegalArgumentException(EAMessages.IS_NOT_OVERLAY.getFormat().toString("<overlay>", arg));
			}
		});
		args.getValue(MARKER_BOSSBAR_PRIORITY).ifPresent(arg -> {
			message.bossbarPriority(arg);
		});
		
		return message;
	}
	
	@Override
	public Optional<EMessageBuilder> parseRemove(CommandSource source, ProtectedRegion region, ProtectedRegion.Group group, List<String> values) {
		Args args = this.patternRemove.build(values);
		
		if (args.getArgs().size() != 0) {
			throw new IllegalArgumentException();
		}
		
		if (args.countOptions() == 0) {
			return Optional.empty();
		}
		EMessageBuilder message = region.getFlag(this).get(group).orElseGet(() -> new EMessageBuilder());
		
		// Chat
		if (args.isOption(MARKER_CHAT_MESSAGE)) message.chatMessage(null);
		if (args.isOption(MARKER_CHAT_PREFIX)) message.chatPrefix(null);
		
		// ActionBar
		if (args.isOption(MARKER_ACTIONBAR_MESSAGE)) message.actionbarMessageString(null);
		if (args.isOption(MARKER_ACTIONBAR_PREFIX)) message.actionbarPrefix(null);
		if (args.isOption(MARKER_ACTIONBAR_STAY)) message.actionbarStay(null);
		if (args.isOption(MARKER_ACTIONBAR_PRIORITY)) message.actionbarPriority(null);
		
		// Title
		if (args.isOption(MARKER_TITLE_MESSAGE)) message.titleMessage(null);
		if (args.isOption(MARKER_TITLE_PREFIX)) message.titlePrefix(null);
		if (args.isOption(MARKER_TITLE_SUBMESSAGE)) message.titleSubMessage(null);
		if (args.isOption(MARKER_TITLE_SUBPREFIX)) message.titleSubPrefix(null);
		if (args.isOption(MARKER_TITLE_STAY)) message.titleStay(null);
		if (args.isOption(MARKER_TITLE_FADEIN)) message.titleFadeIn(null);
		if (args.isOption(MARKER_TITLE_FADEOUT)) message.titleFadeOut(null);
		if (args.isOption(MARKER_TITLE_PRIORITY)) message.titlePriority(null);

		// BossBar
		if (args.isOption(MARKER_BOSSBAR_MESSAGE)) message.bossbarMessage(null);
		if (args.isOption(MARKER_BOSSBAR_PREFIX)) message.bossbarPrefix(null);
		if (args.isOption(MARKER_BOSSBAR_STAY)) message.bossbarStay(null);
		if (args.isOption(MARKER_BOSSBAR_PERCENT)) message.bossbarPercent(null);
		if (args.isOption(MARKER_BOSSBAR_CREATEFOG)) message.bossbarCreateFog(null);
		if (args.isOption(MARKER_BOSSBAR_DARKENSKY)) message.bossbarDarkenSky(null);
		if (args.isOption(MARKER_BOSSBAR_MUSIC)) message.bossbarPlayEndBossMusic(null);
		if (args.isOption(MARKER_BOSSBAR_COLOR)) message.bossbarColor(null);
		if (args.isOption(MARKER_BOSSBAR_OVERLAY)) message.bossbarOverlay(null);
		if (args.isOption(MARKER_BOSSBAR_PRIORITY)) message.bossbarPriority(null);
		
		if (message.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.ofNullable(message);
	}

	@Override
	public String serialize(EMessageBuilder value) throws IllegalArgumentException {
		Gson gson = new Gson();
		try {
			return gson.toJson(EMessageBuilderSerializer.serialize(value));
		} catch (ObjectMappingException e) {
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public EMessageBuilder deserialize(String value) throws IllegalArgumentException {
		try {
			return EMessageBuilderSerializer.deserialize(new JsonParser().parse(value).getAsJsonObject());
		} catch (ObjectMappingException e) {
			throw new IllegalArgumentException(e.getMessage(), e.getCause());
		}
	}
	
	@Override
	public Text getValueFormat(EMessageBuilder value) {
		List<Text> texts = new ArrayList<Text>();
		List<Text> hover = new ArrayList<Text>();
		
		if (value.getChatMessage() != null) {
			hover.add(EAMessages.FLAG_MESSAGE_CHAT_MESSAGE.getFormat()
				.toText("<message>", value.getChatMessage()));
			if (value.getChatPrefix() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_CHAT_PREFIX.getFormat()
						.toText("<prefix>", value.getChatPrefix().toString().toUpperCase()));
			}
			
			texts.add(EAMessages.FLAG_MESSAGE_CHAT.getText().toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
				.build());
		}
		
		if (value.getActionbarMessage() != null) {
			hover = new ArrayList<Text>();
			hover.add(EAMessages.FLAG_MESSAGE_ACTIONBAR_MESSAGE.getFormat()
				.toText("<message>", value.getActionbarMessage()));
			if (value.getActionbarPrefix() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_ACTIONBAR_PREFIX.getFormat()
					.toText("<prefix>", value.getActionbarPrefix().toString().toUpperCase()));
			}
			if (value.getActionbarStay() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_ACTIONBAR_STAY.getFormat()
					.toText("<stay>", value.getActionbarStay()));
			}
			if (value.getActionbarPriority() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_ACTIONBAR_PRIORITY.getFormat()
					.toText("<priority>", value.getActionbarPriority()));
			}
			
			texts.add(EAMessages.FLAG_MESSAGE_ACTIONBAR.getText().toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
				.build());
		}
		
		if (value.getTitleMessage() != null || value.getTitleSubMessage() != null) {
			hover = new ArrayList<Text>();
			if (value.getTitleMessage() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_MESSAGE.getFormat()
					.toText("<message>", value.getTitleMessage()));
			}
			if (value.getTitleSubMessage() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_SUBMESSAGE.getFormat()
					.toText("<submessage>", value.getTitleSubMessage()));
			}
			if (value.getTitlePrefix() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_PREFIX.getFormat()
					.toText("<prefix>", value.getTitlePrefix().toString().toUpperCase()));
			}
			if (value.getTitleSubPrefix() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_SUBPREFIX.getFormat()
					.toText("<subprefix>", value.getTitleSubPrefix().toString().toUpperCase()));
			}
			if (value.getTitleStay() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_STAY.getFormat()
					.toText("<stay>", value.getTitleStay()));
			}
			if (value.getTitleFadeIn() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_FADEIN.getFormat()
					.toText("<fadein>", value.getTitleFadeIn()));
			}
			if (value.getTitleFadeOut() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_FADEOUT.getFormat()
					.toText("<fadeout>", value.getTitleFadeOut()));
			}
			if (value.getTitlePriority() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_TITLE_PRIORITY.getFormat()
					.toText("<priority>", value.getTitlePriority()));
			}
			
			texts.add(EAMessages.FLAG_MESSAGE_TITLE.getText().toBuilder()
				.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
				.build());
		}
		
		if (value.getBossbarMessage() != null) {
			hover = new ArrayList<Text>();
			if (value.getBossbarMessage() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_MESSAGE.getFormat()
					.toText("<message>", value.getBossbarMessage()));
			}
			if (value.getBossbarPrefix() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_PREFIX.getFormat()
					.toText("<prefix>", value.getBossbarPrefix().toString().toUpperCase()));
			}
			if (value.getBossbarStay() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_STAY.getFormat()
					.toText("<stay>", value.getBossbarStay()));
			}
			if (value.getBossbarCreateFog() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_CREATEFOG.getFormat()
					.toText("<createfog>", value.getBossbarCreateFog().toString().toUpperCase()));
			}
			if (value.getBossbarDarkenSky() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_DARKENSKY.getFormat()
					.toText("<darkensky>", value.getBossbarDarkenSky().toString().toUpperCase()));
			}
			if (value.getBossbarPlayEndBossMusic() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_MUSIC.getFormat()
					.toText("<music>", value.getBossbarPlayEndBossMusic().toString().toUpperCase()));
			}
			if (value.getBossbarColor() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_COLOR.getFormat()
					.toText("<color>", value.getBossbarColor().getId()));
			}
			if (value.getBossbarOverlay() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_OVERLAY.getFormat()
					.toText("<overlay>", value.getBossbarOverlay().getId()));
			}
			if (value.getBossbarPercent() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_PERCENT.getFormat()
					.toText("<percent>", value.getBossbarPercent()));
			}
			if (value.getBossbarPriority() != null) {
				hover.add(EAMessages.FLAG_MESSAGE_BOSSBAR_PRIORITY.getFormat()
					.toText("<priority>", value.getBossbarPriority()));
			}
			
			texts.add(EAMessages.FLAG_MESSAGE_BOSSBAR.getText().toBuilder()
					.onHover(TextActions.showText(Text.joinWith(Text.of("\n"), hover)))
					.build());
		}
		
		return Text.joinWith(EAMessages.FLAG_MESSAGE_JOIN.getText(), texts);
	}
}
