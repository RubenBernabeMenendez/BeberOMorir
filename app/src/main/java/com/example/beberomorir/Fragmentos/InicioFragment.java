package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beberomorir.Interfaces.IComunicaFragmentos;
import com.example.beberomorir.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Activity activity;
    CardView cardContinuarPartida, cardNuevaPartida, cardJugadores, cardHistorial, cardAjustes, cardAcercaDe, cardAnadirContenido;
    IComunicaFragmentos iComunicaFragmentos;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);

        cardContinuarPartida = vista.findViewById(R.id.continuarPartidaCard);
        cardContinuarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.continuarPartida();
            }
        });

        cardNuevaPartida = vista.findViewById(R.id.nuevaPartidaCard);
        cardNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.nuevaPartida();
            }
        });

        cardJugadores = vista.findViewById(R.id.jugadoresCard);
        cardJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.jugadores();
            }
        });

        cardAcercaDe = vista.findViewById(R.id.acercaDeCard);
        cardAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.acercaDe();
            }
        });

        cardAjustes = vista.findViewById(R.id.ajustesCard);
        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.ajustes();
            }
        });

        cardAnadirContenido = vista.findViewById(R.id.adminCard);
        cardAnadirContenido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.anadirContenido();
            }
        });

        cardHistorial = vista.findViewById(R.id.historyCard);
        cardHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragmentos.historial();
            }
        });

        return vista;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
            iComunicaFragmentos = (IComunicaFragmentos) activity;
        }
    }
}