package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Transaksi;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaksiAdapter extends FirebaseRecyclerAdapter<Transaksi, TransaksiAdapter.TransaksiViewHolder> {
    public TransaksiAdapter(@NonNull FirebaseRecyclerOptions<Transaksi> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position, @NonNull Transaksi model) {
        Date date = new Date(model.getTanggal());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.tvTanggal.setText(dateFormat.format(date));
        holder.tvSetoran.setText(String.valueOf(model.getSetoran()));
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiViewHolder(view);
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvSetoran;
        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvSetoran = itemView.findViewById(R.id.tv_setoran);
        }
    }
}
