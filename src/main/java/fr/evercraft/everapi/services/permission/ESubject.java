package fr.evercraft.everapi.services.permission;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectReference;

import com.google.common.collect.ImmutableMap;

public interface ESubject extends Subject {
	
	void reload();
	
	void clearCache();

	void addVerbose(CommandSource source, Set<String> permission);

	Optional<Set<String>> getVerbose(CommandSource source);

	void removeVerbose(CommandSource source);

	ImmutableMap<String, Set<String>> getVerboses();

	String getCollectionIdentifier();
	
	List<SubjectReference> getParents(String typeGroup);
}
