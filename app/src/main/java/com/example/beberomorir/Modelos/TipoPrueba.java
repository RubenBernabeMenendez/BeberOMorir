package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TipoPrueba {
    private int TipoPruebaId;
    private String nombre;
    private String descripcion;
    private int tiempoEjecucion;

    public int getTipoPruebaId() {
        return TipoPruebaId;
    }

    public void setTipoPruebaId(int TipoPruebaId) {
        this.TipoPruebaId = TipoPruebaId;
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

    public TipoPrueba findById(SQLiteDatabase bd, int tipoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT tipoPruebaId, nombre, descripcion, tiempoEjecucion FROM TIPO_PRUEBA WHERE tipoPruebaId=" + tipoPruebaId,null);
        if (fila.moveToFirst()) {
            TipoPrueba tipoPrueba = new TipoPrueba();
            tipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoPrueba.setNombre(fila.getString(1));
            tipoPrueba.setDescripcion(fila.getString(2));
            //tipoPrueba.setTiempoEjecucion(Integer.parseInt(fila.getString(3)));
            return tipoPrueba;
        } else {
            return null;
        }
    }

    public List<TipoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT tipoPruebaId, nombre, descripcion, tiempoEjecucion FROM TIPO_PRUEBA",null);
        List<TipoPrueba> TipoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            TipoPrueba tipoPrueba = new TipoPrueba();
            tipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoPrueba.setNombre(fila.getString(1));
            tipoPrueba.setDescripcion(fila.getString(2));
            //tipoPrueba.setTiempoEjecucion(Integer.parseInt(fila.getString(3)));
            TipoPruebas.add(tipoPrueba);
        }
        return TipoPruebas;
    }
}
