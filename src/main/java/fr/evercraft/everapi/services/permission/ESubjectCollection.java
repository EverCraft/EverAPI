package fr.evercraft.everapi.services.permission;

import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.service.permission.SubjectCollection;

public interface ESubjectCollection extends SubjectCollection {

	Optional<String> getTypeWorld(String world);

	Set<String> getTypeWorlds();
}
