/*
 * This file is part of EverAPI.
 *
 * EverAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.evercraft.everapi.command.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.AreaEffectCloud;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Color;

import fr.evercraft.everapi.EACommand;
import fr.evercraft.everapi.EAPermissions;
import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;

public class EATest extends ESubCommand<EverAPI> {
	private AreaEffectCloud area;
	
	public EATest(final EverAPI plugin, final EACommand command) {
        super(plugin, command, "test");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(EAPermissions.TEST.get());
	}

	public Text description(final CommandSource source) {
		return Text.of("Commande de test");
	}
	
	public List<String> subTabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return new ArrayList<String>();
	}

	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName())
					.onClick(TextActions.suggestCommand("/" + this.getName()))
					.color(TextColors.RED)
					.build();
	}
	
	public boolean subExecute(final CommandSource source, final List<String> args) {
		if (args.size() == 0) {
			//return commandTest((EPlayer) source);
		} else if (args.size() == 1) {
			//return commandTest((EPlayer) source, args.get(0));
		}
		source.sendMessage(this.help(source));
		return false;
	}
	/*
	private boolean commandTest(final EPlayer player) {		
		this.area = (AreaEffectCloud) player.getWorld().createEntity(EntityTypes.AREA_EFFECT_CLOUD, player.getLocation().getPosition());
		area.offer(Keys.DISPLAY_NAME, EChat.of("&4Test"));
		area.offer(Keys.CUSTOM_NAME_VISIBLE, true);
		area.offer(Keys.AREA_EFFECT_CLOUD_COLOR, Color.GREEN);
		area.offer(Keys.AREA_EFFECT_CLOUD_RADIUS, 3D);
		area.offer(Keys.AREA_EFFECT_CLOUD_PARTICLE_TYPE, ParticleTypes.MOB_SPELL);
		area.offer(Keys.AREA_EFFECT_CLOUD_WAIT_TIME, 0);
		this.area.offer(Keys.AREA_EFFECT_CLOUD_DURATION, 5000);
		area.offer(Keys.POTION_EFFECTS, Arrays.asList(PotionEffect.builder().potionType(PotionEffectTypes.JUMP_BOOST).duration(3000).amplifier(3).build()));
		
		if(player.getWorld().spawnEntity(area, Cause.source(player).build())) {
			player.sendMessage("&aSpawn entity");
		} else {
			player.sendMessage("&4Error : spawn entity");
		}
		return false;
	}

	private boolean commandTest(final EPlayer player, String name) {
		if (this.area == null) {
			this.commandTest(player);
		}
		
		if (name.equals("0")) {
			player.sendMessage("teleport debut : " + this.area.getLocation().getBlockPosition());
			this.area.setTransform(player.getTransform());
			player.sendMessage("teleport fin : " + this.area.getLocation().getBlockPosition());
		} else if (name.equals("d")) {
			player.sendMessage("AREA_EFFECT_CLOUD_DURATION : " + this.area.get(Keys.AREA_EFFECT_CLOUD_DURATION).get());
		} else if (name.equals("-1")) {
			player.sendMessage("AREA_EFFECT_CLOUD_DURATION_ON_USE debut : " + this.area.get(Keys.AREA_EFFECT_CLOUD_DURATION_ON_USE).get());
			this.area.offer(Keys.AREA_EFFECT_CLOUD_DURATION_ON_USE, 20);
			player.sendMessage("AREA_EFFECT_CLOUD_DURATION_ON_USE fin : " + this.area.get(Keys.AREA_EFFECT_CLOUD_DURATION_ON_USE).get());
		} else if (name.equals("1")) {
			player.sendMessage("AREA_EFFECT_CLOUD_RADIUS debut : " + this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get());
			this.area.offer(Keys.AREA_EFFECT_CLOUD_RADIUS, this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get() + 10);
			player.sendMessage("AREA_EFFECT_CLOUD_RADIUS fin : " + this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get());
		} else if (name.equals("2")) {
			player.sendMessage("AREA_EFFECT_CLOUD_RADIUS debut : " + this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get());
			this.area.offer(Keys.AREA_EFFECT_CLOUD_RADIUS, this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get() - 1);
			player.sendMessage("AREA_EFFECT_CLOUD_RADIUS fin : " + this.area.get(Keys.AREA_EFFECT_CLOUD_RADIUS).get());
		} else if (name.equals("3")) {
			player.sendMessage("COLOR : RED");
			this.area.offer(Keys.COLOR, Color.RED);
		} else if (name.equals("4")) {
			player.sendMessage("COLOR : GREEN");
			this.area.offer(Keys.COLOR, Color.GREEN);
		} else if (name.equals("5")) {
			player.sendMessage("AREA_EFFECT_CLOUD_DURATION : 5000");
			this.area.offer(Keys.AREA_EFFECT_CLOUD_DURATION, 5000);
		} else if (name.equals("6")) {
			player.sendMessage("AREA_EFFECT_CLOUD_DURATION : 40");
			this.area.offer(Keys.AREA_EFFECT_CLOUD_DURATION, 40);
		} else if (name.equals("7")) {
			player.sendMessage("POTION_EFFECTS : 5000");
			this.area.offer(Keys.POTION_EFFECTS, Arrays.asList(PotionEffect.builder().potionType(PotionEffectTypes.JUMP_BOOST).duration(3000).amplifier(3).build()));
		} else if (name.equals("8")) {
			player.sendMessage("POTION_EFFECTS : 40");
			this.area.offer(Keys.POTION_EFFECTS, Arrays.asList(PotionEffect.builder().potionType(PotionEffectTypes.GLOWING).duration(3000).amplifier(3).build()));
		} else if (name.equals("9")) {
			player.sendMessage("AREA_EFFECT_CLOUD_PARTICLE_TYPE : LAVA");
			area.offer(Keys.AREA_EFFECT_CLOUD_PARTICLE_TYPE, ParticleTypes.LAVA);
		} else if (name.equals("10")) {
			player.sendMessage("AREA_EFFECT_CLOUD_PARTICLE_TYPE : MOB_SPELL");
			area.offer(Keys.AREA_EFFECT_CLOUD_PARTICLE_TYPE, ParticleTypes.MOB_SPELL);
		}
		
		return true;
	}*/
}
