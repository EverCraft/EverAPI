package fr.evercraft.everapi.sponge;

import java.util.List;

import org.spongepowered.api.text.Text;

public class UtilsBook {
	private final static int LINE_NUMBER = 16;
	
	public static Text verticalCenter(List<Text> line) {
		String rt = "";
		for(int cpt=0; cpt < Math.max(0, (LINE_NUMBER - line.size() / 2)); cpt++) {
			rt += "\n";
		}
		return Text.of(rt).concat(Text.joinWith(Text.of("\n"), line));
	}
}