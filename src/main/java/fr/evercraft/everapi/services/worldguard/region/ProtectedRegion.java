package fr.evercraft.everapi.services.worldguard.region;

import java.awt.geom.Area;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.server.user.EUser;
import fr.evercraft.everapi.services.worldguard.exception.CircularInheritanceException;
import fr.evercraft.everapi.services.worldguard.flag.EFlag;
import fr.evercraft.everapi.services.worldguard.flag.FlagValue;
import fr.evercraft.everapi.services.worldguard.regions.Association;
import fr.evercraft.everapi.services.worldguard.regions.Domain;

public interface ProtectedRegion extends Comparable<ProtectedRegion> {
	
	static final Pattern VALID_ID_PATTERN = Pattern.compile("^[A-Za-z0-9_,'\\-\\+/]{1,}$");
	
	public static boolean isValidId(String id) {
		Preconditions.checkNotNull(id);
		
		return VALID_ID_PATTERN.matcher(id).matches();
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
	
	String getIdentifier();
	
	ProtectedRegion.Type getType();
	
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
	
	boolean containsPosition(Vector3i pos);
	
	/*
	 * Owner
	 */
	Domain getOwners();
	
	boolean isOwner(EUser player);
	void addOwner(Set<EUser> players);
	void removeOwner(Set<EUser> players);
	
	boolean isOwner(Subject group);
	void addOwnerGroup(Set<Subject> groups);
	void removeOwnerGroup(Set<Subject> groups);

	/*
	 * Member
	 */
	Domain getMembers();
	boolean isMember(EUser player);
	void addMember(Set<EUser> players);
	void removeMember(Set<EUser> players);
	boolean isMember(Subject group);
	void addMemberGroup(Set<Subject> groups);
	void removeMemberGroup(Set<Subject> groups);

	boolean hasMembersOrOwners();
	boolean isOwnerOrMember(EUser player);
	boolean isOwnerOrMember(Subject group);

	<T extends EFlag<V>, V> FlagValue<V> getFlag(T flag);

	<T extends EFlag<V>, V> void setFlag(T flag, Association association, V value);

	Map<EFlag<?>, FlagValue<?>> getFlags();

	void setFlags(Map<EFlag<?>, FlagValue<?>> flags);

	boolean containsPosition(int x, int y, int z);

	boolean containsAnyPosition(List<Vector3i> positions);

	boolean containsChunck(Vector3i position);

	List<ProtectedRegion> getIntersecting(ProtectedRegion region);

	List<ProtectedRegion> getIntersectingRegions(Collection<ProtectedRegion> regions);

	boolean isPhysicalArea();

	Optional<Area> toArea();	
	
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
