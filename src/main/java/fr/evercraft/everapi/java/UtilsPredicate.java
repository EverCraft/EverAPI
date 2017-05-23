package fr.evercraft.everapi.java;

public class UtilsPredicate {
	@FunctionalInterface
	public interface TriPredicate<A, B, C> {
	    boolean test(A a, B b, C c);
	}
}
