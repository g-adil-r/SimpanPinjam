package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.AnggotaActivity;
import com.example.myapplication.Helper.CurrencyHelper;
import com.example.myapplication.Model.Anggota;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;
import com.example.myapplication.ViewModel.TransaksiViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AnggotaAdapter extends FirebaseRecyclerAdapter<Anggota, AnggotaAdapter.AnggotaViewHolder> {
    Context context;
    RecyclerView recyclerView;
    TransaksiViewModel transaksiViewModel;
    AnggotaViewModel anggotaViewModel;
    public AnggotaAdapter(@NonNull FirebaseRecyclerOptions<Anggota> options, TransaksiViewModel transaksiViewModel) {
        super(options);
        this.transaksiViewModel = transaksiViewModel;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        this.context = recyclerView.getContext();
        this.anggotaViewModel = new AnggotaViewModel(this.context);
    }

    @Override
    protected void onBindViewHolder(@NonNull AnggotaViewHolder holder, int position, @NonNull Anggota model) {
        holder.tvNama.setText(model.getNama());

        String strAlamat = "Alamat bidak: "+model.getAlamatBidak();
        holder.tvBidak.setText(strAlamat);
        holder.anggotaId = model.getAnggotaId();

        transaksiViewModel.getTotalSetoran(model.getAnggotaId())
                .observe((LifecycleOwner) context,integer -> {
                    holder.tvSetoran.setText(CurrencyHelper.stringFormatIDR(integer));
                });
    }

    @NonNull
    @Override
    public AnggotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_anggota, parent, false);
        return new AnggotaViewHolder(view);
    }

    class AnggotaViewHolder extends RecyclerView.ViewHolder {
        Context context;
        CardView itemCard;
        TextView tvNama, tvBidak, tvSetoran;
        Button btHapus;
        String anggotaId;

        public AnggotaViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            itemCard = itemView.findViewById(R.id.cardview);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvBidak = itemView.findViewById(R.id.tv_alamat_bidak);
            tvSetoran = itemView.findViewById(R.id.tv_setoran);
            btHapus = itemView.findViewById(R.id.bt_hapus);

            itemCard.setOnClickListener(v -> {
                Intent i = new Intent(context, AnggotaActivity.class);
                i.putExtra("anggotaId", anggotaId);
                context.startActivity(i);
            });

            btHapus.setOnClickListener(v -> {
                showHapusDialog(anggotaId, tvNama.getText().toString());
            });
        }
    }

    public void showHapusDialog(String anggotaId, String nama) {
        String pesan = context.getString(R.string.pesan_hapus_anggota, nama);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(Html.fromHtml(pesan, Html.FROM_HTML_MODE_LEGACY))
                .setPositiveButton("Batal", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                }).setNegativeButton("Hapus", (dialogInterface, i) -> {
                    TransitionManager.beginDelayedTransition(recyclerView);
                    anggotaViewModel.deleteAnggota(anggotaId);
                    dialogInterface.dismiss();
                }).setCancelable(true);

        AlertDialog dialog = dialogBuilder.create();

        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.RED);
        });
        dialog.show();
    }
}
