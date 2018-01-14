package utils;

public final class Constantes {
	public static final Integer PROFESOR = 2;

	public static final Integer ALUMNO = 3;
	public static final Integer COORDINADOR = 1;
	public static final boolean VERDADERO = true;
	public static final boolean FALSO = false;
	
	public static boolean isNumeric(String cadena){
		try {
			cadena.trim();
			Long.valueOf(cadena).longValue();
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

}