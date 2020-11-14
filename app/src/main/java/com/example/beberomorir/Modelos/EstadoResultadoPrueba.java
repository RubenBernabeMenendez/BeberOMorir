package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EstadoResultadoPrueba {
    private int estadoResultadoPruebaId;
    private String nombre;
    private String descripcion;

    public int getTipoResultadoPruebaId() {
        return estadoResultadoPruebaId;
    }

    public void setTipoResultadoPruebaId(int estadoResultadoPruebaId) {
        this.estadoResultadoPruebaId = estadoResultadoPruebaId;
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
        bd.insert("ESTADO_RESULTADO_PRUEBA", null, cv);
    }

    public EstadoResultadoPrueba findById(SQLiteDatabase bd, int estadoResultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT estadoResultadoPruebaId, nombre, descripcion FROM ESTADO_RESULTADO_PRUEBA WHERE estadoResultadoPruebaId=" + estadoResultadoPruebaId,null);
        if (fila.moveToFirst()) {
            EstadoResultadoPrueba estadoResultadoPrueba = new EstadoResultadoPrueba();
            estadoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoPrueba.setNombre(fila.getString(1));
            estadoResultadoPrueba.setDescripcion(fila.getString(2));
            fila.close();
            return estadoResultadoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<EstadoResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT estadoResultadoPruebaId, nombre, descripcion FROM ESTADO_RESULTADO_PRUEBA",null);
        List<EstadoResultadoPrueba> estadoResultadoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            EstadoResultadoPrueba estadoResultadoPrueba = new EstadoResultadoPrueba();
            estadoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoPrueba.setNombre(fila.getString(1));
            estadoResultadoPrueba.setDescripcion(fila.getString(2));
            estadoResultadoPruebas.add(estadoResultadoPrueba);
        }
        fila.close();
        return estadoResultadoPruebas;
    }
}
