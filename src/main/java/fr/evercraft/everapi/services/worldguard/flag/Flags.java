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
package fr.evercraft.everapi.services.worldguard.flag;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.item.ItemType;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.server.location.VirtualTransform;
import fr.evercraft.everapi.services.entity.EntityTemplate;
import fr.evercraft.everapi.services.worldguard.flag.type.FakeFlag;
import fr.evercraft.everapi.services.worldguard.flag.type.StateFlag.State;
import fr.evercraft.everapi.services.worldguard.flag.value.EntityPatternFlagValue;
import fr.evercraft.everapi.services.worldguard.flag.value.EntryFlagValue;

public class Flags {
	
	public static final Flag<State> BUILD = FakeFlag.of("BUILD");
	public static final Flag<State> CHAT_RECEIVE = FakeFlag.of("CHAT_RECEIVE");
	public static final Flag<State> CHAT_SEND = FakeFlag.of("CHAT_SEND");
	public static final Flag<State> ENDER_DRAGON = FakeFlag.of("ENDER_DRAGON");
	public static final Flag<State> ENDERMAN_GRIEF = FakeFlag.of("ENDERMAN_GRIEF");
	public static final Flag<State> ENDERPEARL = FakeFlag.of("ENDERPEARL");
	public static final Flag<State> PVP = FakeFlag.of("PVP");
	public static final Flag<State> INVINCIBILITY = FakeFlag.of("INVINCIBILITY");
	public static final Flag<State> LIGHTNING = FakeFlag.of("LIGHTNING");
	public static final Flag<State> EXP_DROP = FakeFlag.of("EXP_DROP");
	public static final Flag<State> INVENTORY_DROP = FakeFlag.of("INVENTORY_DROP");
	
	public static final Flag<EntryFlagValue<BlockType>> BLOCK_BREAK = FakeFlag.of("BLOCK_BREAK");
	public static final Flag<EntryFlagValue<BlockType>> BLOCK_PLACE = FakeFlag.of("BLOCK_PLACE");
	public static final Flag<EntryFlagValue<BlockType>> INTERACT_BLOCK = FakeFlag.of("INTERACT");
	
	public static final Flag<EntryFlagValue<ItemType>> ITEM_DROP = FakeFlag.of("ITEM_DROP");
	public static final Flag<EntryFlagValue<ItemType>> ITEM_PICKUP = FakeFlag.of("ITEM_PICKUP");
	
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> DAMAGE_ENTITY = FakeFlag.of("DAMAGE_ENTITY");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> INTERACT_ENTITY = FakeFlag.of("INTERACT_ENTITY");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> ENTITY_DAMAGE = FakeFlag.of("ENTITY_DAMAGE");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> ENTITY_SPAWNING = FakeFlag.of("ENTITY_SPAWNING");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> EXPLOSION = FakeFlag.of("EXPLOSION");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> EXPLOSION_BLOCK = FakeFlag.of("EXPLOSION_BLOCK");
	public static final Flag<EntityPatternFlagValue<EntityTemplate, Entity>> EXPLOSION_DAMAGE = FakeFlag.of("EXPLOSION_DAMAGE");
	
	public static final Flag<State> ENTRY = FakeFlag.of("ENTRY");
	public static final Flag<EMessageBuilder> ENTRY_MESSAGE = FakeFlag.of("ENTRY_MESSAGE");
	public static final Flag<EMessageBuilder> ENTRY_DENY_MESSAGE = FakeFlag.of("ENTRY_DENY_MESSAGE");
	
	public static final Flag<State> EXIT = FakeFlag.of("EXIT");
	public static final Flag<EMessageBuilder> EXIT_MESSAGE = FakeFlag.of("EXIT_MESSAGE");
	public static final Flag<EMessageBuilder> EXIT_DENY_MESSAGE = FakeFlag.of("EXIT_DENY_MESSAGE");
	
	public static final Flag<VirtualTransform> SPAWN = FakeFlag.of("SPAWN");
	public static final Flag<VirtualTransform> TELEPORT = FakeFlag.of("TELEPORT");	
}
