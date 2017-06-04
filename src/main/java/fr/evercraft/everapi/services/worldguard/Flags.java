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
package fr.evercraft.everapi.services.worldguard;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.item.ItemType;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.registers.ChatType;
import fr.evercraft.everapi.registers.IceType;
import fr.evercraft.everapi.registers.SnowType;
import fr.evercraft.everapi.server.location.VirtualTransform;
import fr.evercraft.everapi.services.fire.FireType;
import fr.evercraft.everapi.services.worldguard.flag.FakeFlag;
import fr.evercraft.everapi.services.worldguard.flag.StringsFlag;
import fr.evercraft.everapi.services.worldguard.flag.StateFlag.State;
import fr.evercraft.everapi.services.worldguard.flag.value.EntityTemplateFlagValue;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;

public interface Flags {
	
	static final Flag<State> BUILD = FakeFlag.of("BUILD");
	static final Flag<State> ENDERDRAGON_GRIEF = FakeFlag.of("ENDERDRAGON_GRIEF");
	static final Flag<State> ENDERMAN_GRIEF = FakeFlag.of("ENDERMAN_GRIEF");
	static final Flag<State> ENDERPEARL = FakeFlag.of("ENDERPEARL");
	static final Flag<State> PVP = FakeFlag.of("PVP");
	static final Flag<State> INVINCIBILITY = FakeFlag.of("INVINCIBILITY");
	static final Flag<State> LIGHTNING = FakeFlag.of("LIGHTNING");
	static final Flag<State> EXP_DROP = FakeFlag.of("EXP_DROP");
	static final Flag<State> INVENTORY_DROP = FakeFlag.of("INVENTORY_DROP");
	
	static final Flag<EntryFlagValue<BlockType>> BLOCK_BREAK = FakeFlag.of("BLOCK_BREAK");
	static final Flag<EntryFlagValue<BlockType>> BLOCK_PLACE = FakeFlag.of("BLOCK_PLACE");
	static final Flag<EntryFlagValue<BlockType>> INTERACT_BLOCK = FakeFlag.of("INTERACT");
	static final Flag<EntryFlagValue<BlockType>> PROPAGATION = FakeFlag.of("PROPAGATION");
	
	static final Flag<EntryFlagValue<ItemType>> ITEM_DROP = FakeFlag.of("ITEM_DROP");
	static final Flag<EntryFlagValue<ItemType>> ITEM_PICKUP = FakeFlag.of("ITEM_PICKUP");
	
	static final Flag<EntryFlagValue<PotionEffectType>> POTION_SPLASH = FakeFlag.of("POTION_SPLASH");
	
	static final Flag<EntryFlagValue<ChatType>> CHAT = FakeFlag.of("CHAT");
	static final Flag<EntryFlagValue<FireType>> FIRE = FakeFlag.of("FIRE");
	static final Flag<EntryFlagValue<IceType>> ICE = FakeFlag.of("ICE");
	static final Flag<EntryFlagValue<SnowType>> SNOW = FakeFlag.of("SNOW");
	
	static final Flag<EntityTemplateFlagValue> DAMAGE_ENTITY = FakeFlag.of("DAMAGE_ENTITY");
	static final Flag<EntityTemplateFlagValue> INTERACT_ENTITY = FakeFlag.of("INTERACT_ENTITY");
	static final Flag<EntityTemplateFlagValue> ENTITY_DAMAGE = FakeFlag.of("ENTITY_DAMAGE");
	static final Flag<EntityTemplateFlagValue> ENTITY_SPAWNING = FakeFlag.of("ENTITY_SPAWNING");
	static final Flag<EntityTemplateFlagValue> EXPLOSION = FakeFlag.of("EXPLOSION");
	static final Flag<EntityTemplateFlagValue> EXPLOSION_BLOCK = FakeFlag.of("EXPLOSION_BLOCK");
	static final Flag<EntityTemplateFlagValue> EXPLOSION_DAMAGE = FakeFlag.of("EXPLOSION_DAMAGE");
	
	static final Flag<State> ENTRY = FakeFlag.of("ENTRY");
	static final Flag<EMessageBuilder> ENTRY_MESSAGE = FakeFlag.of("ENTRY_MESSAGE");
	static final Flag<EMessageBuilder> ENTRY_DENY_MESSAGE = FakeFlag.of("ENTRY_DENY_MESSAGE");
	
	static final Flag<State> EXIT = FakeFlag.of("EXIT");
	static final Flag<EMessageBuilder> EXIT_MESSAGE = FakeFlag.of("EXIT_MESSAGE");
	static final Flag<EMessageBuilder> EXIT_DENY_MESSAGE = FakeFlag.of("EXIT_DENY_MESSAGE");
	
	static final Flag<VirtualTransform> SPAWN = FakeFlag.of("SPAWN");
	static final Flag<VirtualTransform> TELEPORT = FakeFlag.of("TELEPORT");	
	
	static final Flag<StringsFlag> COMMAND = FakeFlag.of("COMMAND");
}
