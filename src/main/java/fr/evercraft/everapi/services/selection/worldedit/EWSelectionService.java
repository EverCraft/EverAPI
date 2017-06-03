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
package fr.evercraft.everapi.services.selection.worldedit;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.sponge.SpongeWorldEdit;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.services.selection.SelectionService;
import fr.evercraft.everapi.services.selection.SubjectSelection;

public class EWSelectionService implements SelectionService {
	
	private final EverAPI plugin;
	private SpongeWorldEdit worldedit;
	
	public EWSelectionService(final EverAPI plugin) {		
		this.plugin = plugin;
		this.plugin.getGame().getPluginManager().getPlugin("worldedit").ifPresent(worldedit ->  {
			if (worldedit.getInstance().isPresent() || worldedit.getInstance().get() instanceof SpongeWorldEdit) {
				this.worldedit = (SpongeWorldEdit) worldedit.getInstance().get();
			}
		});
		
		if (this.isEnable()) {
			this.plugin.getGame().getServiceManager().setProvider(this.plugin, SelectionService.class, this);
		}
	}
	
	public boolean isEnable() {
		return this.worldedit != null;
	}

	@Override
	public Optional<SubjectSelection> get(UUID uuid) {
		if (!this.isEnable()) return Optional.empty();
		
		Optional<Player> player = this.plugin.getEServer().getPlayer(uuid);
		if (!player.isPresent()) return Optional.empty();
		
		LocalSession session = this.worldedit.getSession(player.get());
		if (session == null) return Optional.empty();
		
		return Optional.of(new EWSubjectSelection(this.worldedit, player.get(), session));
	}

	@Override
	public boolean hasRegistered(UUID uuid) {
		return this.get(uuid).isPresent();
	}
}
