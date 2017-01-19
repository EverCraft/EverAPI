package fr.evercraft.everapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongHashTable<V> {
	
	private class EntryValue extends EntryKey {
		protected V value;

		EntryValue(long k, V v) {
			super(k);
			this.value = v;
		}
	}
	
	private EntryKey[][][] values;
	EntryValue cache;
	
	public LongHashTable() {
		this.values = new EntryKey[256][][];
		this.cache = null;
	}
	
	/*
	 * Accesseur
	 */
	
	public boolean containsKey(int msw, int lsw) {
		return this.containsKey(LongHashTable.toLong(msw, lsw));
	}
	
	public V get(int msw, int lsw) {
		return this.get(LongHashTable.toLong(msw, lsw));
	}

	public void put(int msw, int lsw, V value) {
		this.put(new EntryValue(LongHashTable.toLong(msw, lsw), value));
	}
	
	public void remove(int msw, int lsw) {
		this.remove(LongHashTable.toLong(msw, lsw));
	}
	
	/*
	 * Fonction
	 */

	private synchronized void put(EntryValue entry) {
		int mainKey = (int) (entry.key & 255);
		EntryKey[][] outer = this.values[mainKey];
		if (outer == null) this.values[mainKey] = outer = new EntryKey[256][];

		int outerKey = (int) ((entry.key >> 32) & 255);
		EntryKey[] inner = outer[outerKey];

		if (inner == null) {
			outer[outerKey] = inner = new EntryKey[5];
			inner[0] = this.cache = entry;
		} else {
			int i;
			for (i = 0; i < inner.length; i++) {
				if (inner[i] == null || inner[i].key == entry.key) {
					inner[i] = this.cache = entry;
					return;
				}
			}

			outer[outerKey] = inner = Arrays.copyOf(inner, i + i);
			inner[i] = entry;
		}
	}

	private synchronized EntryValue getEntry(long key) {
		return this.containsKey(key) ? this.cache : null;
	}

	@SuppressWarnings("unchecked")
	public synchronized boolean containsKey(long key) {
		if (this.cache != null && cache.key == key) return true;

		int outerIdx = (int) ((key >> 32) & 255);
		EntryKey[][] outer = this.values[(int) (key & 255)];
		if (outer == null) return false;

		EntryKey[] inner = outer[outerIdx];
		if (inner == null) return false;

		for (int i = 0; i < inner.length; i++) {
			EntryKey e = inner[i];
			if (e == null) {
				return false;
			} else if (e.key == key) {
				this.cache = (EntryValue) e;
				return true;
			}
		}
		return false;
	}

	private synchronized void remove(long key) {
		EntryKey[][] outer = this.values[(int) (key & 255)];
		if (outer == null) return;

		EntryKey[] inner = outer[(int) ((key >> 32) & 255)];
		if (inner == null) return;

		for (int i = 0; i < inner.length; i++) {
			if (inner[i] == null) continue;

			if (inner[i].key == key) {
				for (i++; i < inner.length; i++) {
					if (inner[i] == null) break;
					inner[i - 1] = inner[i];
				}

				inner[i-1] = null;
				this.cache = null;
				return;
			}
		}
	}

	public synchronized V get(long key) {
		EntryValue entry = ((EntryValue) getEntry(key));
		return entry != null ? entry.value : null;
	}

	@SuppressWarnings("unchecked")
	public synchronized List<V> values() {
		List<V> ret = new ArrayList<V>();

		for (EntryKey[][] outer : this.values) {
			if (outer == null) continue;

			for (EntryKey[] inner : outer) {
				if (inner == null) continue;

				for (EntryKey entry : inner) {
					if (entry == null) break;

					ret.add(((EntryValue) entry).value);
				}
			}
		}
		return ret;
	}
	
	/*
	 * Static
	 */
	
	public static long toLong(int msw, int lsw) {
		return ((long) msw << 32) + lsw - Integer.MIN_VALUE;
	}

	public static int msw(long l) {
		return (int) (l >> 32);
	}

	public static int lsw(long l) {
		return (int) (l & 0xFFFFFFFF) + Integer.MIN_VALUE;
	}
}
