package fr.evercraft.everapi.services.permission;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface EGroupSubject extends ESubject {
	
	CompletableFuture<Boolean> setFriendlyIdentifier(String name);

	CompletableFuture<Boolean> setDefault(String typeWorld, boolean value);

	boolean isDefault(String typeWorld);

	Set<String> getTypeWorlds();

	boolean hasTypeWorld(String typeWorld);

	String getName();
	
	EGroupData getSubjectData();
	
	EGroupData getTransientSubjectData();

	CompletableFuture<Boolean> clear(String typeGroup);

}
