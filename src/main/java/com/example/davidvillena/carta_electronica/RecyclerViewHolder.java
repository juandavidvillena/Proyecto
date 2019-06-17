package com.example.davidvillena.carta_electronica;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView nombre, ingredientes, precio;
    public ImageView foto;

    public RecyclerViewHolder(@NonNull View itemView){
        super(itemView);

        nombre = itemView.findViewById(R.id.nombre_plato);
        ingredientes = itemView.findViewById(R.id.ingrediente_plato);
        precio = itemView.findViewById(R.id.precio_plato);
        foto = itemView.findViewById(R.id.foto_plato);
    }
}
