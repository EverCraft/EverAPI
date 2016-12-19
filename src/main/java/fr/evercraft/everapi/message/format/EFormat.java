package fr.evercraft.everapi.message.format;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;

import com.google.common.collect.ImmutableMap;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public abstract class EFormat {

	public abstract boolean isEmpty();
	public abstract Text toText();
	public abstract String toString();
	public abstract Text toText(Map<String, EReplace<?>> replaces);
	
	public Text toText(String k1, Supplier<Object> v1) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public Text toText(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public Text toText(String k1, Object v1) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public String toString(Map<String, EReplace<?>> replaces) {
		String message = this.toString();
		for (Entry<String, EReplace<?>> replace : replaces.entrySet()) {
			if (replace.getValue().get() instanceof String){
				message = message.replaceAll(replace.getKey(), (String) replace.getValue().get());
			} else if (replace.getValue().get() instanceof Text) {
				message = message.replaceAll(replace.getKey(), EChat.serialize((Text) replace.getValue().get()));
			} else if (replace.getValue().get() instanceof EFormat) {
				message = message.replaceAll(replace.getKey(), ((EFormat) replace.getValue().get()).toString(replaces));
			} else {
				message = message.replaceAll(replace.getKey(), replace.getValue().get().toString());
			}
		}
		return message;
	}
	
	public String toString(String key, Supplier<Object> value) {
		return this.toString(key, new EReplace<Object>(value));
	}
	
	public String toString(String key, EReplace<?> value) {
		String message = this.toString();
		if (value.get() instanceof String){
			message = message.replaceAll(key, (String) value.get());
		} else if (value.get() instanceof Text) {
			message = message.replaceAll(key, EChat.serialize((Text) value.get()));
		} else if (value.get() instanceof EFormat) {
			message = message.replaceAll(key, ((EFormat) value.get()).toString(key, value));
		} else {
			message = message.replaceAll(key, value.get().toString());
		}
		return message;
	}
	
	protected Text getText(Object value, Map<String, EReplace<?>> replaces) {
		if (value instanceof String){
			return EChat.of((String) value);
		} else if (value instanceof Text) {
			return (Text) value;
		} else if (value instanceof EFormat) {
			return ((EFormat) value).toText(replaces);
		}
		return EChat.of(value.toString());
	}
}
