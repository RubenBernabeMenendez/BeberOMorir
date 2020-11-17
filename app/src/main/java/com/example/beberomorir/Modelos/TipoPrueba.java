package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.beberomorir.Constantes;

import java.util.ArrayList;
import java.util.List;

public class TipoPrueba {
    private int tipoPruebaId;
    private String nombre;
    private String descripcion;
    private String activo;

    public int getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(int TipoPruebaId) {
        this.tipoPruebaId = TipoPruebaId;
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

    public int insertar(SQLiteDatabase bd, String nombre, String descripcion){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("activo", Constantes.YES);
        long id = bd.insert("TIPO_PRUEBA", null, cv);
        return (int) id;
    }

    public TipoPrueba findById(SQLiteDatabase bd, int tipoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT tipoPruebaId, nombre, descripcion, activo FROM TIPO_PRUEBA WHERE tipoPruebaId=" + tipoPruebaId,null);
        if (fila.moveToFirst()) {
            TipoPrueba tipoPrueba = new TipoPrueba();
            tipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoPrueba.setNombre(fila.getString(1));
            tipoPrueba.setDescripcion(fila.getString(2));
            tipoPrueba.setActivo(fila.getString(3));
            fila.close();
            return tipoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<TipoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT tipoPruebaId, nombre, descripcion, activo FROM TIPO_PRUEBA",null);
        List<TipoPrueba> TipoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            TipoPrueba tipoPrueba = new TipoPrueba();
            tipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(0)));
            tipoPrueba.setNombre(fila.getString(1));
            tipoPrueba.setDescripcion(fila.getString(2));
            tipoPrueba.setActivo(fila.getString(3));
            TipoPruebas.add(tipoPrueba);
        }
        fila.close();
        return TipoPruebas;
    }
}
