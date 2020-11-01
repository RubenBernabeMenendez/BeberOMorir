package com.example.beberomorir.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdaptadorJugador extends ArrayAdapter<Jugador> {

    Activity appCompatActivity;
    List<Jugador> jugadors = new ArrayList<>();

    public AdaptadorJugador(@NonNull Activity context, ArrayList<Jugador> jugadors) {
        super(context, R.layout.layout_jugador, jugadors);
        this.jugadors = jugadors;
        appCompatActivity = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        View item = inflater.inflate(R.layout.layout_jugador, null);

        TextView textView1 = (TextView)item.findViewById(R.id.nombreJugador);


        
        textView1.setText(this.jugadors.get(position).getNombre());

        ImageView imageView1 = (ImageView)item.findViewById(R.id.imagenJugador);
        imageView1.setImageResource(this.jugadors.get(position).getJugadorId());
        return(item);
    }
}
