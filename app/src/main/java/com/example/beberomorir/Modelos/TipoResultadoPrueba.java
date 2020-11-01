package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public TipoResultadoPrueba findById(SQLiteDatabase bd, int tipoResultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT tipoResultadoPruebaId, nombre, descripcion FROM TIPO_RESULTADO_PRUEBA WHERE tipoResultadoPruebaId=" + tipoResultadoPruebaId,null);
        if (fila.moveToFirst()) {
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            tipoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoResultadoPrueba.setNombre(fila.getString(1));
            tipoResultadoPrueba.setDescripcion(fila.getString(2));
            //tipoResultadoPrueba.setTiempoEjecucion(Integer.parseInt(fila.getString(3)));
            return tipoResultadoPrueba;
        } else {
            return null;
        }
    }

    public List<TipoResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT tipoResultadoPruebaId, nombre, descripcion FROM TIPO_RESULTADO_PRUEBA",null);
        List<TipoResultadoPrueba> tipoResultadoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            tipoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoResultadoPrueba.setNombre(fila.getString(1));
            tipoResultadoPrueba.setDescripcion(fila.getString(2));
            tipoResultadoPruebas.add(tipoResultadoPrueba);
        }
        return tipoResultadoPruebas;
    }
}
