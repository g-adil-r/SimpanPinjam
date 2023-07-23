package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;

public class NewAnggotaActivity extends AppCompatActivity {
    EditText etNama, etBidak;
    Button btTambah;
    AnggotaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anggota);

        etNama = findViewById(R.id.et_nama);
        etBidak = findViewById(R.id.et_alamat_bidak);
        btTambah = findViewById(R.id.bt_tambah);

        viewModel = new AnggotaViewModel(getApplicationContext());

        btTambah.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String alamatBidak = etBidak.getText().toString();
            viewModel.addAnggota(nama, alamatBidak);
            finish();
        });
    }
}