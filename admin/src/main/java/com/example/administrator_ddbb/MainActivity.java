package com.example.administrator_ddbb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator_ddbb.Interfaces.Plato;
import com.example.administrator_ddbb.adapter.RecyclerAdapterAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Plato> listaPlatos;
    RecyclerAdapterAdmin adapter;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaPlatos = new ArrayList<>();
        cargarDatosBD();
        setUpRecyclerView();
        setStatusBar();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.home);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(MainActivity.this, AddPlatoActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent3 =  new Intent(MainActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent3);

                    mTextMessage.setText(R.string.cerrarsesion);
                    return true;
            }
            return false;
        }
    };

    private void cargarDatosBD(){
        cargarDatosBebidasBD();
        cargarDatosEntrantesBD();
        cargarDatosCarnesBD();
        cargarDatosPescadoBD();
        cargarDatosPostresBD();
    }
    private void cargarDatosBebidasBD() {

        //TODO recoger los datos de la base datos todos con una sola variable y esta pasarsela como campo al contructor para poder asi acceder a ella
        db.collection("Bebidas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio") + " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto"),
                                    document.getId(),
                                    "Bebidas"

                            );
                            listaPlatos.add(plato);
                        }
                        adapter = new RecyclerAdapterAdmin(context, MainActivity.this, listaPlatos);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarDatosEntrantesBD() {

        db.collection("Entrante")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio") + " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto"),
                                    document.getId(),
                                    "Entrante"

                            );
                            listaPlatos.add(plato);
                        }
                        adapter = new RecyclerAdapterAdmin(context, MainActivity.this, listaPlatos);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarDatosCarnesBD() {
        db.collection("Carnes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio") + " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto"),
                                    document.getId(),
                                    "Carnes"

                            );
                            listaPlatos.add(plato);
                        }
                        adapter = new RecyclerAdapterAdmin(context, MainActivity.this, listaPlatos);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarDatosPescadoBD() {

        db.collection("Pescado")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio") + " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto"),
                                    document.getId(),
                                    "Pescado"

                            );
                            listaPlatos.add(plato);
                        }
                        adapter = new RecyclerAdapterAdmin(context, MainActivity.this, listaPlatos);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarDatosPostresBD() {

        db.collection("Postres")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot document : task.getResult()) {
                            Plato plato = new Plato(document.getString("nombre"),
                                    document.getString("precio") + " €",
                                    document.getString("ingredientes"),
                                    document.getString("foto"),
                                    document.getId(),
                                    "Postres"

                            );
                            listaPlatos.add(plato);
                        }
                        adapter = new RecyclerAdapterAdmin(context, MainActivity.this, listaPlatos);
                        recyclerView.setAdapter(adapter);
                        registerForContextMenu(recyclerView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "No se pudo cargar los datos", Toast.LENGTH_LONG).show();
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

    public void onCreateContextMenu(ContextMenu menu, View vista, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vista, menuInfo);
        menu.setHeaderTitle("Opciones");
        getMenuInflater().inflate(R.menu.menucontextual, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borrar:

                db.collection(listaPlatos.get(adapter.getPosicion()).getTipo()).document(listaPlatos.get(adapter.getPosicion()).getUid())
                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Se ha borrado correctamente el articulo",Toast.LENGTH_SHORT).show();
                        listaPlatos.remove(adapter.getPosicion()) ;
                        recyclerView.removeViewAt(adapter.getPosicion());
                        adapter.notifyItemRemoved(adapter.getPosicion());
                        adapter.notifyItemRangeChanged(adapter.getPosicion(),listaPlatos.size());
                        adapter.notifyDataSetChanged();

                        //finish();
                        //startActivity(getIntent());

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Ha ocurrido un fallo, intentelo más tarde",Toast.LENGTH_SHORT).show();
                    }
                });


                return super.onContextItemSelected(item);

            case R.id.id:
                Plato plato;
                plato = listaPlatos.get(adapter.getPosicion());
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos", plato);
                listaPlatos.get(RecyclerAdapterAdmin.getPosicion());
                Intent intent = new Intent(MainActivity.this, EditarPlato.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
                Toast.makeText(getApplicationContext(), listaPlatos.get(RecyclerAdapterAdmin.getPosicion()).getUid(), Toast.LENGTH_SHORT).show();

            default:
                return super.onContextItemSelected(item);
        }


    }

    public void buscarDB(final String uid) {
        final int cont= 0;
        String tipo = "";
        switch (cont) {
            case 0:
                tipo ="Entrante";
                break;
            case 1:
                tipo = "Postres";
                break;
            case 2:
                tipo = "Carnes";
                break;
            case 3:
                tipo = "Pescado";
                break;
            case 4:
                tipo = "Bebidas";
                break;
        }

        final String finalTipo = tipo;
        db.collection(tipo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (uid == document.getId()) {
                            db.collection(finalTipo).document(uid).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Se ha borrado correctamente",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

