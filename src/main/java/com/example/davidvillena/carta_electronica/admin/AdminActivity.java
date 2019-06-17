package com.example.davidvillena.carta_electronica.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.example.davidvillena.carta_electronica.LoginActivity;
import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.adapter.RecyclerAdapterAdmin;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class AdminActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Plato> listPlats;
    RecyclerAdapterAdmin adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listPlats = new ArrayList<>();

        setUpRecyclerView();

        cargarDatosBD("Entrantes");
        cargarDatosBD("Postres");
        cargarDatosBD("Carnes");
        cargarDatosBD("Pescado");
        cargarDatosBD("Bebidas");


    }


    public void onCreateContextMenu(ContextMenu menu, View vista, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vista, menuInfo);
        menu.setHeaderTitle("Opciones");
        getMenuInflater().inflate(R.menu.contextual_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borrar:

                Toast.makeText(getApplicationContext(), "Se ha borrado correctamente" , Toast.LENGTH_SHORT).show();


                listPlats.remove(RecyclerAdapterAdmin.getPosicion()) ;
                adapter.notifyDataSetChanged() ;

                return super.onContextItemSelected(item);

            default:
                return super.onContextItemSelected(item);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.btn_logout:
                logOut();
                return true;
            case R.id.btn_anadir:
                irAnadirPlato();
                return true;
            default:
                return false;
        }


    }
    private void logOut() {
        irInicioSesion();
    }
    private void irInicioSesion() {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    private void irAnadirPlato(){
        Intent intent = new Intent(AdminActivity.this,AddPlatoAdmin.class);
        startActivity(intent);
    }

    private void cargarDatosBD(String tipo){
        db.collection(tipo)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(
                                    document.getString("nombre"),
                                    document.getString("precio")+ " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto")
                            );
                            listPlats.add(plato);

                        }
                        adapter = new RecyclerAdapterAdmin( AdminActivity.this , listPlats);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminActivity.this,"No se pudo cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
