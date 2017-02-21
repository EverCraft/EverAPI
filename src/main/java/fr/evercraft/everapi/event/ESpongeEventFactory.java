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
package fr.evercraft.everapi.event;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Server;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.event.ActionBarEvent.Add;
import fr.evercraft.everapi.event.ActionBarEvent.Remove;
import fr.evercraft.everapi.event.ActionBarEvent.Replace;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;
import fr.evercraft.everapi.services.bossbar.EBossBar;
import fr.evercraft.everapi.services.essentials.Mail;
import fr.evercraft.everapi.services.jail.Jail;
import fr.evercraft.everapi.services.mojang.check.MojangServer;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Color;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionBanProfile;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionBanIp;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionJail;
import fr.evercraft.everapi.services.sanction.Sanction.SanctionMute;
import fr.evercraft.everapi.services.title.TitleMessage;
import fr.evercraft.everapi.services.worldguard.region.SetProtectedRegion;

public class ESpongeEventFactory {
	
	/*
	 * ActionBar
	 */
	public static ActionBarEvent.Add createActionBarEventAdd(EPlayer player, ActionBarMessage actionBar, Cause cause) {
		return new ActionBarEvent.Add(player, actionBar.getIdentifier(), actionBar.getTime(), actionBar.getMessage(), cause);
    }
	
	public static ActionBarEvent.Remove createActionBarEventRemove(EPlayer player, ActionBarMessage actionBar, Cause cause) {
		return new ActionBarEvent.Remove(player, actionBar.getIdentifier(), actionBar.getTime(), actionBar.getMessage(), cause);
    }
	
	public static ActionBarEvent.Replace createActionBarEventReplace(EPlayer player, ActionBarMessage actionBar, ActionBarMessage newActionBar, Cause cause) {
		return new ActionBarEvent.Replace(player, actionBar.getIdentifier(), actionBar.getTime(), actionBar.getMessage(), 
				newActionBar.getIdentifier(), newActionBar.getTime(), newActionBar.getMessage(), cause);
    }
	
	/*
	 * Afk
	 */
	
	public static AfkEvent.Enable createAfkEventEnable(EPlayer player, AfkEvent.Action action, Cause cause) {
		return new AfkEvent.Enable(player, action, cause);
    }
	
	public static AfkEvent.Disable createAfkEventDisable(EPlayer player, AfkEvent.Action action, Cause cause) {
		return new AfkEvent.Disable(player, action, cause);
    }
	
	/*
	 * Back
	 */
	
	public static BackEvent createBackEvent(EPlayer player, Optional<Transform<World>> before, Optional<Transform<World>> after, Cause cause) {
		return new BackEvent(player, before, after, cause);
    }
	
	/*
	 * BanProfile
	 */
	
	public static BanEvent.Enable createBanEventEnable(EUser user, Text reason, long creationDate, Optional<Long> expirationDate, CommandSource commandSource, SanctionBanProfile sanction, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanEvent.Enable(user, player, reason, creationDate, expirationDate, sanction, commandSource, cause);
    }
	
	public static BanEvent.Disable createBanEventDisable(EUser user, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanProfile sanction, 
			Text pardonReason, Long pardonDate, CommandSource pardonCommandSource, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanEvent.Disable(user, player, reason, creationDate, expirationDate, sanction, source, Optional.of(pardonReason), Optional.of(pardonDate), Optional.of(pardonCommandSource), cause);
    }
	
	public static BanEvent.Disable createBanEventDisable(EUser user, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanProfile sanction, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanEvent.Disable(user, player, reason, creationDate, expirationDate, sanction, source, Optional.empty(), Optional.empty(), Optional.empty(), cause);
    }
	
	/*
	 * BanIp
	 */
	
	public static BanIpEvent.Enable createBanIpEventEnable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, CommandSource commandSource, SanctionBanIp sanction, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanIpEvent.Enable(user, player, address, reason, creationDate, expirationDate, sanction, commandSource, cause);
    }
	
	public static BanIpEvent.Disable createBanIpEventDisable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanIp sanction, 
			Text pardonReason, Long pardonDate, CommandSource pardonCommandSource, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanIpEvent.Disable(user, player, address, reason, creationDate, expirationDate, sanction, source, Optional.of(pardonReason), Optional.of(pardonDate), Optional.of(pardonCommandSource), cause);
    }
	
	public static BanIpEvent.Disable createBanIpEventDisable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanIp sanction, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) :Optional.empty();
		
		return new BanIpEvent.Disable(user, player, address, reason, creationDate, expirationDate, sanction, source, Optional.empty(), Optional.empty(), Optional.empty(), cause);
    }
	
	/*
	 * BossBar
	 */
	
	public static BossBarEvent.Add createBossBarEventAdd(EPlayer player, EBossBar bossbar, Cause cause) {
		return new BossBarEvent.Add(player, bossbar.getIdentifier(), bossbar.getServerBossBar(), cause);
    }
	
	public static BossBarEvent.Replace createBossBarEventReplace(EPlayer player, EBossBar bossBar, EBossBar newBossBar, Cause cause) {
		return new BossBarEvent.Replace(player, bossBar.getIdentifier(), bossBar.getServerBossBar(), newBossBar.getIdentifier(), newBossBar.getServerBossBar(), cause);
    }
	
	public static BossBarEvent.Remove createBossBarEventRemove(EPlayer player, EBossBar bossbar, Cause cause) {
		return new BossBarEvent.Remove(player, bossbar.getIdentifier(), bossbar.getServerBossBar(), cause);
    }
	
	/*
	 * ChatSystemEvent
	 */
	
	public static ChatSystemEvent.Reload createChatSystemEventReload(Cause cause) {
		return new ChatSystemEvent.Reload(cause);
    }
	
	/*
	 * Command
	 */
	
	public static CommandEvent.Send createCommandEventSend(EPlayer player, String command, String arg, final List<String> args, Cause cause) {
		return new CommandEvent.Send(player, command, arg, args, cause);
    }
	
	public static CommandEvent.Result createCommandEventResult(EPlayer player, String command, String arg, final List<String> args, boolean result, Cause cause) {
		return new CommandEvent.Result(player, command, arg, args, result, cause);
    }
	
	/*
	 * Freeze
	 */
	
	public static FightEvent.Start createFightEventStart(EPlayer player, EPlayer other, boolean victim, Cause cause) {
		return new FightEvent.Start(player, other, victim, cause);
    }
	
	public static FightEvent.Stop createFightEventStop(EPlayer player, FightEvent.Stop.Reason reason, Cause cause) {
		return new FightEvent.Stop(player, reason, cause);
    }
	
	/*
	 * Freeze
	 */
	
	public static FreezeEvent.Enable createFreezeEventEnable(EPlayer player, Cause cause) {
		return new FreezeEvent.Enable(player, cause);
    }
	
	public static FreezeEvent.Disable createFreezeEventDisable(EPlayer player, Cause cause) {
		return new FreezeEvent.Disable(player, cause);
    }

	/*
	 * God
	 */
	
	public static GodEvent.Enable createGodEventEnable(EPlayer player, Cause cause) {
		return new GodEvent.Enable(player, cause);
    }
	
	public static GodEvent.Disable createGodEventDisable(EPlayer player, Cause cause) {
		return new GodEvent.Disable(player, cause);
    }
	
	/*
	 * Home
	 */
	
	public static HomeEvent.Add createHomeEventAdd(EPlayer player, String name, Transform<World> location, Cause cause) {
		return new HomeEvent.Add(player, name, location, cause);
    }
	
	public static HomeEvent.Move createHomeEventMove(EPlayer player, String name, Optional<Transform<World>> before, Transform<World> after, Cause cause) {
		return new HomeEvent.Move(player, name, before, after, cause);
    }
	
	public static HomeEvent.Remove createHomeEventRemove(EPlayer player, String name, Optional<Transform<World>> location, Cause cause) {
		return new HomeEvent.Remove(player, name, location, cause);
    }
	
	/*
	 * Ignore
	 */
	
	public static IgnoreEvent.Add createIgnoreEventAdd(EPlayer player, UUID uuid, Cause cause) {
		return new IgnoreEvent.Add(player, uuid, cause);
    }
	
	public static IgnoreEvent.Remove createIgnoreEventRemove(EPlayer player, UUID uuid, Cause cause) {
		return new IgnoreEvent.Remove(player, uuid, cause);
    }
	
	/*
	 * Jail
	 */
	
	public static JailEvent.Enable createJailEventEnable(EUser user, SanctionJail sanction, Jail jail, CommandSource source, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("user", user);
        values.put("value", true);
        values.put("jail", jail);
        values.put("reason", sanction.getReason());
        values.put("creationDate", sanction.getCreationDate());
        values.put("indefinite", sanction.isIndefinite());
        values.put("expirationDate", sanction.getExpirationDate());
        values.put("source", sanction.getSource());
        values.put("commandSource", source);
        values.put("cause", cause);

        if(user instanceof EPlayer) {
            values.put("player", Optional.of((EPlayer) user));
        } else {
            values.put("player", Optional.empty());
        }
        return SpongeEventFactoryUtils.createEventImpl(JailEvent.Enable.class, values);
	}

	public static JailEvent.Disable createJailEventDisable(EUser user, SanctionJail jail, Cause cause, Server server) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("user", user);
        values.put("value", false);
        values.put("jail", jail.getJail());
        values.put("jailName", jail.getJailName());
        values.put("reason", jail.getReason());
        values.put("creationDate", jail.getCreationDate());
        values.put("indefinite", jail.isIndefinite());
        values.put("expirationDate", jail.getExpirationDate());
        values.put("source", jail.getSource());
        values.put("commandSource", jail.getSourceName(server));
        values.put("pardon", jail.isPardon());
        values.put("pardonReason", jail.getPardonReason());
        values.put("pardonDate", jail.getPardonDate());
        values.put("pardonSource", jail.getPardonSource());
        values.put("pardonCommandSource", jail.getPardonSourceName(server));
        values.put("cause", cause);

        if(user instanceof EPlayer) {
            values.put("player", Optional.of((EPlayer) user));
        } else {
            values.put("player", Optional.empty());
        }
        return SpongeEventFactoryUtils.createEventImpl(JailEvent.Disable.class, values);
    }
	
	/*
	 * Mail
	 */
	
	public static MailEvent.Send createMailEventSend(EPlayer player, CommandSource source, String message, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("to", source);
        values.put("action", MailEvent.Action.SEND);
        values.put("cause", cause);
        values.put("message", message);
        return SpongeEventFactoryUtils.createEventImpl(MailEvent.Send.class, values);
    }
	
	public static MailEvent.Read createMailEventRead(EPlayer player, Mail mail, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("mail", mail);
        values.put("action", MailEvent.Action.READ);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MailEvent.Read.class, values);
    }
	
	public static MailEvent.Remove createMailEventRemove(EPlayer player, Mail mail, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("mail", mail);
        values.put("action", MailEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MailEvent.Remove.class, values);
    }
	
	public static MailEvent.Receive createMailEventReceive(EPlayer player, Mail mail, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("mail", mail);
        values.put("action", MailEvent.Action.RECEIVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MailEvent.Receive.class, values);
    }
	
	/*
	 * MojangCheck
	 */
	
	public static MojangCheckEvent createMojangCheckEvent(MojangServer server, Color beforeColor, Color afterColor, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("server", server);
        values.put("beforeColor", beforeColor);
        values.put("afterColor", afterColor);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MojangCheckEvent.class, values);
    }
	
	/*
	 * Mute
	 */
	
	public static MuteEvent.Enable createMuteEventEnable(EUser user, SanctionMute sanction, CommandSource source, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("user", user);
        values.put("value", true);
        values.put("reason", sanction.getReason());
        values.put("creationDate", sanction.getCreationDate());
        values.put("indefinite", sanction.isIndefinite());
        values.put("expirationDate", sanction.getExpirationDate());
        values.put("source", sanction.getSource());
        values.put("commandSource", source);
        values.put("cause", cause);
        
        if(user instanceof EPlayer) {
            values.put("player", Optional.of((EPlayer) user));
        } else {
            values.put("player", Optional.empty());
        }
        return SpongeEventFactoryUtils.createEventImpl(MuteEvent.Enable.class, values);
    }
	
	public static MuteEvent.Disable createMuteEventDisable(EUser user, SanctionMute sanction, Cause cause, Server server) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("user", user);
        values.put("value", false);
        values.put("reason", sanction.getReason());
        values.put("creationDate", sanction.getCreationDate());
        values.put("indefinite", sanction.isIndefinite());
        values.put("expirationDate", sanction.getExpirationDate());
        values.put("source", sanction.getSource());
        values.put("commandSource", sanction.getSourceName(server));
        values.put("pardon", sanction.isPardon());
        values.put("pardonReason", sanction.getPardonReason());
        values.put("pardonDate", sanction.getPardonDate());
        values.put("pardonSource", sanction.getPardonSource());
        values.put("pardonCommandSource", sanction.getPardonSourceName(server));
        values.put("cause", cause);
        
        if(user instanceof EPlayer) {
            values.put("player", Optional.of((EPlayer) user));
        } else {
            values.put("player", Optional.empty());
        }
        return SpongeEventFactoryUtils.createEventImpl(MuteEvent.Disable.class, values);
    }
	
	/*
	 * NameTag
	 */
	
	public static NameTagEvent.Add createNameTagEventAdd(EPlayer player, String identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("action", NameTagEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(NameTagEvent.Add.class, values);
    }
	
	public static NameTagEvent.Replace createNameTagEventReplace(EPlayer player, String identifier, String new_identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("new_identifier", new_identifier);
        values.put("action", NameTagEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(NameTagEvent.Replace.class, values);
    }
	
	public static NameTagEvent.Remove createNameTagEventRemove(EPlayer player, String identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("action", NameTagEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(NameTagEvent.Remove.class, values);
    }
	
	/*
	 * PermSystem
	 */
	
	public static PermSystemEvent.Reload createPermSystemEventReloaded(Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("action", PermSystemEvent.Action.RELOADED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermSystemEvent.Reload.class, values);
    }
	
	public static PermSystemEvent.Default createPermSystemEventDefault(Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("action", PermSystemEvent.Action.DEFAULT_GROUP_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermSystemEvent.Default.class, values);
    }
	
	/*
	 * PermUserEvent
	 */
	
	public static PermUserEvent.Add createPermUserEventAdd(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_ADDED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.Add.class, values);
    }
	
	public static PermUserEvent.Remove createPermUserEventRemove(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_REMOVED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.Remove.class, values);
    }
	
	public static PermUserEvent.Permission createPermUserEventPermission(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_PERMISSION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.Permission.class, values);
    }
	
	public static PermUserEvent.Option createPermUserEventOption(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_OPTION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.Option.class, values);
    }
	
	public static PermUserEvent.Group createPermUserEventGroup(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_GROUP_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.Group.class, values);
    }
	
	public static PermUserEvent.SubGroup createPermUserEventSubGroup(Subject subject, Optional<EPlayer> player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("player", player);
        values.put("action", PermUserEvent.Action.USER_SUBGROUP_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermUserEvent.SubGroup.class, values);
    }
	
	/*
	 * PermGroupEvent
	 */
	
	public static PermGroupEvent.Add createPermGroupEventAdd(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermGroupEvent.Action.GROUP_ADDED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermGroupEvent.Add.class, values);
    }
	
	public static PermGroupEvent.Remove createPermGroupEventRemove(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermGroupEvent.Action.GROUP_REMOVED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermGroupEvent.Remove.class, values);
    }
	
	public static PermGroupEvent.Permission createPermGroupEventPermission(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermGroupEvent.Action.GROUP_PERMISSION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermGroupEvent.Permission.class, values);
    }
	
	public static PermGroupEvent.Inheritance createPermGroupEventInheritance(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermGroupEvent.Action.GROUP_INHERITANCE_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermGroupEvent.Inheritance.class, values);
    }
	
	public static PermGroupEvent.Option createPermGroupEventOption(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermGroupEvent.Action.GROUP_OPTION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermGroupEvent.Option.class, values);
    }
	
	/*
	 * PermOtherEvent
	 */
	
	public static PermOtherEvent.Add createPermOtherEventAdd(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermOtherEvent.Action.OTHER_ADDED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermOtherEvent.Add.class, values);
    }
	
	public static PermOtherEvent.Remove createPermOtherEventRemove(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermOtherEvent.Action.OTHER_REMOVED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermOtherEvent.Remove.class, values);
    }
	
	public static PermOtherEvent.Permission createPermOtherEventPermission(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermOtherEvent.Action.OTHER_PERMISSION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermOtherEvent.Permission.class, values);
    }
	
	public static PermOtherEvent.Inheritance createPermOtherEventInheritance(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermOtherEvent.Action.OTHER_INHERITANCE_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermOtherEvent.Inheritance.class, values);
    }
	
	public static PermOtherEvent.Option createPermOtherEventOption(Subject subject, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("subject", subject);
        values.put("action", PermOtherEvent.Action.OTHER_OPTION_CHANGED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(PermOtherEvent.Option.class, values);
    }
	
	/*
	 * ScoreBoard
	 */
	
	public static ScoreBoardEvent.Add createScoreBoardEventAdd(EPlayer player, Objective objective, DisplaySlot display, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("objective", objective);
        values.put("identifier", objective.getName());
        values.put("displaySlot", display);
        values.put("action", ScoreBoardEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ScoreBoardEvent.Add.class, values);
    }
	
	public static ScoreBoardEvent.Replace createScoreBoardEventReplace(EPlayer player, Objective objective, Objective new_objective, DisplaySlot display, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("objective", objective);
        values.put("identifier", objective.getName());
        values.put("newObjective", new_objective);
        values.put("newIdentifier", new_objective.getName());
        values.put("displaySlot", display);
        values.put("action", ScoreBoardEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ScoreBoardEvent.Replace.class, values);
    }
	
	public static ScoreBoardEvent.Remove createScoreBoardEventRemove(EPlayer player, Objective objective, DisplaySlot display, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("objective", objective);
        values.put("identifier", objective.getName());
        values.put("displaySlot", display);
        values.put("action", ScoreBoardEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ScoreBoardEvent.Remove.class, values);
    }
	
	/*
	 * StatsReloadEvent
	 */
	
	public static StatsSystemEvent.Reload createStatsSystemEventReload(Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("action", StatsSystemEvent.Action.RELOADED);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(StatsSystemEvent.Reload.class, values);
    }
	
	/*
	 * StatsUserEvent
	 */
	
	public static StatsUserEvent.Death createStatsUserEventDeath(EPlayer victim, Long time, DamageType damage, Optional<Entity> killer, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("victim", victim);
        values.put("time", time);
        values.put("damageType", damage);
        values.put("type", StatsUserEvent.Type.DEATH);
        values.put("killer", killer);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(StatsUserEvent.Death.class, values);
    }
	
	public static StatsUserEvent.Kill createStatsUserEventKill(EPlayer victim, Long time, DamageType damage, EPlayer killer, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("victim", victim);
        values.put("time", time);
        values.put("damageType", damage);
        values.put("type", StatsUserEvent.Type.KILL);
        values.put("killer", killer);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(StatsUserEvent.Kill.class, values);
    }
	
	/*
	 * TabList
	 */
	
	public static TabListEvent.Add createTabListEventAdd(EPlayer player, String identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("action", TabListEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TabListEvent.Add.class, values);
    }
	
	public static TabListEvent.Replace createTabListEventReplace(EPlayer player, String identifier, String new_identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("new_identifier", new_identifier);
        values.put("action", TabListEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TabListEvent.Replace.class, values);
    }
	
	public static TabListEvent.Remove createTabListEventRemove(EPlayer player, String identifier, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", identifier);
        values.put("action", TabListEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TabListEvent.Remove.class, values);
    }
	
	/*
	 * TitleEvent
	 */
	
	public static TitleEvent.Add createTitleEventEventAdd(EPlayer player, TitleMessage title, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", title.getIdentifier());
        values.put("time", title.getTime());
        values.put("title", title.getTitle());
        values.put("action", TitleEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TitleEvent.Add.class, values);
    }
	
	public static TitleEvent.Replace createTitleEventEventReplace(EPlayer player, TitleMessage title, TitleMessage new_title, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", title.getIdentifier());
        values.put("time", title.getTime());
        values.put("title", title.getTitle());
        values.put("newIdentifier", new_title.getIdentifier());
        values.put("newTime", new_title.getTime());
        values.put("newTitle", new_title.getTitle());
        values.put("action", TitleEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TitleEvent.Replace.class, values);
    }
	
	public static TitleEvent.Remove createTitleEventEventRemove(EPlayer player, TitleMessage title, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", title.getIdentifier());
        values.put("time", title.getTime());
        values.put("title", title.getTitle());
        values.put("action", TitleEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TitleEvent.Remove.class, values);
    }
	
	/*
	 * Toggle
	 */
	
	public static ToggleEvent.Enable createToggleEventEnable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", true);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ToggleEvent.Enable.class, values);
    }
	
	public static ToggleEvent.Disable createToggleEventDisable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", false);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ToggleEvent.Disable.class, values);
    }
	
	/*
	 * Vanish
	 */
	
	public static VanishEvent.Enable createVanishEventEnable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", true);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(VanishEvent.Enable.class, values);
    }
	
	public static VanishEvent.Disable createVanishEventDisable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", false);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(VanishEvent.Disable.class, values);
    }

	/*
	 * WorldGuard
	 */
	
	public static MoveRegionEvent.Pre createMoveRegionEventPre(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("fromLocation", fromLocation);
        values.put("toLocation", toLocation);
        values.put("fromRegions", fromRegions);
        values.put("toRegions", toRegions);
        values.put("enterRegions", enterRegions);
        values.put("exitRegions", exitRegions);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MoveRegionEvent.Pre.class, values);
    }
	
	public static MoveRegionEvent.Pre.Cancellable createMoveRegionEventPreCancellable(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("fromLocation", fromLocation);
        values.put("toLocation", toLocation);
        values.put("fromRegions", fromRegions);
        values.put("toRegions", toRegions);
        values.put("enterRegions", enterRegions);
        values.put("exitRegions", exitRegions);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MoveRegionEvent.Pre.Cancellable.class, values);
    }
	
	public static MoveRegionEvent createMoveRegionEventPost(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("fromLocation", fromLocation);
        values.put("toLocation", toLocation);
        values.put("fromRegions", fromRegions);
        values.put("toRegions", toRegions);
        values.put("enterRegions", enterRegions);
        values.put("exitRegions", exitRegions);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MoveRegionEvent.Post.class, values);
    }
}
