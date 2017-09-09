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
package fr.evercraft.everapi.services;

public interface InformationService {
	
	public interface Priorities {
	
		public final static String AUTOMESSAGE = "everinformations.automessages";
		
		public final static String NEWBIE_PLAYER = "everinformations.newbie.player";
		
		public final static String NEWBIE_OTHERS = "everinformations.newbie.others";
		
		public final static String CONNECTION_PLAYER = "everinformations.connection.player";
		
		public final static String CONNECTION_OTHERS = "everinformations.connection.others";
		
		public final static String NAMETAG = "everinformations";
		
		public final static String TABLIST = "everinformations";
		
		// Max : 16 lengths
		public final static String SCOREBOARD_BELOW_NAME = "everinfo.below";
		
		// Max : 16 lengths
		public final static String SCOREBOARD_LIST = "everinfo.list";
		
		// Max : 16 lengths
		public final static String SCOREBOARD_SIDEBAR = "everinfo.sidebar";
	}
	
}
