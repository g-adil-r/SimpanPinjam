package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ViewModel.TransaksiViewModel;

public class TambahTransaksiActivity extends AppCompatActivity {
    TransaksiViewModel viewModel;
    String anggotaId;
    TextView tvForm;
    EditText etSetoran;
    Button btTambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        anggotaId = i.getStringExtra("anggotaId");
        viewModel = new TransaksiViewModel(this,anggotaId);

        tvForm = findViewById(R.id.tv_form);
        etSetoran = findViewById(R.id.et_setoran);
        btTambah = findViewById(R.id.bt_tambah);

        tvForm.setText(R.string.tambah_transaksi);
        btTambah.setText(R.string.tambah);

        btTambah.setOnClickListener(v -> {
            String setoran = etSetoran.getText().toString();
            viewModel.addTransaksi(anggotaId,Integer.parseInt(setoran));
            if (!setoran.isEmpty()) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}