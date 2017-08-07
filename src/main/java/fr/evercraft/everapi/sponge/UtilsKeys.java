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
package fr.evercraft.everapi.sponge;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.value.mutable.CompositeValueStore;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Axis;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.util.Direction;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;

import fr.evercraft.everapi.plugin.EChat;

import ninja.leaping.configurate.ConfigurationNode;

public class UtilsKeys {
	
	private static final String EMPTY = "EMPTY";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <B extends BaseValue<T>, T> KeyValue<B, T> parse(String key_string, ConfigurationNode value) throws IllegalArgumentException {
		
		if (!key_string.contains(":")) {
			key_string = "sponge:" + key_string;
		}
		
		Optional<Key> optKey = Sponge.getGame().getRegistry().getType(Key.class, key_string);
		if (!optKey.isPresent()) throw new IllegalArgumentException();
		Key<B> key = optKey.get();
		
		try {
			return new KeyValue<B, T>(key, UtilsKeys.get((TypeToken<T>) key.getElementToken(), value));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}	
	
	@SuppressWarnings("unchecked")
	private static <V, S> V get(TypeToken<V> token, ConfigurationNode value_json) throws Exception {
		String value_string = value_json.getString(null);
		if (value_string != null && value_string.contains("")) {
			
		}
		
		Class<? super V> type_value = token.getRawType();
		if (CatalogType.class.isAssignableFrom(type_value)) {
			return (V) Sponge.getGame().getRegistry().getType((Class<CatalogType>) type_value, value_json.getString("")).orElseThrow(() -> new IllegalArgumentException());
		} else if (type_value.equals(String.class)) {
			return (V) value_json.getString("");
		} else if (type_value.equals(Text.class)) {
			return (V) EChat.of(value_json.getString(""));
		} else if (type_value.equals(Integer.class)) {
			return (V) (Integer) value_json.getInt();
		} else if (type_value.equals(Double.class)) {
			return (V) (Double) value_json.getDouble();
		} else if (type_value.equals(Float.class)) {
			return (V) (Float) value_json.getFloat();
		} else if (type_value.equals(Short.class)) {
			return (V) (Integer) value_json.getInt();
		} else if (type_value.equals(Long.class)) {
			return (V) (Long) value_json.getLong();
		} else if (type_value.equals(Boolean.class)) {
			return (V) (Boolean) value_json.getBoolean();
		} else if (type_value.equals(Vector3i.class)) {
			return (V) value_json.getValue(TypeToken.of(Vector3i.class));
		} else if (type_value.equals(Vector3d.class)) {
			return (V) value_json.getValue(TypeToken.of(Vector3d.class));
		} else if (type_value.equals(UUID.class)) {
			return (V) UUID.fromString(value_json.getString());
		} else if (type_value.equals(Instant.class)) {
			return (V) Instant.ofEpochMilli(value_json.getLong());
		} else if (type_value.equals(Color.class)) {
			try {
				return (V) Color.of(value_json.getValue(TypeToken.of(Vector3i.class)));
			} catch (Exception e) {
				return (V) EChat.getTextColor(value_json.getString("")).getColor();
			}
		} else if (type_value.equals(Direction.class)) {
			return (V) Direction.valueOf(value_json.getString(""));
		} else if (type_value.equals(Axis.class)) {
			return (V) Axis.valueOf(value_json.getString(""));
		} else if (type_value.equals(List.class)) {
			TypeToken<S> newToken = (TypeToken<S>) token.resolveType(List.class.getTypeParameters()[0]);
			List<S> list = new ArrayList<S>();
			for (ConfigurationNode value : value_json.getChildrenList()) {
				list.add(UtilsKeys.get(newToken, value));
			}
			return (V) list;
		} else if (type_value.equals(Set.class)) {
			TypeToken<S> newToken = (TypeToken<S>) token.resolveType(Set.class.getTypeParameters()[0]);
			Set<S> set = new HashSet<S>();
			for (ConfigurationNode value : value_json.getChildrenList()) {
				set.add(UtilsKeys.get(newToken, value));
			}
			return (V) set;
		} else if (type_value.equals(Optional.class)) {
			if (value_json.getString("").equalsIgnoreCase(EMPTY)) {
				return (V) Optional.empty();
			} else {
				TypeToken<S> newToken = (TypeToken<S>) token.resolveType(Optional.class.getTypeParameters()[0]);
				return (V) Optional.ofNullable(UtilsKeys.get(newToken, value_json));
			}
		} else {
			System.err.println(" : " + type_value.getName());
		}
		return null;
	}
	
	public static class KeyValue<B extends BaseValue<T>, T> {
		private final Key<B> key;
		private final T value;
		
		public KeyValue(Key<B> key, T value) {
			this.key = key;
			this.value = value;
		}

		public Key<B> getKey() {
			return key;
		}

		public T getValue() {
			return value;
		}
		
		public <S extends CompositeValueStore<S, H>, H extends ValueContainer<?> > boolean apply(CompositeValueStore<S, H> object) {
			return object.offer(this.key, this.value).isSuccessful();
		}
		
		public <S extends CompositeValueStore<S, H>, H extends ValueContainer<?> > boolean contains(CompositeValueStore<S, H> object) {
			return object.get(this.key).filter(value -> value.equals(this.value)).isPresent();
		}
	}
}
