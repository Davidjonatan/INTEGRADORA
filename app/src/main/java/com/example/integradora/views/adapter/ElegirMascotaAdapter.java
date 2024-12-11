package com.example.integradora.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradora.R;
import com.example.integradora.views.models.Mascota;
import com.example.integradora.views.views.CrearComedero;
import com.example.integradora.views.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ElegirMascotaAdapter extends RecyclerView.Adapter<ElegirMascotaAdapter.ElegirMascotaViewHolder> {
    private List<Mascota> mascotaList =  new ArrayList<>();
    private Context context;

    public ElegirMascotaAdapter(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList, Context context) {
        this.mascotaList = mascotaList;
        this.context = context;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ElegirMascotaAdapter.ElegirMascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elegir_mascota, parent, false);
        return new ElegirMascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElegirMascotaAdapter.ElegirMascotaViewHolder holder, int position) {
        {
            Mascota mascota = mascotaList.get(position);
            holder.tvId.setText("ID de la mascota: " + String.valueOf(mascota.getId()));
            holder.tvName.setText("Nombre de la Mascota: "+ mascota.getNombre());
            holder.tvAnimal.setText("Tipo de animal: " + mascota.getAnimal().toUpperCase());

            holder.btnSeleccionar.setOnClickListener(v -> {
               Intent intent = new Intent(context, CrearComedero.class);
                intent.putExtra("mascota", mascota);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (mascotaList != null) ? mascotaList.size() : 0;
    }

    public class ElegirMascotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvAnimal;
        Button btnSeleccionar;
        public ElegirMascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvAnimal = itemView.findViewById(R.id.tvAnimal);
            btnSeleccionar = itemView.findViewById(R.id.btnSeleccionar);

        }

    }
}
