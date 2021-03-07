package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.beberomorir.Actividades.PartidaActivity;
import com.example.beberomorir.Actividades.RondaPartidaActivity;
import com.example.beberomorir.Adaptadores.AdaptadorJugador;
import com.example.beberomorir.Adaptadores.AdaptadorJugadorTablero;
import com.example.beberomorir.Constantes;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    // Data
    List<MundoPartida> mundoPartidas;
    MundoPartida mundoPartidaActual;
    List<JugadorPartida> jugadorPartidas;
    Boolean elegirMundoPartida;

    // View
    ImageView nivel0, nivel1_1, nivel1_2, nivel2_1, nivel2_2, nivel2_3, nivel2_4;
    TextView tNivel0, tNivel1_1, tNivel1_2, tNivel2_1, tNivel2_2, tNivel2_3, tNivel2_4;
    ImageView jugadorPausa;
    CardView jugadorPausaVisibility;
    LinearLayout layoutJugadoresPrincipal;
    ListView lv1;

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
        final View view = inflater.inflate(R.layout.fragment_tablero, container, false);
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

        layoutJugadoresPrincipal = view.findViewById(R.id.linearLayoutJugadores);
        lv1 = (ListView)view.findViewById(R.id.jugadoresPartidaPrincipal);
        final List<JugadorPartida> jugadoresAux = this.jugadorPartidas;
        final Activity actividadAux = this.actividad;

        jugadorPausaVisibility = view.findViewById(R.id.jugadorPausaCard);
        jugadorPausa = view.findViewById(R.id.jugadorPausaImage);
        jugadorPausaVisibility.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                jugadorPausaVisibility.setVisibility(View.INVISIBLE);
                if (elegirMundoPartida) {
                    List<MundoPartida> mundoPartidasAux = Constantes.getMundoPartidasSiguientes(mundoPartidas, mundoPartidaActual);
                    iComunicaPartida.elegirMundoPartida(mundoPartidasAux, mundoPartidaActual);
                } else {
                    iComunicaPartida.menuRondaJugador(jugadoresAux.get(0), mundoPartidaActual);
                }
            }
        });


        actualizarTablero();
        view.post(new Runnable() {
            @Override
            public void run() {
                final AdaptadorJugadorTablero adaptador = new AdaptadorJugadorTablero(actividadAux, jugadoresAux, Math.min(layoutJugadoresPrincipal.getHeight()/jugadoresAux.size(), 100));
                lv1.setAdapter(adaptador);
                if (elegirMundoPartida) {
                    List<MundoPartida> mundoPartidasAux = Constantes.getMundoPartidasSiguientes(mundoPartidas, mundoPartidaActual);
                    iComunicaPartida.elegirMundoPartida(mundoPartidasAux, mundoPartidaActual);
                } else {
                    iComunicaPartida.menuRondaJugador(jugadoresAux.get(0), mundoPartidaActual);
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                iComunicaPartida.guardarPartida(mundoPartidaActual);
            }
        };

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
        for (int i = 0; i < Constantes.NUMERO_NIVELES_MUNDO_PANTALLA; i++) {
            List<MundoPartida> mundoPartidasAux = new ArrayList<>();
            for (MundoPartida mundoPartida : this.mundoPartidas) {
                if (mundoPartida.getNivelMundo() == i) {
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
                if (mundoPartidasAux.isEmpty()) {
                    if (this.mundoPartidaActual.getOrden() % 2 == 0) {
                        this.tNivel1_1.setText("");
                        this.nivel1_1.setImageResource(0);
                        this.tNivel1_2.setText("");
                        this.nivel1_2.setImageResource(0);
                    } else {
                        this.tNivel1_1.setText("");
                        this.nivel1_1.setImageResource(0);
                        this.tNivel1_2.setText("");
                        this.nivel1_2.setImageResource(0);
                    }
                } else {
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
                }
            } else if (i == 2) {
                if (mundoPartidasAux.isEmpty()) {
                    this.tNivel2_1.setText("");
                    this.nivel2_1.setImageResource(0);
                    this.tNivel2_2.setText("");
                    this.nivel2_2.setImageResource(0);
                    this.tNivel2_3.setText("");
                    this.nivel2_3.setImageResource(0);
                    this.tNivel2_4.setText("");
                    this.nivel2_4.setImageResource(0);
                } else {
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
    }

    public void iniciarJugadores() {
        int height = this.layoutJugadoresPrincipal.getHeight();
        int maxHeightJugador = height / this.jugadorPartidas.size();
        for (JugadorPartida jugadorPartida : this.jugadorPartidas) {
            LinearLayout linearLayoutJug = new LinearLayout(this.actividad);
            linearLayoutJug.setOrientation(LinearLayout.HORIZONTAL);
            linearLayoutJug.setBackgroundColor(getResources().getColor(R.color.yellow));
            linearLayoutJug.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.0));
            linearLayoutJug.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            linearLayoutJug.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            ImageView imageView = new ImageView(this.actividad);
            Bitmap bitmap = BitmapFactory.decodeByteArray(jugadorPartida.getJugador().getUrlImagen(), 0, jugadorPartida.getJugador().getUrlImagen().length);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, (float) 1.0));
            imageView.getLayoutParams().height = Math.min(maxHeightJugador, 50);
            imageView.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            linearLayoutJug.addView(imageView);
            this.layoutJugadoresPrincipal.addView(linearLayoutJug);
        }

    }

    public void setMundoPartidas(List<MundoPartida> mundoPartidas) {
        this.mundoPartidas = mundoPartidas;
    }

    public void addMundoPartidas(List<MundoPartida> mundoPartidas) {
        this.mundoPartidas.addAll(mundoPartidas);
    }

    public void setJugadoresPartida(List<JugadorPartida> jugadoresPartida) {
        this.jugadorPartidas = jugadoresPartida;
    }

    public JugadorPartida nextJugadorPartida() {
        JugadorPartida jugadorPartidaAux = this.jugadorPartidas.get(0);
        this.jugadorPartidas.remove(0);
        this.jugadorPartidas.add(jugadorPartidaAux);
        return this.jugadorPartidas.get(0);
    }

    public void pausarPartida(JugadorPartida jugadorPartida, MundoPartida mundoPartida) {
        if (jugadorPartida != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(this.jugadorPartidas.get(0).getJugador().getUrlImagen(), 0, this.jugadorPartidas.get(0).getJugador().getUrlImagen().length);
            this.jugadorPausa.setImageBitmap(bitmap);
        } else {
            this.jugadorPausa.setImageResource(mundoPartida.getMundo().getUrlImagen());
        }
        this.jugadorPausaVisibility.setVisibility(View.VISIBLE);
    }

    public void setMundoPartidasAndMundoActual(List<MundoPartida> mundoPartidas, MundoPartida mundoPartida) {
        this.mundoPartidaActual = mundoPartida;
        this.mundoPartidas = mundoPartidas;
    }

    public void setMundoPartidaActual(MundoPartida mundoPartidaActual, Boolean configuracionInicial) {
        this.mundoPartidaActual = mundoPartidaActual;
        if (!configuracionInicial) {
            actualizarTablero();
            iComunicaPartida.menuRondaJugador(jugadorPartidas.get(0), mundoPartidaActual);
        }
    }

    public MundoPartida getMundoPartidaActual() {
        return mundoPartidaActual;
    }

    public void setElegirMundoPartida(Boolean elegirMundoPartida) {
        this.elegirMundoPartida = elegirMundoPartida;
    }
}