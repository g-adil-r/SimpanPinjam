package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myapplication.Adapter.AnggotaAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;
import com.example.myapplication.ViewModel.TransaksiViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SemuaAnggotaActivity extends AppCompatActivity {
    RecyclerView rvAnggota;
    ImageButton btTambah;
    AnggotaAdapter anggotaAdapter;
    AnggotaViewModel anggotaViewModel;
    TransaksiViewModel transaksiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semua_anggota);
        rvAnggota = findViewById(R.id.rv_anggota);
        btTambah = findViewById(R.id.bt_tambah);

        anggotaViewModel = new AnggotaViewModel(getApplicationContext());
        transaksiViewModel = new TransaksiViewModel(getApplicationContext());

        anggotaAdapter = new AnggotaAdapter(anggotaViewModel.getAnggotaAdapterOptions(), transaksiViewModel);
        rvAnggota.setLayoutManager(new LinearLayoutManager(this));
        rvAnggota.setAdapter(anggotaAdapter);
        rvAnggota.setItemAnimator(null);

        btTambah.setOnClickListener(view -> {
          startActivity(new Intent(this, TambahAnggotaActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        anggotaAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        anggotaAdapter.stopListening();
    }
}