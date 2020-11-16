package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.beberomorir.AdminSQLDataBase;
import com.example.beberomorir.Constantes;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.ConfigPartida;
import com.example.beberomorir.Modelos.TipoPartida;
import com.example.beberomorir.Modelos.TipoPrueba;
import com.example.beberomorir.Modelos.TipoResultadoPrueba;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigPartidaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigPartidaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Activity actividad;
    IComunicaPartida iComunicaPartida;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Datos
    List<TipoPrueba> tipoPruebas;
    List<TipoResultadoPrueba> tipoResultadoPruebas;
    List<TipoPartida> tipoPartidas;

    // Inputs
    List<CheckBox> tipoResultadoPruebasChecks;
    List<CheckBox> tipoPruebasChecks;
    Spinner comboTipoPartida;
    Spinner comboNivelPruebas;
    Spinner comboNivelResultadosPruebas;
    Switch switchRolesJugadores;
    CardView cardViewElegirJugadores;


    public ConfigPartidaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigPartidaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigPartidaFragment newInstance(String param1, String param2) {
        ConfigPartidaFragment fragment = new ConfigPartidaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void construirPantalla(View v) {
        AdminSQLDataBase admin = new AdminSQLDataBase(this.actividad);
        final SQLiteDatabase bd = admin.getWritableDatabase();

        LinearLayout linearLayout1TP = v.findViewById(R.id.tipoPruebaLayout1);
        LinearLayout linearLayout2TP = v.findViewById(R.id.tipoPruebaLayout2);
        final TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPruebas = tipoPrueba.getAll(bd);
        Typeface typeface = ResourcesCompat.getFont(this.actividad, R.font.architects_daughter);
        tipoResultadoPruebasChecks = new ArrayList<>();
        tipoPruebasChecks = new ArrayList<>();
        for (int i= 0; i < tipoPruebas.size(); i++) {
            TipoPrueba tP = tipoPruebas.get(i);
            CheckBox checkBox = new CheckBox(this.actividad);
            checkBox.setButtonDrawable(ContextCompat.getDrawable(this.actividad, R.drawable.checkbox));
            checkBox.setId(tP.getTipoPruebaId());
            checkBox.setHeight(85);
            checkBox.setPadding(10,10,10,10);
            checkBox.setWidth(250);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            checkBox.setTextSize(14);
            checkBox.setTextColor(getResources().getColor(R.color.black));
            checkBox.setText(tP.getNombre());

            checkBox.setTypeface(typeface);
            if (i % 2 == 0) {
                linearLayout1TP.addView(checkBox);
            } else {
                linearLayout2TP.addView(checkBox);
            }

            tipoPruebasChecks.add(checkBox);
        }

        LinearLayout linearLayoutTRP1 = v.findViewById(R.id.tipoResultadoLayout1);
        LinearLayout linearLayoutTRP2 = v.findViewById(R.id.tipoResultadoLayout2);
        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPruebas = tipoResultadoPrueba.getAll(bd);
        for (int i= 0; i < tipoResultadoPruebas.size(); i++) {
            TipoResultadoPrueba tP = tipoResultadoPruebas.get(i);
            CheckBox checkBox = new CheckBox(this.actividad);
            checkBox.setButtonDrawable(ContextCompat.getDrawable(this.actividad, R.drawable.checkbox));
            checkBox.setId(Constantes.TIPO_RESULTADO_PRUEBA_CHECK_ID + tP.getTipoResultadoPruebaId());
            checkBox.setHeight(85);
            checkBox.setPadding(10,10,10,10);
            checkBox.setWidth(250);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            checkBox.setTextSize(14);
            checkBox.setTextColor(getResources().getColor(R.color.black));
            checkBox.setText(tP.getNombre());

            checkBox.setTypeface(typeface);
            if (i % 2 == 0) {
                linearLayoutTRP1.addView(checkBox);
            }
            else {
                linearLayoutTRP2.addView(checkBox);
            }
            tipoResultadoPruebasChecks.add(checkBox);
        }

        comboTipoPartida = (Spinner) v.findViewById(R.id.comboTipoPartida);
        final TipoPartida tipoPartida = new TipoPartida();
        tipoPartidas = tipoPartida.getAll(bd);
        String []opcionesTipoPartida = new String[tipoPartidas.size()];
        for (int i= 0; i<tipoPartidas.size(); i++) {
            opcionesTipoPartida[i] = tipoPartidas.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.actividad,android.R.layout.simple_spinner_item, opcionesTipoPartida);
        comboTipoPartida.setAdapter(adapter);


        comboNivelPruebas = (Spinner) v.findViewById(R.id.comboNivelPruebas);
        final String []nivelPruebas = new String[Constantes.LISTA_NIVELES_PRUEBA];
        for (int i=0; i< Constantes.LISTA_NIVELES_PRUEBA; i++) {
            nivelPruebas[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.actividad,android.R.layout.simple_spinner_item, nivelPruebas);
        comboNivelPruebas.setAdapter(adapter2);

        comboNivelResultadosPruebas = (Spinner) v.findViewById(R.id.comboNivelResultadosPruebas);
        String []nivelResultadosPruebas = new String[Constantes.LISTA_NIVELES_RESULTADOS_PRUEBA];
        for (int i=0; i< Constantes.LISTA_NIVELES_RESULTADOS_PRUEBA; i++) {
            nivelResultadosPruebas[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.actividad,android.R.layout.simple_spinner_item, nivelResultadosPruebas);
        comboNivelResultadosPruebas.setAdapter(adapter3);

        switchRolesJugadores = v.findViewById(R.id.rolesJugador);

        cardViewElegirJugadores = v.findViewById(R.id.anadirJugadoresConfCard);
        cardViewElegirJugadores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TipoPrueba> tipoPruebas = new ArrayList<>();
                TipoPrueba t = new TipoPrueba();
                List<TipoResultadoPrueba> tipoResultadoPruebas = new ArrayList<>();
                TipoResultadoPrueba trp = new TipoResultadoPrueba();
                for (CheckBox c : tipoPruebasChecks) {
                    if (c.isChecked()) {
                        t = t.findById(bd, c.getId());
                        tipoPruebas.add(t);
                    }
                }
                for (CheckBox c : tipoResultadoPruebasChecks) {
                    if (c.isChecked()) {
                        trp = trp.findById(bd, c.getId() - Constantes.TIPO_RESULTADO_PRUEBA_CHECK_ID);
                        tipoResultadoPruebas.add(trp);
                    }
                }
                TipoPartida tP = tipoPartida.findByNombre(bd, comboTipoPartida.getSelectedItem().toString());
                
                iComunicaPartida.verElegirJugadores(Integer.parseInt(comboNivelPruebas.getSelectedItem().toString()), Integer.parseInt(comboNivelResultadosPruebas.getSelectedItem().toString()), Constantes.stringToBoolean(switchRolesJugadores.isChecked()),
                        tipoPruebas, tipoResultadoPruebas, tP);
            }
        });
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

        View view = inflater.inflate(R.layout.fragment_config_partida, container, false);
        construirPantalla(view);
        return view;
    }
}