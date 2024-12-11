package com.example.integradora.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integradora.R;
import com.example.integradora.views.models.Mascota;

import java.util.ArrayList;
import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {
    private List<Mascota> mascotaList =  new ArrayList<>();
    private Context context;
    private OnMascotaEliminarListener eliminarListener;

    public MascotaAdapter(List<Mascota> mascotaList, OnMascotaEliminarListener eliminarListener) {
        this.mascotaList = mascotaList;
        this.eliminarListener = eliminarListener;

    }

    public interface OnMascotaEliminarListener {
        void onMascotaEliminar(int id);
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
    public MascotaAdapter.MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota, parent, false);
        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaAdapter.MascotaViewHolder holder, int position) {
        Mascota mascota = mascotaList.get(position);
        holder.tvId.setText("ID de la mascota: " + String.valueOf(mascota.getId()));
        holder.tvName.setText("Nombre de la Mascota: "+ mascota.getNombre());
        holder.tvAnimal.setText("Tipo de animal: " + mascota.getAnimal().toUpperCase());

        holder.btnEliminar.setOnClickListener(v -> {
            if (eliminarListener != null) {
                eliminarListener.onMascotaEliminar(mascota.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mascotaList != null) ? mascotaList.size() : 0;
    }

    public class MascotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, tvAnimal;
        Button btnEliminar;
        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvAnimal = itemView.findViewById(R.id.tvAnimal);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
