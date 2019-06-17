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
import com.example.davidvillena.carta_electronica.main.EntrantesActivity;

import java.util.ArrayList;

public class RecyclerAdapterEntrantes extends RecyclerView.Adapter<RecyclerAdapterEntrantes.RecyclerViewHolder> {
    private static int pos;

    ArrayList<Plato> listaPlatos;
    EntrantesActivity entrantesActivity;


    public RecyclerAdapterEntrantes(ArrayList<Plato> listaPlatos, EntrantesActivity entrantesActivity) {
        this.listaPlatos = listaPlatos;
        this.entrantesActivity = entrantesActivity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater =  LayoutInflater.from(entrantesActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.nombre.setText(listaPlatos.get(i).getNombre());
        recyclerViewHolder.precio.setText(listaPlatos.get(i).getPrecio());
        recyclerViewHolder.ingredientes.setText(listaPlatos.get(i).getIngredientes());
        //recyclerViewHolder.foto.setImageResource(listaPlatos.get(i).getFoto());
        //String fotop = listaPlatos.get(i).getFoto();
        //final Bitmap decodedByte = convertBase64ToBitmap(fotop);
        recyclerViewHolder.foto.setImageResource(R.drawable.entrante);
    }
    @Override
    public int getItemCount() {
        return listaPlatos.size();
    }
    public static int getPos(){
        return pos;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, ingredientes, precio;
        public ImageView foto;

        public RecyclerViewHolder(@NonNull View itemView){
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