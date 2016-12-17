package fr.evercraft.everapi.message.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.TextTemplate.Arg;

public class EFormatTemplate extends EFormat {

	public final TextTemplate message;

	public EFormatTemplate(TextTemplate message) {
		this.message = message;
	}

	@Override
	public boolean isEmpty() {
		return this.message.toText().isEmpty();
	}

	@Override
	public Text toText() {
		return this.message.toText();
	}

	@Override
	public Text replaces(Map<String, Supplier<Object>> replaces) {
		HashMap<String, Text> elements = new HashMap<String, Text>();
		for(Entry<String, Arg> arguments : this.message.getArguments().entrySet()) {
			Object value = replaces.get(arguments.getKey());
			if (value != null) {
				elements.put(arguments.getKey(), this.getText(value, replaces));
			}
		}
		return this.message.apply(elements).build();
	}
}
