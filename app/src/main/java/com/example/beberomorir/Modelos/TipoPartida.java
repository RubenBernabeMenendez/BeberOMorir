package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TipoPartida {
    int tipoPartidaId;
    String nombre;
    String descripcion;
    int numeroMundos;
    int numeroPruebasMundo;

    public int getTipoPartidaId() {
        return tipoPartidaId;
    }

    public void setTipoPartidaId(int tipoPartidaId) {
        this.tipoPartidaId = tipoPartidaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeroMundos() {
        return numeroMundos;
    }

    public void setNumeroMundos(int numeroMundos) {
        this.numeroMundos = numeroMundos;
    }

    public int getNumeroPruebasMundo() {
        return numeroPruebasMundo;
    }

    public void setNumeroPruebasMundo(int numeroPruebasMundo) {
        this.numeroPruebasMundo = numeroPruebasMundo;
    }

    public TipoPartida findById(SQLiteDatabase bd, int tipoPartidaId) {
        Cursor fila = bd.rawQuery("SELECT tipoPartidaId, nombre, descripcion, numeroMundos, numeroPruebasMundo FROM TIPO_PARTIDA WHERE tipoPartidaId=" + tipoPartidaId,null);
        if (fila.moveToFirst()) {
            TipoPartida tipoPartida = new TipoPartida();
            tipoPartida.setTipoPartidaId(Integer.parseInt(fila.getString(0)));
            tipoPartida.setNombre(fila.getString(1));
            tipoPartida.setDescripcion(fila.getString(2));
            tipoPartida.setNumeroMundos(Integer.parseInt(fila.getString(3)));
            tipoPartida.setNumeroPruebasMundo(Integer.parseInt(fila.getString(4)));
            return tipoPartida;
        } else {
            return null;
        }
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, int numeroMundos, int numeroPruebasMundo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("numeroMundos", numeroMundos);
        cv.put("numeroPruebasMundo", numeroPruebasMundo);
        bd.insert("TIPO_PARTIDA", null, cv);
    }
}
