package com.example.davidvillena.carta_electronica;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.davidvillena.carta_electronica.admin.AddPlatoAdmin;
import com.example.davidvillena.carta_electronica.admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    //Instancias
    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDb;
    //Vistas
    private ImageView imgLogo;
    private EditText usr,psswd;
    private Button btnLogin,/**btnRegister,**/ btnCarta;
    private String usuario, cnts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbAuth = FirebaseAuth.getInstance();

        usr = findViewById(R.id.usuario);
        psswd = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        //btnRegister = findViewById(R.id.btn_register);
        btnCarta = findViewById(R.id.btn_carta);
        imgLogo = findViewById(R.id.imageView);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert_titulo);
        builder.setMessage(R.string.alert_descripcion);
        builder.setPositiveButton(R.string.alert_confirmar, null);
        builder.create();
        builder.show();

        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(final View v) {
                                             usuario = usr.getText().toString().trim();
                                             cnts = psswd.getText().toString().trim();
                                             iniciarSesion();
                                        }
                                    });

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                enlacePantallaGenerico(RegisterActivity.class);
            }
        });
        btnCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(MainActivity.class);
            }
        });
    }

    public void iniciarSesion(){
        // Asegura no poner más de 15 caracteres
        // psswd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                if (usuario.isEmpty() || cnts.isEmpty()){
                    Toast.makeText(this,R.string.login_error_vacio,Toast.LENGTH_LONG).show();
                }else{
                    fbAuth.signInWithEmailAndPassword(usuario,cnts).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Log.d("fallo-auth","No son correctas la contraseña y/o usuario");
                            }else{
                                enlacePantallaGenerico(AdminActivity.class);
                            }

                            }
                    });
                }
            }
    private void enlacePantallaGenerico(Class destino){
        Intent intent = new Intent(LoginActivity.this , destino);
        startActivity(intent);
    }
        }