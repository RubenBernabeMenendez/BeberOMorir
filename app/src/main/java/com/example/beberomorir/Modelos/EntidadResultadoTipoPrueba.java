package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EntidadResultadoTipoPrueba {
    int entidadResultadoPruebaId;
    int tipoPruebaId;

    public int getEntidadResultadoPruebaId() {
        return entidadResultadoPruebaId;
    }

    public void setEntidadResultadoPruebaId(int entidadResultadoPruebaId) {
        this.entidadResultadoPruebaId = entidadResultadoPruebaId;
    }

    public int getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(int tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
    }

    public List<EntidadResultadoTipoPrueba> findById(SQLiteDatabase bd, int entidadResultadoPruebaId) {
        List<EntidadResultadoTipoPrueba> entidadResultadoTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT entidadResultadoPruebaId, tipoPruebaId FROM ENTIDAD_RESULTADO_TIPO_PRUEBA WHERE entidadResultadoPruebaId=" + entidadResultadoPruebaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            EntidadResultadoTipoPrueba entidadResultadoTipoPrueba = new EntidadResultadoTipoPrueba();
            entidadResultadoTipoPrueba.setEntidadResultadoPruebaId(Integer.parseInt(fila.getString(0)));
            entidadResultadoTipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(1)));
            entidadResultadoTipoPruebas.add(entidadResultadoTipoPrueba);
        }
        fila.close();
        return entidadResultadoTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, int entidadResultadoPruebaId, int tipoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("entidadResultadoPruebaId", entidadResultadoPruebaId);
        cv.put("tipoPruebaId", tipoPruebaId);
        bd.insert("ENTIDAD_RESULTADO_TIPO_PRUEBA", null, cv);
    }
}
