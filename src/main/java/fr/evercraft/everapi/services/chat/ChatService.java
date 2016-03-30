package fr.evercraft.everapi.services.chat;

import java.util.List;
import java.util.Set;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

public interface ChatService {
	
	public String replace(String message);
	
	public List<String> replace(List<String> messages);

	public String getFormat(Subject subject);

	public String getFormat(Subject subject, Set<Context> contexts);
	
}

