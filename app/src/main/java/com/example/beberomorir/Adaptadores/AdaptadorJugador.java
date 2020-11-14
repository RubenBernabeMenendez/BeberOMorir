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
import android.widget.TextView;

import com.example.beberomorir.Constantes;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class AdaptadorJugador extends ArrayAdapter<Jugador> {

    Activity appCompatActivity;
    List<Jugador> jugadors = new ArrayList<>();

    public AdaptadorJugador(@NonNull Activity context, ArrayList<Jugador> jugadors) {
        super(context, R.layout.layout_jugador, jugadors);
        this.jugadors = jugadors;
        appCompatActivity = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = appCompatActivity.getLayoutInflater();
        View item = inflater.inflate(R.layout.layout_jugador, null);

        TextView textView1 = (TextView) item.findViewById(R.id.nombreJugador);

        textView1.setText(this.jugadors.get(position).getNombre());

        ImageView imageView1 = (ImageView) item.findViewById(R.id.imagenJugador);

        if (this.jugadors.get(position).getUrlImagen() == null) {
            imageView1.setImageResource(Integer.parseInt(Integer.toString(R.mipmap.mundos1)));
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(this.jugadors.get(position).getUrlImagen(), 0, this.jugadors.get(position).getUrlImagen().length);
            imageView1.setImageBitmap(bitmap);
        }

        CardView cardView = (CardView) item.findViewById(R.id.jugadorCard);

        CheckBox checkBox = (CheckBox) item.findViewById(R.id.checkboxJugador);
        checkBox.setChecked(Constantes.booleanToString(jugadors.get(position).getSeleccionado()));

        cardView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxJugador);
                if (checkBox != null) {
                    checkBox.setChecked(!checkBox.isChecked());
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                jugadors.get(position).setSeleccionado(b ? "Y" : "N");
            }
        });
        return (item);
    }
}
