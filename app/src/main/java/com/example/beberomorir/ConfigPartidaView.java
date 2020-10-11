package com.example.beberomorir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.beberomorir.Modelos.TipoPartida;
import com.example.beberomorir.Modelos.TipoPrueba;
import com.example.beberomorir.Modelos.TipoResultadoPrueba;

import java.util.List;

public class ConfigPartidaView extends AppCompatActivity {
    List<TipoPrueba> tipoPruebas;
    List<TipoResultadoPrueba> tipoResultadoPruebas;
    List<TipoPartida> tipoPartidas;
    Fragment jugadorFila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_partida_view);
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        LinearLayout linearLayoutTP = findViewById(R.id.tipoPruebaLayout);

        LinearLayout linearLayout1TP = findViewById(R.id.tipoPruebaLayout1);
        LinearLayout linearLayout2TP = findViewById(R.id.tipoPruebaLayout2);
        /*jugadorFila = new JugadorFila();
        getSupportFragmentManager().beginTransaction().add(R.id.tipoResultadoPruebaLayout, jugadorFila).commit();*/
        TipoPrueba tipoPrueba = new TipoPrueba();
        tipoPruebas = tipoPrueba.getAll(bd);
        for (int i= 0; i < tipoPruebas.size(); i++) {
            TipoPrueba tP = tipoPruebas.get(i);
            CheckBox checkBox = new CheckBox(this);
            checkBox.setButtonDrawable(ContextCompat.getDrawable(this, R.drawable.checkbox));
            checkBox.setText(tP.getNombre());
            checkBox.setId(tP.getTipoPruebaId());
            checkBox.setHeight(70);
            checkBox.setPadding(10,10,10,10);
            checkBox.setWidth(150);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (i % 2 == 0) {
                linearLayout1TP.addView(checkBox);
            } else {
                linearLayout2TP.addView(checkBox);
            }


        }

        /*LinearLayout linearLayoutTRP = findViewById(R.id.tipoResultadoPruebaLayout);
        TipoResultadoPrueba tipoResultadoPrueba = new TipoResultadoPrueba();
        tipoResultadoPruebas = tipoResultadoPrueba.getAll(bd);
        for (TipoResultadoPrueba tP : tipoResultadoPruebas) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(tP.getNombre());
            checkBox.setId(tP.getTipoResultadoPruebaId());
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayoutTRP.addView(checkBox);
        }*/

        Spinner comboTipoPartida;
        comboTipoPartida = (Spinner) findViewById(R.id.comboTipoPartida);
        TipoPartida tipoPartida = new TipoPartida();
        tipoPartidas = tipoPartida.getAll(bd);
        String []opcionesTipoPartida = new String[tipoPartidas.size()];
        for (int i= 0; i<tipoPartidas.size(); i++) {
            opcionesTipoPartida[i] = tipoPartidas.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opcionesTipoPartida);
        comboTipoPartida.setAdapter(adapter);

        Spinner comboNivelPruebas;
        comboNivelPruebas = (Spinner) findViewById(R.id.comboNivelPruebas);
        String []nivelPruebas = new String[Constantes.LISTA_NIVELES_PRUEBA];
        for (int i=0; i< Constantes.LISTA_NIVELES_PRUEBA; i++) {
            nivelPruebas[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nivelPruebas);
        comboNivelPruebas.setAdapter(adapter2);

        Spinner comboNivelResultadosPruebas;
        comboNivelResultadosPruebas = (Spinner) findViewById(R.id.comboNivelResultadosPruebas);
        String []nivelResultadosPruebas = new String[Constantes.LISTA_NIVELES_RESULTADOS_PRUEBA];
        for (int i=0; i< Constantes.LISTA_NIVELES_RESULTADOS_PRUEBA; i++) {
            nivelResultadosPruebas[i] = String.valueOf(i);
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nivelResultadosPruebas);
        comboNivelResultadosPruebas.setAdapter(adapter3);


    }
}