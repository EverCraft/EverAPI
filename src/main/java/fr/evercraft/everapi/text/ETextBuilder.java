package fr.evercraft.everapi.text;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.translation.Translation;

import fr.evercraft.everapi.plugin.EChat;

public class ETextBuilder {
	
	public static ETextBuilder toBuilder(String message) {
        return new ETextBuilder(message);
    }
	
	public static ETextBuilder toBuilder(Text text) {
		return new ETextBuilder(text);
	}
	
	private List<Object> texts;

	public ETextBuilder(){
		this.texts = new ArrayList<Object>();
	}
	
	public ETextBuilder(String message){
		this();
		this.texts.add(message);
	}
	
	public ETextBuilder(Text text){
		this();
		this.texts.add(text);
	}
	
	public Text build(){
		Builder builder = Text.builder();
		for(Object text : texts){
			if(text instanceof String){
				builder.append(EChat.of((String) text));
			} else if(text instanceof Text) {
				builder.append((Text) text);
			}
		}
		return builder.build();
	}
	public ETextBuilder replace(String replace_id, Translation replace_text){
		return replace(replace_id, Text.of(replace_text));
	}

	public ETextBuilder replace(String replace_id, Text replace_text){
		String[] split;
		String text;
		int cpt = 0;
		while(cpt < this.texts.size()){
			if(this.texts.get(cpt) instanceof String) {
				text = (String) this.texts.get(cpt);
				if(text.contains(replace_id)) {
					split = text.split(replace_id, 2);
					if(split.length == 2) {
						this.texts.remove(cpt);
						this.texts.add(cpt, split[0]);
						cpt++;
						this.texts.add(cpt, replace_text);
						cpt++;
						this.texts.add(cpt, split[1]);
					}
				}
			}
			cpt++;
		}
		return this;
	}
	
	public ETextBuilder replace(String replace_id, String replace_text) {
		List<Object> texts = new ArrayList<Object>();
		for(Object text : this.texts) {
			if(text instanceof String) {
				texts.add(((String) text).replaceAll(replace_id, replace_text));
			} else {
				texts.add(text);
			}
		}
		this.texts = texts;
		return this;
	}
	
	public boolean contains(String replace_id){
		int cpt = 0;
		boolean resultat = false;
		while(cpt < this.texts.size() && !resultat){
			resultat = (this.texts.get(cpt) instanceof String && ((String) this.texts.get(cpt)).contains(replace_id));
			cpt++;
		}
		return resultat;
	}
	
	public ETextBuilder append(String message){
		this.texts.add(message);
		return this;
	}
	
	public ETextBuilder append(Text text){
		this.texts.add(text);
		return this;
	}
}
