package fr.evercraft.everapi.services.permission;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;

public interface EGroupCollection extends ESubjectCollection {

	ConcurrentMap<String, EGroupSubject> getDefaultGroups();

	Optional<EGroupSubject> getDefaultGroup(String typeWorld);

	Set<EGroupSubject> getGroups(String typeWorld);

	CompletableFuture<Boolean> register(String groupName, String typeGroup);
	

}
