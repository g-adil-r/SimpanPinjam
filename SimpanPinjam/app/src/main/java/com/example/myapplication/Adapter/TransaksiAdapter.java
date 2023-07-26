package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.myapplication.Activity.EditTransaksiActivity;
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
    Context context;
    String anggotaId;
    public TransaksiAdapter(@NonNull FirebaseRecyclerOptions<Transaksi> options, TransaksiViewModel transaksiViewModel, String anggotaId) {
        super(options);
        this.transaksiViewModel = transaksiViewModel;
        this.anggotaId = anggotaId;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        this.context = recyclerView.getContext();
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

            btEdit.setOnClickListener(v -> {
                Intent i = new Intent(context, EditTransaksiActivity.class);
                i.putExtra("transaksiId",transaksiId);
                i.putExtra("anggotaId",anggotaId);
                context.startActivity(i);
            });

            btHapus.setOnClickListener(v -> {
                showHapusDialog(transaksiId);
            });
        }

        public void showHapusDialog(String transaksiId) {
            String pesan = context.getString(R.string.pesan_hapus_transaksi);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            dialogBuilder.setMessage(pesan)
                    .setPositiveButton("Batal", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    }).setNegativeButton("Hapus", (dialogInterface, i) -> {
                        TransitionManager.beginDelayedTransition(recyclerView, new AutoTransition());
                        transaksiViewModel.deleteTransaksi(transaksiId);
                        dialogInterface.dismiss();
                    }).setCancelable(true);

            AlertDialog dialog = dialogBuilder.create();

            dialog.setOnShowListener(dialogInterface -> {
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
            });
            dialog.show();
        }
    }
}
