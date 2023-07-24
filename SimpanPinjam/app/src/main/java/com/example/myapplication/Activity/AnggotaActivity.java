package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.TransaksiAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;
import com.example.myapplication.ViewModel.TransaksiViewModel;

public class AnggotaActivity extends AppCompatActivity {
    TextView tvNama, tvBidak, tvTotal;
    RecyclerView rvTransaksi;
    Button btTambahTransaksi, btEditAnggota;
    AnggotaViewModel anggotaViewModel;
    TransaksiViewModel transaksiViewModel;
    TransaksiAdapter transaksiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);
        String anggotaId = getIntent().getStringExtra("anggotaId");

        tvNama = findViewById(R.id.tv_nama);
        tvBidak = findViewById(R.id.tv_alamat_bidak);
        tvTotal = findViewById(R.id.tv_total);
        rvTransaksi = findViewById(R.id.rv_transaksi);
        btTambahTransaksi = findViewById(R.id.bt_tambah_transaksi);
        btEditAnggota = findViewById(R.id.bt_edit_anggota);

        transaksiViewModel = new TransaksiViewModel(getApplicationContext(), anggotaId);
        transaksiAdapter = new TransaksiAdapter(transaksiViewModel.getTransaksiOptions(anggotaId));

        anggotaViewModel = new AnggotaViewModel(getApplicationContext());
        transaksiViewModel = new TransaksiViewModel(getApplicationContext(), anggotaId);

        anggotaViewModel.getAnggotaFromId(anggotaId)
                .observe(this, anggota -> {
                    tvNama.setText(anggota.getNama());
                    tvBidak.setText(anggota.getAlamatBidak());
                });
        transaksiViewModel.getTotalSetoran(anggotaId)
                .observe(this, integer -> {
                    tvTotal.setText("Total rekening: Rp"+integer);
                });

        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setAdapter(transaksiAdapter);
        rvTransaksi.setItemAnimator(null);

        btTambahTransaksi.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahTransaksiActivity.class);
            intent.putExtra("anggotaId", anggotaId);
            startActivity(intent);
        });

        btEditAnggota.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditAnggotaActivity.class);
            intent.putExtra("anggotaId", anggotaId);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        transaksiAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        transaksiAdapter.stopListening();
    }
}