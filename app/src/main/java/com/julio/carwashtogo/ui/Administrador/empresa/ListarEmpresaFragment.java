package com.julio.carwashtogo.ui.Administrador.empresa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.julio.carwashtogo.R;
import com.julio.carwashtogo.adaptadores.EmpresaRecyclerViewAdapter;
import com.julio.carwashtogo.common.Constantes;
import com.julio.carwashtogo.listeners.EmpresaOnItemClickListener;
import com.julio.carwashtogo.model.Empresa;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarEmpresaFragment extends Fragment {


    private boolean isTwoPane=false;
    private List<Empresa>empresaList = new ArrayList<>();
    public ListarEmpresaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_empresa, container, false);
        if (view.findViewById(R.id.detalle_empresa)!= null){
            isTwoPane = true;
        }
        RecyclerView recyclerView =view.findViewById(R.id.rb_empresas_list);

        setRecyclerView(recyclerView);
        return view;
    }

    private void setRecyclerView(RecyclerView recyclerView){
        recyclerView.setAdapter(new EmpresaRecyclerViewAdapter(empresaList, new EmpresaOnItemClickListener() {
            @Override
            public void OnClick(Empresa empresa) {
                if (isTwoPane){
                    Bundle arguments = new Bundle();
                    arguments.putString(Constantes.UID_EMPRESA,empresa.getUid());
                    EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                    editarEmpresaFragment.setArguments(arguments);

                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.detalle_empresa,editarEmpresaFragment)
                            .commit();
                }else {
                    Bundle argumets = new Bundle();
                    argumets.putString(Constantes.UID_EMPRESA,empresa.getUid());
                    EditarEmpresaFragment editarEmpresaFragment = new EditarEmpresaFragment();
                    editarEmpresaFragment.setArguments(argumets);
                    View view = getView();
                    assert view != null;
                    Navigation.findNavController(view).navigate(R.id.nav_editarEmpresaFragment);
                }
            }
        }));
    }
}
