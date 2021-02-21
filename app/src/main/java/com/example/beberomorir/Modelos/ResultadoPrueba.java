package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ResultadoPrueba {
    private Integer resultadoPruebaId;
    private String nombre;
    private String descripcion;
    private Integer nivelResultadoPrueba;
    private TipoResultadoPrueba tipoResultadoPrueba;
    private EstadoResultadoPrueba estadoResultadoPrueba;
    private EntidadResultadoPrueba entidadResultadoPrueba;

    public Integer getResultadoPruebaId() {
        return resultadoPruebaId;
    }

    public void setResultadoPruebaId(Integer resultadoPruebaId) {
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

    public Integer getNivelResultadoPrueba() {
        return nivelResultadoPrueba;
    }

    public void setNivelResultadoPrueba(Integer nivelResultadoPrueba) {
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

    public EntidadResultadoPrueba getEntidadResultadoPrueba() {
        return entidadResultadoPrueba;
    }

    public void setEntidadResultadoPrueba(EntidadResultadoPrueba entidadResultadoPrueba) {
        this.entidadResultadoPrueba = entidadResultadoPrueba;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, Integer nivelResultadoPrueba, Integer tipoResultadoPruebaId, Integer estadoResultadoPruebaId, Integer entidadResultadoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("nivelResultadoPrueba", nivelResultadoPrueba);
        cv.put("tipoResultadoPruebaId", tipoResultadoPruebaId);
        cv.put("estadoResultadoPruebaId", estadoResultadoPruebaId);
        cv.put("entidadResultadoPruebaId", entidadResultadoPruebaId);

        bd.insert("RESULTADO_PRUEBA", null, cv);
    }

    public ResultadoPrueba findById(SQLiteDatabase bd, Integer resultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba, tipoResultadoPruebaId, estadoResultadoPruebaId, entidadResultadoPruebaId FROM RESULTADO_PRUEBA WHERE resultadoPruebaId=" + resultadoPruebaId,null);
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
            EntidadResultadoPrueba entidadResultadoPrueba = new EntidadResultadoPrueba();
            resultadoPrueba.setEntidadResultadoPrueba(entidadResultadoPrueba.findById(bd, fila.getInt(6)));
            fila.close();
            return resultadoPrueba;
        } else {
            fila.close();
            return null;
        }
    }

    public List<ResultadoPrueba> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba,tipoResultadoPruebaId, estadoResultadoPruebaId, entidadResultadoPruebaId FROM RESULTADO_PRUEBA",null);
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
            EntidadResultadoPrueba entidadResultadoPrueba = new EntidadResultadoPrueba();
            resultadoPrueba.setEntidadResultadoPrueba(entidadResultadoPrueba.findById(bd, fila.getInt(6)));
            resultadoPruebas.add(resultadoPrueba);
        }
        fila.close();
        return resultadoPruebas;
    }

    public List<ResultadoPrueba> findByEntidadResultadoPruebaId(SQLiteDatabase bd, Integer entidadResultadoPruebaId) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba,tipoResultadoPruebaId, estadoResultadoPruebaId, entidadResultadoPruebaId FROM RESULTADO_PRUEBA WHERE entidadResultadoPruebaId=" + entidadResultadoPruebaId,null);
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
            EntidadResultadoPrueba entidadResultadoPrueba = new EntidadResultadoPrueba();
            resultadoPrueba.setEntidadResultadoPrueba(entidadResultadoPrueba.findById(bd, fila.getInt(6)));
            resultadoPruebas.add(resultadoPrueba);
        }
        fila.close();
        return resultadoPruebas;
    }

    public List<ResultadoPrueba> findByEntidadResultadoPruebaIdAndNivelMenor(SQLiteDatabase bd, Integer entidadResultadoPruebaId, Integer nivelResultadoPrueba) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaId, nombre, descripcion, nivelResultadoPrueba,tipoResultadoPruebaId, estadoResultadoPruebaId, entidadResultadoPruebaId FROM RESULTADO_PRUEBA WHERE entidadResultadoPruebaId=" + entidadResultadoPruebaId + "AND (nivelResultadoPrueba <" + nivelResultadoPrueba + "OR nivelResultadoPrueba =" + nivelResultadoPrueba + ")",null);
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
            EntidadResultadoPrueba entidadResultadoPrueba = new EntidadResultadoPrueba();
            resultadoPrueba.setEntidadResultadoPrueba(entidadResultadoPrueba.findById(bd, fila.getInt(6)));
            resultadoPruebas.add(resultadoPrueba);
        }
        fila.close();
        return resultadoPruebas;
    }

}
