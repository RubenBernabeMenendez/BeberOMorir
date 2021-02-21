package com.example.beberomorir;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Constantes {
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final int LISTA_NIVELES_PRUEBA = 4;
    public static final int LISTA_NIVELES_RESULTADOS_PRUEBA = 4;
    public static final int NUMERO_MUNDOS_NIVEL = 4;
    public static final int NUMERO_NIVELES_MUNDO_PANTALLA = 3;
    public static final int ROL_ID = 4;

    public static final int NUM_OPCIONES_RULETA = 6;
    public static final double GRADOS = 360;

    public static final int REQUEST_CODE_TAKE_PHOTO = 1;

    public static final int TIPO_RESULTADO_PRUEBA_CHECK_ID = 1000;

    public static final int TIPO_PRUEBA_YO_NUNCA_REST = 1;
    public static final int TIPO_PRUEBA_YO_NUNCA = 2;
    public static final int TIPO_PRUEBA_AZAR = 3;
    public static final int TIPO_PRUEBA_IMAGEN = 4;
    public static final int TIPO_PRUEBA_ESCRITURA = 5;
    public static final int TIPO_PRUEBA_TIEMPO = 6;
    public static final int TIPO_PRUEBA_SENALAR = 8;
    public static final int TIPO_PRUEBA_PREFERIRIAS = 7;
    public static final ArrayList TIPO_PRUEBAS_REPETIBLES = new ArrayList<Integer>() {{
            add(TIPO_PRUEBA_YO_NUNCA);
            add(TIPO_PRUEBA_SENALAR);
            add(TIPO_PRUEBA_PREFERIRIAS);
    }};

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

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
