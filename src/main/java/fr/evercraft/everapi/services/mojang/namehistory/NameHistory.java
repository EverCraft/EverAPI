package fr.evercraft.everapi.services.mojang.namehistory;

import java.util.Optional;

public class NameHistory {
	
	private final String name;
	
	private final Optional<Long> date;

	public NameHistory(String name, Optional<Long> date) {
		this.name = name;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public Optional<Long> getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "NameHistory [name=" + name + ", date=" + date + "]";
	}
}
