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
package fr.evercraft.everapi.services.worldguard.region;

import java.awt.geom.Area;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.register.ECatalogType;
import fr.evercraft.everapi.register.EFormatCatalogType;
import fr.evercraft.everapi.services.worldguard.Flag;
import fr.evercraft.everapi.services.worldguard.FlagValue;
import fr.evercraft.everapi.services.worldguard.exception.CircularInheritanceException;
import fr.evercraft.everapi.services.worldguard.exception.RegionIdentifierException;

public interface ProtectedRegion extends Comparable<ProtectedRegion> {
	
	public class Group extends EFormatCatalogType {
		public Group(String name, EAMessages format) {
			super(name, format);
	    }
	}
	
	public class Type extends EFormatCatalogType {
		public Type(String name, EAMessages format) {
			super(name, format);
	    }
	}
	
	public class RemoveType extends ECatalogType {
		public RemoveType(String name) {
			super(name);
	    }
	}
	
	public interface Groups {
		static final Group OWNER = new Group("OWNER", EAMessages.REGION_GROUP_OWNER_HOVER);
		static final Group MEMBER = new Group("MEMBER", EAMessages.REGION_GROUP_MEMBER_HOVER);
		static final Group DEFAULT = new Group("DEFAULT", EAMessages.REGION_GROUP_DEFAULT_HOVER);
	}
	
	public interface Types {
		static final Type CUBOID = new Type("CUBOID", EAMessages.REGION_TYPE_CUBOID_HOVER);
		static final Type POLYGONAL = new Type("POLYGONAL", EAMessages.REGION_TYPE_POLYGONAL_HOVER);
		static final Type TEMPLATE = new Type("TEMPLATE", EAMessages.REGION_TYPE_TEMPLATE_HOVER);
		static final Type GLOBAL = new Type("GLOBAL", EAMessages.REGION_TYPE_GLOBAL_HOVER);
	}
	
	public interface RemoveTypes {
		
		/**
		 * Unset the parent in children regions.
		 */
		static final RemoveType UNSET_PARENT_IN_CHILDREN = new RemoveType("UNSET_PARENT_IN_CHILDREN");
		
		/**
		 * Remove any children under the removed regions.
		 */
		static final RemoveType REMOVE_CHILDREN = new RemoveType("REMOVE_CHILDREN");
	}
	
	UUID getId();
	String getName();
	
	ProtectedRegion.Type getType();
	ProtectedRegion.Group getGroup(User user, Set<Context> contexts);
	Vector3i getMinimumPoint();
	Vector3i getMaximumPoint();
	List<Vector3i> getPoints();
	int getPriority();
	int getVolume();
	boolean isTransient();
	Optional<ProtectedRegion> getParent();
	List<ProtectedRegion> getHeritage();
	
	/*
	 * Owner et Member
	 */

	boolean hasMembersOrOwners();
	boolean isOwnerOrMember(User player, Set<Context> contexts);
	boolean isOwnerOrMember(Subject group);
	
	Domain getOwners();
	boolean isPlayerOwner(User player, Set<Context> contexts);
	boolean isGroupOwner(Subject group);
	
	Domain getMembers();
	boolean isPlayerMember(User player, Set<Context> contexts);
	boolean isGroupMember(Subject group);
	
	/*
	 * Flag
	 */

	<V> FlagValue<V> getFlag(Flag<V> flag);
	<V> Optional<V> getFlagInherit(Flag<V> flag, Group group);
	Map<Flag<?>, FlagValue<?>> getFlags();

	boolean containsPosition(Vector3i pos);
	boolean containsPosition(int x, int y, int z);
	boolean containsAnyPosition(List<Vector3i> positions);
	boolean containsChunck(Vector3i position);

	List<ProtectedRegion> getIntersecting(ProtectedRegion region);
	List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions);
	boolean isPhysicalArea();
	Optional<Area> toArea();
	
	CompletableFuture<ProtectedRegion.Cuboid> redefineCuboid(Vector3i pos1, Vector3i pos2);
	CompletableFuture<ProtectedRegion.Polygonal> redefinePolygonal(List<Vector3i> positions);
	CompletableFuture<ProtectedRegion.Template> redefineTemplate();
	
	/*
	 * Setters
	 */
	
	CompletableFuture<Boolean> setName(String identifier) throws RegionIdentifierException;
	CompletableFuture<Boolean> setPriority(int priority);
	<V> CompletableFuture<Boolean> setFlag(Flag<V> flag, Group group, @Nullable V value);
	CompletableFuture<Boolean> setParent(ProtectedRegion parent) throws CircularInheritanceException;
	CompletableFuture<Boolean> clearParent();
	
	CompletableFuture<Set<UUID>> addPlayerOwner(Set<UUID> players);
	CompletableFuture<Set<UUID>> removePlayerOwner(Set<UUID> players);
	CompletableFuture<Set<String>> addGroupOwner(Set<String> groups);
	CompletableFuture<Set<String>> removeGroupOwner(Set<String> groups);
	
	CompletableFuture<Set<UUID>> addPlayerMember(Set<UUID> players);
	CompletableFuture<Set<UUID>> removePlayerMember(Set<UUID> players);
	CompletableFuture<Set<String>> addGroupMember(Set<String> groups);
	CompletableFuture<Set<String>> removeGroupMember(Set<String> groups);
	
	public interface Cuboid extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Types.CUBOID;
		}
	}
	
	public interface Polygonal extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Types.POLYGONAL;
		}
	}
	
	public interface Template extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Types.TEMPLATE;
		}
	}
	
	public interface Global extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Types.GLOBAL;
		}
	}
}
