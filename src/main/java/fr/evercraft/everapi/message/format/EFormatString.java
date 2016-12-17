package fr.evercraft.everapi.message.format;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;

import fr.evercraft.everapi.plugin.EChat;

public class EFormatString extends EFormat {
	
	public final String message;

	public EFormatString(String message) {
		this.message = message;
	}

	@Override
	public boolean isEmpty() {
		return message.isEmpty();
	}

	@Override
	public Text toText() {
		return EChat.of(message);
	}

	@Override
	public Text replaces(Map<String, Supplier<Object>> replaces) {
		List<Object> texts = Arrays.asList(this.message);
		
		for (Entry<String, Supplier<Object>> replace : replaces.entrySet()) {
			String[] split;
			String text;
			int cpt = 0;
			while(cpt < texts.size()){
				if (texts.get(cpt) instanceof String) {
					text = (String) texts.get(cpt);
					if (text.contains(replace.getKey())) {
						split = text.split(replace.getKey(), 2);
						if (split.length == 2) {
							texts.remove(cpt);
							texts.add(cpt, split[0]);
							cpt++;
							texts.add(cpt, replace.getValue().get());
							cpt++;
							texts.add(cpt, split[1]);
						}
					}
				}
				cpt++;
			}
		}
		
		Builder builder = Text.builder();
		for (Object text : texts){
			if (text instanceof String){
				builder.append(EChat.of((String) text));
			} else if (text instanceof Text) {
				builder.append((Text) text);
			} else if (text instanceof EFormat) {
				builder.append(((EFormat) text).replaces(replaces));
			} else {
				builder.append(EChat.of(text.toString()));
			}
		}
		return builder.build();
	}

}
