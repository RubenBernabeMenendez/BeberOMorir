package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Jugador {
    int jugadorId;
    String nombre;
    String apodo;
    String urlImagen;
    String seleccionado;

    public int getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(int jugadorId) {
        this.jugadorId = jugadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(String seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Jugador insertar(SQLiteDatabase bd, String nombre, String apodo, String urlImagen){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("apodo", apodo);
        cv.put("urlImagen", urlImagen);
        Long id = bd.insert("JUGADOR", null, cv);

        Jugador jugador = findById(bd, id.intValue());
        return jugador;
    }

    public Jugador findById(SQLiteDatabase bd, int jugadorId) {
        Cursor fila = bd.rawQuery("SELECT jugadorId, nombre, apodo, urlImagen FROM JUGADOR WHERE jugadorId=" + jugadorId,null);
        if (fila.moveToFirst()) {
            Jugador jugador = new Jugador();
            jugador.setJugadorId(Integer.parseInt(fila.getString(0)));
            jugador.setNombre(fila.getString(1));
            jugador.setApodo(fila.getString(2));
            jugador.setUrlImagen(fila.getString(3));
            return jugador;
        } else {
            return null;
        }
    }

    public ArrayList<Jugador> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT jugadorId, nombre, apodo, urlImagen FROM JUGADOR",null);
        ArrayList<Jugador> Jugadors = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            Jugador jugador = new Jugador();
            jugador.setJugadorId(Integer.parseInt(fila.getString(0)));
            jugador.setNombre(fila.getString(1));
            jugador.setApodo(fila.getString(2));
            jugador.setUrlImagen(fila.getString(3));
            Jugadors.add(jugador);
        }
        return Jugadors;
    }
}
