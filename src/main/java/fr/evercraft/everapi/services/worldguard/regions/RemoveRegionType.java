package fr.evercraft.everapi.services.worldguard.regions;

public enum RemoveRegionType {
	
	/**
	 * Unset the parent in children regions.
	 */
	UNSET_PARENT_IN_CHILDREN,
	
	/**
	 * Remove any children under the removed regions.
	 */
	REMOVE_CHILDREN
}
