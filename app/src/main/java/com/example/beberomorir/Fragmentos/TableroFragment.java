package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beberomorir.Constantes;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableroFragment extends Fragment {

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
    MundoPartida mundoPartidaActual;
    ImageView nivel0, nivel1_1, nivel1_2, nivel2_1, nivel2_2, nivel2_3, nivel2_4;
    TextView tNivel0, tNivel1_1, tNivel1_2, tNivel2_1, tNivel2_2, tNivel2_3, tNivel2_4;

    public TableroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableroFragment newInstance(String param1, String param2) {
        TableroFragment fragment = new TableroFragment();
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
        View view = inflater.inflate(R.layout.fragment_tablero, container, false);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                iComunicaPartida.verPruebaAzar();
            }
        };
        nivel0 = view.findViewById(R.id.casillaNivel0);
        nivel1_1 = view.findViewById(R.id.casillaNivel1_1);
        nivel1_2 = view.findViewById(R.id.casillaNivel1_2);
        nivel2_1 = view.findViewById(R.id.casillaNivel2_1);
        nivel2_2 = view.findViewById(R.id.casillaNivel2_2);
        nivel2_3 = view.findViewById(R.id.casillaNivel2_3);
        nivel2_4 = view.findViewById(R.id.casillaNivel2_4);

        tNivel0 = view.findViewById(R.id.textoNivel0);
        tNivel1_1 = view.findViewById(R.id.textoNivel1_1);
        tNivel1_2 = view.findViewById(R.id.textoNivel1_2);
        tNivel2_1 = view.findViewById(R.id.textoNivel2_1);
        tNivel2_2 = view.findViewById(R.id.textoNivel2_2);
        tNivel2_3 = view.findViewById(R.id.textoNivel2_3);
        tNivel2_4 = view.findViewById(R.id.textoNivel2_4);

        if (this.mundoPartidaActual == null) {
            crearTablero();
        } else {
            actualizarTablero();
        }


        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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

    public List<MundoPartida> getMundoPartidas() {
        return mundoPartidas;
    }

    public void crearTablero() {
        Random random = new Random();
        int aux = random.nextInt(Constantes.NUMERO_MUNDOS_NIVEL);
        for (int i = 0; i < Constantes.NUMERO_NIVELES_MUNDO_PANTALLA; i++) {
            List<MundoPartida> mundoPartidasAux = new ArrayList<>();
            for (MundoPartida mundoPartida : this.mundoPartidas) {
                if (mundoPartida.getNivelMundo() == i) {
                    mundoPartidasAux.add(mundoPartida);
                }
            }
            if (i == 0) {
                this.mundoPartidaActual = mundoPartidasAux.get(aux);
                this.tNivel0.setText(mundoPartidasAux.get(aux).getMundo().getNombre());
                this.nivel0.setImageResource(mundoPartidasAux.get(aux).getUrlImagen());
            } else if (i == 1) {
                if (this.mundoPartidaActual.getOrden() % 2 == 0) {
                    this.tNivel1_1.setText(mundoPartidasAux.get(0).getMundo().getNombre());
                    this.nivel1_1.setImageResource(mundoPartidasAux.get(0).getUrlImagen());
                    this.tNivel1_2.setText(mundoPartidasAux.get(1).getMundo().getNombre());
                    this.nivel1_2.setImageResource(mundoPartidasAux.get(1).getUrlImagen());
                } else {
                    this.tNivel1_1.setText(mundoPartidasAux.get(2).getMundo().getNombre());
                    this.nivel1_1.setImageResource(mundoPartidasAux.get(2).getUrlImagen());
                    this.tNivel1_2.setText(mundoPartidasAux.get(3).getMundo().getNombre());
                    this.nivel1_2.setImageResource(mundoPartidasAux.get(3).getUrlImagen());
                }
            } else if (i == 2) {
                this.tNivel2_1.setText(mundoPartidasAux.get(0).getMundo().getNombre());
                this.nivel2_1.setImageResource(mundoPartidasAux.get(0).getUrlImagen());
                this.tNivel2_2.setText(mundoPartidasAux.get(1).getMundo().getNombre());
                this.nivel2_2.setImageResource(mundoPartidasAux.get(1).getUrlImagen());
                this.tNivel2_3.setText(mundoPartidasAux.get(2).getMundo().getNombre());
                this.nivel2_3.setImageResource(mundoPartidasAux.get(2).getUrlImagen());
                this.tNivel2_4.setText(mundoPartidasAux.get(3).getMundo().getNombre());
                this.nivel2_4.setImageResource(mundoPartidasAux.get(3).getUrlImagen());
            }
        }
    }

    public void actualizarTablero() {
        for (int i = 0; i < Constantes.NUMERO_NIVELES_MUNDO_PANTALLA; i++) {
            List<MundoPartida> mundoPartidasAux = new ArrayList<>();
            for (MundoPartida mundoPartida : this.mundoPartidas) {
                if (mundoPartida.getNivelMundo() == i + this.mundoPartidaActual.getNivelMundo()) {
                    mundoPartidasAux.add(mundoPartida);
                }
            }
            if (i == 0) {
                this.tNivel0.setText(this.mundoPartidaActual.getMundo().getNombre());
                this.nivel0.setImageResource(this.mundoPartidaActual.getUrlImagen());
            } else if (i == 1) {
                if (this.mundoPartidaActual.getOrden() % 2 == 0) {
                    this.tNivel1_1.setText(mundoPartidasAux.get(0).getMundo().getNombre());
                    this.nivel1_1.setImageResource(mundoPartidasAux.get(0).getUrlImagen());
                    this.tNivel1_2.setText(mundoPartidasAux.get(1).getMundo().getNombre());
                    this.nivel1_2.setImageResource(mundoPartidasAux.get(1).getUrlImagen());
                } else {
                    this.tNivel1_1.setText(mundoPartidasAux.get(2).getMundo().getNombre());
                    this.nivel1_1.setImageResource(mundoPartidasAux.get(2).getUrlImagen());
                    this.tNivel1_2.setText(mundoPartidasAux.get(3).getMundo().getNombre());
                    this.nivel1_2.setImageResource(mundoPartidasAux.get(3).getUrlImagen());
                }
            } else if (i == 2) {
                this.tNivel2_1.setText(mundoPartidasAux.get(0).getMundo().getNombre());
                this.nivel2_1.setImageResource(mundoPartidasAux.get(0).getUrlImagen());
                this.tNivel2_2.setText(mundoPartidasAux.get(1).getMundo().getNombre());
                this.nivel2_2.setImageResource(mundoPartidasAux.get(1).getUrlImagen());
                this.tNivel2_3.setText(mundoPartidasAux.get(2).getMundo().getNombre());
                this.nivel2_3.setImageResource(mundoPartidasAux.get(2).getUrlImagen());
                this.tNivel2_4.setText(mundoPartidasAux.get(3).getMundo().getNombre());
                this.nivel2_4.setImageResource(mundoPartidasAux.get(3).getUrlImagen());
            }
        }
    }

    public void setMundoPartidas(List<MundoPartida> mundoPartidas) {
        this.mundoPartidas = mundoPartidas;
    }

    public void setMundoPartidasAndMundoActual(List<MundoPartida> mundoPartidas, MundoPartida mundoPartida) {
        this.mundoPartidaActual = mundoPartida;
        this.mundoPartidas = mundoPartidas;
    }
}