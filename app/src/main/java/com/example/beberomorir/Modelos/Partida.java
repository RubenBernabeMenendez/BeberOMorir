package com.example.beberomorir.Modelos;

import java.util.Date;

public class Partida {
    int partidaId;
    ConfigPartida configPartida;
    Date fecha;
    String nombre;
    String finalizada;
    int mundoPartidaActualId;

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public ConfigPartida getConfigPartida() {
        return configPartida;
    }

    public void setConfigPartida(ConfigPartida configPartida) {
        this.configPartida = configPartida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(String finalizada) {
        this.finalizada = finalizada;
    }

    public int getMundoPartidaActualId() {
        return mundoPartidaActualId;
    }

    public void setMundoPartidaActualId(int mundoPartidaActualId) {
        this.mundoPartidaActualId = mundoPartidaActualId;
    }
}
