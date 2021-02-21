package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfigTipoPrueba {
    Integer configPartidaId;
    Integer tipoPruebaId;

    public Integer getConfigPartidaId() {
        return configPartidaId;
    }

    public void setConfigPartidaId(Integer configPartidaId) {
        this.configPartidaId = configPartidaId;
    }

    public Integer getTipoPruebaId() {
        return tipoPruebaId;
    }

    public void setTipoPruebaId(Integer tipoPruebaId) {
        this.tipoPruebaId = tipoPruebaId;
    }

    public List<ConfigTipoPrueba> findById(SQLiteDatabase bd, Integer configPartidaId) {
        List<ConfigTipoPrueba> configTipoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT configPartidaId, tipoPruebaId FROM CONFIG_TIPO_PRUEBA WHERE configPartidaId=" + configPartidaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            ConfigTipoPrueba configTipoPrueba = new ConfigTipoPrueba();
            configTipoPrueba.setConfigPartidaId(Integer.parseInt(fila.getString(0)));
            configTipoPrueba.setTipoPruebaId(Integer.parseInt(fila.getString(1)));
            configTipoPruebas.add(configTipoPrueba);
        }
        fila.close();
        return configTipoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer configPartidaId, Integer tipoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("configPartidaId", configPartidaId);
        cv.put("tipoPruebaId", tipoPruebaId);
        bd.insert("CONFIG_TIPO_PRUEBA", null, cv);
    }
}
