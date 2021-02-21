package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConfigTipoResultadoPrueba {
    private Integer configPartidaId;
    private Integer tipoResultadoPruebaId;

    public Integer getConfigPartidaId() {
        return configPartidaId;
    }

    public void setConfigPartidaId(Integer configPartidaId) {
        this.configPartidaId = configPartidaId;
    }

    public Integer getTipoResultadoPruebaId() {
        return tipoResultadoPruebaId;
    }

    public void setTipoResultadoPruebaId(Integer tipoResultadoPruebaId) {
        this.tipoResultadoPruebaId = tipoResultadoPruebaId;
    }

    public List<ConfigTipoResultadoPrueba> findById(SQLiteDatabase bd, Integer configPartidaId) {
        List<ConfigTipoResultadoPrueba> configTipoResultadoPruebas = new ArrayList<>();
        Cursor fila = bd.rawQuery("SELECT configPartidaId, tipoResultadoPruebaId FROM CONFIG_TIPO_RESULTADO_PRUEBA WHERE configPartidaId=" + configPartidaId,null);
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            ConfigTipoResultadoPrueba configTipoResultadoPrueba = new ConfigTipoResultadoPrueba();
            configTipoResultadoPrueba.setConfigPartidaId(Integer.parseInt(fila.getString(0)));
            configTipoResultadoPrueba.setTipoResultadoPruebaId(Integer.parseInt(fila.getString(1)));
            configTipoResultadoPruebas.add(configTipoResultadoPrueba);
        }
        fila.close();
        return configTipoResultadoPruebas;
    }

    public void insertar(SQLiteDatabase bd, Integer configPartidaId, Integer tipoResultadoPruebaId){
        ContentValues cv = new ContentValues();
        cv.put("configPartidaId", configPartidaId);
        cv.put("tipoResultadoPruebaId", tipoResultadoPruebaId);
        bd.insert("CONFIG_TIPO_RESULTADO_PRUEBA", null, cv);
    }
}
