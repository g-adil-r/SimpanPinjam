package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Model.Anggota;
import com.example.myapplication.R;
import com.example.myapplication.ViewModel.AnggotaViewModel;

public class EditAnggotaActivity extends AppCompatActivity {
    TextView tvForm;
    EditText etNama, etBidak;
    Button btEdit;
    AnggotaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anggota_form);
        String anggotaId = getIntent().getStringExtra("anggotaId");

        tvForm = findViewById(R.id.tv_form);
        etNama = findViewById(R.id.et_nama);
        etBidak = findViewById(R.id.et_alamat_bidak);
        btEdit = findViewById(R.id.bt_tambah);

        tvForm.setText(R.string.edit_anggota);
        btEdit.setText(R.string.edit);

        viewModel = new AnggotaViewModel(getApplicationContext());

        viewModel.getAnggotaFromId(anggotaId)
                .observe(this, anggota -> {
                    etNama.setText(anggota.getNama());
                    etBidak.setText(anggota.getAlamatBidak());
                });

        btEdit.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String alamatBidak = etBidak.getText().toString();
            viewModel.editAnggota(anggotaId, nama, alamatBidak);
            if (!(nama.isEmpty() || alamatBidak.isEmpty())) {
                finish();
            }
        });
    }
}