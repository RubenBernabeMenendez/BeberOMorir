package com.example.beberomorir;

public class Constantes {
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final int LISTA_NIVELES_PRUEBA = 4;
    public static final int LISTA_NIVELES_RESULTADOS_PRUEBA = 4;

    public static final int TIPO_RESULTADO_PRUEBA_CHECK_ID = 1000;

    public static boolean booleanToString(String activo) {
        return YES.equals(activo);
    }

    public static String stringToBoolean(Boolean b){
        if (b)
            return YES;
        else
            return NO;
    }
}
