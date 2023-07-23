package com.example.myapplication.ViewModel;

import com.example.myapplication.Model.Anggota;
import com.example.myapplication.Model.Transaksi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TransaksiViewModel {
    private DatabaseReference transaksiRef;

    public TransaksiViewModel() {
        this.transaksiRef = FirebaseDatabase.getInstance().getReference().child("transaksi");
    }

    public void addTransaksi(String anggotaId, int setoran) {

    }

    // untuk recyclerview
    public FirebaseRecyclerOptions<Transaksi> getTransaksiOptions(String anggotaId) {
        Query dataQuery = transaksiRef.child(anggotaId).orderByChild("tanggal");
        FirebaseRecyclerOptions<Transaksi> options = new FirebaseRecyclerOptions.Builder<Transaksi>()
                    .setQuery(dataQuery, Transaksi.class)
                    .build();
        return options;
    }
}
