package com.example.beberomorir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.beberomorir.Modelos.*;

import androidx.annotation.Nullable;

public class AdminSQLDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "beberOmorirBD";
    private static final int DB_VERSION = 1;
    private static final String TIPO_PARTIDA_TABLE_CREATE = "CREATE TABLE TIPO_PARTIDA(tipoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, numeroMundos INTEGER, numeroPruebasMundo INTEGER)";
    //private static final String NIVEL_PARTIDA_TABLE_CREATE = "CREATE TABLE NIVEL_PARTIDA(nivelPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadosPruebas INTEGER)";
    private static final String CONFIG_PARTIDA_TABLE_CREATE = "CREATE TABLE CONFIG_PARTIDA(configPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadoPruebas INTEGER, tipoPartidaId INTEGER, rolesJugador TEXT, FOREIGN KEY (tipoPartidaId) REFERENCES TIPO_PARTIDA(tipoPartidaId))";
    private static final String PARTIDA_TABLE_CREATE = "CREATE TABLE PARTIDA(partidaId INTEGER PRIMARY KEY AUTOINCREMENT, configPartidaId INTEGER, fecha DATE, nombre TEXT, descripcion TEXT, finalizada TEXT, mundoPartidaActualId INTEGER, FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId))";
    private static final String TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_PRUEBA(tipoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, entidadResultadoPruebaId INTEGER)";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, visible TEXT)";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, visible TEXT)";
    private static final String ENTIDAD_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE ENTIDAD_RESULTADO_PRUEBA(entidadResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE ENTIDAD_RESULTADO_TIPO_PRUEBA(entidadResultadoPruebaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (entidadResultadoPruebaId, tipoPruebaId), FOREIGN KEY (entidadResultadoPruebaId) REFERENCES ENTIDAD_RESULTADO_PRUEBA(entidadResultadoPruebaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_RESULTADO_PRUEBA(configPartidaId INTEGER, tipoResultadoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoResultadoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId))";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_PRUEBA(configPartidaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String JUGADOR_TABLE_CREATE = "CREATE TABLE JUGADOR(jugadorId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apodo TEXT, urlImagen BLOB)";
    private static final String JUGADOR_PARTIDA_TABLE_CREATE = "CREATE TABLE JUGADOR_PARTIDA(jugadorPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, partidaId INTEGER, jugadorId INTEGER, rolId INTEGER)";
    private static final String MUNDO_TABLE_CREATE = "CREATE TABLE MUNDO(mundoId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, urlImagen INTEGER)";
    private static final String PRUEBA_TABLE_CREATE = "CREATE TABLE PRUEBA(pruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelPrueba INTEGER, tiempoEjecicion INTEGER, tipoPruebaId INTEGER, FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE RESULTADO_PRUEBA(resultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelResultadoPrueba INTEGER, tipoResultadoPruebaId INTEGER, estadoResultadoPruebaId INTEGER, FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId), FOREIGN KEY (estadoResultadoPruebaId) REFERENCES ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId))";
    private static final String MUNDO_PARTIDA_TABLE_CREATE = "CREATE TABLE MUNDO_PARTIDA(mundoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, mundoId INTEGER, partidaId INTEGER, orden INTEGER, nivelMundo INTEGER, urlImagen INTEGER, finalizado TEXT, FOREIGN KEY (mundoId) REFERENCES MUNDO(mundoId), FOREIGN KEY (partidaId) REFERENCES PARTIDA(partidaId))";
    private static final String PRUEBA_PARTIDA_TABLE_CREATE = "CREATE TABLE PRUEBA_PARTIDA(pruebaPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, mundoPartidaId INTEGER, pruebaId INTEGER, nombre TEXT, descripcion TEXT, finalizado TEXT, FOREIGN KEY (mundoPartidaId) REFERENCES MUNDO_PARTIDA(mundoPartidaId), FOREIGN KEY (pruebaId) REFERENCES PRUEBA(pruebaId))";
    private static final String MUNDO_PARTIDA_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE MUNDO_PARTIDA_TIPO_PRUEBA(mundoPartidaId INTEGER, tipoPruebaId INTEGER, numeroTiposPrueba INTEGER, PRIMARY KEY (mundoPartidaId, tipoPruebaId), FOREIGN KEY (mundoPartidaId) REFERENCES MUNDO_PARTIDA(mundoPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";


    private static final String TIPO_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PARTIDA";
    //private static final String NIVEL_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS NIVEL_PARTIDA";
    private static final String CONFIG_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_PARTIDA";
    private static final String PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS PARTIDA";
    private static final String TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PRUEBA";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_RESULTADO_PRUEBA";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ESTADO_RESULTADO_PRUEBA";
    private static final String ENTIDAD_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ENTIDAD_RESULTADO_PRUEBA";
    private static final String ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ENTIDAD_RESULTADO_TIPO_PRUEBA";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_TIPO_RESULTADO_PRUEBA";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_TIPO_PRUEBA";
    private static final String JUGADOR_TABLE_DROP = "DROP TABLE IF EXISTS JUGADOR";
    private static final String JUGADOR_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS JUGADOR_PARTIDA";
    private static final String MUNDO_TABLE_DROP = "DROP TABLE IF EXISTS MUNDO";
    private static final String PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS PRUEBA";
    private static final String RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS RESULTADO_PRUEBA";
    private static final String MUNDO_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS MUNDO_PARTIDA";
    private static final String PRUEBA_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS PRUEBA_PARTIDA";
    private static final String MUNDO_PARTIDA_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS MUNDO_PARTIDA_TIPO_PRUEBA";

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
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_DROP);
        sqLiteDatabase.execSQL(JUGADOR_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(MUNDO_TABLE_DROP);
        sqLiteDatabase.execSQL(PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_TABLE_DROP);
        sqLiteDatabase.execSQL(MUNDO_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(PRUEBA_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(MUNDO_PARTIDA_TIPO_PRUEBA_TABLE_DROP);

        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_CREATE);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(JUGADOR_TABLE_CREATE);
        sqLiteDatabase.execSQL(JUGADOR_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(MUNDO_TABLE_CREATE);
        sqLiteDatabase.execSQL(PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(MUNDO_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PRUEBA_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(MUNDO_PARTIDA_TIPO_PRUEBA_TABLE_CREATE);


        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca restrictivo","En esta prueba hay que crear un yo nunca para que beban todos menos");
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca","En esta prueba hay que leer un yo nunca y todos los participantes deben responder bebiendo o no");
        tipoPrueba.insertar(sqLiteDatabase, "Azar","En esta prueba hay que girar una ruleta");
        tipoPrueba.insertar(sqLiteDatabase, "Reto imagen","En esta prueba hay que conseguir realizar una fotografía con unos requisitos");
        tipoPrueba.insertar(sqLiteDatabase, "Reto escritura","En esta prueba hay que conseguir realizar una composición escrita con unos requisitos");
        tipoPrueba.insertar(sqLiteDatabase, "Reto tiempo TIC TAC","En esta prueba hay que conseguir calcular un número de segundos");
        tipoPrueba.insertar(sqLiteDatabase, "Qué preferirías","En esta prueba el jugador del turno debe responer a una pregunta, después todos los demás jugadores responderán también");
        tipoPrueba.insertar(sqLiteDatabase, "Señalar es de maleducados","En esta prueba se planteará una situación y los jugadores deben escoger qué jugador se adapta mejor con la situación");

        EntidadResultadoPrueba entidadResultadoPrueba = new EntidadResultadoPrueba();
        entidadResultadoPrueba.insertar(sqLiteDatabase, "El jugador","El ganador recibe el premio/castigo");
        entidadResultadoPrueba.insertar(sqLiteDatabase, "Todos","Todos reciben el premio/castigo");
        entidadResultadoPrueba.insertar(sqLiteDatabase, "Los que coinciden","Los que coincidan con el jugador");
        entidadResultadoPrueba.insertar(sqLiteDatabase, "Los NO que coinciden","Los que NO coincidan con el jugador");
        entidadResultadoPrueba.insertar(sqLiteDatabase, "La mayoría","La mayoría recibe el premio/castigo");
        entidadResultadoPrueba.insertar(sqLiteDatabase, "La minoría","La minoría recibe el premio/castigo");

        EntidadResultadoTipoPrueba entidadResultadoTipoPrueba = new EntidadResultadoTipoPrueba();
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase, 1,1);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,2,2);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,1,3);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,2,3);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,1,4);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,1,5);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,1,6);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,3,7);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,4,7);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,5,7);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,6,7);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,3,8);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,4,8);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,5,8);
        entidadResultadoTipoPrueba.insertar(sqLiteDatabase,6,8);

        Prueba prueba = new Prueba();
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado...", "Yo nunca me he corrido más de dos veces en un día", 2,10, 2);

        TipoPartida tipoPartida = new TipoPartida();
        tipoPartida.insertar(sqLiteDatabase, "Mundos corta", "Experiencia corta", 3, 3);


        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Beber", "Puedes repartir tragos", Constantes.YES, Constantes.YES);
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Reto", "Puedes repartir reto", Constantes.YES, Constantes.YES);
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Confesion", "Puedes repartir confesion", Constantes.YES, Constantes.YES);




        Mundo mundo = new Mundo();
        mundo.insertar(sqLiteDatabase, "Mundo 1", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 2", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 3", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 4", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 5", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 6", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 7", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 8", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 9", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 10", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 11", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
        mundo.insertar(sqLiteDatabase, "Mundo 12", "", Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
