package fr.evercraft.everapi.message.replace;

import java.util.function.Supplier;

public class EReplace<T> {
	
	private T value;
	private Supplier<T> fun;
	
	public EReplace(Supplier<T> fun) {
		this.fun = fun;
	}
	
	public T get() {
		if (value == null) {
			this.value = this.fun.get();
		}
		return this.value;
	}
	
	public EReplace<T> reset() {
		this.value = null;
		return this;
	}
	
	public EReplace<T> clone() {
		return new EReplace<T>(this.fun);
	}

	public static <T> EReplace<T> of(T value) {
		return new EReplace<T>(() -> value);
	}
	
	public static <T> EReplace<T> of(Supplier<T> value) {
		return new EReplace<T>(value);
	}
}
