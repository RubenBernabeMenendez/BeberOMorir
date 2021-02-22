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
import com.example.beberomorir.Fragmentos.MenuRondaJugadorFragment;
import com.example.beberomorir.Fragmentos.NuevoJugadorFragment;
import com.example.beberomorir.Fragmentos.PruebaAzarFragment;
import com.example.beberomorir.Fragmentos.TableroFragment;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.MainActivity;
import com.example.beberomorir.Modelos.ConfigPartida;
import com.example.beberomorir.Modelos.ConfigTipoPrueba;
import com.example.beberomorir.Modelos.ConfigTipoResultadoPrueba;
import com.example.beberomorir.Modelos.EntidadResultadoPrueba;
import com.example.beberomorir.Modelos.EntidadResultadoTipoPrueba;
import com.example.beberomorir.Modelos.EstadoResultadoTipoPrueba;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.Modelos.JugadorPartida;
import com.example.beberomorir.Modelos.Mundo;
import com.example.beberomorir.Modelos.MundoPartida;
import com.example.beberomorir.Modelos.MundoPartidaTipoPrueba;
import com.example.beberomorir.Modelos.Partida;
import com.example.beberomorir.Modelos.Prueba;
import com.example.beberomorir.Modelos.PruebaJugador;
import com.example.beberomorir.Modelos.PruebaPartida;
import com.example.beberomorir.Modelos.PruebaResultadoRelaci;
import com.example.beberomorir.Modelos.ResultadoPrueba;
import com.example.beberomorir.Modelos.ResultadoPruebaPartida;
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
import java.util.stream.Collectors;

public class PartidaActivity extends AppCompatActivity implements IComunicaPartida {


    TableroFragment fragmentTablero;
    Fragment fragmentConfigPartida;
    ElegirJugadoresFragment fragmentElegirJugadores;
    NuevoJugadorFragment addJugador;
    PruebaAzarFragment fragmentPruebaAzar;
    MenuRondaJugadorFragment menuRondaJugadorFragment;



    //Datos
    private Uri photoURI;
    private List<TipoPrueba> tipoPruebasPartida;
    private List<TipoResultadoPrueba> tipoResultadosPruebasPartida;
    private ConfigPartida configPartida;
    private List<JugadorPartida> jugadoresPartida;
    private Partida partida;
    private List<MundoPartida> mundosPartida;
    private List<MundoPartidaTipoPrueba> mundoPartidaTipoPruebas;
    private List<PruebaPartida> pruebaPartidas;
    private List<PruebaJugador> pruebaJugadors;
    private List<ResultadoPruebaPartida> resultadoPruebaPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        fragmentConfigPartida = new ConfigPartidaFragment();
        fragmentElegirJugadores = new ElegirJugadoresFragment();
        fragmentTablero = new TableroFragment();
        fragmentPruebaAzar = new PruebaAzarFragment();
        menuRondaJugadorFragment = new MenuRondaJugadorFragment();
        menuRondaJugadorFragment.setCancelable(false);
        this.pruebaJugadors = new ArrayList<>();
        this.pruebaPartidas = new ArrayList<>();
        this.resultadoPruebaPartidas = new ArrayList<>();
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
    public void nextPlayer() {
        fragmentTablero.nextJugadorPartida();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
    }

    @Override
    public void verPruebaAzar() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentPruebaAzar).commit();
    }

    @Override
    public void menuRondaJugador(JugadorPartida jugadorPartida, MundoPartida mundoPartida) {
        // Usamos MundoPartida para obtener lista de MundoPartidaTipoPruebas para dibujar el menu de elegir el tipo de prueba
        // Usamos JugadorPartida y MundoPartida para obtener lista de PruebasJugador y así saber cuáles quedan por elegir

        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        MundoPartidaTipoPrueba mundoPartidaTipoPrueba = new MundoPartidaTipoPrueba();
        List<MundoPartidaTipoPrueba> mundoPartidaTipoPruebas = mundoPartidaTipoPrueba.findById(bd, mundoPartida.getMundoPartidaId());

        PruebaJugador pruebaJugador = new PruebaJugador();
        List<PruebaJugador> pruebaJugadors = pruebaJugador.findByJugadorPartidaId(bd, jugadorPartida.getJugadorPartidaId());
        PruebaPartida pruebaPartida = new PruebaPartida();
        List<PruebaPartida> pruebaPartidas = pruebaPartida.findByMundoPartida(bd, mundoPartida.getMundoPartidaId());
        List<PruebaJugador> pruebaJugadorsPorMundo = pruebaJugadors.stream().filter(pj -> pruebaPartidas.stream().map(PruebaPartida::getPruebaPartidaId).collect(Collectors.toList()).contains(pj.getPruebaPartidaId().getPruebaPartidaId())).collect(Collectors.toList());
        menuRondaJugadorFragment.setJugadorPartida(jugadorPartida);
        menuRondaJugadorFragment.setPruebaJugadors(pruebaJugadorsPorMundo);
        menuRondaJugadorFragment.show(getSupportFragmentManager(), "Menu ronda");
    }

    @Override
    public void empezarPrueba(PruebaJugador pruebaJugador) {
        menuRondaJugadorFragment.onStop();
        menuRondaJugadorFragment.onDestroy();
        menuRondaJugadorFragment.dismiss();
        switch (pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId()) {
            case Constantes.TIPO_PRUEBA_SENALAR:
                System.out.println("Ey");
                break;
            case Constantes.TIPO_PRUEBA_AZAR:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentPruebaAzar).commit();
                break;
            default:

        }
    }

    @Override
    public void empezarPartida(List<Jugador> jugadores){
        AdminSQLDataBase admin = new AdminSQLDataBase(this);
        SQLiteDatabase bd = admin.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + jugador.getSeleccionado());
        }

        this.configPartida = crearConfigPartida(bd);
        this.partida = crearPartida(bd, this.configPartida.getConfigPartidaId(), sdf.format(new Date()));
        this.jugadoresPartida = crearJugadoresPartida(bd, jugadores);
        fragmentTablero.setJugadoresPartida(this.jugadoresPartida);

        //Cada vez que se suba de mundo
        this.mundosPartida = crearMundosPartida(bd, 0, Constantes.NUMERO_NIVELES_MUNDO_PANTALLA);
        this.mundoPartidaTipoPruebas = crearMundosPartidaTipoPruebas(bd, this.mundosPartida, this.configPartida);

        // Crear las PruebaPartida y ResultadoPruebaPartida del nivel seleccionado o menor en configuracionPartida
        // a partir de los jugadoresPartida y los mundoPartida editaremos las pruebas y los resultados
        // para cada tipo de prueba crearemos los resultados de la entidadResultadoTipoPrueba
        // A partir de las PruebaPartida y JugadoresPartida crearemos las PruebaJugador
        // numPruebasPartida = numJugadoresPartida * mundoPartidaTipoPrueba.numero

        crearPruebasPartida(bd, this.mundoPartidaTipoPruebas);

        crearResultadosPartida(bd);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPartida, fragmentTablero).commit();
        fragmentTablero.setMundoPartidas(this.mundosPartida);
    }

    private void crearResultadosPartida(SQLiteDatabase bd) {
        for (PruebaJugador pruebaJugador : this.pruebaJugadors) {
            EntidadResultadoTipoPrueba entidadResultadoTipoPrueba = new EntidadResultadoTipoPrueba();
            List<EntidadResultadoTipoPrueba> entidadResultadoTipoPruebas = entidadResultadoTipoPrueba.findByTipoPruebaId(bd, pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId());
            List<ResultadoPrueba> resultadoPruebas = new ArrayList<>();
            for (EntidadResultadoTipoPrueba ertp: entidadResultadoTipoPruebas) {
                ResultadoPrueba resultadoPrueba = new ResultadoPrueba();
                resultadoPruebas.addAll(resultadoPrueba.findByEntidadResultadoPruebaIdAndNivelMenor(bd, ertp.getEntidadResultadoPruebaId(), configPartida.getNivelResultadoPruebas()));
            }
            // Filtramos por los tipoResultados definidos en la configuración
            resultadoPruebas = resultadoPruebas.stream().filter(el -> this.configPartida.getConfigTipoResultadoPruebas().stream().map(ConfigTipoResultadoPrueba::getTipoResultadoPruebaId).collect(Collectors.toList()).contains(el.getTipoResultadoPrueba().getTipoResultadoPruebaId())).collect(Collectors.toList());
            // Filtramos por los estadoResultadoTipoPrueba
            EstadoResultadoTipoPrueba estadoResultadoTipoPrueba = new EstadoResultadoTipoPrueba();
            List<EstadoResultadoTipoPrueba> estadoResultadoTipoPruebas = estadoResultadoTipoPrueba.findByTipoPruebaId(bd, pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId());
            resultadoPruebas = resultadoPruebas.stream().filter(el -> estadoResultadoTipoPruebas.stream().map(EstadoResultadoTipoPrueba::getEstadoResultadoPruebaId).collect(Collectors.toList()).contains(el.getEstadoResultadoPrueba().getEstadoResultadoPruebaId())).collect(Collectors.toList());
            Collections.shuffle(resultadoPruebas);
            ResultadoPruebaPartida resultadoPruebaPartida = new ResultadoPruebaPartida();
            PruebaResultadoRelaci pruebaResultadoRelaci = new PruebaResultadoRelaci();
            if (pruebaJugador.getPruebaPartidaId().getPrueba().getTipoPrueba().getTipoPruebaId().equals(Constantes.TIPO_PRUEBA_AZAR)) {
                for (int j=0; j < 6; j++) {
                    // Crear resultadoPruebaPartida y PruebaResultadoRelaci
                    resultadoPruebaPartida = resultadoPruebaPartida.insertar(bd, resultadoPruebas.get(j).getResultadoPruebaId(), pruebaJugador.getPruebaPartidaId().getMundoPartidaId(), resultadoPruebas.get(j).getNombre(), resultadoPruebas.get(j).getDescripcion());
                    this.resultadoPruebaPartidas.add(resultadoPruebaPartida);
                    pruebaResultadoRelaci.insertar(bd, resultadoPruebaPartida.getResultadoPruebaPartidaId(), pruebaJugador.getPruebaPartidaId().getPruebaPartidaId());
                }
            } else {
                for (EstadoResultadoTipoPrueba ertp: estadoResultadoTipoPruebas) {
                    List<ResultadoPrueba> resultadoPruebasAux = resultadoPruebas.stream().filter(el -> el.getEstadoResultadoPrueba().getEstadoResultadoPruebaId().equals(ertp.getEstadoResultadoPruebaId())).collect(Collectors.toList());
                    Collections.shuffle(resultadoPruebasAux);
                    // Crear resultadoPruebaPartida y PruebaResultadoRelaci
                    resultadoPruebaPartida = resultadoPruebaPartida.insertar(bd, resultadoPruebasAux.get(0).getResultadoPruebaId(), pruebaJugador.getPruebaPartidaId().getMundoPartidaId(), resultadoPruebasAux.get(0).getNombre(), resultadoPruebasAux.get(0).getDescripcion());
                    this.resultadoPruebaPartidas.add(resultadoPruebaPartida);
                    pruebaResultadoRelaci.insertar(bd, resultadoPruebaPartida.getResultadoPruebaPartidaId(), pruebaJugador.getPruebaPartidaId().getPruebaPartidaId());
                }
            }
        }
    }

    private void crearPruebasPartida(SQLiteDatabase bd, List<MundoPartidaTipoPrueba> mundoPartidaTipoPruebas) {
        Prueba prueba = new Prueba();
        List<Prueba> pruebas = prueba.getAll(bd);
        pruebas = pruebas.stream().filter(prueba1 -> prueba1.getNivelPrueba() <= this.configPartida.getNivelPruebas()).collect(Collectors.toList());
        pruebas = pruebas.stream().filter(prueba1 -> this.configPartida.getConfigTipoPruebas().stream().map(ConfigTipoPrueba::getTipoPruebaId).collect(Collectors.toList()).contains(prueba1.getTipoPrueba().getTipoPruebaId())).collect(Collectors.toList());
        for (JugadorPartida jugadorPartida : this.jugadoresPartida) {
            for (MundoPartidaTipoPrueba mundoPartidaTipoPrueba: mundoPartidaTipoPruebas) {
                for (int j=0; j< mundoPartidaTipoPrueba.getNumeroTiposPrueba(); j++) {
                    PruebaPartida pruebaPartida = new PruebaPartida();
                    PruebaJugador pruebaJugador = new PruebaJugador();
                    List<Prueba> pruebasAux = pruebas.stream().filter(p -> mundoPartidaTipoPrueba.getTipoPruebaId().equals(p.getTipoPrueba().getTipoPruebaId())).collect(Collectors.toList());
                    Collections.shuffle(pruebasAux);
                    // Replace @Jugador y @Mundo
                    String nombreEditado = pruebasAux.get(0).getNombre();
                    String descripcionEditada = pruebasAux.get(0).getDescripcion();
                    // crear PruebaPartida y PruebaJugador
                    pruebaPartida = pruebaPartida.insertar(bd, pruebasAux.get(0).getPruebaId(), mundoPartidaTipoPrueba.getMundoPartidaId(), nombreEditado, descripcionEditada, Constantes.NO);
                    this.pruebaPartidas.add(pruebaPartida);
                    pruebaJugador = pruebaJugador.insertar(bd, pruebaPartida.getPruebaPartidaId(), null, jugadorPartida.getJugadorPartidaId(), null);
                    this.pruebaJugadors.add(pruebaJugador);
                }
            }
        }
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
        configTipoResultadoPrueba.setConfigPartidaId(this.configPartida.getConfigPartidaId());
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

        for (Jugador jugador : jugadores) {
            JugadorPartida jp = new JugadorPartida();
            jp.setPartidaId(this.partida.getPartidaId());
            jp.setJugador(jugador);
            jp = jp.insertar(bd, jp.getJugador().getJugadorId(), jp.getPartidaId(), Constantes.ROL_ID);
            jugadorPartidas.add(jp);
        }

        return jugadorPartidas;
    }

    public Partida crearPartida(SQLiteDatabase bd, int configPartidaId, String nombre) {

        Partida p = new Partida();
        p = p.insertar(bd, configPartidaId, nombre);

        return p;

    }

    private List<MundoPartida> crearMundosPartida(SQLiteDatabase bd, Integer nivel, Integer numeroNiveles) {
        Mundo mundo = new Mundo();
        List<Mundo> mundos = mundo.getAll(bd);
        Collections.shuffle(mundos);
        List<MundoPartida> mundoPartidas = new ArrayList<>();

        for (int i = nivel; i < numeroNiveles - nivel; i++) {
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

    private List<MundoPartidaTipoPrueba> crearMundosPartidaTipoPruebas(SQLiteDatabase bd, List<MundoPartida> mundoPartidas, ConfigPartida configPartida) {
        List<MundoPartidaTipoPrueba> mundoPartidaTipoPruebas = new ArrayList<>();
        int numPruebasPorTipoPruebaRepe = configPartida.getTipoPartida().getNumeroPruebasMundo() / configPartida.getConfigTipoPruebas().size();
        int numPruebasPorTipoPruebaRepeMod = configPartida.getTipoPartida().getNumeroPruebasMundo() / configPartida.getConfigTipoPruebas().size();
        Collections.shuffle(configPartida.getConfigTipoPruebas());
        int numTipoPruebas = 0;
        for (MundoPartida mp: mundoPartidas) {
            for (ConfigTipoPrueba configTipoPrueba: configPartida.getConfigTipoPruebas()) {
                MundoPartidaTipoPrueba mundoPartidaTipoPrueba = new MundoPartidaTipoPrueba();
                numTipoPruebas = 1;
                if (Constantes.TIPO_PRUEBAS_REPETIBLES.contains(configTipoPrueba.getTipoPruebaId())) {
                    numTipoPruebas = numTipoPruebas + numPruebasPorTipoPruebaRepe;
                    if (numPruebasPorTipoPruebaRepeMod != 0) {
                        numTipoPruebas +=1;
                        numPruebasPorTipoPruebaRepeMod -=1;
                    }
                }
                // insertar
                mundoPartidaTipoPrueba.insertar(bd, mp.getMundoPartidaId(), configTipoPrueba.getTipoPruebaId(), numTipoPruebas);
            }
            MundoPartidaTipoPrueba mundoPartidaTipoPrueba = new MundoPartidaTipoPrueba();
            mundoPartidaTipoPruebas.addAll(mundoPartidaTipoPrueba.findById(bd, mp.getMundoPartidaId()));
        }

        return mundoPartidaTipoPruebas;
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
            byte[] buf = new byte[512];
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