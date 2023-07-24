package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TransaksiViewModel;

public class TambahTransaksiActivity extends AppCompatActivity {
    TransaksiViewModel viewModel;
    String anggotaId;
    EditText etSetoran;
    Button btTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi);
        Intent i = getIntent();
        anggotaId = i.getStringExtra("anggotaId");
        viewModel = new TransaksiViewModel(this,anggotaId);

        etSetoran = findViewById(R.id.et_setoran);
        btTambah = findViewById(R.id.bt_tambah);

        btTambah.setOnClickListener(v -> {
            viewModel.addTransaksi(anggotaId,Integer.parseInt(etSetoran.getText().toString()));
            finish();
        });
    }
}