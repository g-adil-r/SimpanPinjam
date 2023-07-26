package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;

public class TambahAnggotaActivity extends AppCompatActivity {
    TextView tvForm;
    EditText etNama, etBidak;
    Button btTambah;
    AnggotaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_form);

        tvForm = findViewById(R.id.tv_form);
        etNama = findViewById(R.id.et_nama);
        etBidak = findViewById(R.id.et_alamat_bidak);
        btTambah = findViewById(R.id.bt_tambah);

        tvForm.setText(R.string.tambah_anggota);
        btTambah.setText(R.string.tambah);

        viewModel = new AnggotaViewModel(getApplicationContext());

        btTambah.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String alamatBidak = etBidak.getText().toString();
            viewModel.addAnggota(nama, alamatBidak);
            if (!(nama.isEmpty() || alamatBidak.isEmpty())) {
                finish();
            }
        });
    }
}