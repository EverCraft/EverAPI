package fr.evercraft.everapi.message.replace;

import java.util.function.Supplier;

public interface EReplace<T> {
	
	T get();
	
	EReplace<T> reset();
	
	EReplace<T> clone();

	public static <T> EReplace<T> of(T value) {
		return new EReplaceValue<T>(value);
	}
	
	public static <T> EReplace<T> of(Supplier<T> fun) {
		return new EReplaceFun<T>(fun);
	}
}
