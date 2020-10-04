package com.example.beberomorir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beberomorir.Modelos.ConfigPartida;

public class MenuActivity extends AppCompatActivity {
    private EditText et1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        et1=(EditText)findViewById(R.id.nameFirst);
        tv1=(TextView)findViewById(R.id.textView);
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