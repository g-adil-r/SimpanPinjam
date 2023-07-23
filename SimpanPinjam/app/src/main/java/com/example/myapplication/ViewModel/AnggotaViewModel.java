package com.example.myapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Anggota;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnggotaViewModel {
    private Context context;
    private FirebaseRecyclerOptions<Anggota> anggotaOptions;
    private DatabaseReference anggotaRef;
    private FirebaseRecyclerOptions<Anggota> options;

    public AnggotaViewModel(Context context) {
        this.context = context;
        anggotaRef = FirebaseDatabase.getInstance().getReference().child("anggota");
    }

    // untuk recyclerview
    public FirebaseRecyclerOptions<Anggota> getAnggotaOptions() {
        if (options == null) {
            Query dataQuery = anggotaRef.orderByChild("nama");
            options = new FirebaseRecyclerOptions.Builder<Anggota>()
                    .setQuery(dataQuery, Anggota.class)
                    .build();
        }
        return options;
    }

    public void addAnggota(String nama, String alamatBidak) {
        if (nama.isEmpty() || alamatBidak.isEmpty()) {
            Toast.makeText(this.context, "Harap isi data", Toast.LENGTH_SHORT).show();
            return;
        }

        String anggotaId = anggotaRef.push().getKey();
        Anggota newAnggota = new Anggota(anggotaId, nama, alamatBidak);

        anggotaRef.child(anggotaId).setValue(newAnggota)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this.context, "Berhasil menambahkan anggota baru", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this.context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
