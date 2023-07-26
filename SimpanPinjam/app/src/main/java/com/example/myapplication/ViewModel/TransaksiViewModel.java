package com.example.myapplication.ViewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Model.Transaksi;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class TransaksiViewModel {
    private DatabaseReference transaksiRef;
    private Context context;

    public TransaksiViewModel(Context context, String anggotaId) {
        this.transaksiRef = FirebaseDatabase.getInstance().getReference()
                .child("transaksi")
                .child(anggotaId);
        this.context = context;
    }

    public TransaksiViewModel(Context context) {
        this.transaksiRef = FirebaseDatabase.getInstance().getReference()
                .child("transaksi");
        this.context = context;
    }

    // untuk recyclerview transaksi per anggota
    public FirebaseRecyclerOptions<Transaksi> getTransaksiAdapterOptions(String anggotaId) {
        Query dataQuery = transaksiRef.orderByChild("tanggal");
        FirebaseRecyclerOptions<Transaksi> options = new FirebaseRecyclerOptions.Builder<Transaksi>()
                .setQuery(dataQuery, Transaksi.class)
                .build();
        return options;
    }

    public void addTransaksi(String anggotaId, int setoran) {
        if (setoran == 0) {
            Toast.makeText(this.context, "Harap isi data", Toast.LENGTH_SHORT).show();
            return;
        }
        long currentDate = (new Date()).getTime();
        String transaksiId = transaksiRef.push().getKey();

        Transaksi transaksi = new Transaksi(transaksiId,currentDate,setoran);
        transaksiRef.child(transaksiId).setValue(transaksi)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this.context, "Berhasil menambahkan setoran", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this.context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void editTransaksi(String transaksiId, int setoran) {
        if (setoran == 0) {
            Toast.makeText(this.context, "Harap isi data", Toast.LENGTH_SHORT).show();
            return;
        }

        transaksiRef.child(transaksiId).get().addOnSuccessListener(dataSnapshot -> {
            Transaksi oldData = dataSnapshot.getValue(Transaksi.class);

            long currentDate = oldData.getTanggal();
            Transaksi newData = new Transaksi(transaksiId,currentDate,setoran);

            transaksiRef.child(transaksiId).setValue(newData).addOnSuccessListener(unused -> {
                Toast.makeText(context, "Berhasil mengedit transaksi", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void deleteTransaksi(String transaksiId) {
        transaksiRef.child(transaksiId).removeValue().addOnSuccessListener(unused -> {
            Toast.makeText(context, "Berhasil menghapus transaksi", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public LiveData<Transaksi> getTransaksiFromId(String transaksiId) {
        MutableLiveData<Transaksi> transaksiLiveData = new MutableLiveData<>();
        transaksiRef.child(transaksiId).get().addOnSuccessListener(dataSnapshot -> {
            transaksiLiveData.setValue(dataSnapshot.getValue(Transaksi.class));
        }).addOnFailureListener(e -> {
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
        return transaksiLiveData;
    }

    public LiveData<Integer> getTotalSetoran() {
        MutableLiveData<Integer> totalLiveData = new MutableLiveData<>();
        transaksiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Transaksi transaksi = ds.getValue(Transaksi.class);
                    total += transaksi.getSetoran();
                }
                totalLiveData.setValue(total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return totalLiveData;
    }

    public LiveData<Integer> getTotalSetoran(String anggotaId) {
        MutableLiveData<Integer> totalLiveData = new MutableLiveData<>();
        transaksiRef.child(anggotaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Transaksi transaksi = ds.getValue(Transaksi.class);
                    total += transaksi.getSetoran();
                }
                totalLiveData.setValue(total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return totalLiveData;
    }
}
