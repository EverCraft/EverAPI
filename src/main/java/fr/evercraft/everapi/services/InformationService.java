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
