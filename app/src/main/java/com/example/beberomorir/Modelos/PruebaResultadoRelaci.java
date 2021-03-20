package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PruebaResultadoRelaci {
    Integer resultadoPruebaPartidaId;
    Integer pruebaPartidaId;

    public Integer getResultadoPruebaPartidaId() {
        return resultadoPruebaPartidaId;
    }

    public void setResultadoPruebaPartidaId(Integer resultadoPruebaPartidaId) {
        this.resultadoPruebaPartidaId = resultadoPruebaPartidaId;
    }

    public Integer getPruebaPartidaId() {
        return pruebaPartidaId;
    }

    public void setPruebaPartidaId(Integer pruebaPartidaId) {
        this.pruebaPartidaId = pruebaPartidaId;
    }

    public List<PruebaResultadoRelaci> findByPruebaPartidaId(SQLiteDatabase bd, Integer pruebaPartidaId) {
        List<PruebaResultadoRelaci> estadoResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaPartidaId, pruebaPartidaId FROM PRUEBA_RESULTADO_RELACI WHERE pruebaPartidaId=" + pruebaPartidaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaResultadoRelaci estadoResultadoTipoPrueba = new PruebaResultadoRelaci();
            estadoResultadoTipoPrueba.setResultadoPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoTipoPrueba.setPruebaPartidaId(Integer.parseInt(fila.getString(1)));
            estadoResultadoTipoPruebas.add(estadoResultadoTipoPrueba);
        }
        fila.close();
        return estadoResultadoTipoPruebas;
    }

    public List<PruebaResultadoRelaci> getAll(SQLiteDatabase bd) {
        List<PruebaResultadoRelaci> estadoResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaPartidaId, pruebaPartidaId FROM PRUEBA_RESULTADO_RELACI",null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaResultadoRelaci estadoResultadoTipoPrueba = new PruebaResultadoRelaci();
            estadoResultadoTipoPrueba.setResultadoPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            estadoResultadoTipoPrueba.setPruebaPartidaId(Integer.parseInt(fila.getString(1)));
            estadoResultadoTipoPruebas.add(estadoResultadoTipoPrueba);
        }
        fila.close();
        return estadoResultadoTipoPruebas;
    }

    public PruebaResultadoRelaci insertar(SQLiteDatabase bd, Integer resultadoPruebaPartidaId, Integer pruebaPartidaId){
        PruebaResultadoRelaci pruebaResultadoRelaci = new PruebaResultadoRelaci();
        ContentValues cv = new ContentValues();
        cv.put("resultadoPruebaPartidaId", resultadoPruebaPartidaId);
        cv.put("pruebaPartidaId", pruebaPartidaId);
        bd.insert("PRUEBA_RESULTADO_RELACI", null, cv);
        pruebaResultadoRelaci.setPruebaPartidaId(pruebaPartidaId);
        pruebaResultadoRelaci.setResultadoPruebaPartidaId(resultadoPruebaPartidaId);
        return pruebaResultadoRelaci;
    }
}
