package fr.evercraft.everapi.sponge;

import io.netty.channel.local.LocalAddress;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class UtilsNetwork {
	public static final String LOCAL_ADDRESS = "local";


	public static Optional<InetAddress> getHost(String address) {
		try {
			return Optional.of(InetAddress.getByName(address));
		} catch (UnknownHostException e) {
			return Optional.empty();
		}
	}
	
	public static String getHostString(InetAddress address) {
		return UtilsNetwork.getHostString(new InetSocketAddress(address, 0));
	}
	
    public static String getHostString(SocketAddress address) {
        if (address instanceof InetSocketAddress) {
            return ((InetSocketAddress) address).getHostString();
        } else if (address instanceof LocalAddress) {
            return LOCAL_ADDRESS;
        }

        return address.toString();
    }
}
