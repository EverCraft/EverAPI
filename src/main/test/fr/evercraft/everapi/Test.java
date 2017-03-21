package fr.evercraft.everapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String [] args) {
		String prefix = "OPTion";
		
		String line = "Salutdz q dqdq zd <" + prefix + "=Sélute ade @> cezd dzqdzd zq da <" + prefix + "=Salute> vaed zdqdzqdzdqz";
		Pattern pattern = Pattern.compile("<(?i)" + prefix + "=(.[^>]*)>");
		Matcher matcher = pattern.matcher(line);
		System.out.println("line " + line);
		while (matcher.find()) {
		    System.out.println("group 1: " + matcher.group(1));
		    line = line.replaceFirst("<" + prefix + "=" + matcher.group(1) + ">", "/Test/");
		    System.out.println("line " + line);
		    matcher = pattern.matcher(line);
		}
    }

}
