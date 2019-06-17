package com.example.davidvillena.carta_electronica.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.adapter.RecyclerAdapterAdmin;
import com.example.davidvillena.carta_electronica.adapter.RecyclerAdapterCarne;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class CarneActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Plato> listaPlatos;
    RecyclerAdapterCarne adapter;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carne);
        setUpRecyclerView();
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
        ctl.setTitle("Carnes");
        ctl.setCollapsedTitleTextColor(getResources().getColor(R.color.trans));
        setStatusBar();
        listaPlatos = new ArrayList<>();
        cargarDatosBD();



    }
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);;
    }
    private void cargarDatosBD(){
        db.collection("Carnes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio")+ " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto")
                            );
                            listaPlatos.add(plato);

                        }
                        adapter = new RecyclerAdapterCarne(context, listaPlatos, CarneActivity.this);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CarneActivity.this,"No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}