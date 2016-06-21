package fr.evercraft.everapi.java;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilsDouble {
	
	/**
	 * Choisir le nombre de chiffre après la virgule
	 * @param value La valeur
	 * @param places Le nombre de chiffre après la virgule
	 * @return Le chiffre avec l'arrondi
	 */
	public static Double round(final double value, final int places) {
		if (places < 0){
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static Double asDouble(final Object value){
    	if ((value instanceof Number)) {
    		return ((Number)value).doubleValue();
    	} else {
    		try {
    			return Double.valueOf(value.toString()).doubleValue();
    		} catch (NumberFormatException localNumberFormatException){}
    	}
    	return 0.0D;
    }
}
