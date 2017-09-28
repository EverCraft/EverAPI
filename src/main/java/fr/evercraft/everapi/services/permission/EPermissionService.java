package fr.evercraft.everapi.services.permission;

import java.util.Optional;

import org.spongepowered.api.service.permission.PermissionService;

public interface EPermissionService extends PermissionService {
	
	public static final String SUBJECT_DEFAULT = "default";
	
	EGroupCollection getGroupSubjects();
	
	EUserCollection getUserSubjects();
	EUserCollection getCommandBlockSubjects();
	EUserCollection getSytemSubjects();

	Optional<ESubjectCollection> get(String identifier);
	
	void registerWorldType(String nameWorld);
	void clearCache();
}
