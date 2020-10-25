package com.example.beberomorir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.beberomorir.Fragmentos.InicioFragment;
import com.example.beberomorir.Interfaces.IComunicaFragmentos;

public class MainActivity extends AppCompatActivity implements IComunicaFragmentos {
    SQLiteDatabase bd;

    Fragment fragmentInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        this.bd = admin.getWritableDatabase();

        fragmentInicio = new InicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipal, fragmentInicio).commit();

        System.out.println(this.bd.getPath());
    }

    /** Called when the user taps the Send button */
    public void openMenu(View view) {
        // Do something in response to button
        Intent i = new Intent(this, ConfigPartidaView.class );
        startActivity(i);
    }

    @Override
    public void continuarPartida() {
        Toast.makeText(getApplicationContext(), "Iniciar juego", Toast.LENGTH_SHORT).show();
    }
}