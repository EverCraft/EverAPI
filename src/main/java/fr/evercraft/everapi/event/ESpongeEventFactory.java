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

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.SpongeEventFactory;
import org.spongepowered.api.event.SpongeEventFactoryUtils;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlot;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.world.World;

import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.actionbar.ActionBarMessage;
import fr.evercraft.everapi.services.bossbar.EBossBar;
import fr.evercraft.everapi.services.essentials.Mail;
import fr.evercraft.everapi.services.mojang.check.MojangServer;
import fr.evercraft.everapi.services.mojang.check.MojangServer.Color;
import fr.evercraft.everapi.services.title.TitleMessage;

public class ESpongeEventFactory extends SpongeEventFactory {
	
	/*
	 * ActionBar
	 */
	
	public static ActionBarEvent.Add createActionBarEventAdd(EPlayer player, ActionBarMessage actionBar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", actionBar.getIdentifier());
        values.put("time", actionBar.getTime());
        values.put("message", actionBar.getMessage());
        values.put("action", ActionBarEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ActionBarEvent.Add.class, values);
    }
	
	public static ActionBarEvent.Remove createActionBarEventRemove(EPlayer player, ActionBarMessage actionBar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", actionBar.getIdentifier());
        values.put("time", actionBar.getTime());
        values.put("message", actionBar.getMessage());
        values.put("action", ActionBarEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ActionBarEvent.Remove.class, values);
    }
	
	public static ActionBarEvent.Replace createActionBarEventReplace(EPlayer player, ActionBarMessage actionBar, ActionBarMessage new_actionBar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", actionBar.getIdentifier());
        values.put("time", actionBar.getTime());
        values.put("message", actionBar.getMessage());
        values.put("newIdentifier", new_actionBar.getIdentifier());
        values.put("newTime", new_actionBar.getTime());
        values.put("newMessage", new_actionBar.getMessage());
        values.put("action", ActionBarEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ActionBarEvent.Replace.class, values);
    }
	
	/*
	 * Afk
	 */
	
	public static AfkEvent.Enable createAfkEventEnable(EPlayer player, AfkEvent.Action action, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", true);
        values.put("action", action);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(AfkEvent.Enable.class, values);
    }
	
	public static AfkEvent.Disable createAfkEventDisable(EPlayer player, AfkEvent.Action action, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", false);
        values.put("action", action);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(AfkEvent.Disable.class, values);
    }
	
	/*
	 * Back
	 */
	
	public static BackEvent createBackEvent(EPlayer player, Optional<Transform<World>> before, Optional<Transform<World>> after, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("beforeLocation", before);
        values.put("afterLocation", after);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(BackEvent.class, values);
    }
	
	/*
	 * BossBar
	 */
	
	public static BossBarEvent.Add createBossBarEventAdd(EPlayer player, EBossBar bossbar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", bossbar.getIdentifier());
        values.put("serverBossBar", bossbar.getServerBossBar());
        values.put("action", BossBarEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(BossBarEvent.Add.class, values);
    }
	
	public static BossBarEvent.Replace createBossBarEventReplace(EPlayer player, EBossBar bossbar, EBossBar new_bossbar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", bossbar.getIdentifier());
        values.put("serverBossBar", bossbar.getServerBossBar());
        values.put("newIdentifier", new_bossbar.getIdentifier());
        values.put("newServerBossBar", new_bossbar.getServerBossBar());
        values.put("action", BossBarEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(BossBarEvent.Replace.class, values);
    }
	
	public static BossBarEvent.Remove createBossBarEventRemove(EPlayer player, EBossBar bossbar, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", bossbar.getIdentifier());
        values.put("serverBossBar", bossbar.getServerBossBar());
        values.put("action", BossBarEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(BossBarEvent.Remove.class, values);
    }
	
	/*
	 * Command
	 */
	
	public static CommandEvent.Send createCommandEventSend(EPlayer player, String command, String arg, final List<String> args, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("command", command);
        values.put("arg", arg);
        values.put("args", args);
        values.put("action", CommandEvent.Action.SEND);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(CommandEvent.Send.class, values);
    }
	
	public static CommandEvent.Result createCommandEventResult(EPlayer player, String command, String arg, final List<String> args, boolean result, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("command", command);
        values.put("arg", arg);
        values.put("args", args);
        values.put("result", result);
        values.put("action", CommandEvent.Action.RESULT);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(CommandEvent.Result.class, values);
    }
	
	/*
	 * Freeze
	 */
	
	public static FreezeEvent.Enable createFreezeEventEnable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", true);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(FreezeEvent.Enable.class, values);
    }
	
	public static FreezeEvent.Disable createFreezeEventDisable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", false);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(FreezeEvent.Disable.class, values);
    }

	/*
	 * God
	 */
	
	public static GodEvent.Enable createGodEventEnable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", true);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(GodEvent.Enable.class, values);
    }
	
	public static GodEvent.Disable createGodEventDisable(EPlayer player, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("value", false);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(GodEvent.Disable.class, values);
    }
	
	/*
	 * Home
	 */
	
	public static HomeEvent.Add createHomeEventAdd(EPlayer player, String name, Transform<World> location, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("name", name);
        values.put("location", location);
        values.put("action", HomeEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(HomeEvent.Add.class, values);
    }
	
	public static HomeEvent.Move createHomeEventMove(EPlayer player, String name, Optional<Transform<World>> before, Transform<World> after, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("name", name);
        values.put("beforeLocation", before);
        values.put("afterLocation", after);
        values.put("action", HomeEvent.Action.MOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(HomeEvent.Move.class, values);
    }
	
	public static HomeEvent.Remove createHomeEventRemove(EPlayer player, String name, Optional<Transform<World>> location, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("name", name);
        values.put("location", location);
        values.put("action", HomeEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(HomeEvent.Remove.class, values);
    }
	
	/*
	 * Ignore
	 */
	
	public static IgnoreEvent.Add createIgnoreEventAdd(EPlayer player, UUID uuid, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("ignore", uuid);
        values.put("action", IgnoreEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(IgnoreEvent.Add.class, values);
    }
	
	public static IgnoreEvent.Remove createIgnoreEventRemove(EPlayer player, UUID uuid, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("ignore", uuid);
        values.put("action", IgnoreEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(IgnoreEvent.Remove.class, values);
    }
	
	/*
	 * Mail
	 */
	
	public static MailEvent.Add createMailEventAdd(EPlayer player, CommandSource source, String message, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("to", source);
        values.put("action", MailEvent.Action.ADD);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(MailEvent.Add.class, values);
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
	 * ScoreBoard
	 */
	
	public static ScoreBoardEvent.Add createScoreBoardEventAdd(EPlayer player, Objective objective, DisplaySlot display, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("objective", objective);
        values.put("identifier", objective.getName());
        values.put("display", display);
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
        values.put("display", display);
        values.put("action", ScoreBoardEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ScoreBoardEvent.Replace.class, values);
    }
	
	public static ScoreBoardEvent.Remove createScoreBoardEventRemove(EPlayer player, Objective objective, DisplaySlot display, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("objective", objective);
        values.put("identifier", objective.getName());
        values.put("display", display);
        values.put("action", ScoreBoardEvent.Action.REMOVE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(ScoreBoardEvent.Remove.class, values);
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
        values.put("action", TabListEvent.Action.REPLACE);
        values.put("cause", cause);
        return SpongeEventFactoryUtils.createEventImpl(TitleEvent.Replace.class, values);
    }
	
	public static TitleEvent.Remove createTitleEventEventRemove(EPlayer player, TitleMessage title, Cause cause) {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("player", player);
        values.put("identifier", title.getIdentifier());
        values.put("time", title.getTime());
        values.put("title", title.getTitle());
        values.put("action", TabListEvent.Action.REMOVE);
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

}
