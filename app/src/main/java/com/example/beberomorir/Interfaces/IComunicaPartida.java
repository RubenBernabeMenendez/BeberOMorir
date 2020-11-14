package com.example.beberomorir.Interfaces;

import android.widget.LinearLayout;

import com.example.beberomorir.Modelos.Jugador;

import java.util.List;

public interface IComunicaPartida {

    void verConfigurarPartida();

    void verElegirJugadores();

    void verTablero(List<Jugador> jugadores);

    void addJugador();

    void nuevaImagen();

    void anadirJugadorToBD(String nombre, String apodo, byte[] urlAnadirJugador);
}
