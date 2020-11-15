package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class ConfigPartida {
    private int configPartidaId;
    private int nivelPruebas;
    private int nivelResultadoPruebas;
    private TipoPartida tipoPartida;
    private String rolesJugador;
    private List<ConfigTipoPrueba> configTipoPruebas;
    private List<ConfigTipoResultadoPrueba> configTipoResultadoPruebas;

    public int getConfigPartidaId() {
        return configPartidaId;
    }

    public void setConfigPartidaId(int configPartidaId) {
        this.configPartidaId = configPartidaId;
    }

    public int getNivelPruebas() {
        return nivelPruebas;
    }

    public void setNivelPruebas(int nivelPruebas) {
        this.nivelPruebas = nivelPruebas;
    }

    public int getNivelResultadoPruebas() {
        return nivelResultadoPruebas;
    }

    public void setNivelResultadoPruebas(int nivelResultadoPruebas) {
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

    public ConfigPartida findConfigById(SQLiteDatabase bd, int configPartidaId) {
        Cursor fila = bd.rawQuery("SELECT configPartidaId, nivelPruebas, nivelResultadoPruebas, tipoPartidaId, rolesJugador FROM CONFIG_PARTIDA WHERE tipoPartidaId=" + configPartidaId,null);
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

    public ConfigPartida findById(SQLiteDatabase bd, int configPartidaId) {
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

    public ConfigPartida insertar(SQLiteDatabase bd, int nivelPruebas, int nivelResultadoPruebas, int tipoPartidaId, String rolesJugador){
        ContentValues cv = new ContentValues();
        cv.put("nivelPruebas", nivelPruebas);
        cv.put("nivelResultadoPruebas", nivelResultadoPruebas);
        cv.put("tipoPartidaId", tipoPartidaId);
        cv.put("rolesJugador", rolesJugador);
        long id = bd.insert("CONFIG_PARTIDA", null, cv);
        System.out.println(id);
        return findById(bd, (int) id);
    }
}
