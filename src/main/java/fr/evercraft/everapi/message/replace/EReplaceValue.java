package fr.evercraft.everapi.message.replace;

public class EReplaceValue<T> implements EReplace<T> {
	
	private final T value;
	
	public EReplaceValue(final T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return this.value;
	}
	
	@Override
	public EReplaceValue<T> reset() {
		return this;
	}
	
	@Override
	public EReplaceValue<T> clone() {
		return new EReplaceValue<T>(this.value);
	}
}
