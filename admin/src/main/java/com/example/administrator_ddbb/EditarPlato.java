package com.example.administrator_ddbb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator_ddbb.Interfaces.Plato;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditarPlato extends AppCompatActivity implements Serializable {

    TextView  Ingrediente;
    EditText Nombre, Precio;
    Button enviar, cancelar;
    Spinner select;
    String tipo, uid, imagen;
    List<String> opciones;
    ArrayAdapter<String> adapterSpinner;
    ImageView preview;
    EditarPlato editarPlato;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plato);
        Plato plato = (Plato) getIntent().getSerializableExtra("datos");
        Nombre = findViewById(R.id.anadir_entrante_nombre);
        Ingrediente = findViewById(R.id.anadir_entrante_ingrediente);
        Precio = findViewById(R.id.anadir_entrante_precio);
        cancelar = findViewById(R.id.cancelar);
        preview = findViewById(R.id.preview);
        select = findViewById(R.id.spinner);
        enviar = findViewById(R.id.enviar_plato);
        select.setVisibility(View.INVISIBLE);
        uid = plato.getUid();
        tipo = plato.getTipo();
        imagen = plato.getFoto();
        final Uri uri = Uri.parse(plato.getFoto());
        Picasso.with(getApplicationContext())
                .load(uri)
                .placeholder(R.drawable.plato)
                .into(preview);

        Ingrediente.setText(plato.getIngredientes());
        Nombre.setText(plato.getNombre());
        Precio.setText(plato.getPrecio());
        Log.e("dato", Precio.getText().toString());
        enviar.setText("Editar plato");
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarPlato.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plato plato1 = new Plato();
                String precio = Precio.getText().toString().trim();
                plato1.setIngredientes(Ingrediente.getText().toString().trim());
                plato1.setNombre(Nombre.getText().toString().trim());
                plato1.setPrecio(precio.substring(0 ,precio.length() -2 ));
                plato1.setFoto(imagen);
                plato1.setTipo(tipo);
                plato1.setUid(uid);
                pushDatos(plato1);
            }
        });

    }
    public void pushDatos(Plato plato){

        db.collection(plato.getTipo()).document(plato.getUid()).set(plato);
        Intent intent = new Intent(EditarPlato.this, MainActivity.class);
        startActivity(intent);
finish();


    }
}
