package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.PruebaJugador;
import com.example.beberomorir.Modelos.ResultadoPruebaPartida;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PruebaDefaultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PruebaDefaultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Activity actividad;
    IComunicaPartida iComunicaPartida;
    List<ResultadoPruebaPartida> resultadoPruebaPartidas = new ArrayList<>();
    PruebaJugador pruebaJugador;

    //View
    TextView nombrePrueba;
    TextView descripcionPrueba;
    TextView nombreTipoPrueba;
    ImageView imagenTipoPrueba;
    CardView resultadoPrueba;
    RelativeLayout cabeceraTipoPrueba;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PruebaDefaultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PruebaDefaultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PruebaDefaultFragment newInstance(String param1, String param2) {
        PruebaDefaultFragment fragment = new PruebaDefaultFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prueba_default, container, false);

        this.nombrePrueba = view.findViewById(R.id.nombrePrueba);
        this.descripcionPrueba = view.findViewById(R.id.descripcionPrueba);
        this.imagenTipoPrueba = view.findViewById(R.id.imagenTipoPrueba);
        this.nombreTipoPrueba = view.findViewById(R.id.nombreTipoPrueba);
        this.cabeceraTipoPrueba = view.findViewById(R.id.relativeLayoutCabeceraTipoPrueba);
        this.resultadoPrueba = view.findViewById(R.id.resultadoCard);

        this.nombreTipoPrueba.setText(this.pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getNombre());
        this.imagenTipoPrueba.setImageResource(this.pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getUrlImagen());
        this.nombrePrueba.setText(this.pruebaJugador.getPruebaPartidaId().getNombre());
        this.descripcionPrueba.setText(this.pruebaJugador.getPruebaPartidaId().getDescripcion());
        this.resultadoPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaPartida.resultadoPrueba(pruebaJugador, resultadoPruebaPartidas.get(0));
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad = (Activity) context;
            iComunicaPartida = (IComunicaPartida) this.actividad;
        }
    }

    public List<ResultadoPruebaPartida> getResultadoPruebaPartidas() {
        return resultadoPruebaPartidas;
    }

    public void setResultadoPruebaPartidas(List<ResultadoPruebaPartida> resultadoPruebaPartidas) {
        this.resultadoPruebaPartidas = resultadoPruebaPartidas;
    }

    public PruebaJugador getPruebaJugador() {
        return pruebaJugador;
    }

    public void setPruebaJugador(PruebaJugador pruebaJugador) {
        this.pruebaJugador = pruebaJugador;
    }
}