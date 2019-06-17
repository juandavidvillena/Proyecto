package com.example.davidvillena.carta_electronica;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtEmail,nombre,apellido,tlfno,alias,txtPsswrd,confirmPass;
    private Button btnDatos;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.register_email);
        txtPsswrd =  findViewById(R.id.register_contrasena);
        confirmPass =  findViewById(R.id.register_confirmar);
        btnDatos = findViewById(R.id.register_btn_send);

        progressDialog = new ProgressDialog(this);
        btnDatos.setOnClickListener(this);
    }
    private void registrarUsuario() {
        String email = txtEmail.getText().toString().trim();
        String password = txtPsswrd.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password) && password == confirm) {
            Toast.makeText(this, "Error en la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Estamos creando tu cuenta porfavor espere unos segundos...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Se ha registrado el usuario con el email: " + txtEmail.getText(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "No se pudo crear la cuenta vuelva a intentarlo", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        //Invocamos al método:
        registrarUsuario();
    }
}