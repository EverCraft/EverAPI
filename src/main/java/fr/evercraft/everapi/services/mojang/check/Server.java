package fr.evercraft.everapi.services.mojang.check;

import java.util.Optional;

public enum Server {
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
	
	private Server(String url) {
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
	
	public static Optional<Server> get(final String url) {
		Server server = null;
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
