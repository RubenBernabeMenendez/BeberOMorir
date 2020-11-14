package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultadoPrueba {
    private int resultadoPruebaId;
    private String nombre;
    private String descripcion;
    private int nivelResultadoPrueba;
    private TipoResultadoPrueba tipoResultadoPrueba;
    private EstadoResultadoPrueba estadoResultadoPrueba;

    public int getResultadoPruebaId() {
        return resultadoPruebaId;
    }

    public void setResultadoPruebaId(int resultadoPruebaId) {
        this.resultadoPruebaId = resultadoPruebaId;
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

    public int getNivelResultadoPrueba() {
        return nivelResultadoPrueba;
    }

    public void setNivelResultadoPrueba(int nivelResultadoPrueba) {
        this.nivelResultadoPrueba = nivelResultadoPrueba;
    }

    public TipoResultadoPrueba getTipoResultadoPrueba() {
        return tipoResultadoPrueba;
    }

    public void setTipoResultadoPrueba(TipoResultadoPrueba tipoResultadoPrueba) {
        this.tipoResultadoPrueba = tipoResultadoPrueba;
    }

    public EstadoResultadoPrueba getEstadoResultadoPrueba() {
        return estadoResultadoPrueba;
    }

    public void setEstadoResultadoPrueba(EstadoResultadoPrueba estadoResultadoPrueba) {
        this.estadoResultadoPrueba = estadoResultadoPrueba;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, int nivelResultadoPrueba, int tipoResultadoPruebaId, int estadoResultadoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("nivelResultadoPrueba", nivelResultadoPrueba);
        cv.put("tipoResultadoPruebaId", tipoResultadoPruebaId);
        cv.put("estadoResultadoPruebaId", estadoResultadoPruebaId);
        bd.insert("RESULTADO_PRUEBA", null, cv);
    }

    public ResultadoPrueba findById(SQLiteDatabase bd, int resultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba, tipoResultadoPruebaId, estadoResultadoPruebaId FROM RESULTADO_PRUEBA WHERE resultadoPruebaId=" + resultadoPruebaId,null);
        if (fila.moveToFirst()) {
            ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
            resultadoPrueba.setResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            resultadoPrueba.setNombre(fila.getString(1));
            resultadoPrueba.setDescripcion(fila.getString(2));
            resultadoPrueba.setNivelResultadoPrueba(fila.getInt(3));
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            resultadoPrueba.setTipoResultadoPrueba(tipoResultadoPrueba.findById(bd, fila.getInt(4)));
            EstadoResultadoPrueba estadoResultadoPrueba = new EstadoResultadoPrueba();
            resultadoPrueba.setEstadoResultadoPrueba(estadoResultadoPrueba.findById(bd, fila.getInt(5)));
            fila.close();
            return resultadoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<ResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba, tiempoEjecicion, tipoPruebaId FROM RESULTADO_PRUEBA",null);
        List<ResultadoPrueba> resultadoPruebas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
            resultadoPrueba.setResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            resultadoPrueba.setNombre(fila.getString(1));
            resultadoPrueba.setDescripcion(fila.getString(2));
            resultadoPrueba.setNivelResultadoPrueba(fila.getInt(3));
            TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
            resultadoPrueba.setTipoResultadoPrueba(tipoResultadoPrueba.findById(bd, fila.getInt(4)));
            EstadoResultadoPrueba estadoResultadoPrueba = new EstadoResultadoPrueba();
            resultadoPrueba.setEstadoResultadoPrueba(estadoResultadoPrueba.findById(bd, fila.getInt(5)));
            resultadoPruebas.add(resultadoPrueba);
        }
        fila.close();
        return resultadoPruebas;
    }

}
