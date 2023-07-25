package com.example.myapplication.ViewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Model.Anggota;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AnggotaViewModel {
    private Context context;
    private DatabaseReference anggotaRef;
    private FirebaseRecyclerOptions<Anggota> options;

    public AnggotaViewModel(Context context) {
        this.context = context;
        anggotaRef = FirebaseDatabase.getInstance().getReference().child("anggota");
    }

    // untuk recyclerview
    public FirebaseRecyclerOptions<Anggota> getAnggotaAdapterOptions() {
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

    public void editAnggota(String anggotaId, String nama, String alamatBidak) {
        if (nama.isEmpty() || alamatBidak.isEmpty()) {
            Toast.makeText(this.context, "Harap isi data", Toast.LENGTH_SHORT).show();
            return;
        }

        Anggota newAnggota = new Anggota(anggotaId, nama, alamatBidak);

        anggotaRef.child(anggotaId).setValue(newAnggota)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this.context, "Berhasil mengedit anggota baru", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this.context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void deleteAnggota(String anggotaId) {
        anggotaRef.child(anggotaId).removeValue().addOnSuccessListener(unused -> {
            Toast.makeText(context, "Berhasil menghapus anggota", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public LiveData<Anggota> getAnggotaFromId(String anggotaId) {
        MutableLiveData<Anggota> anggotaLiveData = new MutableLiveData<>();

        anggotaRef.child(anggotaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                anggotaLiveData.setValue(snapshot.getValue(Anggota.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return anggotaLiveData;
    }
}
