package fr.evercraft.everapi.server.player;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectCollection;
import org.spongepowered.api.service.permission.option.OptionSubject;
import org.spongepowered.api.service.permission.option.OptionSubjectData;
import org.spongepowered.api.util.Tristate;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EverAPI;

public class PlayerPermission extends PlayerKeys implements OptionSubject {
	
	private OptionSubject optionSubject;

	public PlayerPermission(EverAPI plugin, Player player) {
		super(plugin, player);
	}

	private boolean isPresent() {
		if(this.optionSubject == null && this.plugin.getManagerService().getPermission().isPresent()) {
			Subject subject = this.plugin.getManagerService().getPermission().get().getUserSubjects().get(this.player.getIdentifier());
			if(subject instanceof OptionSubject) {
				this.optionSubject = (OptionSubject) subject;
				return true;
			}
		}
		return this.optionSubject != null;
	}
	
	public Optional<Subject> getGroup() {
		return this.getGroup(getActiveContexts());
	}
	
	public Optional<Subject> getGroup(final Set<Context> contexts) {
		Preconditions.checkNotNull(contexts, "contexts");
		
		List<Subject> groups = this.getSubjectData().getParents(contexts);
		if(!groups.isEmpty()) {
			return Optional.of(groups.get(0));
		}
		return Optional.empty();
    }

	@Override
	public Optional<CommandSource> getCommandSource() {
		return this.player.getCommandSource();
	}

	@Override
	public SubjectCollection getContainingCollection() {
		return this.player.getContainingCollection();
	}

	@Override
	public boolean hasPermission(Set<Context> contexts, String permission) {
		return this.player.hasPermission(contexts, permission);
	}

	@Override
	public Tristate getPermissionValue(Set<Context> contexts, String permission) {
		return this.player.getPermissionValue(contexts, permission);
	}

	@Override
	public boolean isChildOf(Set<Context> contexts, Subject parent) {
		return this.player.isChildOf(contexts, parent);
	}

	@Override
	public List<Subject> getParents(Set<Context> contexts) {
		return this.player.getParents();
	}

	@Override
	public String getIdentifier() {
		return this.player.getIdentifier();
	}

	@Override
	public Set<Context> getActiveContexts() {
		return this.player.getActiveContexts();
	}

	@Override
	public OptionSubjectData getSubjectData() {
		if(this.isPresent()) {
			return this.optionSubject.getSubjectData();
		}
		return null;
	}

	@Override
	public OptionSubjectData getTransientSubjectData() {
		if(this.isPresent()) {
			return this.optionSubject.getTransientSubjectData();
		}
		return null;
	}

	@Override
	public Optional<String> getOption(Set<Context> contexts, String key) {
		if(this.isPresent()) {
			return this.optionSubject.getOption(contexts, key);
		}
		return Optional.empty();
	}

}
