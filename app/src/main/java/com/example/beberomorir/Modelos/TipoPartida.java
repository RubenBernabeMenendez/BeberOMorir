package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TipoPartida {
    Integer tipoPartidaId;
    String nombre;
    String descripcion;
    Integer numeroMundos;
    Integer numeroPruebasMundo;

    public Integer getTipoPartidaId() {
        return tipoPartidaId;
    }

    public void setTipoPartidaId(Integer tipoPartidaId) {
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

    public Integer getNumeroMundos() {
        return numeroMundos;
    }

    public void setNumeroMundos(Integer numeroMundos) {
        this.numeroMundos = numeroMundos;
    }

    public Integer getNumeroPruebasMundo() {
        return numeroPruebasMundo;
    }

    public void setNumeroPruebasMundo(Integer numeroPruebasMundo) {
        this.numeroPruebasMundo = numeroPruebasMundo;
    }

    public TipoPartida findById(SQLiteDatabase bd, Integer tipoPartidaId) {
        Cursor fila = bd.rawQuery("SELECT tipoPartidaId, nombre, descripcion, numeroMundos, numeroPruebasMundo FROM TIPO_PARTIDA WHERE tipoPartidaId=" + tipoPartidaId,null);
        if (fila.moveToFirst()) {
            TipoPartida tipoPartida = new TipoPartida();
            tipoPartida.setTipoPartidaId(Integer.parseInt(fila.getString(0)));
            tipoPartida.setNombre(fila.getString(1));
            tipoPartida.setDescripcion(fila.getString(2));
            tipoPartida.setNumeroMundos(Integer.parseInt(fila.getString(3)));
            tipoPartida.setNumeroPruebasMundo(Integer.parseInt(fila.getString(4)));
            fila.close();
            return tipoPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public TipoPartida findByNombre(SQLiteDatabase bd, String nombre) {
        Cursor fila = bd.rawQuery("SELECT tipoPartidaId, nombre, descripcion, numeroMundos, numeroPruebasMundo FROM TIPO_PARTIDA WHERE nombre= \'" + nombre + "\'",null);
        if (fila.moveToFirst()) {
            TipoPartida tipoPartida = new TipoPartida();
            tipoPartida.setTipoPartidaId(Integer.parseInt(fila.getString(0)));
            tipoPartida.setNombre(fila.getString(1));
            tipoPartida.setDescripcion(fila.getString(2));
            tipoPartida.setNumeroMundos(Integer.parseInt(fila.getString(3)));
            tipoPartida.setNumeroPruebasMundo(Integer.parseInt(fila.getString(4)));
            fila.close();
            return tipoPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, Integer numeroMundos, Integer numeroPruebasMundo){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("numeroMundos", numeroMundos);
        cv.put("numeroPruebasMundo", numeroPruebasMundo);
        bd.insert("TIPO_PARTIDA", null, cv);
    }

    public List<TipoPartida> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT tipoPartidaId, nombre, descripcion, numeroMundos, numeroPruebasMundo FROM TIPO_PARTIDA",null);
        List<TipoPartida> tipoPartidas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            TipoPartida tipoPartida = new TipoPartida();
            tipoPartida.setTipoPartidaId(Integer.parseInt(fila.getString(0)));
            tipoPartida.setNombre(fila.getString(1));
            tipoPartida.setDescripcion(fila.getString(2));
            tipoPartida.setNumeroMundos(Integer.parseInt(fila.getString(3)));
            tipoPartida.setNumeroPruebasMundo(Integer.parseInt(fila.getString(4)));
            tipoPartidas.add(tipoPartida);
        }
        fila.close();
        return tipoPartidas;
    }
}
