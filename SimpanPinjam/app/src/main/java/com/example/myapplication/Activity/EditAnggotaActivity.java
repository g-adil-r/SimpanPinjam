package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Model.Anggota;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;

public class EditAnggotaActivity extends AppCompatActivity {
    EditText etNama, etBidak;
    Button btTambah;
    AnggotaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_form);
        String anggotaId = getIntent().getStringExtra("anggotaId");

        etNama = findViewById(R.id.et_nama);
        etBidak = findViewById(R.id.et_alamat_bidak);
        btTambah = findViewById(R.id.bt_tambah);

        viewModel = new AnggotaViewModel(getApplicationContext());

        viewModel.getAnggotaFromId(anggotaId)
                .observe(this, anggota -> {
                    etNama.setText(anggota.getNama());
                    etBidak.setText(anggota.getAlamatBidak());
                });

        btTambah.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String alamatBidak = etBidak.getText().toString();
            viewModel.editAnggota(anggotaId, nama, alamatBidak);
            finish();
        });
    }
}