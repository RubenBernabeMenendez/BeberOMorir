package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Mundo {
    private Integer mundoId;
    private String nombre;
    private String descripcion;
    private Integer urlImagen;

    public Integer getMundoId() {
        return mundoId;
    }

    public void setMundoId(Integer mundoId) {
        this.mundoId = mundoId;
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

    public Integer getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(Integer urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void insertar(SQLiteDatabase bd, String nombre, String descripcion, Integer urlImagen){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("descripcion", descripcion);
        cv.put("urlImagen", urlImagen);
        bd.insert("MUNDO", null, cv);
    }

    public Mundo findById(SQLiteDatabase bd, Integer mundoId) {
        Cursor fila = bd.rawQuery("SELECT mundoId, nombre, descripcion, urlImagen FROM MUNDO WHERE mundoId=" + mundoId,null);
        if (fila.moveToFirst()) {
            Mundo mundo = new Mundo();
            mundo.setMundoId(Integer.parseInt(fila.getString(0)));
            mundo.setNombre(fila.getString(1));
            mundo.setDescripcion(fila.getString(2));
            mundo.setUrlImagen(Integer.parseInt(fila.getString(3)));
            fila.close();
            return mundo;
        } else {
            fila.close();
            return null;
        }
    }

    public List<Mundo> getAll(SQLiteDatabase bd) {
        Cursor fila = bd.rawQuery("SELECT mundoId, nombre, descripcion, urlImagen FROM MUNDO",null);
        List<Mundo> mundos = new ArrayList<>();
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            Mundo mundo = new Mundo();
            mundo.setMundoId(Integer.parseInt(fila.getString(0)));
            mundo.setNombre(fila.getString(1));
            mundo.setDescripcion(fila.getString(2));
            mundo.setUrlImagen(Integer.parseInt(fila.getString(3)));
            mundos.add(mundo);
        }
        fila.close();
        return mundos;
    }

}
