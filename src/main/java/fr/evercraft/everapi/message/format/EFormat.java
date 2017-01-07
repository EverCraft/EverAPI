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
	
	public String toString(Map<String, EReplace<?>> replaces) {
		String message = this.toString();
		for (Entry<String, EReplace<?>> replace : replaces.entrySet()) {
			Object value = replace.getValue().get();
			if (value instanceof String){
				message = message.replaceAll(replace.getKey(), (String) value);
			} else if (replace.getValue().get() instanceof Text) {
				message = message.replaceAll(replace.getKey(), EChat.serialize((Text) value));
			} else if (replace.getValue().get() instanceof EFormat) {
				message = message.replaceAll(replace.getKey(), ((EFormat) value).toString(replaces));
			} else {
				message = message.replaceAll(replace.getKey(), value.toString());
			}
		}
		return message;
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
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4)));
	}
	
	public Text toText(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4, String k5, Object v5) {
		return this.toText(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4), k5, EReplace.of(v5)));
	}
	
	public Text toText(String k1, EReplace<?> v1) {
		return this.toText(ImmutableMap.of(k1, v1));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
	}
	
	public Text toText(String k1, EReplace<?> v1, String k2, EReplace<?> v2, String k3, EReplace<?> v3, String k4, EReplace<?> v4) {
		return this.toText(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4));
	}
	
	public String toString(String k1, Supplier<Object> v1) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public String toString(String k1, Supplier<Object> v1, String k2, Supplier<Object> v2, String k3, Supplier<Object> v3) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3)));
	}
	
	public String toString(String k1, Object v1, String k2, Object v2, String k3, Object v3, String k4, Object v4) {
		return this.toString(ImmutableMap.of(k1, EReplace.of(v1), k2, EReplace.of(v2), k3, EReplace.of(v3), k4, EReplace.of(v4)));
	}
	
	public boolean isListString() {
		return this instanceof EFormatListString;
	}
	
	public boolean isString() {
		return this instanceof EFormatString;
	}
	
	public boolean isTemplate() {
		return this instanceof EFormatTemplate;
	}
}
