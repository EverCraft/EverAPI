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
		String test = "{player} test salue {player}";
		System.out.println(test);
		System.out.println(test.replace("{player}", "rexbut"));
		
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
    }

}
