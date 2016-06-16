package fr.evercraft.everapi.services;

import java.util.List;
import java.util.Set;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

public interface ChatService {
	
	public final static String REGEX_COLOR = "&[0-9a-f]";
	public final static String REGEX_FORMAT = "&[lmnor]";
	public final static String REGEX_MAGIC = "&k";
	
	public String replace(String message);
	
	public List<String> replace(List<String> messages);

	public String getFormat(Subject subject);

	public String getFormat(Subject subject, Set<Context> contexts);
	
}

