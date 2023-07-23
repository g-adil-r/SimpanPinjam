package com.example.myapplication.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Model.Anggota;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnggotaViewModel extends ViewModel {
    private Context context;
    private DatabaseReference anggotaRef;
//    private DatabaseReference transactionsRef;

    public AnggotaViewModel(Context context) {
        this.context = context.getApplicationContext();
        anggotaRef = FirebaseDatabase.getInstance().getReference("tbl_anggota");
//        transactionsRef = FirebaseDatabase.getInstance().getReference("transactions");
    }

    public void addAnggota(String nama, String alamatBidak) {
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
