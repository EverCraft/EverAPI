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
		if(address == null) {
			return Optional.empty();
		}
		
		try {
			return Optional.of(InetAddress.getByName(address));
		} catch (UnknownHostException e) {}
		
		return Optional.empty();
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
