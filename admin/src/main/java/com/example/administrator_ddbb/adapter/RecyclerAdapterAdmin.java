package com.example.administrator_ddbb.adapter;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator_ddbb.Interfaces.Plato;
import com.example.administrator_ddbb.MainActivity;
import com.example.administrator_ddbb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapterAdmin extends RecyclerView.Adapter<RecyclerAdapterAdmin.RecyclerViewHolder> {
    private Context context;
    private MainActivity mainActivity;
    private ArrayList<Plato> listaPlatos;
    private static int posicion;


    public RecyclerAdapterAdmin(Context context, MainActivity mainActivity, ArrayList<Plato> listaPlatos) {
        this.context = context;
        this.mainActivity = mainActivity;
        this.listaPlatos = listaPlatos;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        recyclerViewHolder.nombre.setText(listaPlatos.get(i).getNombre());
        recyclerViewHolder.precio.setText(listaPlatos.get(i).getPrecio());
        recyclerViewHolder.ingredientes.setText(listaPlatos.get(i).getIngredientes());
        Uri uri = Uri.parse(listaPlatos.get(i).getFoto());
        Picasso.with(mainActivity.getBaseContext())
                .load(uri)
                .placeholder(R.drawable.plato)
                .into(recyclerViewHolder.foto);
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
