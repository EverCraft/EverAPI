package fr.evercraft.everapi.message.replace;

import java.util.function.Supplier;

public class EReplaceFun<T> implements EReplace<T> {
	
	private T value;
	private Supplier<T> fun;
	
	public EReplaceFun(Supplier<T> fun) {
		this.fun = fun;
	}
	
	@Override
	public T get() {
		if (value == null) {
			this.value = this.fun.get();
		}
		return this.value;
	}
	
	@Override
	public EReplaceFun<T> reset() {
		this.value = null;
		return this;
	}
	
	@Override
	public EReplaceFun<T> clone() {
		return new EReplaceFun<T>(this.fun);
	}
}
