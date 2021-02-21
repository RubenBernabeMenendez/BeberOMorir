package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EntidadResultadoPrueba {
    private Integer entidadResultadoPruebaId;
    private String nombre;
    private String descripcion;

    public Integer getEntidadResultadoPruebaId() {
        return entidadResultadoPruebaId;
    }

    public void setEntidadResultadoPruebaId(Integer entidadResultadoPruebaId) {
        this.entidadResultadoPruebaId = entidadResultadoPruebaId;
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
        bd.insert("ENTIDAD_RESULTADO_PRUEBA", null, cv);
    }

    public EntidadResultadoPrueba findById(SQLiteDatabase bd, Integer entidadResultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT entidadResultadoPruebaId, nombre, descripcion FROM ENTIDAD_RESULTADO_PRUEBA WHERE entidadResultadoPruebaId=" + entidadResultadoPruebaId,null);
        if (fila.moveToFirst()) {
            EntidadResultadoPrueba estadoResultadoPrueba = new EntidadResultadoPrueba();
            estadoResultadoPrueba.setEntidadResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoPrueba.setNombre(fila.getString(1));
            estadoResultadoPrueba.setDescripcion(fila.getString(2));
            fila.close();
            return estadoResultadoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<EntidadResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT entidadResultadoPruebaId, nombre, descripcion FROM ENTIDAD_RESULTADO_PRUEBA",null);
        List<EntidadResultadoPrueba> entidadResultadoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            EntidadResultadoPrueba estadoResultadoPrueba = new EntidadResultadoPrueba();
            estadoResultadoPrueba.setEntidadResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoPrueba.setNombre(fila.getString(1));
            estadoResultadoPrueba.setDescripcion(fila.getString(2));
            entidadResultadoPruebas.add(estadoResultadoPrueba);
        }
        fila.close();
        return entidadResultadoPruebas;
    }
}
