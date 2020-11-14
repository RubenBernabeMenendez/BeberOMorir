package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Prueba {
    private int pruebaId;
    private String nombre;
    private String descripcion;
    private int nivelPrueba;
    private int tiempoEjecucion;
    private TipoPrueba tipoPrueba;

    public int getPruebaId() {
        return pruebaId;
    }

    public void setPruebaId(int pruebaId) {
        this.pruebaId = pruebaId;
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

    public int getNivelPrueba() {
        return nivelPrueba;
    }

    public void setNivelPrueba(int nivelPrueba) {
        this.nivelPrueba = nivelPrueba;
    }

    public int getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(int tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public TipoPrueba getTipoPrueba() {
        return tipoPrueba;
    }

    public void setTipoPrueba(TipoPrueba tipoPrueba) {
        this.tipoPrueba = tipoPrueba;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, int nivelPrueba, int tiempoEjecucion, int tipoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("nivelPrueba", nivelPrueba);
        cv.put("tiempoEjecucion", tiempoEjecucion);
        cv.put("tipoPruebaId", tipoPruebaId);
        bd.insert("PRUEBA", null, cv);
    }

    public Prueba findById(SQLiteDatabase bd, int pruebaId) {
        Cursor fila = bd.rawQuery("SELECT pruebaId, nombre, descripcion, nivelPrueba, tiempoEjecicion, tipoPruebaId FROM PRUEBA WHERE pruebaId=" + pruebaId,null);
        if (fila.moveToFirst()) {
            Prueba prueba = new Prueba();
            prueba.setPruebaId(Integer.parseInt(fila.getString(0)));
            prueba.setNombre(fila.getString(1));
            prueba.setDescripcion(fila.getString(2));
            prueba.setNivelPrueba(fila.getInt(3));
            prueba.setTiempoEjecucion(fila.getInt(4));
            TipoPrueba tipoPrueba = new TipoPrueba();
            prueba.setTipoPrueba(tipoPrueba.findById(bd, fila.getInt(5)));
            fila.close();
            return prueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<Prueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT pruebaId, nombre, descripcion, nivelPrueba, tiempoEjecicion, tipoPruebaId FROM PRUEBA",null);
        List<Prueba> pruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            Prueba prueba = new Prueba();
            prueba.setPruebaId(Integer.parseInt(fila.getString(0)));
            prueba.setNombre(fila.getString(1));
            prueba.setDescripcion(fila.getString(2));
            prueba.setNivelPrueba(fila.getInt(3));
            prueba.setTiempoEjecucion(fila.getInt(4));
            TipoPrueba tipoPrueba = new TipoPrueba();
            prueba.setTipoPrueba(tipoPrueba.findById(bd, fila.getInt(5)));
            pruebas.add(prueba);
        }
        fila.close();
        return pruebas;
    }

}
