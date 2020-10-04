package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class TipoResultadoPrueba {
    private int tipoResultadoPruebaId;
    private String nombre;
    private String descripcion;

    public int getTipoResultadoPruebaId() {
        return tipoResultadoPruebaId;
    }

    public void setTipoResultadoPruebaId(int tipoResultadoPruebaId) {
        this.tipoResultadoPruebaId = tipoResultadoPruebaId;
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

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        bd.insert("TIPO_RESULTADO_PRUEBA", null, cv);
    }
}
