package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.beberomorir.Constantes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public Partida insertar(SQLiteDatabase bd, int configPartidaId, String nombre) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = sdf.format(new Date());
        ContentValues cv = new ContentValues();
        cv.put("configPartidaId", configPartidaId);
        cv.put("nombre", nombre);
        cv.put("fecha", date);
        cv.put("finalizada", Constantes.NO);
        long id = bd.insert("PARTIDA", null, cv);

        return  findById(bd, (int) id);
    }

    public Partida findById(SQLiteDatabase bd, int partidaId) {
        Cursor fila = bd.rawQuery("SELECT partidaId, configPartidaId, fecha, finalizada, mundoPartidaActualId FROM PARTIDA WHERE partidaId=" + partidaId,null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (fila.moveToFirst()) {
            Partida partida = new Partida();
            ConfigPartida configPartida = new ConfigPartida();
            partida.setPartidaId(fila.getInt(0));
            configPartida = configPartida.findById(bd, fila.getInt(1));
            partida.setConfigPartida(configPartida);
            try {
                partida.setFecha(sdf.parse(fila.getString(2)));
            } catch (ParseException e) {
                System.out.println(e);
            }
            partida.setFinalizada(fila.getString(3));
            fila.close();
            return partida;
        } else {
            fila.close();
            return null;
        }
    }

    public ArrayList<Partida> getAll(SQLiteDatabase bd) throws ParseException {
        Cursor fila = bd.rawQuery("SELECT partidaId, configPartidaId, fecha, finalizada, mundoPartidaActual FROM PARTIDA",null);
        ArrayList<Partida> partidas = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()) {
            Partida partida = new Partida();
            ConfigPartida configPartida = new ConfigPartida();
            partida.setPartidaId(fila.getInt(0));
            configPartida = configPartida.findById(bd, fila.getInt(1));
            partida.setConfigPartida(configPartida);
            partida.setFecha(sdf.parse(fila.getString(2)));
            partida.setFinalizada(fila.getString(3));
            partidas.add(partida);
        }
        fila.close();
        return partidas;
    }
}
