package com.example.davidvillena.carta_electronica.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.adapter.RecyclerAdapterAdmin;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AddPlatoAdmin extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Plato> listaPlatos;
    RecyclerAdapterAdmin adapter;
    RecyclerView recyclerView;
    Plato plato;
    ArrayList<String> opciones;
    String seleccion;
    TextView anadirNombre, anadirIngrediente, anadirPrecio, anadirFoto;
    EditText Nombre, Ingrediente, Precio, Foto;
    Button enviar;
    Spinner select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        select = findViewById(R.id.spinner);

        //EditView
        Nombre = findViewById(R.id.anadir_entrante_nombre);
        Ingrediente = findViewById(R.id.anadir_entrante_ingrediente);
        Precio = findViewById(R.id.anadir_entrante_precio);
        Foto = findViewById(R.id.anadir_entrante_foto);
        //TextView
        anadirFoto = findViewById(R.id.anadir_foto);
        anadirPrecio = findViewById(R.id.anadir_precio);
        anadirIngrediente = findViewById(R.id.anadir_ingrediente);
        anadirNombre = findViewById(R.id.anadir_nombre);
        enviar = findViewById(R.id.enviar_plato);

        opciones = new ArrayList<String>();

        opciones.add("Entrante");
        opciones.add("Postres");
        opciones.add("Carnes");
        opciones.add("Pescado");
        opciones.add("Bebidas");

        ArrayAdapter adp = new ArrayAdapter(AddPlatoAdmin.this, R.layout.support_simple_spinner_dropdown_item, opciones);
        select.setAdapter(adp);

        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String elemento = (String) select.getAdapter().getItem(position);
                seleccion = elemento;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       enviar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               crearPlato();
               enlacePantallaGenerico(AdminActivity.class);
           }
       });

    }

    public void comprobarDatos(){

        if(Nombre == null || Precio == null || Ingrediente == null || Foto == null){
            Toast.makeText(AddPlatoAdmin.this, R.string.error_anadir, Toast.LENGTH_LONG).show();
        }else{
            db.collection(seleccion)
                    .add(plato);
        }
    }
    public void crearPlato(){
        plato = new Plato(Nombre.getText().toString().trim(),
                Precio.getText().toString().trim(),
                Ingrediente.getText().toString().trim(),
                Foto.getText().toString().trim()
        );

        comprobarDatos();

    }
    private void enlacePantallaGenerico(Class destino){
        Intent intent = new Intent(AddPlatoAdmin.this , destino);
        startActivity(intent);
    }
}
