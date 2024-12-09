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
import com.example.integradora.views.models.Comedero;
import com.example.integradora.views.views.DetalleComedero;

import java.util.List;

public class ComederoAdapter extends RecyclerView.Adapter<ComederoAdapter.ComederoViewHolder>{
    private List<Comedero> comederoList;
    private Context context;

    public void setComederoList(List<Comedero> comederoList, Context context) {
        this.comederoList = comederoList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComederoAdapter.ComederoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comedero, parent, false);
        return new ComederoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComederoAdapter.ComederoViewHolder holder, int position) {
        Comedero comedero = comederoList.get(position);
        holder.tvId.setText("Comedero ID: " + String.valueOf(comedero.getId()));
        holder.tvEstado.setText("Estado: " + comedero.getEstado());
        holder.tvMascotaNombre.setText("Mascota: " + comedero.getMascota().getNombre());

        holder.btnVerDetalles.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleComedero.class);
            intent.putExtra("comederoId", comedero.getId());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return comederoList == null ? 0 : comederoList.size();
    }

    public class ComederoViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvMascotaNombre, tvEstado;
        Button btnVerDetalles;
        public ComederoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvMascotaNombre = itemView.findViewById(R.id.tvMascotaNombre);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
        }
    }
}
