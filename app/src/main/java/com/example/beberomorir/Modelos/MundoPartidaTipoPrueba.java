package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MundoPartidaTipoPrueba {
    private Integer mundoPartidaId;
    private Integer tipoPruebaId;
    private Integer numeroTiposPrueba;

    public Integer getMundoPartidaId() {
        return mundoPartidaId;
    }

    public void setMundoPartidaId(Integer mundoPartidaId) {
        this.mundoPartidaId = mundoPartidaId;
    }

    public Integer getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(Integer tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
    }

    public Integer getNumeroTiposPrueba() {
        return numeroTiposPrueba;
    }

    public void setNumeroTiposPrueba(Integer numeroTiposPrueba) {
        this.numeroTiposPrueba = numeroTiposPrueba;
    }

    public List<MundoPartidaTipoPrueba> findById(SQLiteDatabase bd, Integer mundoPartidaId) {
        List<MundoPartidaTipoPrueba> mundoPartidaTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT mundoPartidaId, tipoPruebaId, numeroTiposPrueba FROM MUNDO_PARTIDA_TIPO_PRUEBA WHERE mundoPartidaId=" + mundoPartidaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            MundoPartidaTipoPrueba mundoPartidaTipoPrueba = new MundoPartidaTipoPrueba();
            mundoPartidaTipoPrueba.setMundoPartidaId(Integer.parseInt(fila.getString(0)));
            mundoPartidaTipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(1)));
            mundoPartidaTipoPrueba.setNumeroTiposPrueba(Integer.parseInt(fila.getString(2)));
            mundoPartidaTipoPruebas.add(mundoPartidaTipoPrueba);
        }
        fila.close();
        return mundoPartidaTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer mundoPartidaId, Integer tipoPruebaId, Integer numeroTiposPrueba){
        ContentValues cv = new ContentValues();
        cv.put("mundoPartidaId", mundoPartidaId);
        cv.put("tipoPruebaId", tipoPruebaId);
        cv.put("numeroTiposPrueba", numeroTiposPrueba);
        bd.insert("MUNDO_PARTIDA_TIPO_PRUEBA", null, cv);
    }
}
