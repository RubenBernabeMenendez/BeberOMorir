package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MundoPartida {
    private Integer mundoPartidaId;
    private Mundo mundo;
    private Integer partidaId;
    private Integer urlImagen;
    private Integer orden;
    private Integer nivelMundo;
    private String finalizado;

    public Integer getMundoPartidaId() {
        return mundoPartidaId;
    }

    public void setMundoPartidaId(Integer mundoPartidaId) {
        this.mundoPartidaId = mundoPartidaId;
    }

    public Integer getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(Integer urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Mundo getMundo() {
        return mundo;
    }

    public void setMundo(Mundo mundo) {
        this.mundo = mundo;
    }

    public Integer getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Integer partidaId) {
        this.partidaId = partidaId;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getNivelMundo() {
        return nivelMundo;
    }

    public void setNivelMundo(Integer nivelMundo) {
        this.nivelMundo = nivelMundo;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public MundoPartida insertar(SQLiteDatabase bd, Integer mundoId, Integer partidaId, Integer urlImagen, Integer orden, Integer nivelMundo, String finalizado){
        ContentValues cv = new ContentValues();
        cv.put("mundoId", mundoId);
        cv.put("partidaId", partidaId);
        cv.put("urlImagen", urlImagen);
        cv.put("orden", orden);
        cv.put("nivelMundo", nivelMundo);
        cv.put("finalizado", finalizado);
        long id = bd.insert("MUNDO_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public MundoPartida findById(SQLiteDatabase bd, Integer mundoPartidaId) {
        Cursor fila = bd.rawQuery("SELECT mundoPartidaId, mundoId, partidaId, urlImagen, orden, nivelMundo, finalizado FROM MUNDO_PARTIDA WHERE mundoPartidaId=" + mundoPartidaId,null);
        if (fila.moveToFirst()) {
            MundoPartida mundoPartida = new MundoPartida();
            Mundo mundo = new Mundo();
            mundoPartida.setMundoPartidaId(Integer.parseInt(fila.getString(0)));
            mundoPartida.setMundo(mundo.findById(bd, fila.getInt(1)));
            mundoPartida.setPartidaId(fila.getInt(2));
            mundoPartida.setUrlImagen(fila.getInt(3));
            mundoPartida.setOrden(fila.getInt(4));
            mundoPartida.setNivelMundo(fila.getInt(5));
            mundoPartida.setFinalizado(fila.getString(6));
            fila.close();
            return mundoPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<MundoPartida> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT mundoPartidaId, mundoId, partidaId, urlImagen, orden, nivelMundo, finalizado FROM MUNDO_PARTIDA",null);
        ArrayList<MundoPartida> mundoPartidas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            MundoPartida mundoPartida = new MundoPartida();
            Mundo mundo = new Mundo();
            mundoPartida.setMundoPartidaId(Integer.parseInt(fila.getString(0)));
            mundoPartida.setMundo(mundo.findById(bd, fila.getInt(1)));
            mundoPartida.setPartidaId(fila.getInt(2));
            mundoPartida.setUrlImagen(fila.getInt(3));
            mundoPartida.setOrden(fila.getInt(4));
            mundoPartida.setNivelMundo(fila.getInt(5));
            mundoPartida.setFinalizado(fila.getString(6));
            mundoPartidas.add(mundoPartida);
        }
        fila.close();
        return mundoPartidas;
    }
}
