package com.julio.carwashtogo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.julio.carwashtogo.common.Constantes;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText edt_correo,edt_password;

    private Button btnLogin,btnLoginWithFacebook,btnLoginWithTwitter,btnLoginWithGoogle;
    private String rol = "";
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(Constantes.USER_CHILD);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.tb_login);
        setSupportActionBar(toolbar);

        toolbar.setTitle(getString(R.string.login));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edt_correo = findViewById(R.id.edt_correoR);
        edt_password = findViewById(R.id.edt_passwordR);

        btnLogin = findViewById(R.id.btnLogin);
        btnLoginWithFacebook = findViewById(R.id.btnLoginWithFacebook);
        btnLoginWithTwitter = findViewById(R.id.btnLoginWithTwitter);
        btnLoginWithGoogle = findViewById(R.id.btnLoginWithGoogle);


        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = edt_correo.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                login(correo,password);

            }
        });

    }

    private void login(String c,String p){
        mAuth.signInWithEmailAndPassword(c,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser usuarioActual = mAuth.getCurrentUser();
                    if (usuarioActual != null){
                        updateUI(usuarioActual);
                    }
                }
            }
        });
    }

    private void updateUI(FirebaseUser usuarioActual) {
        databaseReference.child(Constantes.USER_CHILD).child(usuarioActual.getUid()).child("rol").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rol = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "ocurrio un error "+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Intent irPrincipal = new Intent(getApplicationContext(),PrincipalActivity.class);
        irPrincipal.putExtra(Constantes.ROL_USER,rol);
        startActivity(irPrincipal);
        finish();
    }
}
