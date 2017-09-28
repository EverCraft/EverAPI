package fr.evercraft.everapi.services.permission;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.SubjectReference;

public interface EUserSubject extends ESubject {

	Optional<SubjectReference> getGroup();

	Optional<SubjectReference> getGroup(Set<Context> contexts);

	CompletableFuture<Boolean> clear();
	
	EUserData getSubjectData();
	
	EUserData getTransientSubjectData();
}
