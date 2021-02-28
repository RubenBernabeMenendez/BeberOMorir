package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.beberomorir.Constantes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PruebaPartida {
    private Integer pruebaPartidaId;
    private Prueba prueba;
    private Integer mundoPartidaId;
    private String nombre;
    private String descripcion;
    private String finalizado;

    public Integer getPruebaPartidaId() {
        return pruebaPartidaId;
    }

    public void setPruebaPartidaId(Integer pruebaPartidaId) {
        this.pruebaPartidaId = pruebaPartidaId;
    }

    public Prueba getPrueba() {
        return prueba;
    }

    public void setPrueba(Prueba prueba) {
        this.prueba = prueba;
    }

    public Integer getMundoPartidaId() {
        return mundoPartidaId;
    }

    public void setMundoPartidaId(Integer mundoPartidaId) {
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

    public PruebaPartida insertar(SQLiteDatabase bd, Integer pruebaId, Integer mundoPartidaId, String nombre, String descripcion, String finalizado){
        ContentValues cv = new ContentValues();
        cv.put("pruebaId", pruebaId);
        cv.put("mundoPartidaId", mundoPartidaId);
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("finalizado", finalizado);
        long id = bd.insert("PRUEBA_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public PruebaPartida updateFinalizado(SQLiteDatabase bd, Integer pruebaPartidaId){
        PruebaPartida pruebaPartida = new PruebaPartida();
        pruebaPartida = pruebaPartida.findById(bd, (int) pruebaPartidaId);
        ContentValues cv = new ContentValues();
        cv.put("pruebaId", pruebaPartida.getPrueba().getPruebaId());
        cv.put("mundoPartidaId", pruebaPartida.getMundoPartidaId());
        cv.put("nombre", pruebaPartida.getNombre());
        cv.put("descripcion", pruebaPartida.getDescripcion());
        cv.put("finalizado", Constantes.YES);
        bd.update("PRUEBA_PARTIDA", cv, "pruebaPartidaId=" + pruebaPartidaId, null);

        pruebaPartida.setFinalizado(Constantes.YES);
        return  pruebaPartida;
    }

    public PruebaPartida findById(SQLiteDatabase bd, Integer pruebaPartidaId) {
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

    public ArrayList<PruebaPartida> findByMundoPartida(SQLiteDatabase bd, Integer mundoPartidaId) {
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
        return pruebaPartidas;
    }
}
