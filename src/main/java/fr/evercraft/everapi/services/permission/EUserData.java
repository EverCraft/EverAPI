package fr.evercraft.everapi.services.permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.SubjectReference;

public interface EUserData extends ESubjectData {
	
	EUserSubject getSubject();

	Optional<SubjectReference> getGroup(Set<Context> contexts);

	Optional<SubjectReference> getGroup(String typeWorld);

	List<SubjectReference> getSubGroup(Set<Context> contexts);

	List<SubjectReference> getSubGroup(String typeWorld);

	CompletableFuture<Boolean> setGroup(Set<Context> contexts, SubjectReference parent);

	CompletableFuture<Boolean> setGroup(String typeWorld, SubjectReference parent);
}
