package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ConfigPartida {
    private Integer configPartidaId;
    private Integer nivelPruebas;
    private Integer nivelResultadoPruebas;
    private TipoPartida tipoPartida;
    private String rolesJugador;
    private List<ConfigTipoPrueba> configTipoPruebas;
    private List<ConfigTipoResultadoPrueba> configTipoResultadoPruebas;

    public Integer getConfigPartidaId() {
        return configPartidaId;
    }

    public void setConfigPartidaId(Integer configPartidaId) {
        this.configPartidaId = configPartidaId;
    }

    public Integer getNivelPruebas() {
        return nivelPruebas;
    }

    public void setNivelPruebas(Integer nivelPruebas) {
        this.nivelPruebas = nivelPruebas;
    }

    public Integer getNivelResultadoPruebas() {
        return nivelResultadoPruebas;
    }

    public void setNivelResultadoPruebas(Integer nivelResultadoPruebas) {
        this.nivelResultadoPruebas = nivelResultadoPruebas;
    }

    public TipoPartida getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(TipoPartida tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public String getRolesJugador() {
        return rolesJugador;
    }

    public void setRolesJugador(String rolesJugador) {
        this.rolesJugador = rolesJugador;
    }

    public List<ConfigTipoPrueba> getConfigTipoPruebas() {
        return configTipoPruebas;
    }

    public void setConfigTipoPruebas(List<ConfigTipoPrueba> configTipoPruebas) {
        this.configTipoPruebas = configTipoPruebas;
    }

    public List<ConfigTipoResultadoPrueba> getConfigTipoResultadoPruebas() {
        return configTipoResultadoPruebas;
    }

    public void setConfigTipoResultadoPruebas(List<ConfigTipoResultadoPrueba> configTipoResultadoPruebas) {
        this.configTipoResultadoPruebas = configTipoResultadoPruebas;
    }

    public ConfigPartida findConfigById(SQLiteDatabase bd, Integer configPartidaId) {
        Cursor fila = bd.rawQuery("SELECT configPartidaId, nivelPruebas, nivelResultadoPruebas, tipoPartidaId, rolesJugador FROM CONFIG_PARTIDA WHERE configPartidaId=" + configPartidaId,null);
        if (fila.moveToFirst()) {
            ConfigPartida configPartida = new ConfigPartida();
            configPartida.setConfigPartidaId(Integer.parseInt(fila.getString(0)));
            configPartida.setNivelPruebas(Integer.parseInt(fila.getString(1)));
            configPartida.setNivelResultadoPruebas(Integer.parseInt(fila.getString(2)));
            configPartida.setRolesJugador(fila.getString(4));
            TipoPartida tipoPartida = new TipoPartida();
            configPartida.setTipoPartida(tipoPartida.findById(bd, Integer.parseInt(fila.getString(3))));
            ConfigTipoPrueba configTipoPrueba = new ConfigTipoPrueba();
            configPartida.setConfigTipoPruebas(configTipoPrueba.findById(bd, Integer.parseInt(fila.getString(0))));
            ConfigTipoResultadoPrueba configTipoResultadoPrueba = new ConfigTipoResultadoPrueba();
            configPartida.setConfigTipoResultadoPruebas(configTipoResultadoPrueba.findById(bd, Integer.parseInt(fila.getString(0))));
            fila.close();
            return configPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ConfigPartida findById(SQLiteDatabase bd, Integer configPartidaId) {
        Cursor fila = bd.rawQuery("SELECT configPartidaId, nivelPruebas, nivelResultadoPruebas, tipoPartidaId, rolesJugador FROM CONFIG_PARTIDA WHERE configPartidaId=" + configPartidaId,null);
        if (fila.moveToFirst()) {
            ConfigPartida configPartida = new ConfigPartida();
            configPartida.setConfigPartidaId(Integer.parseInt(fila.getString(0)));
            configPartida.setNivelPruebas(Integer.parseInt(fila.getString(1)));
            configPartida.setNivelResultadoPruebas(Integer.parseInt(fila.getString(2)));
            configPartida.setRolesJugador(fila.getString(4));
            fila.close();
            return configPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ConfigPartida insertar(SQLiteDatabase bd, Integer nivelPruebas, Integer nivelResultadoPruebas, Integer tipoPartidaId, String rolesJugador){
        ContentValues cv = new ContentValues();
        cv.put("nivelPruebas", nivelPruebas);
        cv.put("nivelResultadoPruebas", nivelResultadoPruebas);
        cv.put("tipoPartidaId", tipoPartidaId);
        cv.put("rolesJugador", rolesJugador);
        long id = bd.insert("CONFIG_PARTIDA", null, cv);
        return findById(bd, (int) id);
    }
}
