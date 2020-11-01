package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.beberomorir.Adaptadores.AdaptadorJugador;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElegirJugadoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElegirJugadoresFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Activity actividad;
    IComunicaPartida iComunicaPartida;
    CardView configComenzarPartida;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ElegirJugadoresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElegirJugadoresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ElegirJugadoresFragment newInstance(String param1, String param2) {
        ElegirJugadoresFragment fragment = new ElegirJugadoresFragment();
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
        View view = inflater.inflate(R.layout.fragment_elegir_jugadores, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                iComunicaPartida.verConfigurarPartida();
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        configComenzarPartida = view.findViewById(R.id.empezarPartidaConfCard);
        configComenzarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaPartida.verTablero();
            }
        });
        ArrayList<Jugador> listaJugadores=new ArrayList<Jugador>();
        for (int i = 0; i < 4; i++) {
            Jugador j1= new Jugador();
            Jugador j2= new Jugador();
            Jugador j3= new Jugador();
            j1.setNombre("Nombre1");
            j1.setJugadorId(R.mipmap.mundos1);
            listaJugadores.add(j1);
            j2.setNombre("Nombre2");
            j2.setJugadorId(R.mipmap.mundos2);
            listaJugadores.add(j2);
            j3.setNombre("Nombre3");
            j3.setJugadorId(R.mipmap.mundos3);
            listaJugadores.add(j3);
        }

        AdaptadorJugador adaptador = new AdaptadorJugador(this.actividad, listaJugadores);
        ListView lv1 = (ListView)view.findViewById(R.id.listaJugadores);
        lv1.setAdapter(adaptador);
        return view;
    }
}