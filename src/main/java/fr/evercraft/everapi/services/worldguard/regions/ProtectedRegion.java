package fr.evercraft.everapi.services.worldguard.regions;

import java.awt.geom.Area;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;

import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.worldguard.exception.CircularInheritanceException;
import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;

public interface ProtectedRegion extends Comparable<ProtectedRegion> {
	
	static final Pattern VALID_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_,'\\-\\+/]{1,}$");

	
	public static boolean isValidId(String id) {
		Preconditions.checkNotNull(id);
		
		return VALID_ID_PATTERN.matcher(id).matches();
	}

	String getIdentifier();
	
	RegionType getType();
	
	Vector3i getMinimumPoint();
	
	Vector3i getMaximumPoint();
	
	List<Vector3i> getPoints();
	
	int getPriority();
	
	void setPriority(int priority);
	
	int getVolume();
	
	boolean isTransient();
	
	Optional<ProtectedRegion> getParent();
	
	List<ProtectedRegion> getHeritage() throws CircularInheritanceException;
	
	void setParent(@Nullable ProtectedRegion parent) throws CircularInheritanceException;
	
	Domain getOwners();
	
	boolean containsPosition(Vector3i pos);

	Domain getMembers();

	boolean hasMembersOrOwners();

	boolean isOwner(EUser player);

	boolean isMember(EUser player);
	
	boolean isOwnerOrMember(EUser player);
	
	boolean isOwner(String group);

	boolean isMember(String group);
	
	boolean isOwnerOrMember(String group);

	<T extends Flag<V>, V> FlagValue<V> getFlag(T flag);

	<T extends Flag<V>, V> void setFlag(T flag, Association association, V value);

	Map<Flag<?>, FlagValue<?>> getFlags();

	void setFlags(Map<Flag<?>, FlagValue<?>> flags);

	boolean containsPosition(int x, int y, int z);

	boolean containsAnyPosition(List<Vector3i> positions);

	boolean containsChunk(Vector3i position);

	List<ProtectedRegion> getIntersecting(ProtectedRegion region);

	List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions);

	boolean isPhysicalArea();

	Optional<Area> toArea();	
	
}
