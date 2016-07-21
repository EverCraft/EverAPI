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
package fr.evercraft.everapi.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.Server;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.profile.GameProfileManager;
import org.spongepowered.api.resourcepack.ResourcePack;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.world.ChunkTicketManager;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.storage.ChunkLayout;
import org.spongepowered.api.world.storage.WorldProperties;

import fr.evercraft.everapi.EverAPI;

public class ServerSponge implements Server {

	protected final EverAPI plugin;
	
	protected final Server server;

	public ServerSponge(final EverAPI plugin){
		this.plugin = plugin;
		this.server = this.plugin.getGame().getServer();
	}
	
	public Collection<Player> getOnlinePlayers() {
		return this.server.getOnlinePlayers();
	}

	public int getMaxPlayers() {
		return this.server.getMaxPlayers();
	}

	public Collection<World> getWorlds() {
		return this.server.getWorlds();
	}

	public Collection<WorldProperties> getUnloadedWorlds() {
		return this.server.getUnloadedWorlds();
	}

	public Collection<WorldProperties> getAllWorldProperties() {
		return this.server.getAllWorldProperties();
	}

	public Optional<World> getWorld(UUID uniqueId) {
		return this.server.getWorld(uniqueId);
	}

	public Optional<World> getWorld(String worldName) {
		return this.server.getWorld(worldName);
	}

	public Optional<WorldProperties> getDefaultWorld() {
		return this.server.getDefaultWorld();
	}

	public String getDefaultWorldName() {
		return this.server.getDefaultWorldName();
	}

	public Optional<World> loadWorld(String worldName) {
		return this.server.loadWorld(worldName);
	}

	public Optional<World> loadWorld(UUID uniqueId) {
		return this.server.getWorld(uniqueId);
	}

	public Optional<World> loadWorld(WorldProperties properties) {
		return this.server.loadWorld(properties);
	}

	public Optional<WorldProperties> getWorldProperties(String worldName) {
		return this.server.getWorldProperties(worldName);
	}

	public Optional<WorldProperties> getWorldProperties(UUID uniqueId) {
		return this.server.getWorldProperties(uniqueId);
	}

	public boolean unloadWorld(World world) {
		return this.server.unloadWorld(world);
	}

	public CompletableFuture<Optional<WorldProperties>> copyWorld(WorldProperties worldProperties, String copyName) {
		return this.server.copyWorld(worldProperties, copyName);
	}

	public Optional<WorldProperties> renameWorld(WorldProperties worldProperties, String newName) {
		return this.server.renameWorld(worldProperties, newName);
	}

	public CompletableFuture<Boolean> deleteWorld(WorldProperties worldProperties) {
		return this.server.deleteWorld(worldProperties);
	}

	public boolean saveWorldProperties(WorldProperties properties) {
		return this.server.saveWorldProperties(properties);
	}

	public Optional<Scoreboard> getServerScoreboard() {
		return this.server.getServerScoreboard();
	}

	public ChunkLayout getChunkLayout() {
		return this.server.getChunkLayout();
	}

	public int getRunningTimeTicks() {
		return this.server.getRunningTimeTicks();
	}

	public MessageChannel getBroadcastChannel() {
		return this.server.getBroadcastChannel();
	}

	public void setBroadcastChannel(MessageChannel channel) {
		this.server.setBroadcastChannel(channel);
	}

	public Optional<InetSocketAddress> getBoundAddress() {
		return this.server.getBoundAddress();
	}

	public boolean hasWhitelist() {
		return this.server.hasWhitelist();
	}

	public void setHasWhitelist(boolean enabled) {
		this.server.setHasWhitelist(enabled);
	}

	public boolean getOnlineMode() {
		return this.server.getOnlineMode();
	}

	public Text getMotd() {
		return this.server.getMotd();
	}

	public void shutdown() {
		this.server.shutdown();
	}

	public void shutdown(Text kickMessage) {
		this.server.shutdown(kickMessage);
	}

	public ConsoleSource getConsole() {
		return this.server.getConsole();
	}

	public ChunkTicketManager getChunkTicketManager() {
		return this.server.getChunkTicketManager();
	}

	public GameProfileManager getGameProfileManager() {
		return this.server.getGameProfileManager();
	}

	public double getTicksPerSecond() {
		return this.server.getTicksPerSecond();
	}

	public Optional<ResourcePack> getDefaultResourcePack() {
		return this.server.getDefaultResourcePack();
	}

	public Optional<Player> getPlayer(UUID uniqueId) {
		return this.server.getPlayer(uniqueId);
	}

	public Optional<Player> getPlayer(String name) {
		return this.server.getPlayer(name);
	}

	@Override
	public WorldProperties createWorldProperties(String folderName, WorldArchetype archetype) throws IOException {
		return this.server.createWorldProperties(folderName, archetype);
	}

	@Override
	public int getPlayerIdleTimeout() {
		return this.server.getPlayerIdleTimeout();
	}

	@Override
	public void setPlayerIdleTimeout(int timeout) {
		this.server.setPlayerIdleTimeout(timeout);
	}
	
}
