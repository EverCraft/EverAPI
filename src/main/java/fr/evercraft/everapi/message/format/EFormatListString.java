package fr.evercraft.everapi.message.format;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;

public class EFormatListString extends EFormat {
	
	public final List<String> messages;

	public EFormatListString(List<String> messages) {
		this.messages = messages;
	}

	@Override
	public boolean isEmpty() {
		return this.messages.isEmpty();
	}

	@Override
	public Text toText() {
		return Text.joinWith(Text.of("\n"), EChat.of(this.messages));
	}

	@Override
	public String toString() {
		return String.join("\n", this.messages);
	}
	
	@Override
	public Text toText(Map<String, EReplace<?>> replaces) {
		List<Text> texts = new ArrayList<Text>();
		this.messages.forEach(message -> texts.add(EFormatString.apply(message, replaces)));
		return Text.joinWith(Text.of("\n"), texts);
	}

}
