package com.example.davidvillena.carta_electronica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.davidvillena.carta_electronica.main.BebidaActivity;
import com.example.davidvillena.carta_electronica.main.CarneActivity;
import com.example.davidvillena.carta_electronica.main.EntrantesActivity;
import com.example.davidvillena.carta_electronica.main.GaleriaActivity;
import com.example.davidvillena.carta_electronica.main.PescadoActivity;
import com.example.davidvillena.carta_electronica.main.PostresActivity;
import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {
    public ImageView imgLogo;
    public Button btnEntrante, btnCarne, btnPescado, btnPostre, btnBebida, btnAdmin, btnGaleria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Esconder Toolbar
        getSupportActionBar().hide();

        setStatusBar();
        //Botones
        btnAdmin = findViewById(R.id.btn_admin);
        btnBebida = findViewById(R.id.btn_bebidas);
        btnCarne = findViewById(R.id.btn_carnes);
        btnEntrante = findViewById(R.id.btn_entrantes);
        btnGaleria = findViewById(R.id.btn_galeria);
        btnPescado = findViewById(R.id.btn_pescado);
        btnPostre  = findViewById(R.id.btn_postres);
        //IMG
        imgLogo = findViewById(R.id.logo);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               enlacePantallaGenerico(LoginActivity.class);
            }
        });

        btnBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(BebidaActivity.class);
            }
        });

        btnPostre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(PostresActivity.class);
            }
        });

        btnPescado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(PescadoActivity.class);
            }
        });
        btnCarne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(CarneActivity.class);
            }
        });

        btnEntrante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(EntrantesActivity.class);
            }
        });

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlacePantallaGenerico(GaleriaActivity.class);
            }
        });
    }
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);;
    }
    private void enlacePantallaGenerico(Class destino){
        Intent intent = new Intent(MainActivity.this , destino);
        startActivity(intent);
    }
}
