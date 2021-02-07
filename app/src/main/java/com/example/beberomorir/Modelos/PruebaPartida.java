package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PruebaPartida {
    private int pruebaPartidaId;
    private Prueba prueba;
    private int mundoPartidaId;
    private String nombre;
    private String descripcion;
    private String finalizado;

    public int getPruebaPartidaId() {
        return pruebaPartidaId;
    }

    public void setPruebaPartidaId(int pruebaPartidaId) {
        this.pruebaPartidaId = pruebaPartidaId;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    public int getMundoPartidaId() {
        return mundoPartidaId;
    }

    public void setMundoPartidaId(int mundoPartidaId) {
        this.mundoPartidaId = mundoPartidaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(String finalizado) {
        this.finalizado = finalizado;
    }

    public PruebaPartida insertar(SQLiteDatabase bd, int pruebaId, int mundoPartidaId, String nombre, String descripcion, String finalizado){
        ContentValues cv = new ContentValues();
        cv.put("pruebaId", pruebaId);
        cv.put("mundaPartidaId", mundoPartidaId);
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("finalizado", finalizado);
        long id = bd.insert("PRUEBA_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public PruebaPartida findById(SQLiteDatabase bd, int pruebaPartidaId) {
        Cursor fila = bd.rawQuery("SELECT pruebaPartidaId, pruebaId, mundoPartidaId, nombre, descripcion, finalizado FROM PRUEBA_PARTIDA WHERE pruebaPartidaId=" + pruebaPartidaId,null);
        if (fila.moveToFirst()) {
            PruebaPartida pruebaPartida = new PruebaPartida();
            Prueba prueba = new Prueba();
            pruebaPartida.setPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            pruebaPartida.setMundoPartidaId(Integer.parseInt(fila.getString(2)));
            pruebaPartida.setPrueba(prueba.findById(bd, fila.getInt(1)));
            pruebaPartida.setNombre(fila.getString(3));
            pruebaPartida.setDescripcion(fila.getString(4));
            pruebaPartida.setFinalizado(fila.getString(5));
            fila.close();
            return pruebaPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<PruebaPartida> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT pruebaPartidaId, pruebaId, mundoPartidaId, nombre, descripcion, finalizado FROM PRUEBA_PARTIDA",null);
        ArrayList<PruebaPartida> pruebaPartidas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaPartida pruebaPartida = new PruebaPartida();
            Prueba prueba = new Prueba();
            pruebaPartida.setPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            pruebaPartida.setMundoPartidaId(Integer.parseInt(fila.getString(2)));
            pruebaPartida.setPrueba(prueba.findById(bd, fila.getInt(1)));
            pruebaPartida.setNombre(fila.getString(3));
            pruebaPartida.setDescripcion(fila.getString(4));
            pruebaPartida.setFinalizado(fila.getString(5));
            pruebaPartidas.add(pruebaPartida);
        }
        fila.close();
        return pruebaPartidas;
    }

    public ArrayList<PruebaPartida> findByTipoPruebaAndMundoPartida(SQLiteDatabase bd, int tipoPruebaId, int mundoPartidaId) {
        Cursor fila = bd.rawQuery("SELECT pruebaPartidaId, pruebaId, mundoPartidaId, nombre, descripcion, finalizado FROM PRUEBA_PARTIDA WHERE mundoPartidaId=" + mundoPartidaId,null);
        ArrayList<PruebaPartida> pruebaPartidas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            PruebaPartida pruebaPartida = new PruebaPartida();
            Prueba prueba = new Prueba();
            pruebaPartida.setPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            pruebaPartida.setMundoPartidaId(Integer.parseInt(fila.getString(2)));
            pruebaPartida.setPrueba(prueba.findById(bd, fila.getInt(1)));
            pruebaPartida.setNombre(fila.getString(3));
            pruebaPartida.setDescripcion(fila.getString(4));
            pruebaPartida.setFinalizado(fila.getString(5));
            pruebaPartidas.add(pruebaPartida);
        }
        fila.close();
        pruebaPartidas.removeIf(el -> el.getPrueba().getTipoPrueba().getTipoPruebaId() != tipoPruebaId);
        return pruebaPartidas;
    }
}
