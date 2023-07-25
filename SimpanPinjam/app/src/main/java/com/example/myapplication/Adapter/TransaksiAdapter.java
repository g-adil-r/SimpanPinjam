package com.example.myapplication.Adapter;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Helper.CurrencyHelper;
import com.example.myapplication.Model.Transaksi;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TransaksiViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransaksiAdapter extends FirebaseRecyclerAdapter<Transaksi, TransaksiAdapter.TransaksiViewHolder> {
    TransaksiViewModel transaksiViewModel;
    RecyclerView recyclerView;
    public TransaksiAdapter(@NonNull FirebaseRecyclerOptions<Transaksi> options, TransaksiViewModel transaksiViewModel) {
        super(options);
        this.transaksiViewModel = transaksiViewModel;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onBindViewHolder(@NonNull TransaksiViewHolder holder, int position, @NonNull Transaksi model) {
        Date date = new Date(model.getTanggal());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        holder.tvTanggal.setText(dateFormat.format(date));
        holder.tvSetoran.setText(CurrencyHelper.stringFormatIDR(model.getSetoran()));
        holder.transaksiId = model.getTransaksiId();
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaksi, parent, false);
        return new TransaksiViewHolder(view);
    }

    class TransaksiViewHolder extends RecyclerView.ViewHolder {
        String transaksiId;
        TextView tvTanggal, tvSetoran;
        Button btEdit, btHapus;
        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvSetoran = itemView.findViewById(R.id.tv_setoran);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btHapus = itemView.findViewById(R.id.bt_hapus);

            btHapus.setOnClickListener(v -> {
                TransitionManager.beginDelayedTransition(recyclerView, new AutoTransition());
                transaksiViewModel.deleteTransaksi(transaksiId);
            });
        }
    }
}
