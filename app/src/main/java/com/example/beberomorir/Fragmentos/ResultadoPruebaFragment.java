package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.ResultadoPruebaPartida;
import com.example.beberomorir.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadoPruebaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoPruebaFragment extends AppCompatDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Activity actividad;
    IComunicaPartida iComunicaPartida;
    TextView descripcionResultado;
    ImageView imagenResultado, imagenEntidadResultado;

    //Datos
    ResultadoPruebaPartida resultadoPruebaPartida;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultadoPruebaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultadoPruebaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultadoPruebaFragment newInstance(String param1, String param2) {
        ResultadoPruebaFragment fragment = new ResultadoPruebaFragment();
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
        View view = inflater.inflate(R.layout.fragment_resultado_prueba, container, false);
        this.imagenResultado = view.findViewById(R.id.resultadoPartidaImage);
        this.descripcionResultado = view.findViewById(R.id.resultadoPartidaText);
        this.imagenEntidadResultado = view.findViewById(R.id.entidadResultadoPartidaImage);
        this.imagenEntidadResultado.setImageResource(this.resultadoPruebaPartida.getResultadoPrueba().getUrlImagenEntidad());
        this.imagenResultado.setImageResource(this.resultadoPruebaPartida.getResultadoPrueba().getUrlImagen());
        this.descripcionResultado.setText(this.resultadoPruebaPartida.getDescripcion());

        return view;
    }

    public void setResultadoPruebaPartida(ResultadoPruebaPartida resultadoPruebaPartida) {
        this.resultadoPruebaPartida = resultadoPruebaPartida;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        iComunicaPartida.nextPlayer();
    }
}