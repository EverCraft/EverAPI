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

import java.util.List;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import fr.evercraft.everapi.server.player.EPlayer;

public abstract class CommandEvent extends AbstractEvent {
	public static enum Action {
    	SEND,
    	RESULT;
    }

	private final EPlayer player;
	private final String command;
	private final String arg;
	private final List<String> args;
	private final Cause cause;
	
	public CommandEvent(EPlayer player, String command, String arg, List<String> args, Cause cause) {
		super();
		this.player = player;
		this.command = command;
		this.arg = arg;
		this.args = args;
		this.cause = cause;
	}

	public abstract Action getAction();
	
    public EPlayer getPlayer() {
    	return this.player;
    }
    
    public String getCommand() {
    	return this.command;
    }
    
    public String getArg() {
    	return this.arg;
    }
    
    public List<String> getArgs() {
    	return this.args;
    }
    
    @Override
	public Cause getCause() {
		return this.cause;
	}
    
    public static class Send extends CommandEvent implements Cancellable {
    	private boolean cancelled;

		public Send(EPlayer player, String command, String arg, List<String> args, Cause cause) {
			super(player, command, arg, args, cause);
			this.cancelled = false;
		}

		@Override
		public boolean isCancelled() {
			return this.cancelled;
		}

		@Override
		public void setCancelled(boolean cancel) {
			this.cancelled = cancel;
		}

		@Override
		public Action getAction() {
			return Action.SEND;
		}
	}
    
    public static class Result extends CommandEvent {
    	private final boolean result;
    	
    	public Result(EPlayer player, String command, String arg, List<String> args, boolean result, Cause cause) {
			super(player, command, arg, args, cause);
			this.result = result;
		}

		public boolean getResult() {
    		return this.result;
    	}
    	
    	@Override
		public Action getAction() {
			return Action.RESULT;
		}
    }
}
