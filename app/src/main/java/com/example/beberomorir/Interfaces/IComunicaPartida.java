package com.example.beberomorir.Interfaces;

import android.widget.LinearLayout;

import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.Modelos.PruebaJugador;
import com.example.beberomorir.Modelos.TipoPartida;
import com.example.beberomorir.Modelos.TipoPrueba;
import com.example.beberomorir.Modelos.TipoResultadoPrueba;

import java.util.List;

public interface IComunicaPartida {

    void verConfigurarPartida();

    void verElegirJugadores(int nivelPruebas, int nivelResultados, String roles, List<TipoPrueba> tipoPruebas, List<TipoResultadoPrueba> tipoResultadoPruebas, TipoPartida tipoPartida);

    void verTablero(List<MundoPartida> mundoPartidas);

    void nextPlayer();

    void empezarPartida(List<Jugador> jugadores);

    void addJugador();

    void nuevaImagen();

    void anadirJugadorToBD(String nombre, String apodo, byte[] urlAnadirJugador);

    void verPruebaAzar();

    void menuRondaJugador(JugadorPartida jugadorPartida, MundoPartida mundoPartida);

    void empezarPrueba(PruebaJugador pruebaJugador);
}
