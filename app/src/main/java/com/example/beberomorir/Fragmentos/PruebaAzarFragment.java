package com.example.beberomorir.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.beberomorir.Constantes;
import com.example.beberomorir.Interfaces.IComunicaPartida;
import com.example.beberomorir.Modelos.Jugador;
import com.example.beberomorir.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PruebaAzarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PruebaAzarFragment extends Fragment implements Animation.AnimationListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences sharedPreferences;
    Activity actividad;
    IComunicaPartida iComunicaPartida;
    CardView girarRuleta;
    ImageView ruleta;
    ConstraintLayout ruletaConstraintLayout;
    public float grados;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PruebaAzarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PruebaAzarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PruebaAzarFragment newInstance(String param1, String param2) {
        PruebaAzarFragment fragment = new PruebaAzarFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prueba_azar, container, false);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.actividad);
        this.girarRuleta = view.findViewById(R.id.girarRuletaCard);
        this.girarRuleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                girarRuleta();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                iComunicaPartida.nextPlayer();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        this.ruleta = view.findViewById(R.id.ruletaImagen);
        this.ruletaConstraintLayout = view.findViewById(R.id.constraintLayoutRuleta);

        return view;
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        Double result = Constantes.NUM_OPCIONES_RULETA - Math.floor((this.grados / (Constantes.GRADOS / Constantes.NUM_OPCIONES_RULETA)));
        System.out.println(result);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void girarRuleta() {
        int ran = new Random().nextInt(360) + 3600;
        RotateAnimation rotateAnimation = new RotateAnimation(this.grados, this.grados + ran, 1,0.5f,1,0.5f);
        this.grados = (this.grados + ran) %  (float) Constantes.GRADOS;
        rotateAnimation.setDuration(ran);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(this);
        this.ruletaConstraintLayout.setAnimation(rotateAnimation);
        this.ruletaConstraintLayout.startAnimation(rotateAnimation);
        this.girarRuleta.setVisibility(View.INVISIBLE);
    }
}