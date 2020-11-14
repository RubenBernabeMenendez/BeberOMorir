package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.beberomorir.Adaptadores.AdaptadorJugador;
import com.example.beberomorir.AdminSQLDataBase;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.Modelos.Partida;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

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
    CardView configComenzarPartida, addJugadoresConfCard;
    ArrayList<Jugador> listaJugadores=new ArrayList<Jugador>();
    View elegirJugadoreView;

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
        this.elegirJugadoreView = view;

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                iComunicaPartida.verConfigurarPartida();
                // Handle the back button event
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        AdminSQLDataBase admin = new AdminSQLDataBase(this.actividad);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Jugador jugador = new Jugador();
        listaJugadores=jugador.getAll(bd);

        final AdaptadorJugador adaptador = new AdaptadorJugador(this.actividad, listaJugadores);
        ListView lv1 = (ListView)view.findViewById(R.id.listaJugadores);
        lv1.setAdapter(adaptador);


        configComenzarPartida = view.findViewById(R.id.empezarPartidaConfCard);
        configComenzarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Jugador> jugadors = new ArrayList<>();
                for (int i=0 ; i < adaptador.getCount(); i++) {
                    jugadors.add(adaptador.getItem(i));
                }
                iComunicaPartida.verTablero(jugadors);
            }
        });

        addJugadoresConfCard = view.findViewById(R.id.addJugadoresConfCard);
        addJugadoresConfCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                iComunicaPartida.addJugador();
            }
        });
        return view;
    }

    public void recargarJugadores() {

        AdminSQLDataBase admin = new AdminSQLDataBase(this.actividad);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Jugador jugador = new Jugador();
        listaJugadores=jugador.getAll(bd);

        final AdaptadorJugador adaptador = new AdaptadorJugador(this.actividad, listaJugadores);
        ListView lv1 = (ListView)elegirJugadoreView.findViewById(R.id.listaJugadores);
        lv1.setAdapter(adaptador);
    }
}