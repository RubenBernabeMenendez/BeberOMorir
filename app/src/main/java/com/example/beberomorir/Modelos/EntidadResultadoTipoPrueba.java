package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EntidadResultadoTipoPrueba {
    Integer entidadResultadoPruebaId;
    Integer tipoPruebaId;

    public Integer getEntidadResultadoPruebaId() {
        return entidadResultadoPruebaId;
    }

    public void setEntidadResultadoPruebaId(Integer entidadResultadoPruebaId) {
        this.entidadResultadoPruebaId = entidadResultadoPruebaId;
    }

    public Integer getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(Integer tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
    }

    public List<EntidadResultadoTipoPrueba> findByTipoPruebaId(SQLiteDatabase bd, Integer tipoPruebaId) {
        List<EntidadResultadoTipoPrueba> entidadResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT entidadResultadoPruebaId, tipoPruebaId FROM ENTIDAD_RESULTADO_TIPO_PRUEBA WHERE tipoPruebaId=" + tipoPruebaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            EntidadResultadoTipoPrueba entidadResultadoTipoPrueba = new EntidadResultadoTipoPrueba();
            entidadResultadoTipoPrueba.setEntidadResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            entidadResultadoTipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(1)));
            entidadResultadoTipoPruebas.add(entidadResultadoTipoPrueba);
        }
        fila.close();
        return entidadResultadoTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer entidadResultadoPruebaId, Integer tipoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("entidadResultadoPruebaId", entidadResultadoPruebaId);
        cv.put("tipoPruebaId", tipoPruebaId);
        bd.insert("ENTIDAD_RESULTADO_TIPO_PRUEBA", null, cv);
    }
}
