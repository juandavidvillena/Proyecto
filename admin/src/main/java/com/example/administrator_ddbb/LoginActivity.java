package com.example.administrator_ddbb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView txtRecuperar;
    TextView txtRegistrar;
    Button btnLogin;
    EditText usuario;
    EditText password;
    ImageView logo;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        declararVistas();
        clickListener();

    }

    void declararVistas() {

        logo = findViewById(R.id.logo);
        password = findViewById(R.id.password);
        usuario = findViewById(R.id.usuario);
        btnLogin = findViewById(R.id.btn_login);
        txtRegistrar = findViewById(R.id.register);
        txtRecuperar = findViewById(R.id.recuperar);
        progressBar = findViewById(R.id.progressBar);
    }

    void clickListener() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario == null || password == null) {
                    Toast.makeText(LoginActivity.this, R.string.login_error, Toast.LENGTH_SHORT).show();
                } else {
                    iniciarSesion(usuario.getText().toString().trim(), password.getText().toString().trim());
                }
            }
        });

        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertIntent(getResources().getString(R.string.titAlertResetPass), getResources().getString(R.string.txtAlertResetPass));
            }

        });

    }

    public void AlertIntent(String titulo, String texto) {

        AlertDialog.Builder msg = new AlertDialog.Builder(LoginActivity.this);
        msg.setTitle(titulo);
        msg.setMessage(texto);

        final EditText input = new EditText(this);
        msg.setView(input);
        msg.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String correo = input.getText().toString().trim();
                input.setPadding(5,5,5,5);
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            executeTimer();
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"No se ha podido efectuar el cambio, intentelo de nuevo",Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }

        });

        msg.show();
        // FORÇA O TECLADO APARECER AO ABRIR O ALERT
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    void executeTimer(){

        final int DURATION_SPLASH = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this,"Has recibido un correo para cambiar tu contraseña",Toast.LENGTH_SHORT).show();
            }
        }, DURATION_SPLASH);

    }

    void iniciarSesion(String usu, String pass) {


        firebaseAuth.signInWithEmailAndPassword(usu, pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, R.string.errorMalIntroducido, Toast.LENGTH_LONG).show();
                    usuario.setBackgroundColor(getResources().getColor(R.color.red));
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
