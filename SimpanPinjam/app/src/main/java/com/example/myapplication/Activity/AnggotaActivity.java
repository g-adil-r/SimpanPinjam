package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Adapter.TransaksiAdapter;
import com.example.myapplication.Helper.CurrencyHelper;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;
import com.example.myapplication.ViewModel.TransaksiViewModel;

import java.text.NumberFormat;

public class AnggotaActivity extends AppCompatActivity {
    TextView tvNama, tvBidak, tvTotal;
    RecyclerView rvTransaksi;
    ImageButton btTambahTransaksi;
    Button btEditAnggota;
    AnggotaViewModel anggotaViewModel;
    TransaksiViewModel transaksiViewModel;
    TransaksiAdapter transaksiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String anggotaId = getIntent().getStringExtra("anggotaId");

        tvNama = findViewById(R.id.tv_nama);
        tvBidak = findViewById(R.id.tv_alamat_bidak);
        tvTotal = findViewById(R.id.tv_total);
        rvTransaksi = findViewById(R.id.rv_transaksi);
        btTambahTransaksi = findViewById(R.id.bt_tambah_transaksi);
        btEditAnggota = findViewById(R.id.bt_edit_anggota);

        transaksiViewModel = new TransaksiViewModel(getApplicationContext(), anggotaId);
        transaksiAdapter = new TransaksiAdapter(transaksiViewModel.getTransaksiAdapterOptions(anggotaId), transaksiViewModel, anggotaId);

        anggotaViewModel = new AnggotaViewModel(getApplicationContext());

        anggotaViewModel.getAnggotaFromId(anggotaId)
                .observe(this, anggota -> {
                    tvNama.setText(anggota.getNama());
                    tvBidak.setText(anggota.getAlamatBidak());
                });
        transaksiViewModel.getTotalSetoran()
                .observe(this, integer -> {
                    tvTotal.setText(CurrencyHelper.stringFormatIDR(integer));
                });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        rvTransaksi.setLayoutManager(mLayoutManager);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}