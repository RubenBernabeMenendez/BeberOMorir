package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TipoResultadoPrueba {
    private Integer tipoResultadoPruebaId;
    private String nombre;
    private String descripcion;
    private String activo;
    private String visible;

    public Integer getTipoResultadoPruebaId() {
        return tipoResultadoPruebaId;
    }

    public void setTipoResultadoPruebaId(Integer tipoResultadoPruebaId) {
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

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, String activo, String visible){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("activo", activo);
        cv.put("visible", visible);
        bd.insert("TIPO_RESULTADO_PRUEBA", null, cv);
    }

    public TipoResultadoPrueba findById(SQLiteDatabase bd, Integer tipoResultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT tipoResultadoPruebaId, nombre, descripcion, activo, visible FROM TIPO_RESULTADO_PRUEBA WHERE tipoResultadoPruebaId=" + tipoResultadoPruebaId,null);
        if (fila.moveToFirst()) {
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            tipoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoResultadoPrueba.setNombre(fila.getString(1));
            tipoResultadoPrueba.setDescripcion(fila.getString(2));
            tipoResultadoPrueba.setActivo(fila.getString(3));
            tipoResultadoPrueba.setVisible(fila.getString(4));
            fila.close();
            return tipoResultadoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<TipoResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT tipoResultadoPruebaId, nombre, descripcion, activo, visible FROM TIPO_RESULTADO_PRUEBA",null);
        List<TipoResultadoPrueba> tipoResultadoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            tipoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoResultadoPrueba.setNombre(fila.getString(1));
            tipoResultadoPrueba.setDescripcion(fila.getString(2));
            tipoResultadoPrueba.setActivo(fila.getString(3));
            tipoResultadoPrueba.setVisible(fila.getString(4));
            tipoResultadoPruebas.add(tipoResultadoPrueba);
        }
        fila.close();
        return tipoResultadoPruebas;
    }
}
