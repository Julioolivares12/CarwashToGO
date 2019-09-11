package com.julio.carwashtogo.ui.Administrador.empresa;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.julio.carwashtogo.R;
import com.julio.carwashtogo.common.Constantes;
import com.julio.carwashtogo.common.UploadFotos;
import com.julio.carwashtogo.model.Empresa;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarEmpresaFragment extends Fragment {


   private EditText edtNombreEmpresaActualizar,edtEncargadoActualizar,edtUbicacionActualizar,edtTelefonoActualiar;
    private Spinner spnNivelActualizar;
    private Button btnActualizarEmpresaLogo,btnActualizarEmpresa;
    private Uri url_imagen_seleccionada;
    private String nivel;
    private static final int REQUEST_GALERIA_LOGO=11;
    private String Uid;

    Empresa empresa;

   private Bundle datos;

    //firebase
    //----------------------------------------
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference refEmpresas = firebaseDatabase.getReference(Constantes.REF_EMPRESA);
    private StorageReference storageReference;
    //-----------------------------------------
    public EditarEmpresaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_empresa, container, false);
        edtNombreEmpresaActualizar= view.findViewById(R.id.edtNombreEmpresaActualizar);
        edtEncargadoActualizar = view.findViewById(R.id.edtEncargadoActualizar);
        edtUbicacionActualizar = view.findViewById(R.id.edtUbicacionActualizar);
        edtTelefonoActualiar = view.findViewById(R.id.edtTelefonoActualizar);

        spnNivelActualizar = view.findViewById(R.id.spnNivelActualizar);

        UploadFotos.fillSpinner(getContext(),Constantes.niveles,spnNivelActualizar);
        btnActualizarEmpresaLogo = view.findViewById(R.id.btnActualizarEmpresalogo);
        btnActualizarEmpresa = view.findViewById(R.id.btnActualizarEmpresa);

        storageReference = FirebaseStorage.getInstance().getReference();
        datos = getArguments();
        if (datos != null){
            edtNombreEmpresaActualizar.setText(datos.getString(Constantes.NOMBRE_EMPRESA));
            edtEncargadoActualizar.setText(datos.getString(Constantes.ENCARGADO_ENPRESA));
            edtUbicacionActualizar.setText(datos.getString(Constantes.UBICACION_EMPRESA));
            edtTelefonoActualiar.setText(datos.getString(Constantes.TELEFONO_EMPRESA));
            Uid = datos.getString(Constantes.UID_EMPRESA);
        }

        spnNivelActualizar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivel = Constantes.niveles[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnActualizarEmpresaLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"selecciona una foto"),REQUEST_GALERIA_LOGO);
            }
        });

        btnActualizarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre= edtNombreEmpresaActualizar.getText().toString().trim();
                String encargado = edtEncargadoActualizar.getText().toString().trim();
                String ubicacion = edtUbicacionActualizar.getText().toString().trim();
                String telefono = edtTelefonoActualiar.getText().toString().trim();

                empresa = new Empresa(nombre,encargado,ubicacion,telefono,nivel,"",Uid);
                actualiarEmpresa(empresa);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALERIA_LOGO){
            if (resultCode == RESULT_OK){
                url_imagen_seleccionada = data.getData();
            }
        }
    }

    private void actualiarEmpresa(Empresa e){
        refEmpresas.child(e.getUid()).setValue(e);
        subirFoto(e);
    }

    private void subirFoto(Empresa em){
        FragmentActivity fragmentActivity = getActivity();
        assert fragmentActivity != null;
       Uri uri = UploadFotos.subirFoto(fragmentActivity,
               storageReference
               ,Constantes.LOGOS_EMPRESAS
               ,Uid
               ,url_imagen_seleccionada);
       em.setUrlImagen(uri.toString());
       refEmpresas.child(Uid).setValue(em);
        View view = getView();
        assert view != null;
        Snackbar.make(view,"Empresa actualizada",Snackbar.LENGTH_SHORT).show();
    }

}
