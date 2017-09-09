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
package fr.evercraft.everapi.registers;

import java.util.Optional;

import fr.evercraft.everapi.register.ECatalogType;

public class MojangServer extends ECatalogType {

	private final String url;
	private Status status;
	
	public MojangServer(String id, String url) {
		super(id, url);
		
		this.url = url;
		this.status = Status.ONLINE;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public enum Status {
		ONLINE ("online"),
		WARN ("warn"),
		OFFLINE ("offline");
		
		private final String name;
		private Status(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static Optional<Status> get(final String name) {
			Status status = null;
			int cpt = 0;
			while(cpt < values().length && status == null){
				if (values()[cpt].getName().equalsIgnoreCase(name)) {
					status = values()[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(status);
		}
		
	}
	
	public static interface MojangServers {
		static final MojangServer MINECRAFT_NET = new MojangServer("MINECRAFT_NET", "minecraft.net");
		static final MojangServer SESSION = new MojangServer("SESSION", "session.minecraft.net");
		static final MojangServer SKINS = new MojangServer("SKINS", "skins.minecraft.net");
		static final MojangServer TEXTURES = new MojangServer("TEXTURES", "textures.minecraft.net");
		static final MojangServer ACCOUNT = new MojangServer("ACCOUNT", "account.mojang.com");
		static final MojangServer AUTH = new MojangServer("AUTH", "auth.mojang.com");
		static final MojangServer AUTHSERVER = new MojangServer("AUTHSERVER", "authserver.mojang.com");
		static final MojangServer SESSIONSERVER = new MojangServer("SESSIONSERVER", "sessionserver.mojang.com");
		static final MojangServer API = new MojangServer("API", "api.mojang.com");
		static final MojangServer MOJANG = new MojangServer("MOJANG", "mojang.com");
	}
}
