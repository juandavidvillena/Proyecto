package com.example.davidvillena.carta_electronica.main;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.adapter.RecyclerAdapterBebidas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class BebidaActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Plato> listaPlatos;
    RecyclerAdapterBebidas adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebida);
        listaPlatos = new ArrayList<>();
        setUpRecyclerView();
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);
        ctl.setTitle("Bebidas");
        ctl.setCollapsedTitleTextColor(getResources().getColor(R.color.trans));
        setStatusBar();
        cargarDatosBD();



    }
    private void cargarDatosBD(){
        db.collection("Bebidas")
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
                            adapter = new RecyclerAdapterBebidas(BebidaActivity.this, listaPlatos);
                            recyclerView.setAdapter(adapter);
                            registerForContextMenu(recyclerView);
                        }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BebidaActivity.this,"No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }
}
