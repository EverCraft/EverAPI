package fr.evercraft.everapi.registers;

import fr.evercraft.everapi.register.ECatalogType;

public class ChatType extends ECatalogType {

	public ChatType(String name) {
		super(name);
	}
	
	public static interface ChatTypes {
		static ChatType SEND = new ChatType("SEND");
		static ChatType RECEIVE = new ChatType("RECEIVE");
	}
}
