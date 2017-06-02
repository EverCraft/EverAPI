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

import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import com.flowpowered.math.vector.Vector3i;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.services.worldguard.exception.CircularInheritanceException;
import fr.evercraft.everapi.services.worldguard.exception.RegionIdentifierException;
import fr.evercraft.everapi.services.worldguard.flag.Flag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;
import fr.evercraft.everapi.services.worldguard.regions.Domain;

public interface ProtectedRegion extends Comparable<ProtectedRegion> {
	
	public enum Group {
		OWNER(EAMessages.REGION_GROUP_OWNER_HOVER),
		MEMBER(EAMessages.REGION_GROUP_MEMBER_HOVER),
		DEFAULT(EAMessages.REGION_GROUP_DEFAULT_HOVER);

		private final EAMessages format;
		
		Group(EAMessages format) {
			this.format = format;
	    }
		
		public String getName() {
	        return this.name();
	    }
		
		public Text getHover() {
	        return this.format.getText();
	    }
		
		public Text getNameFormat() {
	        return Text.builder(this.name())
	        				.onHover(TextActions.showText(this.format.getText()))
	        				.onShiftClick(TextActions.insertText(this.name()))
	        				.build();
	    }
		
		public static Optional<Group> get(final String name) {
			Group entity = null;
			int cpt = 0;
			while(cpt < values().length && entity == null){
				if (values()[cpt].name().equalsIgnoreCase(name)) {
					entity = values()[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(entity);
		}
	}
	
	public enum  Type {
		CUBOID(EAMessages.REGION_TYPE_CUBOID, EAMessages.REGION_TYPE_CUBOID_HOVER),
		POLYGONAL(EAMessages.REGION_TYPE_POLYGONAL, EAMessages.REGION_TYPE_POLYGONAL_HOVER),
		TEMPLATE(EAMessages.REGION_TYPE_TEMPLATE, EAMessages.REGION_TYPE_TEMPLATE_HOVER),
		GLOBAL(EAMessages.REGION_TYPE_GLOBAL, EAMessages.REGION_TYPE_GLOBAL_HOVER);

		private final EAMessages name;
		private final EAMessages format;
		
		Type(EAMessages name, EAMessages format) {
			this.name = name;
			this.format = format;
	    }
		
		public String getName() {
	        return this.name.getString();
	    }
		
		public Text getHover() {
	        return this.format.getText();
	    }
		
		public Text getNameFormat() {
	        return this.name.getText().toBuilder()
	        				.onHover(TextActions.showText(this.format.getText()))
	        				.onShiftClick(TextActions.insertText(this.name()))
	        				.build();
	    }

		public static Optional<Type> of(String name) {
			Type type = null;
			int cpt = 0;
			while(cpt < values().length && type == null){
				if (values()[cpt].name().equalsIgnoreCase(name)) {
					type = values()[cpt];
				}
				cpt++;
			}
			return Optional.ofNullable(type);
		}
	}
	
	public enum RemoveType {
		
		/**
		 * Unset the parent in children regions.
		 */
		UNSET_PARENT_IN_CHILDREN,
		
		/**
		 * Remove any children under the removed regions.
		 */
		REMOVE_CHILDREN;
	}
	
	UUID getId();
	String getName();
	void setName(String identifier) throws RegionIdentifierException;
	
	ProtectedRegion.Type getType();
	ProtectedRegion.Group getGroup(User user, Set<Context> contexts);
	Vector3i getMinimumPoint();
	Vector3i getMaximumPoint();
	List<Vector3i> getPoints();
	int getPriority();
	void setPriority(int priority);
	int getVolume();
	boolean isTransient();
	Optional<ProtectedRegion> getParent();
	List<ProtectedRegion> getHeritage() throws CircularInheritanceException;
	void clearParent();
	void setParent(@Nullable ProtectedRegion parent) throws CircularInheritanceException;
	
	/*
	 * Owner
	 */
	Domain getOwners();
	
	boolean isPlayerOwner(User player, Set<Context> contexts);
	Set<UUID> addPlayerOwner(Set<UUID> players);
	Set<UUID> removePlayerOwner(Set<UUID> players);
	
	boolean isGroupOwner(Subject group);
	Set<String> addGroupOwner(Set<String> groups);
	Set<String> removeGroupOwner(Set<String> groups);

	/*
	 * Member
	 */
	Domain getMembers();
	boolean isPlayerMember(User player, Set<Context> contexts);
	Set<UUID> addPlayerMember(Set<UUID> players);
	Set<UUID> removePlayerMember(Set<UUID> players);
	
	boolean isGroupMember(Subject group);
	Set<String> addGroupMember(Set<String> groups);
	Set<String> removeGroupMember(Set<String> groups);

	boolean hasMembersOrOwners();
	boolean isOwnerOrMember(User player, Set<Context> contexts);
	boolean isOwnerOrMember(Subject group);

	<V> FlagValue<V> getFlag(Flag<V> flag);
	<V> Optional<V> getFlagInherit(Flag<V> flag, Group group);
	<V> void setFlag(Flag<V> flag, Group group, V value);
	Map<Flag<?>, FlagValue<?>> getFlags();

	boolean containsPosition(Vector3i pos);
	boolean containsPosition(int x, int y, int z);
	boolean containsAnyPosition(List<Vector3i> positions);
	boolean containsChunck(Vector3i position);

	List<ProtectedRegion> getIntersecting(ProtectedRegion region);
	List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions);
	boolean isPhysicalArea();
	Optional<Area> toArea();
	
	Optional<ProtectedRegion.Cuboid> redefineCuboid(Vector3i pos1, Vector3i pos2);
	Optional<ProtectedRegion.Polygonal> redefinePolygonal(List<Vector3i> positions);
	Optional<ProtectedRegion.Template> redefineTemplate();
	
	public interface Cuboid extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Type.CUBOID;
		}
	}
	
	public interface Polygonal extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Type.POLYGONAL;
		}
	}
	
	public interface Template extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Type.TEMPLATE;
		}
	}
	
	public interface Global extends ProtectedRegion {
		default ProtectedRegion.Type getType() {
			return ProtectedRegion.Type.GLOBAL;
		}
	}
}
