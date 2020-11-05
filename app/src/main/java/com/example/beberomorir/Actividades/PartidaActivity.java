package com.example.beberomorir.Actividades;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.beberomorir.AdminSQLDataBase;
import com.example.beberomorir.Fragmentos.ConfigPartidaFragment;
import com.example.beberomorir.Fragmentos.ElegirJugadoresFragment;
import com.example.beberomorir.Fragmentos.TableroFragment;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.MainActivity;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.util.List;
import java.util.Objects;

public class PartidaActivity extends AppCompatActivity implements IComunicaPartida {

    private TextView mTextView;
    Fragment fragmentTablero;
    Fragment fragmentJugadores;
    Fragment dialogFragmentConfigPartida;
    Fragment dialogFragmentElegirJugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        /*fragmentTablero = new TableroFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();*/
        mTextView = (TextView) findViewById(R.id.text);
        dialogFragmentConfigPartida = new ConfigPartidaFragment();
        dialogFragmentElegirJugadores = new ElegirJugadoresFragment();
        fragmentTablero = new TableroFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, dialogFragmentConfigPartida).commit();
        //dialogFragmentConfigPartida.getView().setFocusableInTouchMode(true);
        //dialogFragmentConfigPartida.getView().requestFocus();
        //dialogFragmentConfigPartida.show(getSupportFragmentManager(), "Dialogo config partida");
    }

    @Override
    public void verConfigurarPartida() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, dialogFragmentConfigPartida).commit();
    }

    @Override
    public void verElegirJugadores() {
        //dialogFragmentConfigPartida.dismissAllowingStateLoss();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, dialogFragmentElegirJugadores).commit();
        //dialogFragmentElegirJugadores.show(getSupportFragmentManager(), "Dialogo elegir jugadores partida");
    }

    @Override
    public void verTablero(List<Jugador> jugadores) {
        for (Jugador jugador : jugadores){
            System.out.println(jugador.getNombre() + jugador.getSeleccionado());
        }
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Jugador jugador = new Jugador();

        Jugador j = jugador.insertar(bd, "Jugador 3", "Superpringao", "");
        Toast.makeText(this, j.getNombre(),
                Toast.LENGTH_SHORT).show();
        System.out.println(j.getNombre());

        List<Jugador> jugadors = jugador.getAll(bd);

        for (Jugador jugador1: jugadors) {
            System.out.println(jugador1.getNombre());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
    }
}