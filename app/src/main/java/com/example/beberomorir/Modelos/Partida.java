package com.example.beberomorir.Modelos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.beberomorir.Constantes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Partida {
    Integer partidaId;
    ConfigPartida configPartida;
    Date fecha;
    String nombre;
    String finalizada;
    Integer mundoPartidaActualId;

    public Integer getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Integer partidaId) {
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

    public Integer getMundoPartidaActualId() {
        return mundoPartidaActualId;
    }

    public void setMundoPartidaActualId(Integer mundoPartidaActualId) {
        this.mundoPartidaActualId = mundoPartidaActualId;
    }
    public Partida insertar(SQLiteDatabase bd, Integer configPartidaId, String nombre) {
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

    public Partida update(SQLiteDatabase bd, Partida partida){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        ContentValues cv = new ContentValues();
        cv.put("partidaId", partida.getPartidaId());
        cv.put("configPartidaId", partida.getConfigPartida().getConfigPartidaId());
        cv.put("nombre", partida.getNombre());
        cv.put("fecha", sdf.format(partida.getFecha()));
        cv.put("finalizada", partida.getFinalizada());
        cv.put("mundoPartidaActualId", partida.getMundoPartidaActualId());
        bd.update("PARTIDA", cv, "partidaId=" + partida.getPartidaId(), null);
        return  partida;
    }

    public Partida findById(SQLiteDatabase bd, Integer partidaId) {
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
        Cursor fila = bd.rawQuery("SELECT partidaId, configPartidaId, fecha, finalizada, mundoPartidaActualId FROM PARTIDA",null);
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
            partida.setMundoPartidaActualId(fila.getInt(4));
            partidas.add(partida);
        }
        fila.close();
        return partidas;
    }

    public Partida getLast(SQLiteDatabase bd) {
        Partida partida = new Partida();
        try {
            List<Partida> partidas = partida.getAll(bd);
            Comparator<Partida> c = Comparator.comparing(Partida::getPartidaId);
            partidas = partidas.stream().sorted(c.reversed()).collect(Collectors.toList());
            return partidas.isEmpty() ? null : partidas.get(0);
        } catch (ParseException e) {
            return null;
        }
    }
}
