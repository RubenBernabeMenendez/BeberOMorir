package com.example.beberomorir.Actividades;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.beberomorir.AdminSQLDataBase;
import com.example.beberomorir.Constantes;
import com.example.beberomorir.Fragmentos.ConfigPartidaFragment;
import com.example.beberomorir.Fragmentos.ElegirJugadoresFragment;
import com.example.beberomorir.Fragmentos.NuevoJugadorFragment;
import com.example.beberomorir.Fragmentos.TableroFragment;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.MainActivity;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PartidaActivity extends AppCompatActivity implements IComunicaPartida {

    private TextView mTextView;
    Fragment fragmentTablero;
    Fragment fragmentConfigPartida;
    Fragment fragmentElegirJugadores;
    NuevoJugadorFragment addJugador;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        mTextView = (TextView) findViewById(R.id.text);
        fragmentConfigPartida = new ConfigPartidaFragment();
        fragmentElegirJugadores = new ElegirJugadoresFragment();
        fragmentTablero = new TableroFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentConfigPartida).commit();
    }

    @Override
    public void verConfigurarPartida() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentConfigPartida).commit();
    }

    @Override
    public void verElegirJugadores() {

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentElegirJugadores).commit();
    }

    @Override
    public void verTablero(List<Jugador> jugadores) {
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + jugador.getSeleccionado());
        }
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Jugador jugador = new Jugador();

        List<Jugador> jugadors = jugador.getAll(bd);

        for (Jugador jugador1 : jugadors) {
            System.out.println(jugador1.getNombre());
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
    }

    @Override
    public void addJugador() {
        addJugador = new NuevoJugadorFragment();
        addJugador.show(getSupportFragmentManager(), "Nuevo jugador");
    }

    @Override
    public void nuevaImagen() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        227);
            }


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        226);
            }
        } else {
            Intent sacarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (sacarFoto.resolveActivity(getPackageManager()) != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Beber o morir");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Sacada: " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                sacarFoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(sacarFoto, Constantes.REQUEST_CODE_TAKE_PHOTO);
            }

        }

    }

    @Override
    public void anadirJugadorToBD(String nombre, String apodo, byte[] imagen) {
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Jugador jugador = new Jugador();

        Jugador j = jugador.insertar(bd, nombre, apodo, imagen);
        addJugador.dismiss();
        System.out.println(j.getNombre() + j.getApodo() );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            
            addJugador.setUrlImage(convertUriToByteArray(photoURI));

            addJugador.setFoto(photoURI);

        }
    }
    
    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            Log.e("TAG", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private byte[] convertUriToByteArray(Uri uri)
    {
        byte[] result = new byte[0];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            FileInputStream fis = new FileInputStream(new File(String.valueOf(getRealPathFromURI(this, uri))));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);

            result = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}