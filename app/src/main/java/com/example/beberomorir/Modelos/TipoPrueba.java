package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class TipoPrueba {
    private int tipoPruebaId;
    private String nombre;
    private String descripcion;
    private int tiempoEjecucion;

    public int getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(int tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
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

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        bd.insert("TIPO_PRUEBA", null, cv);
    }
}
