package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class JugadorPartida {
    private Integer jugadorPartidaId;
    private Jugador jugador;
    private Integer partidaId;
    private Integer rolId;

    public Integer getJugadorPartidaId() {
        return jugadorPartidaId;
    }

    public void setJugadorPartidaId(Integer jugadorPartidaId) {
        this.jugadorPartidaId = jugadorPartidaId;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Integer getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Integer partidaId) {
        this.partidaId = partidaId;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public JugadorPartida insertar(SQLiteDatabase bd, Integer jugadorId, Integer partidaId, Integer rolId){
        ContentValues cv = new ContentValues();
        cv.put("jugadorId", jugadorId);
        cv.put("partidaId", partidaId);
        cv.put("rolId", rolId);
        long id = bd.insert("JUGADOR_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public JugadorPartida findById(SQLiteDatabase bd, Integer jugadorPartidaId) {
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
