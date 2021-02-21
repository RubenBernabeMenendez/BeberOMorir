package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EstadoResultadoTipoPrueba {
    Integer estadoResultadoPruebaId;
    Integer tipoPruebaId;

    public Integer getEstadoResultadoPruebaId() {
        return estadoResultadoPruebaId;
    }

    public void setEstadoResultadoPruebaId(Integer estadoResultadoPruebaId) {
        this.estadoResultadoPruebaId = estadoResultadoPruebaId;
    }

    public Integer getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(Integer tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
    }

    public List<EstadoResultadoTipoPrueba> findByTipoPruebaId(SQLiteDatabase bd, Integer tipoPruebaId) {
        List<EstadoResultadoTipoPrueba> estadoResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT estadoResultadoPruebaId, tipoPruebaId FROM ESTADO_RESULTADO_TIPO_PRUEBA WHERE tipoPruebaId=" + tipoPruebaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            EstadoResultadoTipoPrueba estadoResultadoTipoPrueba = new EstadoResultadoTipoPrueba();
            estadoResultadoTipoPrueba.setEstadoResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoTipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(1)));
            estadoResultadoTipoPruebas.add(estadoResultadoTipoPrueba);
        }
        fila.close();
        return estadoResultadoTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer estadoResultadoPruebaId, Integer tipoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("estadoResultadoPruebaId", estadoResultadoPruebaId);
        cv.put("tipoPruebaId", tipoPruebaId);
        bd.insert("ESTADO_RESULTADO_TIPO_PRUEBA", null, cv);
    }
}
