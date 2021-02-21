package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PruebaJugador {
    Integer pruebaJugadorId;
    PruebaPartida pruebaPartidaId;
    ResultadoPruebaPartida resultadoPruebaPartidaId;
    JugadorPartida jugadorPartidaId;
    String informacion;

    public Integer getPruebaJugadorId() {
        return pruebaJugadorId;
    }

    public void setPruebaJugadorId(Integer pruebaJugadorId) {
        this.pruebaJugadorId = pruebaJugadorId;
    }

    public PruebaPartida getPruebaPartidaId() {
        return pruebaPartidaId;
    }

    public void setPruebaPartidaId(PruebaPartida pruebaPartidaId) {
        this.pruebaPartidaId = pruebaPartidaId;
    }

    public ResultadoPruebaPartida getResultadoPruebaPartidaId() {
        return resultadoPruebaPartidaId;
    }

    public void setResultadoPruebaPartidaId(ResultadoPruebaPartida resultadoPruebaPartidaId) {
        this.resultadoPruebaPartidaId = resultadoPruebaPartidaId;
    }

    public JugadorPartida getJugadorPartidaId() {
        return jugadorPartidaId;
    }

    public void setJugadorPartidaId(JugadorPartida jugadorPartidaId) {
        this.jugadorPartidaId = jugadorPartidaId;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public PruebaJugador insertar(SQLiteDatabase bd, Integer pruebaPartidaId, Integer resultadoPruebaPartidaId, Integer jugadorPartidaId, String informacion){
        ContentValues cv = new ContentValues();
        cv.put("pruebaPartidaId", pruebaPartidaId);
        cv.put("resultadoPruebaPartidaId", resultadoPruebaPartidaId);
        cv.put("jugadorPartidaId", jugadorPartidaId);
        cv.put("informacion", informacion);
        long id = bd.insert("PRUEBA_JUGADOR", null, cv);

        return  findById(bd, (int) id);
    }

    public PruebaJugador findById(SQLiteDatabase bd, Integer pruebaJugadorId) {
        Cursor fila = bd.rawQuery("SELECT pruebaJugadorId, pruebaPartidaId, resultadoPruebaPartidaId, jugadorPartidaId, informacion FROM PRUEBA_JUGADOR WHERE pruebaJugadorId=" + pruebaJugadorId,null);
        if (fila.moveToFirst()) {
            PruebaJugador pruebaJugador = new PruebaJugador();
            PruebaPartida pruebaPartida = new PruebaPartida();
            JugadorPartida jugadorPartida = new JugadorPartida();
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            pruebaJugador.setPruebaJugadorId(Integer.parseInt(fila.getString(0)));
            pruebaJugador.setPruebaPartidaId(pruebaPartida.findById(bd, fila.getInt(1)));
            if (fila.getString(2) != null) {
                pruebaJugador.setResultadoPruebaPartidaId(resultadoPruebaPartida.findById(bd, Integer.parseInt(fila.getString(2))));
            }
            pruebaJugador.setJugadorPartidaId(jugadorPartida.findById(bd, Integer.parseInt(fila.getString(3))));
            pruebaJugador.setInformacion(fila.getString(4));
            fila.close();
            return pruebaJugador;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<PruebaJugador> findByJugadorPartidaId(SQLiteDatabase bd, Integer jugadorPartidaId) {
        Cursor fila = bd.rawQuery("SELECT pruebaJugadorId, pruebaPartidaId, resultadoPruebaPartidaId, jugadorPartidaId, informacion FROM PRUEBA_JUGADOR WHERE jugadorPartidaId=" + jugadorPartidaId,null);
        ArrayList<PruebaJugador> pruebaJugadors = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaJugador pruebaJugador = new PruebaJugador();
            PruebaPartida pruebaPartida = new PruebaPartida();
            JugadorPartida jugadorPartida = new JugadorPartida();
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            pruebaJugador.setPruebaJugadorId(Integer.parseInt(fila.getString(0)));
            pruebaJugador.setPruebaPartidaId(pruebaPartida.findById(bd, fila.getInt(1)));
            if (fila.getString(2) != null) {
                pruebaJugador.setResultadoPruebaPartidaId(resultadoPruebaPartida.findById(bd, Integer.parseInt(fila.getString(2))));
            }
            pruebaJugador.setJugadorPartidaId(jugadorPartida.findById(bd, Integer.parseInt(fila.getString(3))));
            pruebaJugador.setInformacion(fila.getString(4));
            pruebaJugadors.add(pruebaJugador);
        }
        fila.close();
        return pruebaJugadors;
    }

    public ArrayList<PruebaJugador> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT pruebaJugadorId, pruebaPartidaId, resultadoPruebaPartidaId, jugadorPartidaId, informacion FROM PRUEBA_JUGADOR",null);
        ArrayList<PruebaJugador> pruebaJugadors = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaJugador pruebaJugador = new PruebaJugador();
            PruebaPartida pruebaPartida = new PruebaPartida();
            JugadorPartida jugadorPartida = new JugadorPartida();
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            pruebaJugador.setPruebaJugadorId(Integer.parseInt(fila.getString(0)));
            pruebaJugador.setPruebaPartidaId(pruebaPartida.findById(bd, fila.getInt(1)));
            pruebaJugador.setResultadoPruebaPartidaId(resultadoPruebaPartida.findById(bd, Integer.parseInt(fila.getString(2))));
            pruebaJugador.setJugadorPartidaId(jugadorPartida.findById(bd, Integer.parseInt(fila.getString(3))));
            pruebaJugador.setInformacion(fila.getString(4));
            pruebaJugadors.add(pruebaJugador);
        }
        fila.close();
        return pruebaJugadors;
    }
}
