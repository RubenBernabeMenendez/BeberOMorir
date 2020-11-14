package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NuevoJugadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NuevoJugadorFragment extends AppCompatDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Activity actividad;
    IComunicaPartida iComunicaPartida;
    CardView imagenAnadirJugadorCard, confirmarAnadirJugadorCard;
    ImageView imagenJugador;
    EditText nombreForm, apodoForm;
    byte[] urlImage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NuevoJugadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevoJugadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NuevoJugadorFragment newInstance(String param1, String param2) {
        NuevoJugadorFragment fragment = new NuevoJugadorFragment();
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

        View view = inflater.inflate(R.layout.fragment_nuevo_jugador, container, false);
        imagenAnadirJugadorCard = view.findViewById(R.id.imagenAnadirJugadorCard);
        imagenAnadirJugadorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hacer foto
                iComunicaPartida.nuevaImagen();
            }
        });

        imagenJugador = view.findViewById(R.id.imagenAnadirJugadorButton);
        nombreForm = view.findViewById(R.id.editTextNombre);
        apodoForm = view.findViewById(R.id.editTextApodo);

        confirmarAnadirJugadorCard = view.findViewById(R.id.confirmarAnadirJugadorCard);
        confirmarAnadirJugadorCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Guardar jugador
                if (!vacio(nombreForm)) {
                    iComunicaPartida.anadirJugadorToBD(nombreForm.getText().toString(), apodoForm.getText().toString(), urlImage);
                }
            }
        });

        return view;
    }

    public void setFoto(Uri url) {
        imagenJugador.setImageURI(url);
    }

    public void setUrlImage (byte[] urlImage){
        this.urlImage = urlImage;
    }

    public  boolean vacio(EditText campo){
        String dato = campo.getText().toString().trim();
        if(TextUtils.isEmpty(dato)){
            campo.setError("Campo obligatorio");
            campo.requestFocus();
            return true;
        }
        else{
            return false;
        }
    }
}