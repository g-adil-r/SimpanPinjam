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

public class EditTransaksiActivity extends AppCompatActivity {
    TransaksiViewModel viewModel;
    String anggotaId, transaksiId;
    TextView tvForm;
    EditText etSetoran;
    Button btEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        anggotaId = i.getStringExtra("anggotaId");
        transaksiId = i.getStringExtra("transaksiId");
        viewModel = new TransaksiViewModel(this,anggotaId);

        etSetoran = findViewById(R.id.et_setoran);
        btEdit = findViewById(R.id.bt_tambah);
        tvForm = findViewById(R.id.tv_form);

        tvForm.setText(R.string.edit_transaksi);
        btEdit.setText(R.string.edit);

        viewModel.getTransaksiFromId(transaksiId)
                .observe(this,transaksi -> {
                    etSetoran.setText(String.valueOf(transaksi.getSetoran()));
                });

        btEdit.setOnClickListener(v -> {
            String setoran = etSetoran.getText().toString();
            viewModel.editTransaksi(transaksiId,Integer.parseInt(setoran));
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