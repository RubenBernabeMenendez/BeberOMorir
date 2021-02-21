package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PruebaResultadoRelaci {
    Integer resultadoPruebaPartidaId;
    Integer tipoPruebaPartidaId;

    public Integer getResultadoPruebaPartidaId() {
        return resultadoPruebaPartidaId;
    }

    public void setResultadoPruebaPartidaId(Integer resultadoPruebaPartidaId) {
        this.resultadoPruebaPartidaId = resultadoPruebaPartidaId;
    }

    public Integer getTipoPruebaPartidaId() {
        return tipoPruebaPartidaId;
    }

    public void setTipoPruebaPartidaId(Integer tipoPruebaPartidaId) {
        this.tipoPruebaPartidaId = tipoPruebaPartidaId;
    }

    public List<PruebaResultadoRelaci> findByTipoPruebaPartidaId(SQLiteDatabase bd, Integer tipoPruebaPartidaId) {
        List<PruebaResultadoRelaci> estadoResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaPartidaId, tipoPruebaPartidaId FROM PRUEBA_RESULTADO_RELACI WHERE tipoPruebaPartidaId=" + tipoPruebaPartidaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaResultadoRelaci estadoResultadoTipoPrueba = new PruebaResultadoRelaci();
            estadoResultadoTipoPrueba.setResultadoPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoTipoPrueba.setTipoPruebaPartidaId(Integer.parseInt(fila.getString(1)));
            estadoResultadoTipoPruebas.add(estadoResultadoTipoPrueba);
        }
        fila.close();
        return estadoResultadoTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer resultadoPruebaPartidaId, Integer tipoPruebaPartidaId){
        ContentValues cv = new ContentValues();
        cv.put("resultadoPruebaPartidaId", resultadoPruebaPartidaId);
        cv.put("tipoPruebaPartidaId", tipoPruebaPartidaId);
        bd.insert("PRUEBA_RESULTADO_RELACI", null, cv);
    }
}
