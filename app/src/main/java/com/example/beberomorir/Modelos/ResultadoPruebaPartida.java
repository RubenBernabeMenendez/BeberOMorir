package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ResultadoPruebaPartida {
    private Integer resultadoPruebaPartidaId;
    private ResultadoPrueba resultadoPrueba;
    private Integer mundoPartidaId;
    private String nombre;
    private String descripcion;

    public Integer getResultadoPruebaPartidaId() {
        return resultadoPruebaPartidaId;
    }

    public void setResultadoPruebaPartidaId(Integer resultadoPruebaPartidaId) {
        this.resultadoPruebaPartidaId = resultadoPruebaPartidaId;
    }

    public ResultadoPrueba getResultadoPrueba() {
        return resultadoPrueba;
    }

    public void setResultadoPrueba(ResultadoPrueba resultadoPrueba) {
        this.resultadoPrueba = resultadoPrueba;
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
    
    public ResultadoPruebaPartida insertar(SQLiteDatabase bd, Integer resultadoPruebaId, Integer mundoPartidaId, String nombre, String descripcion){
        ContentValues cv = new ContentValues();
        cv.put("resultadoPruebaId", resultadoPruebaId);
        cv.put("mundaPartidaId", mundoPartidaId);
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        long id = bd.insert("RESULTADO_PRUEBA_PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public ResultadoPruebaPartida findById(SQLiteDatabase bd, Integer resultadoPruebaPartidaId) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaPartidaId, resultadoPruebaId, mundoPartidaId, nombre, descripcion FROM RESULTADO_PRUEBA_PARTIDA WHERE resultadoPruebaPartidaId=" + resultadoPruebaPartidaId,null);
        if (fila.moveToFirst()) {
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
            resultadoPruebaPartida.setResultadoPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            resultadoPruebaPartida.setMundoPartidaId(Integer.parseInt(fila.getString(2)));
            resultadoPruebaPartida.setResultadoPrueba(resultadoPrueba.findById(bd, fila.getInt(1)));
            resultadoPruebaPartida.setNombre(fila.getString(3));
            resultadoPruebaPartida.setDescripcion(fila.getString(4));
            fila.close();
            return resultadoPruebaPartida;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<ResultadoPruebaPartida> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT resultadoPruebaPartidaId, resultadoPruebaId, mundoPartidaId, nombre, descripcion, finalizado FROM RESULTADO_PRUEBA_PARTIDA",null);
        ArrayList<ResultadoPruebaPartida> resultadoPruebaPartidas = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
            resultadoPruebaPartida.setResultadoPruebaPartidaId(Integer.parseInt(fila.getString(0)));
            resultadoPruebaPartida.setMundoPartidaId(Integer.parseInt(fila.getString(2)));
            resultadoPruebaPartida.setResultadoPrueba(resultadoPrueba.findById(bd, fila.getInt(1)));
            resultadoPruebaPartida.setNombre(fila.getString(3));
            resultadoPruebaPartida.setDescripcion(fila.getString(4));
            resultadoPruebaPartidas.add(resultadoPruebaPartida);
        }
        fila.close();
        return resultadoPruebaPartidas;
    }
}
