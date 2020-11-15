package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class MundoPartida {
    private int mundoPartidaId;
    private Mundo mundo;
    private int partidaId;
    private int urlImagen;
    private int orden;
    private int nivelMundo;
    private String finalizado;

    public int getMundoPartidaId() {
        return mundoPartidaId;
    }

    public void setMundoPartidaId(int mundoPartidaId) {
        this.mundoPartidaId = mundoPartidaId;
    }

    public int getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(int urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Mundo getMundo() {
        return mundo;
    }

    public void setMundo(Mundo mundo) {
        this.mundo = mundo;
    }

    public int getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(int partidaId) {
        this.partidaId = partidaId;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public int getNivelMundo() {
        return nivelMundo;
    }

    public void setNivelMundo(int nivelMundo) {
        this.nivelMundo = nivelMundo;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public MundoPartida insertar(SQLiteDatabase bd, int mundoId, int partidaId, int urlImagen, int orden, int nivelMundo, String finalizado){
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

    public MundoPartida findById(SQLiteDatabase bd, int mundoPartidaId) {
        Cursor fila = bd.rawQuery("SELECT mundoPartidaId, mundoId, partidaId, urlImagen, orden, nivelMundo, finalizado FROM MUNDO_PARTIDA WHERE mundoPartidaId=" + mundoPartidaId,null);
        if (fila.moveToFirst()) {
            MundoPartida mundoPartida = new MundoPartida();
            Mundo mundo = new Mundo();
            mundoPartida.setMundoPartidaId(Integer.parseInt(fila.getString(0)));
            mundoPartida.setMundo(mundo.findById(bd, fila.getInt(1)));
            mundoPartida.setPartidaId(fila.getInt(2));
            mundoPartida.setOrden(fila.getInt(3));
            mundoPartida.setNivelMundo(fila.getInt(4));
            mundoPartida.setFinalizado(fila.getString(5));
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
            mundoPartida.setOrden(fila.getInt(3));
            mundoPartida.setNivelMundo(fila.getInt(4));
            mundoPartida.setFinalizado(fila.getString(5));
            mundoPartidas.add(mundoPartida);
        }
        fila.close();
        return mundoPartidas;
    }
}
