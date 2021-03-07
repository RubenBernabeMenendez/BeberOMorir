package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.Mundo;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElegirMundoPartidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElegirMundoPartidaFragment extends AppCompatDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Activity actividad;
    IComunicaPartida iComunicaPartida;
    List<MundoPartida> mundoPartidas;

    // View
    TextView nombreMundoIzq, nombreMundoDer;
    CardView mundoIzq, mundoDer;

    public ElegirMundoPartidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElegirMundoPartidaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ElegirMundoPartidaFragment newInstance(String param1, String param2) {
        ElegirMundoPartidaFragment fragment = new ElegirMundoPartidaFragment();
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
        View view = inflater.inflate(R.layout.fragment_elegir_mundo_partida, container, false);
        nombreMundoDer = view.findViewById(R.id.nombreMundoDer);
        nombreMundoIzq = view.findViewById(R.id.nombreMundoIzq);
        nombreMundoIzq.setText(mundoPartidas.get(0).getMundo().getNombre());
        nombreMundoDer.setText(mundoPartidas.get(1).getMundo().getNombre());
        mundoDer = view.findViewById(R.id.mundoDerCard);
        mundoIzq = view.findViewById(R.id.mundoIzqCard);
        mundoDer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaPartida.seleccionarMundoPartida(mundoPartidas.get(1));
            }
        });
        mundoIzq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaPartida.seleccionarMundoPartida(mundoPartidas.get(0));
            }
        });


        return view;
    }

    public void setMundoPartidas(List<MundoPartida> mundoPartidas) {
        this.mundoPartidas = mundoPartidas;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        iComunicaPartida.pausarPartida(null, mundoPartidas.get(0));
    }
}