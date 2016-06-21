/*
 * EverAPI
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
package fr.evercraft.everapi.services.mojang.check;

import java.util.Optional;

public enum MojangServer {
	MINECRAFT_NET("minecraft.net"),
	SESSION("session.minecraft.net"),
	SKINS("skins.minecraft.net"),
	TEXTURES("textures.minecraft.net"),
	ACCOUNT("account.mojang.com"),
	AUTH("auth.mojang.com"),
	AUTHSERVER("authserver.mojang.com"),
	SESSIONSERVER("sessionserver.mojang.com"),
	API("api.mojang.com"),
	MOJANG("mojang.com");
	
	private final String url;
	private Color color;
	
	private MojangServer(String url) {
		this.url = url;
		this.color = Color.YELLOW;
	}
	
	public String getURL() {
		return this.url;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public static Optional<MojangServer> get(final String url) {
		MojangServer server = null;
		int cpt = 0;
		while(cpt < values().length && server == null){
			if (values()[cpt].getURL().equalsIgnoreCase(url)) {
				server = values()[cpt];
			}
			cpt++;
		}
		return Optional.ofNullable(server);
	}
	
	public enum Color {
		GREEN ("green"),
		YELLOW ("yellow"),
		RED ("red");
		
		private final String name;
		private Color(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static Optional<Color> get(final String name) {
			Color color = null;
			int cpt = 0;
			while(cpt < values().length && color == null){
				if (values()[cpt].getName().equalsIgnoreCase(name)) {
					color = values()[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(color);
		}
		
	}
}
