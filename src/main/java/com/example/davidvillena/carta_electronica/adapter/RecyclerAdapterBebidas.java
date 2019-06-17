package com.example.davidvillena.carta_electronica.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidvillena.carta_electronica.Plato;
import com.example.davidvillena.carta_electronica.R;
import com.example.davidvillena.carta_electronica.main.BebidaActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterBebidas extends RecyclerView.Adapter<RecyclerAdapterBebidas.RecyclerViewHolder> {
private static int pos;
Context context;
ArrayList<Plato> listaPlatos;
BebidaActivity bebidaActivity;



public RecyclerAdapterBebidas(BebidaActivity bebidaActivity, ArrayList<Plato> listaPlatos){

    this.bebidaActivity = bebidaActivity;
    this.listaPlatos = listaPlatos;

}
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater =  LayoutInflater.from(bebidaActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);

    return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.nombre.setText(listaPlatos.get(i).getNombre());
        recyclerViewHolder.precio.setText(listaPlatos.get(i).getPrecio());
        recyclerViewHolder.ingredientes.setText(listaPlatos.get(i).getIngredientes());
        Picasso.with(this.context)
                .load(listaPlatos.get(i).getFoto())
                .placeholder(R.drawable.bebida)
                .into(recyclerViewHolder.foto);
    }
    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, ingredientes, precio;
        public ImageView foto;

        private RecyclerViewHolder(@NonNull View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre_plato);
            ingredientes = itemView.findViewById(R.id.ingrediente_plato);
            precio = itemView.findViewById(R.id.precio_plato);
            foto = itemView.findViewById(R.id.foto_plato);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    pos = getAdapterPosition();
                    return false;
                }
            });
        }
    }

}
