package fr.evercraft.everapi.services.sanction.manual;

import java.net.InetAddress;

public interface SanctionManualIP extends SanctionManual {
	
	public InetAddress getAddress();
	
	public interface Ban extends SanctionManualIP {}
}
