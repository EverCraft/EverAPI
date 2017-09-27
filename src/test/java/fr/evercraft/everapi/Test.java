/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi;

public class Test {

	public static void main(String [] args) {
		/*
		Pattern URL_PATTERN = Pattern.compile("(?{selectGroupName}@{select}((?:(?!select).)*){/select})");
		Matcher match = URL_PATTERN.matcher("salut @{select}b{select}a{/select}c{/select} czdq");
		if (match.find()) {
			for (int cpt=0; cpt < match.groupCount(); cpt++) {
				System.out.println(cpt + " : " + match.group(cpt));	
			}	
		} else {
			System.out.println("Non");
		}*/
		/*Pattern URL_PATTERN = Pattern.compile("((?{style}(&[0-9a-z])*)(?{url}(((https?)://)?[\\w-_\\.]{2,})\\.([a-zA-Z]{2,3}(/\\S+)?)))");
		Matcher match = URL_PATTERN.matcher("salut &c&lts.evercraft.fr sa czdq");
		if (match.find()) {
			System.out.println("Oui : " + match.group(1));
			Matcher url = URL_PATTERN.matcher(match.group(1));
			System.out.println(url.find());	
			for (int cpt=0; cpt < match.groupCount(); cpt++) {
				System.out.println(cpt + " : " + url.group(cpt));	
			}
			System.out.println("style : " + url.group("style"));	
			System.out.println("url : " + url.group("url"));	
		} else {
			System.out.println("Non");
		}
		
		String test = "player";
		
		if(test.matches("#.[\\S]#")) {
			System.out.println("ta_chaine ne contient aucun espace");
		} else {
			System.out.println("ta_chaine contient un espace");
		}*/
		
		/*MessageFlag flag = new MessageFlag("MESSAGE");
		
		EMessageBuilder message = (new EMessageBuilder())
			.chatMessage(EFormatString.of("Message de test"))
			.chatPrefix(false);
		String v1 = flag.serialize(message);
		String v2 = flag.serialize(flag.deserialize(v1));
		System.out.println(v1);
		System.out.println(v2);
		assert(v1.equals(v2));
		
		message = (new EMessageBuilder())
				.actionbarMessage(EFormatString.of("actionbarMessage \"de\" test"))
				.actionbarPrefix(true)
				.actionbarPriority("test.priority")
				.actionbarStay(10);
		v1 = flag.serialize(message);
		v2 = flag.serialize(flag.deserialize(v1));
		System.out.println(v1);
		System.out.println(v2);
		assert(v1.equals(v2));
		
		message = (new EMessageBuilder())
				.titleMessage(EFormatString.of("titleMessage \"de\" test"))
				.titleMessage(EFormatString.of("title_submessage \"de\" test"))
				.titlePrefix(true)
				.titleSubPrefix(false)
				.titlePriority("test.priority")
				.titleStay(10)
				.titleFadeIn(5)
				.titleFadeOut(0);
		v1 = flag.serialize(message);
		v2 = flag.serialize(flag.deserialize(v1));
		System.out.println(v1);
		System.out.println(v2);
		assert(v1.equals(v2));
		
		message = (new EMessageBuilder())
				.bossbarMessage(EFormatString.of("bossbarMessage \"de\" test"))
				.bossbarPrefix(true)
				.bossbarPriority("test.priority")
				.bossbarStay(10)
				.bossbarCreateFog(true)
				.bossbarDarkenSky(false)
				.bossbarPercent(20F)
				.bossbarPlayEndBossMusic(true);
		v1 = flag.serialize(message);
		v2 = flag.serialize(flag.deserialize(v1));
		System.out.println(v1);
		System.out.println(v2);
		assert(v1.equals(v2));*/
		
		Integer a1=1000;
		Integer a2=1000;
		if (a1 == a2) System .out. println (" a1 == a2 ");
    }

}
