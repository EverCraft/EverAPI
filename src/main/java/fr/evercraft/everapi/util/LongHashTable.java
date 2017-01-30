package fr.evercraft.everapi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LongHashTable<V> {
	
	private class EntryValue extends EntryKey {
		protected V value;

		EntryValue(long k, V v) {
			super(k);
			this.value = v;
		}
	}
	
	private EntryKey[][][] values;
	
	// MultiThreading
	private final ReadWriteLock lock;
	private final Lock write_lock;
	private final Lock read_lock;
	
	public LongHashTable() {
		this.values = new EntryKey[256][][];
		
		// MultiThreading
		this.lock = new ReentrantReadWriteLock();
		this.write_lock = this.lock.writeLock();
		this.read_lock = this.lock.readLock();
	}
	
	/*
	 * Accesseurs
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
	
	public V remove(int msw, int lsw) {
		return this.remove(LongHashTable.toLong(msw, lsw));
	}
	
	/*
	 * Fonctions
	 */

	private void put(EntryValue entry) {
		this.write_lock.lock();
		try {
			int mainKey = (int) (entry.key & 255);
			EntryKey[][] outer = this.values[mainKey];
			if (outer == null) this.values[mainKey] = outer = new EntryKey[256][];
	
			int outerKey = (int) ((entry.key >> 32) & 255);
			EntryKey[] inner = outer[outerKey];
	
			if (inner == null) {
				outer[outerKey] = inner = new EntryKey[5];
				inner[0] = entry;
			} else {
				int i;
				for (i = 0; i < inner.length; i++) {
					if (inner[i] == null || inner[i].key == entry.key) {
						inner[i] = entry;
						return;
					}
				}
	
				outer[outerKey] = inner = Arrays.copyOf(inner, i + i);
				inner[i] = entry;
			}
		} finally {
			this.write_lock.unlock();
		}
	}

	private boolean containsKey(long key) {
		this.read_lock.lock();
		try {
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
					return true;
				}
			}
		} finally {
			this.read_lock.unlock();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private V remove(long key) {
		this.write_lock.lock();
		try {
			EntryKey[][] outer = this.values[(int) (key & 255)];
			if (outer == null) return null;
	
			EntryKey[] inner = outer[(int) ((key >> 32) & 255)];
			if (inner == null) return null;
	
			for (int i = 0; i < inner.length; i++) {
				if (inner[i] == null) continue;
	
				if (inner[i].key == key) {
					V value = ((EntryValue) inner[i]).value;
					for (i++; i < inner.length; i++) {
						if (inner[i] == null) break;
						inner[i - 1] = inner[i];
					}
					inner[i-1] = null;
					return value;
				}
			}
		} finally {
			this.write_lock.unlock();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public V get(long key) {
		this.read_lock.lock();
		try {
			int outerIdx = (int) ((key >> 32) & 255);
			EntryKey[][] outer = this.values[(int) (key & 255)];
			if (outer == null) return null;
	
			EntryKey[] inner = outer[outerIdx];
			if (inner == null) return null;
	
			for (int i = 0; i < inner.length; i++) {
				EntryKey e = inner[i];
				if (e == null) {
					return null;
				} else if (e.key == key) {
					return ((EntryValue) e).value;
				}
			}
		} finally {
			this.read_lock.unlock();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		List<V> ret = new ArrayList<V>();

		this.read_lock.lock();
		try {
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
		} finally {
			this.read_lock.unlock();
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
