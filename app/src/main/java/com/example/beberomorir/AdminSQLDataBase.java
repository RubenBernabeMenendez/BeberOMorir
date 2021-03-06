package com.example.beberomorir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.beberomorir.Modelos.*;

import java.util.Random;

import androidx.annotation.Nullable;

public class AdminSQLDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "beberOmorirBD";
    private static final int DB_VERSION = 1;
    private static final String TIPO_PARTIDA_TABLE_CREATE = "CREATE TABLE TIPO_PARTIDA(tipoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, numeroMundos INTEGER, numeroPruebasMundo INTEGER)";
    //private static final String NIVEL_PARTIDA_TABLE_CREATE = "CREATE TABLE NIVEL_PARTIDA(nivelPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadosPruebas INTEGER)";
    private static final String CONFIG_PARTIDA_TABLE_CREATE = "CREATE TABLE CONFIG_PARTIDA(configPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, nivelPruebas INTEGER, nivelResultadoPruebas INTEGER, tipoPartidaId INTEGER, rolesJugador TEXT, FOREIGN KEY (tipoPartidaId) REFERENCES TIPO_PARTIDA(tipoPartidaId))";
    private static final String PARTIDA_TABLE_CREATE = "CREATE TABLE PARTIDA(partidaId INTEGER PRIMARY KEY AUTOINCREMENT, configPartidaId INTEGER, fecha DATE, nombre TEXT, descripcion TEXT, finalizada TEXT, mundoPartidaActualId INTEGER, FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId))";
    private static final String TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_PRUEBA(tipoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, urlImagen INTEGER)";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, visible TEXT)";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, activo TEXT, visible TEXT)";
    private static final String ENTIDAD_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE ENTIDAD_RESULTADO_PRUEBA(entidadResultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT)";
    private static final String ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE ENTIDAD_RESULTADO_TIPO_PRUEBA(entidadResultadoPruebaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (entidadResultadoPruebaId, tipoPruebaId), FOREIGN KEY (entidadResultadoPruebaId) REFERENCES ENTIDAD_RESULTADO_PRUEBA(entidadResultadoPruebaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String ESTADO_RESULTADO_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE ESTADO_RESULTADO_TIPO_PRUEBA(estadoResultadoPruebaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (estadoResultadoPruebaId, tipoPruebaId), FOREIGN KEY (estadoResultadoPruebaId) REFERENCES ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String CONFIG_TIPO_RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_RESULTADO_PRUEBA(configPartidaId INTEGER, tipoResultadoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoResultadoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId))";
    private static final String CONFIG_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE CONFIG_TIPO_PRUEBA(configPartidaId INTEGER, tipoPruebaId INTEGER, PRIMARY KEY (configPartidaId, tipoPruebaId), FOREIGN KEY (configPartidaId) REFERENCES CONFIG_PARTIDA(configPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String JUGADOR_TABLE_CREATE = "CREATE TABLE JUGADOR(jugadorId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apodo TEXT, urlImagen BLOB)";
    private static final String JUGADOR_PARTIDA_TABLE_CREATE = "CREATE TABLE JUGADOR_PARTIDA(jugadorPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, partidaId INTEGER, jugadorId INTEGER, rolId INTEGER)";
    private static final String MUNDO_TABLE_CREATE = "CREATE TABLE MUNDO(mundoId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, urlImagen INTEGER)";
    private static final String PRUEBA_TABLE_CREATE = "CREATE TABLE PRUEBA(pruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelPrueba INTEGER, tiempoEjecucion INTEGER, tipoPruebaId INTEGER, FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String RESULTADO_PRUEBA_TABLE_CREATE = "CREATE TABLE RESULTADO_PRUEBA(resultadoPruebaId INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, descripcion TEXT, nivelResultadoPrueba INTEGER, tipoResultadoPruebaId INTEGER, estadoResultadoPruebaId INTEGER, entidadResultadoPruebaId INTEGER, urlImagen INTEGER, urlImagenEntidad INTEGER, FOREIGN KEY (entidadResultadoPruebaId) REFERENCES ENTIDAD_RESULTADO_PRUEBA(entidadResultadoPruebaId), FOREIGN KEY (tipoResultadoPruebaId) REFERENCES TIPO_RESULTADO_PRUEBA(tipoResultadoPruebaId), FOREIGN KEY (estadoResultadoPruebaId) REFERENCES ESTADO_RESULTADO_PRUEBA(estadoResultadoPruebaId))";
    private static final String MUNDO_PARTIDA_TABLE_CREATE = "CREATE TABLE MUNDO_PARTIDA(mundoPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, mundoId INTEGER, partidaId INTEGER, orden INTEGER, nivelMundo INTEGER, urlImagen INTEGER, finalizado TEXT, FOREIGN KEY (mundoId) REFERENCES MUNDO(mundoId), FOREIGN KEY (partidaId) REFERENCES PARTIDA(partidaId))";
    private static final String PRUEBA_PARTIDA_TABLE_CREATE = "CREATE TABLE PRUEBA_PARTIDA(pruebaPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, mundoPartidaId INTEGER, pruebaId INTEGER, nombre TEXT, descripcion TEXT, finalizado TEXT, FOREIGN KEY (mundoPartidaId) REFERENCES MUNDO_PARTIDA(mundoPartidaId), FOREIGN KEY (pruebaId) REFERENCES PRUEBA(pruebaId))";
    private static final String MUNDO_PARTIDA_TIPO_PRUEBA_TABLE_CREATE = "CREATE TABLE MUNDO_PARTIDA_TIPO_PRUEBA(mundoPartidaId INTEGER, tipoPruebaId INTEGER, numeroTiposPrueba INTEGER, PRIMARY KEY (mundoPartidaId, tipoPruebaId), FOREIGN KEY (mundoPartidaId) REFERENCES MUNDO_PARTIDA(mundoPartidaId), FOREIGN KEY (tipoPruebaId) REFERENCES TIPO_PRUEBA(tipoPruebaId))";
    private static final String RESULTADO_PRUEBA_PARTIDA_TABLE_CREATE = "CREATE TABLE RESULTADO_PRUEBA_PARTIDA(resultadoPruebaPartidaId INTEGER PRIMARY KEY AUTOINCREMENT, mundoPartidaId INTEGER, resultadoPruebaId INTEGER, nombre TEXT, descripcion TEXT, FOREIGN KEY (mundoPartidaId) REFERENCES MUNDO_PARTIDA(mundoPartidaId), FOREIGN KEY (resultadoPruebaId) REFERENCES RESULTADO_PRUEBA(resultadoPruebaId))";
    private static final String PRUEBA_RESULTADO_RELACI_TABLE_CREATE = "CREATE TABLE PRUEBA_RESULTADO_RELACI(resultadoPruebaPartidaId INTEGER, pruebaPartidaId INTEGER, PRIMARY KEY (resultadoPruebaPartidaId, pruebaPartidaId), FOREIGN KEY (resultadoPruebaPartidaId) REFERENCES RESULTADO_PRUEBA_PARTIDA(estadoResultadoPruebaId), FOREIGN KEY (pruebaPartidaId) REFERENCES PRUEBA_PARTIDA(pruebaPartidaId))";
    private static final String PRUEBA_JUGADOR_TABLE_CREATE = "CREATE TABLE PRUEBA_JUGADOR(pruebaJugadorId INTEGER PRIMARY KEY AUTOINCREMENT, pruebaPartidaId INTEGER, resultadoPruebaPartidaId INTEGER, jugadorPartidaId INTEGER, informacion TEXT, FOREIGN KEY (resultadoPruebaPartidaId) REFERENCES RESULTADO_PRUEBA_PARTIDA(resultadoPruebaPartidaId), FOREIGN KEY (pruebaPartidaId) REFERENCES PRUEBA_PARTIDA(pruebaPartidaId), FOREIGN KEY (jugadorPartidaId) REFERENCES JUGADOR_PARTIDA(jugadorPartidaId))";

    private static final String TIPO_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PARTIDA";
    //private static final String NIVEL_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS NIVEL_PARTIDA";
    private static final String CONFIG_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS CONFIG_PARTIDA";
    private static final String PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS PARTIDA";
    private static final String TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_PRUEBA";
    private static final String TIPO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS TIPO_RESULTADO_PRUEBA";
    private static final String ESTADO_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ESTADO_RESULTADO_PRUEBA";
    private static final String ENTIDAD_RESULTADO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ENTIDAD_RESULTADO_PRUEBA";
    private static final String ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ENTIDAD_RESULTADO_TIPO_PRUEBA";
    private static final String ESTADO_RESULTADO_TIPO_PRUEBA_TABLE_DROP = "DROP TABLE IF EXISTS ESTADO_RESULTADO_TIPO_PRUEBA";
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
    private static final String RESULTADO_PRUEBA_PARTIDA_TABLE_DROP = "DROP TABLE IF EXISTS RESULTADO_PRUEBA_PARTIDA";
    private static final String PRUEBA_RESULTADO_RELACI_TABLE_DROP = "DROP TABLE IF EXISTS PRUEBA_RESULTADO_RELACI";
    private static final String PRUEBA_JUGADOR_TABLE_DROP = "DROP TABLE IF EXISTS PRUEBA_JUGADOR";

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
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_TIPO_PRUEBA_TABLE_DROP);
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
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_PARTIDA_TABLE_DROP);
        sqLiteDatabase.execSQL(PRUEBA_RESULTADO_RELACI_TABLE_DROP);
        sqLiteDatabase.execSQL(PRUEBA_JUGADOR_TABLE_DROP);

        sqLiteDatabase.execSQL(TIPO_PARTIDA_TABLE_CREATE);
        //sqLiteDatabase.execSQL(NIVEL_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(CONFIG_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(TIPO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ENTIDAD_RESULTADO_TIPO_PRUEBA_TABLE_CREATE);
        sqLiteDatabase.execSQL(ESTADO_RESULTADO_TIPO_PRUEBA_TABLE_CREATE);
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
        sqLiteDatabase.execSQL(RESULTADO_PRUEBA_PARTIDA_TABLE_CREATE);
        sqLiteDatabase.execSQL(PRUEBA_RESULTADO_RELACI_TABLE_CREATE);
        sqLiteDatabase.execSQL(PRUEBA_JUGADOR_TABLE_CREATE);


        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca restrictivo","En esta prueba hay que crear un yo nunca para que beban todos menos", R.mipmap.yonuncarest_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Yo nunca","En esta prueba hay que leer un yo nunca y todos los participantes deben responder bebiendo o no", R.mipmap.yonunca_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Azar","En esta prueba hay que girar una ruleta", R.mipmap.azar_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Reto imagen","En esta prueba hay que conseguir realizar una fotografía con unos requisitos", R.mipmap.foto_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Reto escritura","En esta prueba hay que conseguir realizar una composición escrita con unos requisitos", R.mipmap.escritura_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Reto tiempo TIC TAC","En esta prueba hay que conseguir calcular un número de segundos", R.mipmap.tiktak_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Qué preferirías","En esta prueba el jugador del turno debe responer a una pregunta, después todos los demás jugadores responderán también", R.mipmap.elegir_prueba);
        tipoPrueba.insertar(sqLiteDatabase, "Señalar es de maleducados","En esta prueba se planteará una situación y los jugadores deben escoger qué jugador se adapta mejor con la situación", R.mipmap.senalar_prueba);

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

        EstadoResultadoPrueba estadoResultadoPrueba = new EstadoResultadoPrueba();
        estadoResultadoPrueba.insertar(sqLiteDatabase, "Correcto", "Prueba Superada");
        estadoResultadoPrueba.insertar(sqLiteDatabase, "Fallo", "Prueba NO Superada");

        EstadoResultadoTipoPrueba estadoResultadoTipoPrueba = new EstadoResultadoTipoPrueba();
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 1);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 1);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 2);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 3);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 3);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 4);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 4);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 5);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 5);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 6);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 6);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 7);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 2, 7);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 8);
        estadoResultadoTipoPrueba.insertar(sqLiteDatabase, 1, 9);

        Prueba prueba = new Prueba();
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado...", "Yo nunca me he corrido más de dos veces en un día", 2,10, 2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he corrido más de dos veces en un día",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca he participado en un trío",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Te unes?","Yo nunca he sido pillado masturbándome",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cotillas, mirones...","Yo nunca he pillado a un familiar masturbándose",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cotillas, mirones...","Yo nunca he pillado a un familiar teniendo sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cotillas, mirones...","Yo nunca he pillado a un amigo teniendo sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en un sitio público",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en el mar",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en el probador de una tienda",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en un sitio público",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en un coche",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca lo he hecho en una moto",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca he participado en una orgía",2,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca me he sentido atraído por alguien del mismo sexo",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por alguien de este grupo",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por un profesor o profesora",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por un familiar",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por la pareja de una amistad",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por una persona del trabajo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he meado en la calle",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he sido pillado borracho al llegar a casa",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he sido pillado fumado al llegar a casa",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he estado esposado",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he sido detenido por la policía",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he robado algo de menos de 100 euros",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he robado algo de más de 100 euros",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca me he grabado o tomado una foto teniendo sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he tenido sexo telefónico",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he tenido sexo con alguien que acababa de conocer",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca me he masturbado con un objeto extraño",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he fingido un orgasmo",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he mentido en el “yo nunca”",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he ligado con la pareja de una amistad",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca me he masturbado fuera de casa",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he fingido estar enfermo para no ir al trabajo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he fingido estar enfermo para no ir a la escuela",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he hablado por Whatsapp con un amigo mientras estaba en el lavabo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he probado la comida para animales",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca le he escrito un mensaje borracho/a a mi ex",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he sentido atraído por una persona mayor de 40",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he espiado la nueva pareja de mi ex por redes",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he robado copas de una discoteca",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he hablado mal de una persona de este grupo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca me he liado con una persona de este grupo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he tenido más de tres orgasmos en un día",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he tenido más de dos orgasmos en una cita",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca he usado juguetes sexuales con mi pareja",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he sido infiel",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he tenido una relación abierta con mi pareja",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Alcohólic@? Sí ¿por?","Yo nunca he vomitado en público después de beber",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Alcohólic@? Sí ¿por?","Yo nunca he perdido el conocimiento después de beber",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Pringad@s por aquí?","Yo nunca me he tropezado por mirar el móvil",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Alcohólic@? Sí ¿por?","Yo nunca he ido a trabajar borracho",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he mentido a mis amigos",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he mentido a mi pareja",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca he hecho un baile erótico",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Atrevid@s?","Yo nunca he recibido un baile erótico",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he tomado la pastilla del día después",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he enviado un mensaje “hot” a un contacto equivocado",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he paseado por casa desnudo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he paseado por la calle sin ropa interior",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he consumido drogas",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he fumado en un bong",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he cantado en la ducha",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he liado con una persona casada",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca me he vestido con ropa del sexo opuesto",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he visto a mis padres desnudos",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cotillas, mirones...","Yo nunca he pillado a mis padres teniendo sexo",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca me he hecho un test de embarazo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca me he masturbado más de 3 veces en un día",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he tenido sexo con mi expareja",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he usado lubricante sexual",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he recibido un cumplido por mis genitales",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Salvaje? Sí ¿por?","Yo nunca he roto la cama durante el sexo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Salvaje? Sí ¿por?","Yo nunca he probado más de 5 posturas durante el sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca me he emborrachado por la mañana",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he probado el cibersexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he jugado a ser otra persona durante el sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he gritado el nombre de otra persona durante el sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he hecho cosas indecentes en el cine",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he hecho cosas indecentes en compañía de amigos",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he llorado por una película",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he usado preservativos de sabores",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he besado a alguien sin saber su nombre",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he visto porno con mi pareja",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he hecho juegos de bondage",2,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he hecho un streaptease",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he recibido un streaptease",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he hecho sexo oral conduciendo",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he tenido que huir de la policía",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he pasado la noche en el cuartelillo",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he jugado al streap poker",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca he tenido algo con alguien de esta habitación",2,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca he tenido sexo en la cama de mis padres",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Os animáis?","Yo nunca me he acostado con dos personas el mismo día",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he ligado con alguien estando en pareja",1,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he fingido dormir para no tener sexo",2,10,2);
        prueba.insertar(sqLiteDatabase, "Cuidado cuidado…","Yo nunca he tenido un fetiche extraño",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Y quién no?","Yo nunca he llegado al orgasmo con el sexo oral",2,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca he tenido sexo en la cocina",1,10,2);
        prueba.insertar(sqLiteDatabase, "Why not?","Yo nunca he tenido sexo en casa de otra persona",1,10,2);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener 5 hijos propios o no poder tener hijos y adoptar?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: estar calvo y musculoso o gordo y con pelazo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser invisible o leer la mente?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser rico y no encontrar el amor o vivir con la pareja ideal y ser pobre?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: saberlo todo y que nada te sorprenda o no saberlo y que todo te sorprenda?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no poder comer carne o no poder comer verdura?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ganar la lotería o que tu peor enemigo pierda todo el dinero?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener mal aliento o mal olor corporal?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no volver a perder el teléfono o las llaves?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: solo ser capaz de gritar o solo poder susurrar?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener 0 años siempre o tener 50 siempre?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: poder ser invisible o poder volar?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: hablar con los animales o con los muertos?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: curar el hambre en el mundo o frenar el cambio climático?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un famoso con una vida turbia o una persona anónima y feliz?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un actor famoso o un cantante famoso?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: reencarnarte en una mosca o en un gusano?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: Wi-fi gratis para siempre o café gratis en cualquier lado?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: vivir tu peor pesadilla durante una noche y perder el miedo o no hacerlo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: cantar o bailar en público? (En caso de que te de vergüenza)",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: cambiar de color según tus emociones o tatuajes de lo que hiciste ayer?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: poder leer la mente o ser invisible?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: acabar con el machismo o con el racismo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un gigante o del tamaño de una pulga?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser millonario (y que toda tu familia lo sea) o encontrar una cura para el VIH?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: un coche volador o un coche acuático?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no salir nunca de tu ciudad o salir y no poder volver?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: sólo comer picante o sólo comer cosas liquidas y puré?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no hacer colas nunca más o que todos los semáforos se pusieran en verde al pasar?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener algo en el ojo para siempre o andar siempre de forma extraña?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un vampiro o un hombre lobo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener un chef particular o un chófer particular?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no afeitarte durante un año o no ponerte desodorante en un año?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no volver a comer chocolate o no volver a comer queso?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: vestir siempre ropa de invierno o ropa de verano?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no poder beber cerveza o sólo beber cerveza?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: un botón de pausa en tu vida o un botón de rebobinar?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: una mente adulta atrapada en el cuerpo de un niño o viceversa?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no comprender a nadie o que nadie te comprenda?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: que nadie vaya a tu boda o que nadie acuda a tu funeral?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no poder calmar tu sed o no poder calmar tu hambre?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener la respuesta a todo y ser infeliz o no saberlo y ser feliz?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no distinguir colores o tener siempre un leve pitido en los oídos?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener un pezón o dos ombligos?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: despertar en mitad de un desierto o en un bote en mitad del océano?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: vivir en la Antártida o en el Sáhara?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no sentir frío nunca o no sentir calor nunca?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser una persona anónima en la actualidad o ser un rey de la Edad Media?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: pasar el resto de tu vida en la cárcel o en una isla desierta?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no tener cejas o tener sólo una ceja?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: perder 500 euros o que tu peor enemigo caiga en la quiebra?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: hablar con los animales o hablar todos los idiomas?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener 30 centímetros de altura o pesar 200 kilos?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser inmortal o la persona más rica que existe?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un genio muy feo o un tonto con cuerpazo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: cambiar de sexo o ser rico durante una semana?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: controlar lo que sueñas o ver tus sueños al día siguiente?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ir siempre en chándal o en traje?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser un vagabundo o un criminal adinerado?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: un millón de dólares ahora mismo o diez millones dentro de 20 años?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: poder ver el futuro o tener todos tus recuerdos grabados en vídeo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener mejor visión o mejor oído?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: que te dejen por Whatsapp o en público?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no poder besar a tu pareja o no poder abrazarla?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: perder todo el pelo o tener pelo por todo el cuerpo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no volver a sentir tristeza o no poder experimentar la alegría?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: ser la persona más divertida del mundo o la más inteligente?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: eructar de forma involuntaria o tirarte pedos incontrolablemente?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener lengua de serpiente o ojos de sapo?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: vivir un apocalipsis zombie o la Tercera Guerra Mundial?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: no volver a enfermar o no volver a tener una situación vergonzosa?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: desaparecer siempre que quieras o hacer desaparecer a los demás?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: que nunca te pillen cuando mientes o saber siempre cuándo te mienten?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: amanecer con la peor resaca a diario o no poder salir más de fiesta?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Elegimos?","¿Qué preferirías: tener un tercer ojo o una segunda boca?",1,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: tener sexo con cualquier persona que desees o ser multimillonario?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: enterarte de que te han puesto los cuernos o no saberlo nunca?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: una relación sin amor con el mejor sexo o una relación ideal sin sexo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: que tu pareja te pille poniéndole los cuernos o pillar a tu pareja?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: durar siempre más de 2 horas en la cama o durar siempre 4 minutos?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: una pareja que cocine de forma espectacular o que sea experta en el sexo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: llegar siempre una hora antes o una hora tarde?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: fingir un orgasmo delante de tu familia o en público?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: sexo con alguien atractivo que te cae mal o con un feo que te cae bien?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: masturbarte 1 vez al año o quedarte sin Wi-fi?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: recibir un aviso cuando tus padres tienen sexo o que en la oficina reciban un aviso siempre que tienes sexo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: gritar ‘te amo, mamá’ siempre que llegas al orgasmo o gritar el nombre de tu ex?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: llegar al orgasmo siempre que te acercas a una fruta o no volver a tener orgasmos?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: morir congelado o quemado vivo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: morir ahogado o por un fallo del paracaídas?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: perder la capacidad de comunicarte o que los demás sepan lo que piensa?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: vivir para siempre o morir en un mes?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: tener un bebé mañana mismo o a los 6 años?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: comer de un plato suculento lleno de pelos o de mocos?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: nacer sin nariz o sin orejas?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: tener tres brazos o tres piernas?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: no poder ducharte en un año o tener que ir desnudo todo un año?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: diarrea permanente o migraña permanente?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: trabajar como un esclavo todo un año o pasar un año siendo un vagabundo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: tener sexo con una anciana en privado o con tu pareja en público?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: matar a un inocente o a 5 personas que han cometido un delito menor?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: quedarte sin manos o sin pies?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: besar a un vagabundo por 10 euros o tener sexo con él por 1.000?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: matar a una anciana moribunda o a un gatito recién nacido?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: matar a tu mejor amigo o al resto de amigos?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: estar en la cárcel 1 años o en coma 2 años?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: vender todas tus pertenencias o un órgano?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: matar a un bebé o a 1 adultos?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: no volver a usar ropa interior o usar ropa interior usada?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: saber la fecha de tu muerte o la causa de tu muerte?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: matar a alguien cada semana o ir desnudo un día de la semana?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: comer un brownie con sabor a caca o una caca con sabor a brownie?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: cortarte una pierna o pasar 3 años en la cárcel?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: cortarte una pierna o un brazo?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: ser un dictador o vivir siempre en la pobreza?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: bañarte entre cucarachas o entre caca?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: defecar o tener sexo en mitad de la calle?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: que un miembro aleatorio de tu familia muera o empezar la Tercera Guerra Mundial?",2,10,7);
        prueba.insertar(sqLiteDatabase, "¿Nos mojamos?","¿Qué prefieres: pillar a tus padres teniendo sexo o que tus padres te pillen a ti?",2,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Prefieres probar el juego de rol sexual o probar el “sado”? ¿Sexo juguetón o duro?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías dejar que tu pareja te domine en el sexo o dominar a tu pareja durante el sexo?¿Eres más del tipo dominante o sumiso?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo en la posición del misionero o perrito? ¿Te gusta la forma tradicional o por detrás?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías ser estrangulado o abofeteado mientras tienes sexo? Ambas opciones son grandes maneras de intensificar tu orgasmo (siempre que te guste)",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Prefieres caminar con una erección durante seis horas al día, o no volver a tener una erección?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Prefieres tener sexo anal o sexo oral?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías ser una chica/chico de cámara o publicar un video sexual tuyo?¿Cómo quieres empezar tu carrera porno?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías unirte a una orgia o a un trío?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con una persona que muerda o que grite mucho? ¿Qué te pone más?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías solo poder tener sexo mientras ves la película de terror más aterradora o sólo mientras ves “Dora la Exploradora”?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no..."," ¿Preferirías hacer cosas románticas en la cama o probar algunas cosas pervertidas?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías averiguar que tu pareja se acostó con tu padre o tu pareja se acostó con tu mejor amigo/a?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías que te hiciesen “fisting” o hacérselo tu a alguien? O te lo tomas como un placer o como una tortura",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías masturbarte todo el día o tener sexo durante todo el día? Después de ese día, no vas a ser capaz de tener sexo en mucho tiempo",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Prefieres que tu pareja sólo pueda usar sus manos durante los preliminares, o que tu pareja sólo pueda usar la boca?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías no tener sexo nunca más o no volver a masturbarte? La gente va y viene, pero tus manos nunca te dejarán",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con el período el resto de tu vida o tener sexo anal el resto de tu vida?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías hacer un trio con una persona que conoces o con un extraño?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener una pareja con adicción al sexo o con un bajo deseo sexual?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con mi mejor amiga o con tu mejor amiga?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías probar el “fisting” o la doble penetración? Esta pregunta también va dirigida a los hombres",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías ponerle los cuernos a tu pareja o sugerirle de hacer un trio?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías que te hagan los dedos debajo de la mesa de un restaurante o debajo de la mesa en la casa de tus padres?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías hacerle una mamada profunda a alguien, o tener sexo anal?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías gritar cada vez que te corres “Amo a Jesús” o gritar “tengo una ETS?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías se malo besando o malo en el sexo oral?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías dejar que tu pareja durmiese con tu mejor amigo o con la pareja de tu mejor amigo? ¿Te pondrías muy celoso?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con alguien que tuviese un cuerpo bonito, pero una cara fea o con alguien que tuviese un cuerpo feo pero una cara bonita?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Prefieres tragar o escupir? ¡Se honesto!",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías se capaz de tener sexo durante 6 horas, o no volver a tener sexo nunca más?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías no tener sexo otra vez, o tener que usar Bing como buscador el resto de tu vida?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no..."," ¿Preferirías tener una pareja que se corra en 60 segundos o que tarde 3 horas en correrse? ¿Tienes resistencia?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías recibir una alerta cada vez que tus padres tienen sexo, o que tu oficina se alerte cada vez que tienes sexo?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con una persona que se niegue a quitarse los calcetines, o con alguien que ponga cara rara al llegar al orgasmo? ¿Por qué tienen los calcetines tan mala reputación?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías ver a tu pareja masturbándose o dejar que tu pareja mire cuando te masturbas?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con los ojos vendados o mientras estas esposado?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener una pareja a la que no le guste el sexo oral o una pareja obsesionada con el sexo anal?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías tener sexo con alguien que tenga acento alemán o acento ruso?",3,10,7);
        prueba.insertar(sqLiteDatabase, "Manos quietas, o no...","¿Preferirías que comerle el culo a tu pareja o que tu pareja te coma el culo? ¿Quieres hacer tú el trabajo o que lo haga tu pareja?",3,10,7);

        prueba.insertar(sqLiteDatabase, "Quién será será…","Señalar a la persona que creéis es más rechulón",1,10,8);
        prueba.insertar(sqLiteDatabase, "Se viene la suerte","Elegir entre resultadoPruebas",1,10,3);
        prueba.insertar(sqLiteDatabase, "A pensar…","Realiza un yo nunca para beber todos menos tú",1,10,1);
        prueba.insertar(sqLiteDatabase, "Posa posa","Hazte una foto con un árbol",1,10,4);
        prueba.insertar(sqLiteDatabase, "Hazte un Neruda","Escribe 4 versos que rimen con polla",1,10,5);
        prueba.insertar(sqLiteDatabase, "A contar","Cuenta hasta 30 segundos y pulsa el botón",1,10,6);


        TipoPartida tipoPartida = new TipoPartida();
        tipoPartida.insertar(sqLiteDatabase, "Mundos corta", "Experiencia corta", 3, 3);


        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Beber","Repartir/tomar tragos","Y","Y");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Confesión","Debes confesar/obligar a confesar algo","Y","Y");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "StripPoker","Debes quitarte/obligar a quitar algo","Y","Y");
        tipoResultadoPrueba.insertar(sqLiteDatabase, "Regla","Puedes imponer/ser impuesto a una regla durante X tiempo","Y","Y");



        ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Manda beber 3 chupitos",2,1,1,1,calcularImagenResultado(1,2),calcularImagenEntidadResultado(1,1));
        resultadoPrueba.insertar(sqLiteDatabase, "Toca mojarse","¿Quién está borrach@?",1,2,1,2,calcularImagenResultado(2,1),calcularImagenEntidadResultado(2,1));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Beber todos 1 chupito",1,1,1,2,calcularImagenResultado(1,1),calcularImagenEntidadResultado(2,1));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Manda beber entre 1 y 3 chupitos a @Jugador",2,1,1,1,calcularImagenResultado(1,2),calcularImagenEntidadResultado(1,1));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho"," @Jugador te manda beber entre 1 y 3 chupitos",2,1,2,1,calcularImagenResultado(1,2),calcularImagenEntidadResultado(1,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","El último que coja su vaso se lo tiene que terminar",2,1,1,2,calcularImagenResultado(1,2),calcularImagenEntidadResultado(2,1));
        resultadoPrueba.insertar(sqLiteDatabase, "Hace un poco de calor ¿no?","Todos los que lleven una prenda/complemento de color rojo deben elegir uno y quitárselo",1,3,1,2,calcularImagenResultado(3,1),calcularImagenEntidadResultado(2,1));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Los que coincidan mandar beber entre 1 y 3 chupitos",1,1,1,3,calcularImagenResultado(1,1),calcularImagenEntidadResultado(3,1));
        resultadoPrueba.insertar(sqLiteDatabase, "Toca mojarse","¿Cuántas veces te has tocado como máximo en un día?",1,2,1,2,calcularImagenResultado(2,1),calcularImagenEntidadResultado(2,1));
        resultadoPrueba.insertar(sqLiteDatabase, "¿Eres sociable?","Publica en una red social algún mensaje con el texto \"Quiero que seas mi papi/mami\" y menciona a @Jugador",2,4,2,1,calcularImagenResultado(4,2),calcularImagenEntidadResultado(1,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Los que no coincidan beben 1 chupito",1,1,2,4,calcularImagenResultado(1,1),calcularImagenEntidadResultado(4,2));
        resultadoPrueba.insertar(sqLiteDatabase, "Hace un poco de calor ¿no?","La minoría manda quitar una prenda",1,3,1,5,calcularImagenResultado(3,1),calcularImagenEntidadResultado(5,1));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","La mayoría manda beber 1 chupito a alguien de la minoría",1,1,1,6,calcularImagenResultado(1,1),calcularImagenEntidadResultado(6,1));
        resultadoPrueba.insertar(sqLiteDatabase, "Toca mojarse","¿Quién quiere pillar esta noche?",1,2,2,2,calcularImagenResultado(2,1),calcularImagenEntidadResultado(2,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Beber todos 1 chupito",1,1,2,2,calcularImagenResultado(1,1),calcularImagenEntidadResultado(2,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Los que coincidan beben 2 chupitos",2,1,2,3,calcularImagenResultado(1,2),calcularImagenEntidadResultado(3,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","Los que no coincidan mandan beben 1 chupito",1,1,1,4,calcularImagenResultado(1,1),calcularImagenEntidadResultado(4,1));
        resultadoPrueba.insertar(sqLiteDatabase, "Hace un poco de calor ¿no?","La minoría se quita una prenda",1,3,2,5,calcularImagenResultado(3,1),calcularImagenEntidadResultado(5,2));
        resultadoPrueba.insertar(sqLiteDatabase, "A beber se ha dicho","La mayoría bebe 1 chupito",1,1,2,6,calcularImagenResultado(1,1),calcularImagenEntidadResultado(6,2));



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

    private Integer calcularImagenResultado(Integer tipoResultadoId, Integer nivel) {
        switch (tipoResultadoId) {
            case Constantes.TIPO_RESULTADO_BEBER:
                switch (nivel) {
                    case 1:
                        return R.mipmap.chupitos_level1;

                    case 2:
                        return R.mipmap.chupitos_level2;

                    case 3:
                        return R.mipmap.chupitos_level3;

                    case 4:
                        return R.mipmap.chupitos_level4;

                    default:
                        return R.mipmap.chupitos_level3;
                }
            case Constantes.TIPO_RESULTADO_CONFESION:
                return R.mipmap.secreto;
            case Constantes.TIPO_RESULTADO_RETO:
                return R.mipmap.normas;
            case Constantes.TIPO_RESULTADO_STREAP:
                Random random = new Random();
                int aux = random.nextInt(2);
                if (aux == 1) {
                    return R.mipmap.ropa_level1;
                } else {
                    return R.mipmap.ropa_level1_1;
                }

            default:
                return R.mipmap.default_prueba;
        }
    }

    private Integer calcularImagenEntidadResultado(Integer entidadResultadoId, Integer estadoResultadoId) {
        switch (entidadResultadoId) {
            case Constantes.ENTIDAD_RESULTADO_JUGADOR:
                if (estadoResultadoId == Constantes.ESTADO_RESULTADO_CORRECTO) {
                    return R.mipmap.ganador;
                } else {
                    return R.mipmap.perdedor;
                }
            case Constantes.ENTIDAD_RESULTADO_TODOS:
                if (estadoResultadoId == Constantes.ESTADO_RESULTADO_CORRECTO) {
                    return R.mipmap.todos;
                } else {
                    return R.mipmap.perdedor;
                }
            case Constantes.ENTIDAD_RESULTADO_COINCIDEN:
                return R.mipmap.coinciden;
            case Constantes.ENTIDAD_RESULTADO_NO_COINCIDEN:
                return R.mipmap.no_coinciden;
            case Constantes.ENTIDAD_RESULTADO_MAYORIA:
                return R.mipmap.mayoria;
            case Constantes.ENTIDAD_RESULTADO_MINORIA:
                return R.mipmap.minoria;
            default:
                return R.mipmap.todos;
        }
    }
}
