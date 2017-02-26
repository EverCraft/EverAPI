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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;
import fr.evercraft.everapi.services.bossbar.EBossBar;
import fr.evercraft.everapi.services.essentials.Mail;
import fr.evercraft.everapi.services.jail.Jail;
import fr.evercraft.everapi.services.mojang.check.MojangServer;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Status;
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
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new BanEvent.Enable(user, player, reason, creationDate, expirationDate, sanction, commandSource, cause);
    }
	
	public static BanEvent.Disable createBanEventDisable(EUser user, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanProfile sanction, 
			Text pardonReason, Long pardonDate, CommandSource pardonCommandSource, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new BanEvent.Disable(user, player, reason, creationDate, expirationDate, sanction, source, Optional.of(pardonReason), Optional.of(pardonDate), Optional.of(pardonCommandSource), cause);
    }
	
	public static BanEvent.Disable createBanEventDisable(EUser user, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanProfile sanction, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new BanEvent.Disable(user, player, reason, creationDate, expirationDate, sanction, source, Optional.empty(), Optional.empty(), Optional.empty(), cause);
    }
	
	/*
	 * BanIp
	 */
	
	public static BanIpEvent.Enable createBanIpEventEnable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, CommandSource commandSource, SanctionBanIp sanction, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new BanIpEvent.Enable(user, player, address, reason, creationDate, expirationDate, sanction, commandSource, cause);
    }
	
	public static BanIpEvent.Disable createBanIpEventDisable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanIp sanction, 
			Text pardonReason, Long pardonDate, CommandSource pardonCommandSource, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new BanIpEvent.Disable(user, player, address, reason, creationDate, expirationDate, sanction, source, Optional.of(pardonReason), Optional.of(pardonDate), Optional.of(pardonCommandSource), cause);
    }
	
	public static BanIpEvent.Disable createBanIpEventDisable(EUser user, InetAddress address, Text reason, long creationDate, Optional<Long> expirationDate, String source, SanctionBanIp sanction, Cause cause) {
		
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
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
        Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
        
        return new JailEvent.Enable(user, player, sanction.getReason(), sanction.getCreationDate(), 
        		sanction.getExpirationDate(), sanction, jail, source, cause);
	}

	public static JailEvent.Disable createJailEventDisable(EUser user, SanctionJail sanction, Optional<CommandSource> pardonSource, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
        
        return new JailEvent.Disable(user, player, sanction.getReason(), sanction.getCreationDate(), 
        		sanction.getExpirationDate(), sanction, sanction.getJail(), sanction.getJailName(), 
        		sanction.getSource(), sanction.getPardonReason(), sanction.getPardonDate(), pardonSource, cause);
	}
	
	/*
	 * Mail
	 */
	
	public static MailEvent.Send createMailEventSend(EUser user, CommandSource to, String message, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
        return new MailEvent.Send(user, player , to, message, cause);
    }
	
	public static MailEvent.Read createMailEventRead(EUser user, Mail mail, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
        return new MailEvent.Read(user, player , mail, cause);
    }
	
	public static MailEvent.Remove createMailEventRemove(EUser user, Mail mail, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
        return new MailEvent.Remove(user, player , mail, cause);
    }
	
	public static MailEvent.Receive createMailEventReceive(EUser user, Mail mail, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
        return new MailEvent.Receive(user, player , mail, cause);
    }
	
	/*
	 * MojangCheck
	 */
	
	public static MojangCheckEvent createMojangCheckEvent(MojangServer server, Status beforeStatus, Status afterStatus, Cause cause) {
        return new MojangCheckEvent(server, beforeStatus, afterStatus, cause);
    }
	
	/*
	 * Mute
	 */
	
	public static MuteEvent.Enable createMuteEventEnable(EUser user, SanctionMute sanction, CommandSource source, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new MuteEvent.Enable(user, player, sanction.getReason(), sanction.getCreationDate(), sanction.getExpirationDate(), sanction, source, cause);
    }
	
	public static MuteEvent.Disable createMuteEventDisable(EUser user, SanctionMute sanction, Optional<CommandSource> pardonSource, Cause cause) {
		Optional<EPlayer> player = (user instanceof EPlayer) ? Optional.of((EPlayer) user) : Optional.empty();
		
		return new MuteEvent.Disable(user, player, sanction.getReason(), sanction.getCreationDate(), sanction.getExpirationDate(), sanction, sanction.getSource(), sanction.getPardonReason(), sanction.getPardonDate(), pardonSource, cause);
    }
	
	/*
	 * NameTag
	 */
	
	public static NameTagEvent.Add createNameTagEventAdd(EPlayer player, String identifier, Cause cause) {
		return new NameTagEvent.Add(player , identifier, cause);
    }
	
	public static NameTagEvent.Replace createNameTagEventReplace(EPlayer player, String identifier, String newIdentifier, Cause cause) {
		return new NameTagEvent.Replace(player , identifier, newIdentifier, cause);
    }
	
	public static NameTagEvent.Remove createNameTagEventRemove(EPlayer player, String identifier, Cause cause) {
		return new NameTagEvent.Remove(player , identifier, cause);
    }
	
	/*
	 * PermSystem
	 */
	
	public static PermSystemEvent.Reload createPermSystemEventReloaded(Cause cause) {
		return new PermSystemEvent.Reload(cause);
    }
	
	public static PermSystemEvent.Default createPermSystemEventDefault(Cause cause) {
		return new PermSystemEvent.Default(cause);
    }
	
	/*
	 * PermUserEvent
	 */
	
	public static PermUserEvent.Add createPermUserEventAdd(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.Add(subject, player, cause);
    }
	
	public static PermUserEvent.Remove createPermUserEventRemove(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.Remove(subject, player, cause);
    }
	
	public static PermUserEvent.Permission createPermUserEventPermission(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.Permission(subject, player, cause);
    }
	
	public static PermUserEvent.Option createPermUserEventOption(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.Option(subject, player, cause);
    }
	
	public static PermUserEvent.Group createPermUserEventGroup(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.Group(subject, player, cause);
    }
	
	public static PermUserEvent.SubGroup createPermUserEventSubGroup(Subject subject, Optional<EPlayer> player, Cause cause) {
		return new PermUserEvent.SubGroup(subject, player, cause);
    }
	
	/*
	 * PermGroupEvent
	 */
	
	public static PermGroupEvent.Add createPermGroupEventAdd(Subject subject, Cause cause) {
		return new PermGroupEvent.Add(subject, cause);
    }
	
	public static PermGroupEvent.Remove createPermGroupEventRemove(Subject subject, Cause cause) {
		return new PermGroupEvent.Remove(subject, cause);
    }
	
	public static PermGroupEvent.Permission createPermGroupEventPermission(Subject subject, Cause cause) {
		return new PermGroupEvent.Permission(subject, cause);
    }
	
	public static PermGroupEvent.Inheritance createPermGroupEventInheritance(Subject subject, Cause cause) {
		return new PermGroupEvent.Inheritance(subject, cause);
    }
	
	public static PermGroupEvent.Option createPermGroupEventOption(Subject subject, Cause cause) {
		return new PermGroupEvent.Option(subject, cause);
    }
	
	/*
	 * PermOtherEvent
	 */
	
	public static PermOtherEvent.Add createPermOtherEventAdd(Subject subject, Cause cause) {
		return new PermOtherEvent.Add(subject, cause);
    }
	
	public static PermOtherEvent.Remove createPermOtherEventRemove(Subject subject, Cause cause) {
		return new PermOtherEvent.Remove(subject, cause);
    }
	
	public static PermOtherEvent.Permission createPermOtherEventPermission(Subject subject, Cause cause) {
		return new PermOtherEvent.Permission(subject, cause);
    }
	
	public static PermOtherEvent.Inheritance createPermOtherEventInheritance(Subject subject, Cause cause) {
		return new PermOtherEvent.Inheritance(subject, cause);
    }
	
	public static PermOtherEvent.Option createPermOtherEventOption(Subject subject, Cause cause) {
		return new PermOtherEvent.Option(subject, cause);
    }
	
	/*
	 * ScoreBoard
	 */
	
	public static ScoreBoardEvent.Add createScoreBoardEventAdd(EPlayer player, Objective objective, DisplaySlot displaySlot, Cause cause) {
		return new ScoreBoardEvent.Add(player, objective, objective.getName(), displaySlot, cause);
    }
	
	public static ScoreBoardEvent.Replace createScoreBoardEventReplace(EPlayer player, Objective objective, Objective newObjective, DisplaySlot displaySlot, Cause cause) {
		return new ScoreBoardEvent.Replace(player, objective, objective.getName(), newObjective, newObjective.getName(), displaySlot, cause);
    }
	
	public static ScoreBoardEvent.Remove createScoreBoardEventRemove(EPlayer player, Objective objective, DisplaySlot displaySlot, Cause cause) {
		return new ScoreBoardEvent.Remove(player, objective, objective.getName(), displaySlot, cause);
    }
	
	/*
	 * StatsReloadEvent
	 */
	
	public static StatsSystemEvent.Reload createStatsSystemEventReload(Cause cause) {
		return new StatsSystemEvent.Reload(cause);
    }
	
	/*
	 * StatsUserEvent
	 */
	
	public static StatsUserEvent.Death createStatsUserEventDeath(EPlayer victim, Long time, DamageType damage, Optional<Entity> killer, Cause cause) {
		return new StatsUserEvent.Death(victim, time, damage, killer, cause);
    }
	
	public static StatsUserEvent.Kill createStatsUserEventKill(EPlayer victim, Long time, DamageType damage, EPlayer killer, Cause cause) {
		return new StatsUserEvent.Kill(victim, time, damage, killer, cause);
    }
	
	/*
	 * TabList
	 */
	
	public static TabListEvent.Add createTabListEventAdd(EPlayer player, String identifier, Cause cause) {
		return new TabListEvent.Add(player, identifier, cause);
    }
	
	public static TabListEvent.Replace createTabListEventReplace(EPlayer player, String identifier, String newIdentifier, Cause cause) {
		return new TabListEvent.Replace(player, identifier, newIdentifier, cause);
    }
	
	public static TabListEvent.Remove createTabListEventRemove(EPlayer player, String identifier, Cause cause) {
		return new TabListEvent.Remove(player, identifier, cause);
    }
	
	/*
	 * TitleEvent
	 */
	
	public static TitleEvent.Add createTitleEventEventAdd(EPlayer player, TitleMessage title, Cause cause) {
		return new TitleEvent.Add(player, title.getIdentifier(), title.getTitle(), title.getTime(), cause);
    }
	
	public static TitleEvent.Replace createTitleEventEventReplace(EPlayer player, TitleMessage title, TitleMessage new_title, Cause cause) {
		return new TitleEvent.Replace(player, title.getIdentifier(), title.getTitle(), title.getTime(),
				title.getIdentifier(), title.getTitle(), title.getTime(), cause);
    }
	
	public static TitleEvent.Remove createTitleEventEventRemove(EPlayer player, TitleMessage title, Cause cause) {
		return new TitleEvent.Remove(player, title.getIdentifier(), title.getTitle(), title.getTime(), cause);
    }
	
	/*
	 * Toggle
	 */
	
	public static ToggleEvent.Enable createToggleEventEnable(EPlayer player, Cause cause) {
		return new ToggleEvent.Enable(player, cause);
    }
	
	public static ToggleEvent.Disable createToggleEventDisable(EPlayer player, Cause cause) {
		return new ToggleEvent.Disable(player, cause);
    }
	
	/*
	 * Vanish
	 */
	
	public static VanishEvent.Enable createVanishEventEnable(EPlayer player, Cause cause) {
		return new VanishEvent.Enable(player, cause);
    }
	
	public static VanishEvent.Disable createVanishEventDisable(EPlayer player, Cause cause) {
		return new VanishEvent.Disable(player, cause);
    }

	/*
	 * WorldGuard
	 */
	
	public static MoveRegionEvent.Pre createMoveRegionEventPre(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
		
		return new MoveRegionEvent.Pre(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);
    }
	
	public static MoveRegionEvent.Pre.Cancellable createMoveRegionEventPreCancellable(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
		
		return new MoveRegionEvent.Pre.Cancellable(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);
    }
	
	public static MoveRegionEvent.Post createMoveRegionEventPost(EPlayer player, 
			Location<World> fromLocation, Location<World> toLocation, 
			SetProtectedRegion fromRegions, SetProtectedRegion toRegions,
			SetProtectedRegion enterRegions, SetProtectedRegion exitRegions, Cause cause) {
		
		return new MoveRegionEvent.Post(player, fromLocation, toLocation, fromRegions, toRegions, enterRegions, exitRegions, cause);
    }
}
