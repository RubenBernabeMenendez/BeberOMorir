package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beberomorir.Constantes;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.Modelos.MundoPartidaTipoPrueba;
import com.example.beberomorir.Modelos.PruebaJugador;
import com.example.beberomorir.Modelos.TipoPrueba;
import com.example.beberomorir.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.beberomorir.Constantes.distinctByKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuRondaJugadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuRondaJugadorFragment extends AppCompatDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Activity actividad;
    IComunicaPartida iComunicaPartida;
    JugadorPartida jugadorPartida;
    List<PruebaJugador> pruebaJugadors;

    public MenuRondaJugadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuRondaJugadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuRondaJugadorFragment newInstance(String param1, String param2) {
        MenuRondaJugadorFragment fragment = new MenuRondaJugadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad = (Activity) context;
            iComunicaPartida = (IComunicaPartida) this.actividad;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_ronda_jugador, container, false);

        ImageView imageViewJugador = view.findViewById(R.id.imagenJugador);
        TextView textViewJugador = view.findViewById(R.id.nombreJugador);
        LinearLayout linearLayoutContenedor = view.findViewById(R.id.linearLayoutNivel1);
        LinearLayout linearLayoutContenedor2 = view.findViewById(R.id.linearLayoutNivel2);
        LinearLayout linearLayoutContenedor3 = view.findViewById(R.id.linearLayoutNivel3);

        Bitmap bitmap = BitmapFactory.decodeByteArray(this.jugadorPartida.getJugador().getUrlImagen(), 0, this.jugadorPartida.getJugador().getUrlImagen().length);
        imageViewJugador.setImageBitmap(bitmap);
        textViewJugador.setText(this.jugadorPartida.getJugador().getNombre());

        List<PruebaJugador> pruebaJugadorsSorter = this.pruebaJugadors.stream()
                .filter(distinctByKey(p -> p.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId()))
                .collect(Collectors.toList());

        for (int i= 0; i < pruebaJugadorsSorter.size(); i++) {
            final Integer index = i;
            CardView cardView = new CardView(this.actividad);
            LinearLayout.LayoutParams layoutParamsCard = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.0);
            layoutParamsCard.leftMargin = 10;
            layoutParamsCard.rightMargin = 10;
            layoutParamsCard.bottomMargin = 10;
            layoutParamsCard.topMargin = 10;
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            cardView.setCardElevation(0);
            cardView.setLayoutParams(layoutParamsCard);
            cardView.setRadius(20);
            List<PruebaJugador> pruebaJugadorsLibres = this.pruebaJugadors.stream()
                    .filter(pruebaJugador -> pruebaJugadorsSorter.get(index).getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId().equals(pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId()))
                    .filter(p -> Constantes.NO.equals(p.getPruebaPartidaId().getFinalizado())).collect(Collectors.toList());
            if (!pruebaJugadorsLibres.isEmpty()) {
                PruebaJugador pruebaJugadorAux = pruebaJugadorsLibres.get(0);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iComunicaPartida.empezarPrueba(pruebaJugadorAux);
                    }
                });
            } else {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar = Snackbar
                                .make(view,"No quedan pruebas", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }


            LinearLayout linearLayoutCard = new LinearLayout(this.actividad);
            linearLayoutCard.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.0);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
            linearLayoutCard.setBackgroundColor(Color.GREEN);
            linearLayoutCard.setLayoutParams(layoutParams);
            linearLayoutCard.setTextAlignment(LinearLayout.TEXT_ALIGNMENT_CENTER);

            ImageView imageView = new ImageView(this.actividad);
            imageView.setImageResource(pruebaJugadorsSorter.get(i).getPruebaPartidaId().getPrueba().getTipoPrueba().getUrlImagen());
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
            layoutParamsImage.gravity = Gravity.CENTER;
            imageView.setLayoutParams(layoutParamsImage);
            layoutParamsImage.rightMargin = 4;
            layoutParamsImage.leftMargin = 4;

            TextView textView = new TextView(this.actividad);
            textView.setText(pruebaJugadorsSorter.get(i).getPruebaPartidaId().getPrueba().getTipoPrueba().getNombre() + " " + pruebaJugadorsLibres.size());


            linearLayoutCard.addView(imageView);
            linearLayoutCard.addView(textView);
            cardView.addView(linearLayoutCard);
            if (i / 3 == 0) {
                linearLayoutContenedor.addView(cardView);
            } else if (i / 3 == 1) {
                linearLayoutContenedor2.addView(cardView);
            } else {
                linearLayoutContenedor3.addView(cardView);
            }


        }


        return view;
    }

    public void setJugadorPartida(JugadorPartida jugadorPartida) {
        this.jugadorPartida = jugadorPartida;
    }

    public void setPruebaJugadors(List<PruebaJugador> pruebaJugadors) {
        this.pruebaJugadors = pruebaJugadors;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        iComunicaPartida.pausarPartida(jugadorPartida, null);
    }

}