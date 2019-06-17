package com.example.davidvillena.carta_electronica.adapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.admin.AdminActivity;

import java.util.ArrayList;

public class RecyclerAdapterAdmin extends RecyclerView.Adapter<RecyclerAdapterAdmin.RecyclerViewHolder> {

    private AdminActivity adminActivity;
    private ArrayList<Plato> listaPlatos;
    private static int posicion;


    public RecyclerAdapterAdmin(AdminActivity adminActivity, ArrayList<Plato> listaPlatos) {
        this.adminActivity = adminActivity;
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(adminActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        recyclerViewHolder.nombre.setText(listaPlatos.get(i).getNombre());
        recyclerViewHolder.precio.setText(listaPlatos.get(i).getPrecio());
        recyclerViewHolder.ingredientes.setText(listaPlatos.get(i).getIngredientes());
        recyclerViewHolder.foto.setImageResource(R.drawable.plato);
    }

    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }
    public static int getPosicion(){
        return posicion;
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView nombre, ingredientes, precio;
        public ImageView foto;

        private RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_plato);
            ingredientes = itemView.findViewById(R.id.ingrediente_plato);
            precio = itemView.findViewById(R.id.precio_plato);
            foto = itemView.findViewById(R.id.foto_plato);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View vista) {

                    posicion = getAdapterPosition();
                    return false;
                }
            });
        }
    }
}
