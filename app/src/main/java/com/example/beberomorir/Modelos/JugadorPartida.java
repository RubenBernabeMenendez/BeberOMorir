package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class JugadorPartida {
    private int jugadorPartidaId;
    private Jugador jugador;
    private int partidaId;
    private int rolId;

    public int getJugadorPartidaId() {
        return jugadorPartidaId;
    }

    public void setJugadorPartidaId(int jugadorPartidaId) {
        this.jugadorPartidaId = jugadorPartidaId;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public JugadorPartida insertar(SQLiteDatabase bd, int jugadorId, int partidaId, int rolId){
        ContentValues cv = new ContentValues();
        cv.put("jugadorId", jugadorId);
        cv.put("partidaId", partidaId);
        cv.put("rolId", rolId);
        long id = bd.insert("JUGADOR_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public JugadorPartida findById(SQLiteDatabase bd, int jugadorPartidaId) {
        Cursor fila = bd.rawQuery("SELECT jugadorPartidaId, jugadorId, partidaId, rolId FROM JUGADOR_PARTIDA WHERE jugadorPartidaId=" + jugadorPartidaId,null);
        if (fila.moveToFirst()) {
            JugadorPartida jugadorPartida = new JugadorPartida();
            Jugador jugador = new Jugador();
            jugadorPartida.setJugadorPartidaId(fila.getInt(0));
            jugadorPartida.setJugador(jugador.findById(bd, fila.getInt(1)));
            jugadorPartida.setRolId(fila.getInt(3));
            jugadorPartida.setPartidaId(fila.getInt(2));
            fila.close();
            return jugadorPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<JugadorPartida> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT jugadorPartidaId, jugadorId, partidaId, rolId FROM JUGADOR_PARTIDA",null);
        ArrayList<JugadorPartida> Jugadors = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            JugadorPartida jugadorPartida = new JugadorPartida();
            Jugador jugador = new Jugador();
            jugadorPartida.setJugadorPartidaId(fila.getInt(0));
            jugadorPartida.setJugador(jugador.findById(bd, fila.getInt(1)));
            jugadorPartida.setRolId(fila.getInt(3));
            jugadorPartida.setPartidaId(fila.getInt(2));
            Jugadors.add(jugadorPartida);
        }
        fila.close();
        return Jugadors;
    }
}
