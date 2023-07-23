package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Anggota;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AnggotaAdapter extends FirebaseRecyclerAdapter<Anggota, AnggotaAdapter.AnggotaViewHolder> {
    public AnggotaAdapter(@NonNull FirebaseRecyclerOptions<Anggota> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position, @NonNull Anggota model) {
        holder.tvNama.setText(model.getNama());
        holder.tvBidak.setText(model.getAlamatBidak());
        holder.anggotaId = model.getAnggotaId();
    }

    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anggota, parent, false);
        return new AnggotaViewHolder(view);
    }

    class AnggotaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvBidak;
        String anggotaId;

        public AnggotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvBidak = itemView.findViewById(R.id.tv_alamat_bidak);
        }
    }
}
