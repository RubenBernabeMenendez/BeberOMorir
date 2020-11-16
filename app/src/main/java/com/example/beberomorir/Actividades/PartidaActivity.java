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
import com.example.beberomorir.Fragmentos.PruebaAzarFragment;
import com.example.beberomorir.Fragmentos.TableroFragment;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.MainActivity;
import com.example.beberomorir.Modelos.ConfigPartida;
import com.example.beberomorir.Modelos.ConfigTipoPrueba;
import com.example.beberomorir.Modelos.ConfigTipoResultadoPrueba;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.Modelos.Mundo;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.Modelos.Partida;
import com.example.beberomorir.Modelos.TipoPartida;
import com.example.beberomorir.Modelos.TipoPrueba;
import com.example.beberomorir.Modelos.TipoResultadoPrueba;
import com.example.beberomorir.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PartidaActivity extends AppCompatActivity implements IComunicaPartida {


    TableroFragment fragmentTablero;
    Fragment fragmentConfigPartida;
    ElegirJugadoresFragment fragmentElegirJugadores;
    NuevoJugadorFragment addJugador;
    PruebaAzarFragment fragmentPruebaAzar;


    //Datos
    private Uri photoURI;
    private List<TipoPrueba> tipoPruebasPartida;
    private List<TipoResultadoPrueba> tipoResultadosPruebasPartida;
    private ConfigPartida configPartida;
    private List<JugadorPartida> jugadoresPartida;
    private Partida partida;
    private List<MundoPartida> mundosPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        fragmentConfigPartida = new ConfigPartidaFragment();
        fragmentElegirJugadores = new ElegirJugadoresFragment();
        fragmentTablero = new TableroFragment();
        fragmentPruebaAzar = new PruebaAzarFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentConfigPartida).commit();
    }

    @Override
    public void verConfigurarPartida() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentConfigPartida).commit();
    }

    @Override
    public void verElegirJugadores(int nivelPruebas, int nivelResultados, String roles, List<TipoPrueba> tipoPruebas, List<TipoResultadoPrueba> tipoResultadoPruebas, TipoPartida tipoPartida) {
        configPartida = new ConfigPartida();
        configPartida.setTipoPartida(tipoPartida);
        configPartida.setRolesJugador(roles);
        configPartida.setNivelResultadoPruebas(nivelResultados);
        configPartida.setNivelPruebas(nivelPruebas);
        tipoPruebasPartida = tipoPruebas;
        tipoResultadosPruebasPartida = tipoResultadoPruebas;
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentElegirJugadores).commit();
    }

    @Override
    public void verTablero(List<MundoPartida> mundoPartidas) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
    }

    @Override
    public void verPruebaAzar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentPruebaAzar).commit();
    }

    @Override
    public void empezarPartida(List<Jugador> jugadores){
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + jugador.getSeleccionado());
        }

        this.configPartida = crearConfigPartida(bd);
        this.partida = crearPartida(bd, this.configPartida.getConfigPartidaId(), "Prueba");
        this.jugadoresPartida = crearJugadoresPartida(bd, jugadores);
        this.mundosPartida = crearMundosPartida(bd);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
        fragmentTablero.setMundoPartidas(this.mundosPartida);
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


            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        225);
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        227);
            }

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
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
        fragmentElegirJugadores.recargarJugadores();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constantes.REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            
            addJugador.setUrlImage(convertUriToByteArray(photoURI));

            addJugador.setFoto(photoURI);

        }
    }

    public ConfigPartida crearConfigPartida(SQLiteDatabase bd) {

        ConfigPartida cp = this.configPartida;
        this.configPartida = cp.insertar(bd, cp.getNivelPruebas(), cp.getNivelResultadoPruebas(), cp.getTipoPartida().getTipoPartidaId(), cp.getRolesJugador());

        ConfigTipoPrueba configTipoPrueba = new ConfigTipoPrueba();
        List<ConfigTipoPrueba> configTipoPruebas = new ArrayList<>();
        configTipoPrueba.setConfigPartidaId(this.configPartida.getConfigPartidaId());
        for (TipoPrueba tipoPrueba : this.tipoPruebasPartida) {
            configTipoPrueba.setTipoPruebaId(tipoPrueba.getTipoPruebaId());
            configTipoPruebas.add(configTipoPrueba);
            configTipoPrueba.insertar(bd, configTipoPrueba.getConfigPartidaId(), configTipoPrueba.getTipoPruebaId());
        }

        ConfigTipoResultadoPrueba configTipoResultadoPrueba = new ConfigTipoResultadoPrueba();
        List<ConfigTipoResultadoPrueba> configTipoResultadoPruebas = new ArrayList<>();
        configTipoResultadoPrueba.setConfigPartidaId(cp.getConfigPartidaId());
        for (TipoResultadoPrueba tipoResultadoPrueba : this.tipoResultadosPruebasPartida) {
            configTipoResultadoPrueba.setTipoResultadoPruebaId(tipoResultadoPrueba.getTipoResultadoPruebaId());
            configTipoResultadoPruebas.add(configTipoResultadoPrueba);
            configTipoResultadoPrueba.insertar(bd, configTipoResultadoPrueba.getConfigPartidaId(), configTipoResultadoPrueba.getTipoResultadoPruebaId());
        }
        cp.setConfigTipoPruebas(configTipoPruebas);
        cp.setConfigTipoResultadoPruebas(configTipoResultadoPruebas);

        return cp.findConfigById(bd, this.configPartida.getConfigPartidaId());
    }

    public List<JugadorPartida> crearJugadoresPartida(SQLiteDatabase bd, List<Jugador> jugadores) {

        List<JugadorPartida> jugadorPartidas = new ArrayList<>();
        JugadorPartida jp = new JugadorPartida();
        jp.setPartidaId(this.partida.getPartidaId());

        for (Jugador jugador : jugadores) {
            if (Constantes.YES.equals(jugador.getSeleccionado())) {
                jp.setJugador(jugador);
                jugadorPartidas.add(jp);
                jp = jp.insertar(bd, jp.getPartidaId(), jp.getJugador().getJugadorId(), Constantes.ROL_ID);
            }
        }

        return jugadorPartidas;
    }

    public Partida crearPartida(SQLiteDatabase bd, int configPartidaId, String nombre) {

        Partida p = new Partida();
        p = p.insertar(bd, configPartidaId, nombre);

        return p;

    }

    private List<MundoPartida> crearMundosPartida(SQLiteDatabase bd) {
        Mundo mundo = new Mundo();
        List<Mundo> mundos = mundo.getAll(bd);
        Collections.shuffle(mundos);
        List<MundoPartida> mundoPartidas = new ArrayList<>();

        for (int i = 0; i < Constantes.NUMERO_NIVELES_MUNDO_PANTALLA; i++) {
            for (int j = 0; j < Constantes.NUMERO_MUNDOS_NIVEL; j++) {
                MundoPartida mundoPartida = new MundoPartida();
                mundoPartida.setPartidaId(this.partida.getPartidaId());
                mundoPartida.setFinalizado(Constantes.NO);
                mundoPartida.setMundo(mundos.get(mundoPartidas.size()));
                mundoPartida.setNivelMundo(i);
                mundoPartida.setOrden(j);
                if (mundoPartida.getNivelMundo() == (this.configPartida.getTipoPartida().getNumeroMundos() - 1)) {
                    mundoPartida.setUrlImagen(Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos)));
                } else {
                    mundoPartida.setUrlImagen(Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
                }
                mundoPartida = mundoPartida.insertar(bd, mundoPartida.getMundo().getMundoId(), mundoPartida.getPartidaId(), mundoPartida.getUrlImagen(), mundoPartida.getOrden(), mundoPartida.getNivelMundo(), mundoPartida.getFinalizado());
                mundoPartidas.add(mundoPartida);
            }

        }
        return mundoPartidas;
    }

    private void añadirNivelMundosPartida(SQLiteDatabase bd, int ultimoNivel) throws Exception {
        if (this.configPartida.getTipoPartida().getNumeroMundos() < ultimoNivel) {
            Mundo mundo = new Mundo();
            List<Mundo> mundos = mundo.getAll(bd);
            MundoPartida mundoPartida = new MundoPartida();
            mundoPartida.setPartidaId(this.partida.getPartidaId());
            mundoPartida.setFinalizado(Constantes.NO);
            // Filtramos los mundos que ya se han hecho
            for (MundoPartida mp : this.mundosPartida) {
                if ((mp.getNivelMundo() == (ultimoNivel -1)) || (mp.getNivelMundo() == ultimoNivel)) {
                    mundos.remove(mp.getMundo());
                }
                if (Constantes.YES.equals(mp.getFinalizado())) {
                    mundos.remove(mp.getMundo());
                }
            }
            for (int j = 0; j < Constantes.NUMERO_MUNDOS_NIVEL; j++) {
                if (!mundos.isEmpty()) {
                    mundoPartida.setMundo(mundos.iterator().next());
                    mundoPartida.setNivelMundo(ultimoNivel + 1);
                    mundoPartida.setOrden(j);
                    if (mundoPartida.getNivelMundo() == (this.configPartida.getTipoPartida().getNumeroMundos() - 1)) {
                        mundoPartida.setMundoPartidaId(Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos)));
                    } else {
                        mundoPartida.setMundoPartidaId(Integer.parseInt(Integer.toString(R.mipmap.plantilla_mundos_caminos)));
                    }
                    mundoPartida = mundoPartida.insertar(bd, mundoPartida.getMundo().getMundoId(), mundoPartida.getPartidaId(), mundoPartida.getUrlImagen(), mundoPartida.getOrden(), mundoPartida.getNivelMundo(), mundoPartida.getFinalizado());
                    this.mundosPartida.add(mundoPartida);
                } else {
                    throw new Exception();
                }
            }
        }
    }
    
    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            } else {
                return null;
            }
        } catch (Exception e) {
            Log.e("TAG", "getRealPathFromURI Exception : " + e.toString());
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private byte[] convertUriToByteArray(Uri uri) {
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