package com.example.beberomorir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.beberomorir.Modelos.*;

import androidx.annotation.Nullable;

public class AdminSQLDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "beberOmorirBD";
    private static final int DB_VERSION = 4;
    private static final String TIPO_PARTIDA_TABLE_CREATE = "CREATE TABLE TIPO_PARTIDA(tipoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, numeroMundos INTEGER, numeroPruebasMundo INTEGER)";
    //private static final String NIVEL_PARTIDA_TABLE_CREATE = "CREATE TABLE NIVEL_PARTIDA(nivelPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadosPruebas INTEGER)";
    private static final String CONFIG_PARTIDA_TABLE_CREATE = "CREATE TABLE CONFIG_PARTIDA(configPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadoPruebas INTEGER, tipoPartidaId INTEGER, rolesJugador TEXT, FOREIGN KEY (tipoPartidaId) REFERENCES TIPO_PARTIDA(tipoPartidaId))";
    private static final String PARTIDA_TABLE_CREATE = "CREATE TABLE PARTIDA(partidaId INTEGER PRIMARY KEY AUTOINCREMENT, configPartidaId INTEGER, fecha DATE, nombre TEXT, descripcion TEXT, finalizada TEXT, mundoPartidaActualId INTEGER, FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId))";
    private static final String TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_PRUEBA(tipoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_RESULTADO_PRUEBA(configPartidaId INTEGER, tipoResultadoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoResultadoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId))";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_PRUEBA(configPartidaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String JUGADOR_TABLE_CREATE = "CREATE TABLE JUGADOR(jugadorId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apodo TEXT, urlImagen BLOB)";
    private static final String MUNDO_TABLE_CREATE = "CREATE TABLE MUNDO(mundoId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String PRUEBA_TABLE_CREATE = "CREATE TABLE PRUEBA(pruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelPrueba INTEGER, tiempoEjecicion INTEGER, tipoPruebaId INTEGER, FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE RESULTADO_PRUEBA(resultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelResultadoPrueba INTEGER, tipoResultadoPruebaId INTEGER, estadoResultadoPruebaId INTEGER, FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId), FOREIGN KEY (estadoResultadoPruebaId) REFERENCES ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId))";

    private static final String TIPO_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PARTIDA";
    //private static final String NIVEL_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS NIVEL_PARTIDA";
    private static final String CONFIG_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_PARTIDA";
    private static final String PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS PARTIDA";
    private static final String TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PRUEBA";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_RESULTADO_PRUEBA";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ESTADO_RESULTADO_PRUEBA";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_TIPO_RESULTADO_PRUEBA";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_TIPO_PRUEBA";
    private static final String JUGADOR_TABLE_DROP = "DROP TABLE IF EXISTS JUGADOR";
    private static final String MUNDO_TABLE_DROP = "DROP TABLE IF EXISTS MUNDO";
    private static final String PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS PRUEBA";
    private static final String RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS RESULTADO_PRUEBA";

    public AdminSQLDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AdminSQLDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_DROP);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_DROP);
        sqLiteDatabase.execSQL(MUNDO_TABLE_DROP);
        sqLiteDatabase.execSQL(PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_TABLE_DROP);

        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_CREATE);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_CREATE);
        sqLiteDatabase.execSQL(MUNDO_TABLE_CREATE);
        sqLiteDatabase.execSQL(PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_TABLE_CREATE);


        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPrueba.insertar(sqLiteDatabase, "Beber", "En esta prueba hay que beber X");
        TipoPartida tipoPartida = new TipoPartida();
        tipoPartida.insertar(sqLiteDatabase, "Mundos corta", "Experiencia corta", 3, 3);
        ConfigPartida configPartida = new ConfigPartida();
        configPartida.insertar(sqLiteDatabase, 2,2, 1, "N");
        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Beber", "Puedes repartir tragos");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Reto", "Puedes repartir reto");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Confesion", "Puedes repartir confesion");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca restrictivo", "En esta prueba hay que crear un yo nunca para que beban todos menos");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca", "En esta prueba hay que leer un yo nunca");
        tipoPrueba.insertar(sqLiteDatabase, "Azar", "En esta prueba hay que girar una ruleta");
        tipoPrueba.insertar(sqLiteDatabase, "Foto con", "En esta prueba hay que hacerse una foto con");
        tipoPrueba.insertar(sqLiteDatabase, "Foto haciendo", "En esta prueba hay hacerse una foto haciendo");
        tipoPrueba.insertar(sqLiteDatabase, "Reto", "En esta prueba hay que retar a X con algo");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_DROP);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_DROP);
        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_CREATE);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_CREATE);
        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPrueba.insertar(sqLiteDatabase, "Beber", "En esta prueba hay que beber X");
        TipoPartida tipoPartida = new TipoPartida();
        tipoPartida.insertar(sqLiteDatabase, "Mundos corta", "Experiencia corta", 3, 3);
        ConfigPartida configPartida = new ConfigPartida();
        configPartida.insertar(sqLiteDatabase, 2,2, 1, "N");
        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Beber", "Puedes repartir tragos");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Reto", "Puedes repartir reto");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Confesion", "Puedes repartir confesion");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca restrictivo", "En esta prueba hay que crear un yo nunca para que beban todos menos");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca", "En esta prueba hay que leer un yo nunca");
        tipoPrueba.insertar(sqLiteDatabase, "Azar", "En esta prueba hay que girar una ruleta");
        tipoPrueba.insertar(sqLiteDatabase, "Foto con", "En esta prueba hay que hacerse una foto con");
        tipoPrueba.insertar(sqLiteDatabase, "Foto haciendo", "En esta prueba hay hacerse una foto haciendo");
        tipoPrueba.insertar(sqLiteDatabase, "Reto", "En esta prueba hay que retar a X con algo");
    }
}
