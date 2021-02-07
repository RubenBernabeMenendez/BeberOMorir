package com.example.beberomorir.Adaptadores;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beberomorir.Constantes;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class AdaptadorJugadorTablero extends ArrayAdapter<JugadorPartida> {

    Activity appCompatActivity;
    List<JugadorPartida> jugadorPartidas = new ArrayList<>();
    int height;

    public AdaptadorJugadorTablero(@NonNull Activity context, List<JugadorPartida> jugadorPartidas, int height) {
        super(context, R.layout.layout_jugador_partida, jugadorPartidas);
        this.height = height;
        this.jugadorPartidas = jugadorPartidas;
        appCompatActivity = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        View item = inflater.inflate(R.layout.layout_jugador_partida, null);

        ImageView imageView1 = (ImageView) item.findViewById(R.id.imagenJugadorPartida);
        imageView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.0));
        imageView1.getLayoutParams().height = this.height;
        imageView1.getLayoutParams().width = this.height;

        if (this.jugadorPartidas.get(position).getJugador().getUrlImagen() == null) {
            imageView1.setImageResource(Integer.parseInt(Integer.toString(R.mipmap.mundos1)));
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(this.jugadorPartidas.get(position).getJugador().getUrlImagen(), 0, this.jugadorPartidas.get(position).getJugador().getUrlImagen().length);
            imageView1.setImageBitmap(bitmap);
        }

        return (item);
    }

}
