package com.example.administrator_ddbb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText txtEmail, txtPsswrd, confirmPass;
    private Button btn_send;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        enlaceVistas();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filtro()== true){
                    registrarUsuario();
                }
            }
        });
    }
private void enlaceVistas(){
    txtEmail = findViewById(R.id.editEmail);
    txtPsswrd = findViewById(R.id.editPass);
    confirmPass = findViewById(R.id.editRePassword);
    btn_send = findViewById(R.id.btn_send_register);

    progressDialog = new ProgressDialog(this);
}
    Boolean filtro(){
        if (txtPsswrd.getText().toString().equals("")  && confirmPass.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this,"Rellene los campos",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!confirmPass.getText().toString().trim().equals( txtPsswrd.getText().toString().trim())){
            Toast.makeText(RegisterActivity.this,"Las contraseñas no son las mismas",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void registrarUsuario() {
        firebaseAuth = FirebaseAuth.getInstance();

        String email = txtEmail.getText().toString().trim();
        String password = txtPsswrd.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
        }

        if (TextUtils.isEmpty(password) && password == confirm) {
            Toast.makeText(this, "Error en la contraseña", Toast.LENGTH_LONG).show();
        }
        progressDialog.setMessage("Estamos creando tu cuenta porfavor espere unos segundos...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Se ha registrado el usuario con el email: " + txtEmail.getText(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "No se pudo crear la cuenta vuelva a intentarlo", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

       executeTimer();

    }
    void executeTimer(){

        final int DURATION_SPLASH = 5000;


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURATION_SPLASH);
    }
}
