package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Adapter.AnggotaAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SemuaAnggotaActivity extends AppCompatActivity {
    RecyclerView rvAnggota;
    FloatingActionButton btTambah;
    AnggotaAdapter anggotaAdapter;
    AnggotaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_anggota);
        rvAnggota = findViewById(R.id.rv_anggota);
        btTambah = findViewById(R.id.bt_tambah);

        viewModel = new AnggotaViewModel(getApplicationContext());
        anggotaAdapter = new AnggotaAdapter(viewModel.getAnggotaOptions());
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