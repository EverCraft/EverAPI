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
package fr.evercraft.everapi.services.cooldown.event;

import java.util.List;

import org.spongepowered.api.event.cause.Cause;

import com.google.common.collect.ImmutableList;

import fr.evercraft.everapi.event.CommandEvent;
import fr.evercraft.everapi.server.player.EPlayer;

public abstract class ECommandEvent implements CommandEvent {
	
	private final Action action;
	private final EPlayer player;
    private final String command;
	private final List<String> args;
	private final Cause cause;

    public ECommandEvent(final Action action, final EPlayer player, final String command, final List<String> args, final Cause cause) {
    	this.action = action;
    	this.player = player;    	
    	this.command = command;
        this.args = ImmutableList.copyOf(args);
        this.cause = cause;
    }

    @Override
    public EPlayer getPlayer() {
        return this.player;
    }
    
    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
	public String getCommand() {
		return this.command;
	}

	@Override
	public List<String> getArgs() {
		return this.args;
	}
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
}
