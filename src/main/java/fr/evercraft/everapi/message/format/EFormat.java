package fr.evercraft.everapi.message.format;

import java.util.Map;
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.plugin.EChat;

public abstract class EFormat {

	public abstract boolean isEmpty();
	public abstract Text toText();
	public abstract Text replaces(Map<String, Supplier<Object>> replaces);
	
	public Text getText(Object value, Map<String, Supplier<Object>> replaces) {
		if (value instanceof String){
			return EChat.of((String) value);
		} else if (value instanceof Text) {
			return (Text) value;
		} else if (value instanceof EFormat) {
			return ((EFormat) value).replaces(replaces);
		}
		return EChat.of(value.toString());
	}
}
