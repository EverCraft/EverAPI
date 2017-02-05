package fr.evercraft.everapi.services.worldguard.flag.type;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
public abstract class EntityTypesFlag extends SetFlag<EntityType> {

	public EntityTypesFlag(String name) {
		super(name);
	}
	
	@Override
	public Collection<String> getSuggestAdd(final List<String> args) {
		return Sponge.getGame().getRegistry().getAllOf(EntityType.class).stream()
				.map(type -> type.getId())
				.collect(Collectors.toSet());
	}

	@Override
	public String subSerialize(EntityType value) {
		return value.getId();
	}

	@Override
	public EntityType subDeserialize(String value) throws IllegalArgumentException {
		Optional<EntityType> type = Sponge.getGame().getRegistry().getType(EntityType.class, value);
		if (!type.isPresent()) {
			throw new IllegalArgumentException();
		}
		
		return type.get();
	}
}
