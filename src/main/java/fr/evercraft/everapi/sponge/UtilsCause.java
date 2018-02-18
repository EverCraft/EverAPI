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
package fr.evercraft.everapi.sponge;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import fr.evercraft.everapi.plugin.EChat;

public class UtilsCause {
	
	public static final String PLACE_EVENT = "PlaceEvent";
	public static Boolean debug = false;
	
	public static void debug(final Cause cause, final String name) {
		if (!UtilsCause.debug) return;
		
		List<Text> list = new ArrayList<Text>();
		cause.getContext().asMap().forEach((key, value) -> {
			list.add(Text.builder(key.getId())
					.onHover(TextActions.showText(Text.of(EChat.fixLength(value.toString(), 254))))
					.onClick(TextActions.suggestCommand(EChat.fixLength(value.toString(), 254)))
					.build());
		});
		Sponge.getGame().getServer().getBroadcastChannel().send(Text.of(name + " : ").concat(Text.joinWith(Text.of(", "), list)));
	}
	
	public static String getContextKeys(final Cause cause) {
		return cause.getContext().keySet().stream().map(key -> key.getId()).reduce((k1, k2) -> k1 + ", " + k2).orElse("");
	}

}
