package com.julio.carwashtogo.ui.Administrador.promocion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.julio.carwashtogo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrearPromocionFragment extends Fragment {


    public CrearPromocionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_promocion, container, false);
    }

}
