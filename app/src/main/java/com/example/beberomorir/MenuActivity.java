package com.example.beberomorir;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beberomorir.Modelos.ConfigPartida;
import com.example.beberomorir.Modelos.TipoPartida;

import java.util.List;

public class MenuActivity extends AppCompatActivity {
    private AlertDialog.Builder configPartidaBuilder;
    private AlertDialog configPartidaView;
    private EditText et1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void save(View view) {
        // Do something in response to button
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        System.out.println(bd.getPath());
        String cod = et1.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("descripcion", "descri2");
        registro.put("precio", 1.5);
        bd.insert("articulos", null, registro);
        bd.close();
        et1.setText("");
        Toast.makeText(this, "Se cargaron los datos del artículo",
                Toast.LENGTH_SHORT).show();
    }
    public void get(View view) {
        // Do something in response to button
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = et1.getText().toString();
        ConfigPartida configPartida = new ConfigPartida();
        configPartida = configPartida.findById(bd, Integer.parseInt(cod));
        if (configPartida != null) {
            tv1.setText(configPartida.getTipoPartida().getNombre());
        } else
            Toast.makeText(this, "No existe un artículo con dicho código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }
}