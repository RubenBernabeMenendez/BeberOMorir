package com.example.beberomorir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.beberomorir.Modelos.*;

import androidx.annotation.Nullable;

public class AdminSQLDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "beberOmorirBD";
    private static final int DB_VERSION = 2;
    private static final String TIPO_PARTIDA_TABLE_CREATE = "CREATE TABLE TIPO_PARTIDA(tipoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, numeroMundos INTEGER, numeroPruebasMundo INTEGER)";
    private static final String NIVEL_PARTIDA_TABLE_CREATE = "CREATE TABLE NIVEL_PARTIDA(nivelPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadosPruebas INTEGER)";
    private static final String CONFIG_PARTIDA_TABLE_CREATE = "CREATE TABLE CONFIG_PARTIDA(configPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadoPruebas INTEGER, tipoPartidaId INTEGER, rolesJugador TEXT, FOREIGN KEY (tipoPartidaId) REFERENCES TIPO_PARTIDA(tipoPartidaId))";
    private static final String PARTIDA_TABLE_CREATE = "CREATE TABLE PARTIDA(partidaId INTEGER PRIMARY KEY AUTOINCREMENT, configPartidaId INTEGER, fecha DATE, nombre TEXT, descripcion TEXT, finalizada TEXT, mundoPartidaActualId INTEGER, FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId))";
    private static final String TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_PRUEBA(tipoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, tiempoEjecucion INTEGER)";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_RESULTADO_PRUEBA(configPartidaId INTEGER, tipoResultadoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoResultadoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId))";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_PRUEBA(configPartidaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String JUGADOR_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_PRUEBA(jugadorId INTEGER, nombre TEXT, apodo TEXT, urlImagen TEXT, PRIMARY KEY (configPartidaId))";

    public AdminSQLDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AdminSQLDataBase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_CREATE);
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
        Jugador jugador = new Jugador();
        jugador.insertar(sqLiteDatabase, "Jugador 1", "Pringao", "");
        jugador.insertar(sqLiteDatabase, "Jugador 2", "Superpringao", "");
        jugador.insertar(sqLiteDatabase, "Jugador 3", "Hiperpringao", "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Beber", "Puedes repartir tragos");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Reto", "Puedes repartir reto");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Confesion", "Puedes repartir confesion");
        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca restrictivo", "En esta prueba hay que crear un yo nunca para que beban todos menos");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca", "En esta prueba hay que leer un yo nunca");
        tipoPrueba.insertar(sqLiteDatabase, "Azar", "En esta prueba hay que girar una ruleta");
        tipoPrueba.insertar(sqLiteDatabase, "Foto con", "En esta prueba hay que hacerse una foto con");
        tipoPrueba.insertar(sqLiteDatabase, "Foto haciendo", "En esta prueba hay hacerse una foto haciendo");
        tipoPrueba.insertar(sqLiteDatabase, "Reto", "En esta prueba hay que retar a X con algo");

    }
}
